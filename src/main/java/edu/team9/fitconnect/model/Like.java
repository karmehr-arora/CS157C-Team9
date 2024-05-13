package edu.team9.fitconnect.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

@Getter
@Setter
@AllArgsConstructor
@Table("like")
public class Like {
    @PrimaryKey
    private UUID likeId;
    private String userEmail;
    private UUID postId;
}