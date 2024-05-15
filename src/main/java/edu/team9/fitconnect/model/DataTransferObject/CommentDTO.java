package edu.team9.fitconnect.model.DataTransferObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CommentDTO {
    private UUID id;
    private String userEmail;
    private String firstName;
    private String lastName;
    private String comment;
    private UUID postId;
    private LocalDateTime timestamp;
}