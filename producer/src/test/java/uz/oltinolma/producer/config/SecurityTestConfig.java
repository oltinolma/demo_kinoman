package uz.oltinolma.producer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import uz.oltinolma.producer.security.token.JwtTokenFactory;

import java.sql.Date;
import java.time.LocalDate;

import static uz.oltinolma.producer.security.UserDummies.userContextForAdmin;
import static uz.oltinolma.producer.security.UserDummies.userContextForGuest;

@Profile("test-security-profile")
@SpringBootApplication(scanBasePackages={"uz.oltinolma.producer.security",
        "uz.oltinolma.producer.mvc.controller", "uz.oltinolma.producer.rabbitmq"})
//@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = ExcludeFromTests.class))
public class SecurityTestConfig {
    @Component
    public class TokenHelper {
        private JwtTokenFactory tokenFactory;

        @Autowired
        public void setTokenFactory(JwtTokenFactory tokenFactory) {
            this.tokenFactory = tokenFactory;
        }

        public String expiredToken() {
            return tokenFactory.createAccessJwtToken(userContextForAdmin(), Date.valueOf(LocalDate.ofYearDay(1999, 1)), Date.valueOf(LocalDate.ofYearDay(1999, 2))).getToken();
        }

        public String normalTokenForAdmin() {
            return tokenFactory.createAccessJwtToken(userContextForAdmin()).getToken();
        }


        public String normalTokenForGuest() {
            return tokenFactory.createAccessJwtToken(userContextForGuest()).getToken();
        }
    }
}