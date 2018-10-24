package uz.oltinolma.producer.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uz.oltinolma.producer.config.SecurityTestConfig;
import uz.oltinolma.producer.security.mvc.user.service.UserService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uz.oltinolma.producer.security.UserDummies.authorizedUser;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SecurityTestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles({"test", "test-security-profile"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest {
    @MockBean(name = "userServiceH2Impl")
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        given(userService.findByLogin(authorizedUser().getLogin())).willReturn(authorizedUser());
    }


    @Test
    public void giveTokenWhenCorrectLoginAndPassword() throws Exception {
        mockMvc.perform(post("/auth/login")
                .headers(headers())
                .content("{\"login\":\"admin\",\"password\":\"correct_password\"}")
        ).andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("token").exists());
    }

    @Test
    void whenWrongCredentials401() throws Exception {
        mockMvc.perform(post("/auth/login")
                .headers(headers())
                .content("{\"login\":\"user\",\"password\":\"wrong_password\"}")
        ).andExpect(status().is(401))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/auth/login")
                .headers(headers())
                .content("{\"login\":\"wrong_login\",\"password\":\"correct_password\"}")
        ).andExpect(status().isUnauthorized())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Requested-With", "XMLHttpRequest");
        headers.set("Content-Type", "application/json");
        return headers;
    }
}
