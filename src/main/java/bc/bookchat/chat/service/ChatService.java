package bc.bookchat.chat.service;

import bc.bookchat.chat.domain.entity.Message;
import bc.bookchat.chat.domain.entity.Session;
import bc.bookchat.chat.domain.infra.MessageRepository;
import bc.bookchat.chat.domain.infra.SessionRepository;
import bc.bookchat.chat.presentation.dto.MessageRequestDto;
import bc.bookchat.chat.presentation.dto.MessageResponseDto;
import bc.bookchat.common.exception.CustomException;
import bc.bookchat.common.exception.ErrorCode;
import bc.bookchat.member.entity.Member;
import bc.bookchat.member.repository.MemberRepository;
import bc.bookchat.room.domain.entity.Room;
import bc.bookchat.room.domain.repository.RoomRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final SessionRepository sessionRepository;
    private final SimpMessageSendingOperations messagingTemplate;
    private final MemberRepository memberRepository;

    @Transactional
    public void enter(MessageRequestDto messageRequestDto, Member member) {
        // user setting
        messageRequestDto.setSender(member.getUserName());
        // 존재하는 방인지 확인
        Room room = roomRepository.findByRoomId(messageRequestDto.getRoomId()).orElseThrow(
            () -> new CustomException(ErrorCode.ROOM_NOT_FOUND));
        // 유저가 존재하는지 확인
        memberRepository.findByUserName(messageRequestDto.getSender()).orElseThrow(
            () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 입장 메세지 추가
        messageRequestDto.setMessage(messageRequestDto.getSender() + "님이 입장하셨습니다.");
        // 채팅방 접속 목록 추가
        addUser(room, messageRequestDto.getSender());

        // 채팅방 유저 리스트 가져오기
        ArrayList<String> userList = findListAll(room);

        // 전송
        MessageResponseDto messageResponseDto = MessageResponseDto.toDto(messageRequestDto.getRoomId(),
            messageRequestDto.getSender(),
            messageRequestDto.getMessage(),
            userList
        );
        messagingTemplate.convertAndSend("/sub/chat/rooms/" + messageRequestDto.getRoomId(), messageResponseDto);
    }

    @Transactional
    public void publish(MessageRequestDto messageRequestDto, Member member) {
        // user setting
        messageRequestDto.setSender(member.getUserName());
        // 존재하는 방인지 확인
        Room room = roomRepository.findByRoomId(messageRequestDto.getRoomId()).orElseThrow(
            () -> new CustomException(ErrorCode.ROOM_NOT_FOUND));
        // 유저가 존재하는지 확인
        memberRepository.findByUserName(messageRequestDto.getSender()).orElseThrow(() -> new CustomException(
            ErrorCode.MEMBER_NOT_FOUND));

        // DB 저장
        Message message = Message.create(messageRequestDto.getMessage());
        messageRepository.save(message);

        // 채팅방 유저 리스트 가져오기
        ArrayList<String> userList = findListAll(room);

        // 전송
        MessageResponseDto messageResponseDto = MessageResponseDto.toDto(messageRequestDto.getRoomId(),
            messageRequestDto.getSender(),
            messageRequestDto.getMessage(),
            userList
        );
        messagingTemplate.convertAndSend("/sub/chat/rooms/" + messageRequestDto.getRoomId(), messageResponseDto);
    }

    @Transactional
    public void quit(MessageRequestDto messageRequestDto, Member member) {
        // user setting
        messageRequestDto.setSender(member.getUserName());
        // 존재하는 방인지 확인
        Room room = roomRepository.findByRoomId(messageRequestDto.getRoomId()).orElseThrow(
            () -> new CustomException(ErrorCode.ROOM_NOT_FOUND));
        // 유저가 존재하는지 확인
        memberRepository.findByUserName(messageRequestDto.getSender()).orElseThrow(() -> new CustomException(
            ErrorCode.MEMBER_NOT_FOUND));

        // 입장 메세지 제거
        messageRequestDto.setMessage(messageRequestDto.getSender() + "님이 퇴장하셨습니다.");
        // 채팅방 접속 목록 제거
        removeUser(room, messageRequestDto.getSender());

        // 채팅방 유저 리스트 가져오기
        ArrayList<String> userList = findListAll(room);

        // 전송
        MessageResponseDto messageResponseDto = MessageResponseDto.toDto(messageRequestDto.getRoomId(),
            messageRequestDto.getSender(),
            messageRequestDto.getMessage(),
            userList
        );
        messagingTemplate.convertAndSend("/sub/chat/rooms/" + messageRequestDto.getRoomId(), messageResponseDto);
    }

    // 현재 접속자 리스트 가져오기
    public ArrayList<String> findListAll(Room room) {
        List<Session> userList = sessionRepository.findSessionsByRoom(room);
        ArrayList<String> usernameList = new ArrayList<>();
        for (Session s : userList) {
            usernameList.add(s.getUsername());
        }
        return usernameList;
    }

    // 채팅방 접속 인원 추가
    public void addUser(Room room, String username) {
        sessionRepository.save(Session.create(username, room));
    }

    // 채팅방 접속 인원 제거
    public void removeUser(Room room, String username) {
        sessionRepository.deleteByRoomAndUsername(room, username);
    }
}
