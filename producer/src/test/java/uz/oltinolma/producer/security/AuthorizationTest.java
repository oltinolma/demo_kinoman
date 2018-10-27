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
import uz.oltinolma.producer.config.SecurityTestConfig;
import uz.oltinolma.producer.security.mvc.permission.service.PermissionService;
import uz.oltinolma.producer.security.mvc.user.service.UserService;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uz.oltinolma.producer.security.UserDummies.authorizedUser;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SecurityTestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles({"test", "test-security-profile"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthorizationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean(name = "userServiceH2Impl")
    private UserService userService;

    @MockBean(name = "permissionServiceH2Impl")
    private PermissionService permissionService;

    @Autowired
    private SecurityTestConfig.TokenHelper
            tokenHelper;


    @BeforeEach
    public void setup() {
        Set<String> permissions = new HashSet<String>();
        permissions.add("permission_1");
        permissions.add("permission_2");
        given(permissionService.getByLogin(authorizedUser().getLogin())).willReturn(permissions);
        given(userService.findByLogin(authorizedUser().getLogin())).willReturn(authorizedUser());
        tokenHelper.setUserService(userService);
    }

    @Test
    @DisplayName("Authorization header cannot be blank.")
    public void requestAnyUrlWithoutRequiredHeaders401() throws Exception {
        mockMvc.perform(get("/anyUrl"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("message").value("Authorization header cannot be blank!"))
                .andDo(print());
        mockMvc.perform(post("/anyUrl"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("message").value("Authorization header cannot be blank!"))
                .andDo(print());
    }

    @Test
    @DisplayName("Unauthorized when invalid token.")
    public void whenTokenWrong401() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Authorization", "wrong_token");
        headers.set("Content-Type", "application/json");
        mockMvc.perform(post("/anyUrl").headers(headers))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("message").value("Invalid access token."))
                .andDo(print());
        mockMvc.perform(get("/anyUrl").headers(headers))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("message").value("Invalid access token."))
                .andDo(print());
    }

    @Test
    @DisplayName("400 when Routing-Key header is missing in the request to rabbitMQ.")
    public void whenRoutingHeaderIsMissing400() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Authorization", "Bearer " + tokenHelper.normalTokenForAdmin());
        headers.set("Content-Type", "application/json");
        mockMvc.perform(post("/v1/send").headers(headers))
                .andDo(print())
                .andExpect(status().is(400));
        mockMvc.perform(post("/v1/request").headers(headers))
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    @DisplayName("Unauthorized when token is expired.")
    public void whenTokenExpired401() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Authorization", "Bearer " + tokenHelper.expiredToken());
        headers.set("Content-Type", "application/json");
        mockMvc.perform(post("/anyUrl").headers(headers))
                .andDo(print())
                .andExpect(status().is(401))
                .andExpect(jsonPath("message").value("Access token is expired."));
    }

    @Test
    @DisplayName("Authorized when has permission.")
    public void authorizedWhenHasPermission() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Authorization", "Bearer " + tokenHelper.normalTokenForAdmin());
        headers.set("Content-Type", "application/json");

        mockMvc.perform(post("/anyUrl").headers(headers))
                .andExpect(status().is(404));
        mockMvc.perform(get("/hello").headers(headers))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("/search is accessible without authorization.")
    public void searchIsNotSecured() throws Exception {
        mockMvc.perform(get("/search/anyUrl"))
                .andExpect(status().is(404));
    }
}
