package uz.oltinolma.producer.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.oltinolma.producer.security.model.UserContext;
import uz.oltinolma.producer.security.mvc.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDummies {
    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();
    public static User sampleUser() {
        return new User(UUID.randomUUID(), "user", encoder.encode("correct_password"), "guest", true);
    }




    public static UserContext userContextForGuest() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(sampleUser().getRole()));

        return UserContext.create(sampleUser().getLogin(), authorities, null);
    }
}
