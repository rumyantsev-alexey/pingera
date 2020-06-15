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


    public User convert() {

        User result = uuu.findByName(this.getName());
        if (result == null) {
            result = new User();
            result.setName("Anonymus");
            result.setPassword(p.encode(""));
            result.setEmail("empty@empty.ru");
            uuu.save(result);
        }
        return result;
    }
}
