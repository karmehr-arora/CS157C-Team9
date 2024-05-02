package edu.team9.fitconnect.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Table("workout")
@AllArgsConstructor
public class Workout {
    @PrimaryKey
    private UUID id;

    private String userId;

    private String nameOfWorkout;

    private int sets;

    private int reps;

    private double weight;

    private LocalDateTime date;


}
