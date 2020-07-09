package ru.job4j.pingera.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

       @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @Getter
        @Setter
        private int id;

        @Getter
        @Setter
        @NonNull
        @Column(name = "name")
        private String name;

        @Getter
        @Setter
        @NonNull
        @Column(name = "password")
        private String password;

        @Getter
        @Setter
        @Email
        @Column(name = "email")
        private String email;

        public String toString() {
         StringBuilder result = new StringBuilder();
         result.append(System.out.format("user: %s (id = %s) email:%s", name, id, email));
         return result.toString();
        }
}
