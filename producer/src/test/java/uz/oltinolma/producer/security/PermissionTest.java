package uz.oltinolma.producer.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uz.oltinolma.producer.security.config.InitAll;
import uz.oltinolma.producer.security.model.UserContext;
import uz.oltinolma.producer.security.mvc.permission.service.PermissionService;
import uz.oltinolma.producer.security.mvc.user.service.UserService;
import uz.oltinolma.producer.security.token.JwtTokenFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uz.oltinolma.producer.security.UserDummies.sampleUser;
import static uz.oltinolma.producer.security.UserDummies.userContextForGuest;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PermissionTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean(name = "userServiceH2Impl")
    private UserService userService;
    @MockBean(name = "permissionServiceH2Impl")
    private PermissionService permissionService;
    @MockBean
    private InitAll initAll;
    @Autowired
    private JwtTokenFactory tokenFactory;

    @BeforeEach
    public void setup() {
        Set<String> permissions = new HashSet<String>();
        permissions.add("permission_1");
        permissions.add("permission_2");
        given(userService.findByLogin(sampleUser().getLogin())).willReturn(sampleUser());
        given(permissionService.getByLogin(sampleUser().getLogin())).willReturn(permissions);

    }

    @Test
    public void requestAnyUrlWithoutRequiredHeaders401() throws Exception {
        mockMvc.perform(get("/anyUrl"))
                .andExpect(status().isUnauthorized()).andDo(print());
        mockMvc.perform(post("/anyUrl"))
                .andExpect(status().isUnauthorized()).andDo(print());

    }

    @Test
    public void whenTokenWrong401() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Authorization", "wrong_token");
        headers.set("Content-Type", "application/json");
        headers.set("user-agent", "someAgent");
        mockMvc.perform(post("/anyUrl").headers(headers))
                .andExpect(status().isUnauthorized()).andDo(print());
        mockMvc.perform(get("/anyUrl").headers(headers))
                .andExpect(status().isUnauthorized()).andDo(print());
    }

    @Test
    @DisplayName("When Routing-Key header is missing response status must be 400.")
    public void whenRoutingHeaderIsMIssing400() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Authorization", "Bearer " + normalToken());
        headers.set("Content-Type", "application/json");
        headers.set("user-agent", "someAgent");
        mockMvc.perform(post("/anyUrl").headers(headers))
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    @DisplayName("When token expired response status 401")
    public void whenTokenExpired401() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Authorization", "Bearer " + expiredToken());
        headers.set("Content-Type", "application/json");
        headers.set("Routing-Key", "someRoutingKey");
        headers.set("user-agent", "someAgent");
        mockMvc.perform(post("/anyUrl").headers(headers))
                .andDo(print())
                .andExpect(status().is(401))
                .andExpect(jsonPath("message").value("Access token is expired."));
    }


    public String expiredToken() {
        return tokenFactory.createAccessJwtToken(userContextForGuest(), Date.valueOf(LocalDate.ofYearDay(1999, 1)), Date.valueOf(LocalDate.ofYearDay(1999, 2))).getToken();
    }

    public String normalToken() {
        return tokenFactory.createAccessJwtToken(userContextForGuest()).getToken();
    }
}
