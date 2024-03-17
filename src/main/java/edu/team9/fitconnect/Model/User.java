package edu.team9.fitconnect.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Table
public class User {

    @PrimaryKey
    private UUID id;
    private String firstName;
    private String email;
    private int weight;
    private int heightInInches;
}
