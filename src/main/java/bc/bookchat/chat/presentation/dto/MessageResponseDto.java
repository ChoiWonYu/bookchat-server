package bc.bookchat.chat.presentation.dto;

import java.util.ArrayList;
import lombok.Getter;

@Getter
public class MessageResponseDto {
    private Long roomId;
    private String sender;
    private boolean isAdmin;
    private String message;
    private ArrayList<String> onlineUserList;

    private MessageResponseDto(Long roomId, String sender, boolean isAdmin, String message,
        ArrayList<String> onlineUserList) {
        this.roomId = roomId;
        this.sender = sender;
        this.isAdmin = isAdmin;
        this.message = message;
        this.onlineUserList = onlineUserList;
    }

    public static MessageResponseDto toDto(Long roomId,
        String sender,
        boolean isAdmin,
        String message,
        ArrayList<String> onlineUserList) {
        return new MessageResponseDto(roomId, sender, isAdmin, message, onlineUserList);
    }
}
