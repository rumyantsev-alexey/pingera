package ru.job4j.pingera.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
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
//    @Lob
    private String result;

    @Getter
    @Setter
    private boolean work;

    @Getter
    @Setter
    private boolean complete;

    @Getter
    @Setter
    private boolean successfully;

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(System.out.format("Subtask №: %s from task №:%s" + System.lineSeparator(), id, task.getId()));
        result.append(System.out.format("Do in local time: %s" + System.lineSeparator(), date1));
        result.append(System.out.format("Successfully: %s" + System.lineSeparator(), successfully));
        result.append("Result:" + System.lineSeparator());
        result.append(result + System.lineSeparator());
        return result.toString();
    }
}

