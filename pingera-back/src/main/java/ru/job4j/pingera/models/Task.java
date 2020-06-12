package ru.job4j.pingera.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Table(name = "archive"
)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    private int id;

    @Getter
    @Setter
    @NonNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
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
    private String sellist1;

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
    private String sellist4;

    @Getter
    @Setter
    @Column(name = "filter3")
    private String text4;

}
