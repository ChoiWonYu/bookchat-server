package bc.bookchat.chat.service;

import bc.bookchat.chat.domain.entity.Message;
import bc.bookchat.room.domain.entity.Session;
import bc.bookchat.chat.domain.infra.MessageRepository;
import bc.bookchat.room.domain.entity.Visited;
import bc.bookchat.room.domain.repository.SessionRepository;
import bc.bookchat.chat.presentation.dto.MessageRequestDto;
import bc.bookchat.chat.presentation.dto.MessageResponseDto;
import bc.bookchat.common.exception.CustomException;
import bc.bookchat.common.exception.ErrorCode;
import bc.bookchat.member.entity.Member;
import bc.bookchat.room.domain.entity.Room;
import bc.bookchat.room.domain.repository.RoomRepository;
import bc.bookchat.room.domain.repository.VisitedRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final SessionRepository sessionRepository;
    private final VisitedRepository visitedRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    private static String adminId;

    @Autowired
    public void setAdminId() {
        ChatService.adminId = "관리자";
    }

    @Transactional
    public void enter(MessageRequestDto messageRequestDto, Member member) {
        // IS NULL 존재하는 방인지 확인
        Room room = roomRepository.findByRoomId(messageRequestDto.getRoomId()).orElseThrow(
            () -> new CustomException(ErrorCode.ROOM_NOT_FOUND));

        // session(채팅방 접속 목록) 추가, visited(접속한적 있는 채팅방 목록) 추가
        addUser(room, member.getUserName());
        addVisitedUser(room, member);

        // 채팅방 유저 리스트 가져오기
        ArrayList<String> userList = findListAll(room);

        // 전송
        MessageResponseDto messageResponseDto = MessageResponseDto.toDto(room.getRoomId(),
            member.getUserName(),
            adminId,
            member.getUserName() + "님이 입장하셨습니다.",
            userList
        );
        messagingTemplate.convertAndSend("/sub/chat/rooms/" + messageRequestDto.getRoomId(), messageResponseDto);
    }

    @Transactional
    public void publish(MessageRequestDto messageRequestDto, Member member) {
        // 존재하는 방인지 확인
        Room room = roomRepository.findByRoomId(messageRequestDto.getRoomId()).orElseThrow(
            () -> new CustomException(ErrorCode.ROOM_NOT_FOUND));

        // DB 저장
        Message message = Message.create(messageRequestDto.getMessage());
        messageRepository.save(message);

        // 채팅방 유저 리스트 가져오기
        ArrayList<String> userList = findListAll(room);

        // 전송
        MessageResponseDto messageResponseDto = MessageResponseDto.toDto(room.getRoomId(),
            member.getUserName(),
            messageRequestDto.getSessionId(),
            messageRequestDto.getMessage(),
            userList
        );
        messagingTemplate.convertAndSend("/sub/chat/rooms/" + messageRequestDto.getRoomId(),
            messageResponseDto);
    }

    @Transactional
    public void quit(MessageRequestDto messageRequestDto, Member member) {
        // 존재하는 방인지 확인
        Room room = roomRepository.findByRoomId(messageRequestDto.getRoomId()).orElseThrow(
            () -> new CustomException(ErrorCode.ROOM_NOT_FOUND));

        // 채팅방 접속 목록 제거
        removeUser(room, member.getUserName());

        // 채팅방 유저 리스트 가져오기
        ArrayList<String> userList = findListAll(room);

        // 전송
        MessageResponseDto messageResponseDto = MessageResponseDto.toDto(room.getRoomId(),
            member.getUserName(),
            adminId,
            member.getUserName() + "님이 퇴장하셨습니다.",
            userList
        );
        messagingTemplate.convertAndSend("/sub/chat/rooms/" + messageRequestDto.getRoomId(), messageResponseDto);
    }

    /**
     * SESSION Table 관련 메서드
     */
    public ArrayList<String> findListAll(Room room) {
        List<Session> userList = sessionRepository.findSessionsByRoom(room);
        ArrayList<String> usernameList = new ArrayList<>();
        for (Session s : userList) {
            usernameList.add(s.getUsername());
        }
        return usernameList;
    }
    public void addUser(Room room, String username) {
        sessionRepository.save(Session.create(username, room));
    }
    public void removeUser(Room room, String username) {
        sessionRepository.deleteByRoomAndUsername(room, username);
    }

    /**
     * VISITED Table 관련 메서드
     */
    public void addVisitedUser(Room room, Member member) {
        Visited visited = Visited.create(member, room);
        visitedRepository.save(visited);
    }
}
