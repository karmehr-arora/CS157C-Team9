package edu.team9.fitconnect.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;

@Getter
@Setter
@Table("category")
@AllArgsConstructor
public class Category {
    @PrimaryKey
    private String category;

    private LocalDateTime created;
    private String fileName;
    @JsonIgnore
    private ByteBuffer fileData;
    private String fileType;
}
