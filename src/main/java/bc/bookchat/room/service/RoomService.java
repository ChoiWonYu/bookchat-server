package bc.bookchat.room.service;

import bc.bookchat.book.entity.MajorBook;
import bc.bookchat.book.service.BookService;
import bc.bookchat.common.exception.CustomException;
import bc.bookchat.common.exception.ErrorCode;
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

    @Transactional
    public RoomResponseDto createRoom(Long isbn) {
        // DUPLICATE Room name
        System.out.println("isbn : " + isbn);
        if (roomRepository.findByRoomId(isbn).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        }
        // IF BOOK NOT FIND, CREATE BOOK
        findBookOrCreate(isbn);
        MajorBook majorBook = bookService.findMajorBookByIsbn(isbn).orElseThrow();
        Room room = Room.create(majorBook.getTitle(), isbn);
        roomRepository.save(room);
        return RoomResponseDto.toDto(isbn, majorBook.getTitle());
    }

    @Transactional(readOnly = true)
    public List<RoomResponseDto> findAll() {
        List<Room> rooms = roomRepository.findAll();
        List<RoomResponseDto> roomResponseDtos = new ArrayList<>();
        for (Room r : rooms) {
            roomResponseDtos.add(RoomResponseDto.toDto(r.getRoomId(), r.getName()));
        }
        return roomResponseDtos;
    }

    @Transactional(readOnly = true)
    public RoomResponseDto findRoom(Long roomId) {
        Room room = roomRepository.findByRoomId(roomId).orElseThrow(() -> new CustomException(
            ErrorCode.ROOM_NOT_FOUND));
        return RoomResponseDto.toDto(room.getRoomId(), room.getName());
    }

    @Transactional(readOnly = true)
    public List<VisitedResponseDto> findVisitedAll(Member member) {
        List<Visited> visitedList = visitedRepository.findAllByMember_Email(member.getEmail());
        List<VisitedResponseDto> responseDtoList = new ArrayList<>();
        for (Visited visited : visitedList) {
            String roomName = visited.getRoom().getName();
            String enterAt = visited.getEnterAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            VisitedResponseDto responseDto = VisitedResponseDto.create(roomName, enterAt);
            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }

    @Transactional
    public void findBookOrCreate(Long isbn){
        Optional<MajorBook> book=bookService.findMajorBookByIsbn(isbn);
        if(book.isEmpty()){
            bookService.createBook(isbn);
        }
    }
}
