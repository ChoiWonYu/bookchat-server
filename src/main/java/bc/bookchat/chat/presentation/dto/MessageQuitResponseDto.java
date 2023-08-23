package bc.bookchat.chat.presentation.dto;

import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageQuitResponseDto {
    private Long roomId;
    private String sender;
    private String sessionId;
    private String message;
    private ArrayList<String> onlineUserList;
    private ArrayList<String> visitedUserList;

    public static MessageQuitResponseDto toDto(
        Long roomId,
        String sender,
        String sessionId,
        String message,
        ArrayList<String> onlineUserList,
        ArrayList<String> visitedUserList) {
        return new MessageQuitResponseDto(roomId, sender, sessionId, message, onlineUserList, visitedUserList);
    }
}
