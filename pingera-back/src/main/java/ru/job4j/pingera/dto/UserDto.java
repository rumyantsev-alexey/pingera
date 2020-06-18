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

    @Autowired
    private PasswordEncoder p;

    private String name;
    private  String password;


    public User convert() {

        User result = uuu.findByName(this.getName());
        if (result == null) {
            result = new User();
            result.setName("anonymus");
            result.setPassword(p.encode("1"));
            result.setEmail("empty@empty.ru");
            result.setId(3);
            uuu.save(result);
        }
        return result;
    }
}
