package bc.bookchat.room.service;

import bc.bookchat.book.entity.MajorBook;
import bc.bookchat.book.service.BookService;
import bc.bookchat.chat.domain.entity.Message;
import bc.bookchat.chat.domain.infra.MessageRepository;
import bc.bookchat.chat.presentation.dto.MessageBeforeResponseDto;
import bc.bookchat.member.entity.Member;
import bc.bookchat.room.domain.entity.Room;
import bc.bookchat.room.domain.entity.Visited;
import bc.bookchat.room.domain.repository.RoomRepository;
import bc.bookchat.room.domain.repository.VisitedRepository;
import bc.bookchat.room.dto.RoomResponseDto;
import bc.bookchat.room.dto.VisitedResponseDto;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final VisitedRepository visitedRepository;
    private final BookService bookService;
    private final MessageRepository messageRepository;

    @Transactional
    public RoomResponseDto createRoom(Long isbn) {
        // IF BOOK NOT FIND, CREATE BOOK
        findBookOrCreate(isbn);
        MajorBook majorBook = bookService.findMajorBookByIsbn(isbn).orElseThrow();

        // NOT DUPLICATE Room name -> room create(db에 저장)
        // DUPLICATE -> 에러 대신 그냥 출력
        System.out.println("isbn : " + isbn);
        if (roomRepository.findByRoomId(isbn).isEmpty()) {
            //throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
            Room room = Room.create(majorBook.getTitle(), isbn);
            roomRepository.save(room);
        }

        return RoomResponseDto.toDto(isbn, majorBook.getTitle());
    }

    @Transactional(readOnly = true)
    public List<VisitedResponseDto> findVisitedAll(Member member) {
        List<Visited> visitedList = visitedRepository.findDistinctRoom_IdByMember_UserNameOrderByEnterAtDesc(member.getUserName());
        List<VisitedResponseDto> responseDtoList = new ArrayList<>();
        for (Visited visited : visitedList) {
            MajorBook majorBook = bookService.findMajorBookByIsbn(visited.getRoom().getRoomId())
                .orElseThrow();
            Long isbn = majorBook.getIsbn();
            String roomName = visited.getRoom().getName();
            String enterAt = visited.getEnterAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String imageUrl = majorBook.getImageUrl();
            String writer = majorBook.getAuthor();
            VisitedResponseDto responseDto = VisitedResponseDto.toDto(isbn, roomName, enterAt, imageUrl, writer);
            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }

    /**
     * 채팅방 이전 채팅 목록 조회
     */
    @Transactional(readOnly = true)
    public List<MessageBeforeResponseDto> findBeforeMessageAll(Long isbn) {
        List<Message> messageList = messageRepository.findAllByRoomId(isbn);
        List<MessageBeforeResponseDto> messageDtoList = new ArrayList<>();
        for (Message m : messageList) {
            MessageBeforeResponseDto dto = MessageBeforeResponseDto.toDto(m.getContent(),
                m.getRoomId(),
                m.getSender(),
                m.getCreateAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            messageDtoList.add(dto);
        }
        return messageDtoList;
    }

    public void findBookOrCreate(Long isbn){
        Optional<MajorBook> book=bookService.findMajorBookByIsbn(isbn);
        if(book.isEmpty()){
            bookService.createBook(isbn);
        }
    }
}
