package edu.team9.fitconnect.model;

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
@Table("comment")
public class Comment {
    @JsonIgnore
    @PrimaryKey
    private UUID commentId;
    private String userEmail;
    private String comment;
    private UUID postId;
    private LocalDateTime timestamp;
}