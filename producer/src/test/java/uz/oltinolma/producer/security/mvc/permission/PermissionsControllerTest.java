package uz.oltinolma.producer.security.mvc.permission;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import uz.oltinolma.producer.config.SecurityTestConfig;
import uz.oltinolma.producer.security.config.InitH2;
import uz.oltinolma.producer.security.mvc.permission.dao.PermissionDao;
import uz.oltinolma.producer.security.mvc.permission.service.PermissionService;
import uz.oltinolma.producer.security.mvc.user.service.UserService;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
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
@Sql({"/permission.sql"})
class PermissionsControllerTest {
    @MockBean
    private InitH2 excludedBean;
    @Autowired
    private SecurityTestConfig.TokenHelper tokenHelper;
    @SpyBean(name = "permissionServiceH2Impl")
    private PermissionService serviceH2ImplSpy;
    @SpyBean(name = "userServiceH2Impl")
    private UserService userServiceH2Impl;
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
            permissionNames.add("permission.insert");
            permissionNames.add("permission.delete");
            given(serviceH2ImplSpy.getPermissionsForUser(authorizedUser().getLogin())).willReturn(permissionNames);
            given(userServiceH2Impl.findByLogin(authorizedUser().getLogin())).willReturn(authorizedUser());
            tokenForAdmin = "Bearer " + tokenHelper.normalTokenForAdmin();
        }

        private HttpHeaders headers() {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Authorization", tokenForAdmin);
            headers.set("Content-Type", "application/json");
            return headers;
        }

        @Nested
        @DisplayName("and updates data")
        @SpringBootTest(classes = SecurityTestConfig.class)
        @AutoConfigureMockMvc
        @ActiveProfiles({"test", "test-security-profile"})
        @TestInstance(Lifecycle.PER_CLASS)
        @Sql(value = "/permission.sql")
        class UpdateTests {
            @SpyBean(name = "permissionServicePostgresImpl")
            private PermissionService servicePostgresImplSpy;
            @SpyBean(name = "permissionDaoPostgresImpl")
            private PermissionDao DaoPostgresImplSpy;
            @SpyBean(name = "permissionDaoH2Impl")
            private PermissionDao DaoH2ImplSpy;
            @MockBean
            private InitH2 excludedBean;
            @Autowired
            private MockMvc mockMvc;
            @SpyBean(name = "permissionServiceH2Impl")
            private PermissionService serviceH2ImplSpy;
            @SpyBean
            private PermissionsController controllerSpy;

            @BeforeEach
            void setup(){
                given(serviceH2ImplSpy.getPermissionsForUser(authorizedUser().getLogin())).willReturn(permissionNames);
                serviceH2ImplSpy.deleteAll();
                serviceH2ImplSpy.insertAll(servicePostgresImplSpy.list());
            }

            @Test
            @DisplayName("Structure of update is ok.")
            void updateStructure() throws Exception {
                InOrder orderVerifier = inOrder(serviceH2ImplSpy,servicePostgresImplSpy,
                        DaoH2ImplSpy,DaoPostgresImplSpy, controllerSpy);

                Permission per = new Permission().setId(1).setName("updated permission name").setNotes("dfdfdfd");
                mockMvc.perform(put("/permissions")
                        .content(mapper.writeValueAsString(per))
                        .headers(headers())).andExpect(status().isOk()).andDo(print());

                orderVerifier.verify(controllerSpy).update(any());
                orderVerifier.verify(servicePostgresImplSpy).update(any());
                orderVerifier.verify(serviceH2ImplSpy).update(any());
                orderVerifier.verify(DaoH2ImplSpy).update(any());
                orderVerifier.verify(DaoPostgresImplSpy).update(any());
            }

            @Test
            @DisplayName("PUT request updates given permission.")
            public void temp() {
                //TODO implemewnt it!
                System.out.println("serviceH2ImplSpy");
                serviceH2ImplSpy.list().forEach(p-> System.out.println("h2 " + p));
                System.out.println("servicePostgresImplSpy");
                servicePostgresImplSpy.list().forEach(p-> System.out.println("pg " + p));
            }

            @Test
            @DisplayName("404 if worng id is passed for the permission to be updated.")
            void notUpdatedMeansNotFound() throws Exception {
                final int wrongId = Integer.MAX_VALUE;
                Permission permissionThatDoesNotExist = new Permission().setId(wrongId).setName("updated permission name").setNotes("dfdfdfd");
                mockMvc.perform(put("/permissions")
                        .content(mapper.writeValueAsString(permissionThatDoesNotExist))
                        .headers(headers())).andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("and only reads data")
        class ReadTests {
            ReadTests() {
                given(serviceH2ImplSpy.list()).willReturn(permissionDummies.getAll());
                for (Permission p : permissionDummies.getAll()) {
                    given(serviceH2ImplSpy.get(p.getId())).willReturn(p);
                }
            }

            //TODO test reads from h2 only

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
                            .andExpect(jsonPath("notes").value(p.getNotes()));
            }
        }
    }

    @Nested
    @DisplayName("when unauthorized")
    class WhenUnauthorized {
        private String tokenForGuest;

        WhenUnauthorized() {
            permissionDummies = new PermissionDummies();
            given(userServiceH2Impl.findByLogin(guestUser().getLogin())).willReturn(guestUser());
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

        @Test
        @DisplayName("delete 403")
        void deleteTest() throws Exception {
            mockMvc.perform(delete("/permissions/{id}", new Random().nextInt(1000)).headers(headers()))
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