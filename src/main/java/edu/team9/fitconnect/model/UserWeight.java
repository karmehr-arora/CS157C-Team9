package edu.team9.fitconnect.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Table("user_weight")
public class UserWeight {
    @PrimaryKey
    private UUID id;
    @Indexed
    private String user_email;

    private double weight; // in LB
    private LocalDateTime timestamp;
}
