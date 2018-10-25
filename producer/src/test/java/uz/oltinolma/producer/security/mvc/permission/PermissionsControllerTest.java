package uz.oltinolma.producer.security.mvc.permission;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static uz.oltinolma.producer.security.UserDummies.authorizedUser;
import static uz.oltinolma.producer.security.UserDummies.guestUser;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SecurityTestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles({"test", "test-security-profile"})
@TestInstance(Lifecycle.PER_CLASS)
class PermissionsControllerTest {
    @Autowired
    private SecurityTestConfig.TokenHelper tokenHelper;
    @MockBean(name = "permissionServiceH2Impl")
    private PermissionService permissionService;
    @MockBean(name = "userServiceH2Impl")
    private UserService userService;
    private PermissionDummies permissionDummies;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Nested
    @DisplayName("when authorized")
    class WhenAuthorized {
        private String tokenForAdmin;
        Set<String> permissionNames;

        WhenAuthorized() {
            permissionDummies = new PermissionDummies();
            permissionNames = new HashSet<String>();
            permissionNames.add("permission.info");
            permissionNames.add("permission.update");
            given(userService.findByLogin(authorizedUser().getLogin())).willReturn(authorizedUser());
            tokenHelper.setUserService(userService);
            tokenForAdmin = "Bearer " + tokenHelper.normalTokenForAdmin();
            given(permissionService.getByLogin(authorizedUser().getLogin())).willReturn(permissionNames);
            given(permissionService.list()).willReturn(permissionDummies.getAll());
            for (Permission p : permissionDummies.getAll()) {
                given(permissionService.get(p.getId())).willReturn(p);
            }



        }


        @Test
        @DisplayName("getList() returns all permissions")
        void getList_MustReturnAllPermissions() throws Exception {
            mockMvc.perform(get("/permissions/list").headers(headers()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(mapper.writeValueAsString(permissionDummies.getAll())));
        }

        @Test
        @DisplayName("getListByLogin returns permission names for current user")
        void getListByLogin_MustReturnPermissionNamesForCurrentUser() throws Exception {
            mockMvc.perform(get("/permissions/list/for/current/user").headers(headers()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(mapper.writeValueAsString(permissionNames)));

        }

        @Test
        @DisplayName("getById returns permission object for given id")
        void getById() throws Exception {
            for (Permission p : permissionDummies.getAll())
                mockMvc.perform(get("/permissions/{id}", p.getId()).headers(headers()))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("id").value(p.getId()))
                        .andExpect(jsonPath("name").value(p.getName()))
                        .andExpect(jsonPath("info").value(p.getInfo()));
        }

        private HttpHeaders headers() {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Authorization", tokenForAdmin);
            headers.set("Content-Type", "application/json");
            return headers;
        }
    }

    @Nested
    @DisplayName("when unauthorized")
    class WhenUnauthorized {
        private String tokenForGuest;

        WhenUnauthorized() {
            permissionDummies = new PermissionDummies();
            given(userService.findByLogin(guestUser().getLogin())).willReturn(guestUser());
            tokenHelper.setUserService(userService);
            tokenForGuest = "Bearer " + tokenHelper.normalTokenForGuest();
        }


        @Test
        @DisplayName("getList 403")
        void getList() throws Exception {
            mockMvc.perform(get("/permissions/list").headers(headers()))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("getListByLogin 403")
        void getListByLogin_MustReturnPermissionNamesForCurrentUser() throws Exception {
            mockMvc.perform(get("/permissions/list/for/current/user").headers(headers()))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("getById 403")
        void getById() throws Exception {
            for (Permission p : permissionDummies.getAll())
                mockMvc.perform(get("/permissions/{id}", p.getId()).headers(headers()))
                        .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("update 403")
        void update() throws Exception {
            String jsonPermissionToUpdate = mapper.writeValueAsString(permissionDummies.getAll().get(0));
            mockMvc.perform(put("/permissions")
                        .headers(headers())
                        .content(jsonPermissionToUpdate))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("insert 403")
        void insert() throws Exception {
            String jsonPermissionToInsert = mapper.writeValueAsString(new Permission());
            mockMvc.perform(post("/permissions")
                        .headers(headers())
                        .content(jsonPermissionToInsert))
                    .andExpect(status().isForbidden());
        }

        void deleteTest() throws Exception {
            mockMvc.perform(delete("/permissions").headers(headers()))
                    .andExpect(status().isForbidden());
        }

        private HttpHeaders headers() {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Authorization", tokenForGuest);
            headers.set("Content-Type", "application/json");
            return headers;
        }
    }
}