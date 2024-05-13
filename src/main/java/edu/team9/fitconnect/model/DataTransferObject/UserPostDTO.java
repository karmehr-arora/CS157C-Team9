package edu.team9.fitconnect.model.DataTransferObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class UserPostDTO {
    private UUID id;
    private String email;
    private String fileName;
    private String fileType;
    private LocalDateTime created;
    private String titleText;
    private String bodyText;
    private String category;
    private String username;
    private String firstName;
    private String lastName;
    private boolean isLiked;
}
