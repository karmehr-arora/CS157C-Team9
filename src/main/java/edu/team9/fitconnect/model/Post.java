package edu.team9.fitconnect.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Table("post")
@AllArgsConstructor
public class Post {
    @PrimaryKey
    @JsonIgnore
    private UUID id;

    private String fileName;
    @JsonIgnore
    private ByteBuffer fileData;
    private String fileType;
    private LocalDateTime created;

    private String userId;

    private String titleText;
    private String bodyText;

    private String category;

    //todo: upvotes, downvotes, and comments
}
