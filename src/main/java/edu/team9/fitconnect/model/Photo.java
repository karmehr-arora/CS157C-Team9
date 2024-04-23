package edu.team9.fitconnect.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Table("progress_photo")
public class Photo {
    @PrimaryKey
    private UUID id;

    private String fileName;

    private ByteBuffer fileData;

    private LocalDateTime created;

    private String description;

    private String userId;

}
