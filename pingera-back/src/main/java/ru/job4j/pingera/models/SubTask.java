package ru.job4j.pingera.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Clob;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Table (name = "subtasks")
public class SubTask {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    private long id;

    @Getter
    @Setter
    @NonNull
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Task task;

    @Getter
    @Setter
    @NonNull
    @Column(name = "time")
    private Timestamp date1;

    @Getter
    @Setter
    private Clob result;

    @Getter
    @Setter
    private boolean work;

    @Getter
    @Setter
    private boolean complete;
}
