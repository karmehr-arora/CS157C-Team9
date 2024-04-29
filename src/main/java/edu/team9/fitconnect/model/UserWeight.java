package edu.team9.fitconnect.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Table
public class UserWeight {
    @JsonIgnore
    @PrimaryKey
    private UUID id;

    @JsonIgnore
    @Indexed
    private String email;

    private double weight; // in LB
    private LocalDateTime timestamp;
}
