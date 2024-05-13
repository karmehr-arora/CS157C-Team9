package edu.team9.fitconnect.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Table("comment")
public class Comment {
    @PrimaryKey
    private UUID commentId;
    private String userEmail;
    private String comment;
    private UUID postId;
}