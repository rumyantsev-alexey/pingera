package ru.job4j.pingera.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.job4j.pingera.models.User;
import ru.job4j.pingera.repositories.UsersRepository;

@Getter
@Setter
@NoArgsConstructor
@Component
public class UserDto {

    @Autowired
    private UsersRepository uuu;

    private String name;
    private  String password;


    public User convert() {

        User result = uuu.findByName(this.getName());
        return result;
    }
}
