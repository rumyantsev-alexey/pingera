package ru.job4j.pingera.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import ru.job4j.pingera.clasez.Tools;
import ru.job4j.pingera.clasez.ToolHandlers;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    private long id;

    @Getter
    @Setter
    @NonNull
    @ManyToOne(cascade = CascadeType.REFRESH)
    private User user;

    @Getter
    @Setter
    @NonNull
    @Column(name = "name")
    private String name1;

    @Getter
    @Setter
    @NonNull
    @Column(name = "tools")
    private Tools sellist1;

    @Getter
    @Setter
    @Column(name = "cnt")
    private Integer cnt;

    @Getter
    @Setter
    @NonNull
    @Column(name = "packetsize")
    private Integer packetsize;

    @Getter
    @Setter
    @NonNull
    @Column(name = "ttl")
    private Integer ttl;

    @Getter
    @Setter
    @NonNull
    @Column(name = "timeout")
    private Integer timeout;

    @Getter
    @Setter
    @NonNull
    @Column(name = "host")
    private String text2;

    @Getter
    @Setter
    @NonNull
    @Column(name = "at_time")
    private Timestamp date1;

   @Getter
    @Setter
    @NonNull
    @Column(name = "time")
    private Integer text3;

    @Getter
    @Setter
    @NonNull
    @Column(name = "time1")
    private String sellist2;

    @Getter
    @Setter
    @Column(name = "filter")
    private String sellist3;

    @Getter
    @Setter
    @Column(name = "filter2")
    private ToolHandlers sellist4;

    @Getter
    @Setter
    @Column(name = "filter3")
    private String text4;

    @Getter
    @Setter
    @NonNull
    @Column(name = "total")
    private Integer total;

    @Getter
    @Setter
    @NonNull
    @Column(name = "actual")
    private boolean actual;

    @Getter
    @Setter
    @NonNull
    @Column(name = "split")
    private boolean split;

    @Getter
    @Setter
    @NonNull
    @Column(name = "account")
    private boolean account;

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Task: " + name1 + " (id = " + id + ")" + System.lineSeparator());
        result.append(user + System.lineSeparator());
        result.append("Uses " + sellist1 + " (parameters: cnt=" + cnt + " packetsize=" + packetsize + " ttl=" + ttl + " timeout=" + timeout + ")" + System.lineSeparator());
        result.append("To host " + text2 + " at local time " + date1 + ". Every " + text3 + " " + sellist2 + ". Total: " + total + " times." + System.lineSeparator());
        result.append("With " + sellist3 + " uses " + sellist4 + " with attr " + text4 + System.lineSeparator());
        return result.toString();
    }

}
