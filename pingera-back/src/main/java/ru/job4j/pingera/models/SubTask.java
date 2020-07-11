package ru.job4j.pingera.models;

import lombok.*;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
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
    private byte[] result;

    @Getter
    @Setter
    private boolean work;

    @Getter
    @Setter
    private boolean complete;

    @Getter
    @Setter
    private boolean successfully;

    @SneakyThrows
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Subtask №: " + id + " from task №:" + task.getId() + System.lineSeparator());
        result.append("Do in local time: " + date1 + System.lineSeparator());
        result.append("Work: " + work + System.lineSeparator());
        result.append("Complete: " + complete + System.lineSeparator());
        result.append("Successfully: " + successfully + System.lineSeparator());
        result.append("Result:" + System.lineSeparator());
        result.append( new String(this.result, StandardCharsets.UTF_8) + System.lineSeparator());
        return result.toString();
    }
}

