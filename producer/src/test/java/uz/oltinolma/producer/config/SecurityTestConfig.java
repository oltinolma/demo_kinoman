package uz.oltinolma.producer.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile("test-security-profile")
@SpringBootApplication(scanBasePackages={"uz.oltinolma.producer.security"})
public class SecurityTestConfig {

}