package uz.oltinolma.producer.security.mvc.user;

import java.util.UUID;

public class User {
    private UUID id;
    private String login;
    private String password;
    private String role;
    private boolean enable;
    private Long id_roles;
    private String name;


    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public User() {
    }

    public User(UUID id, String login, String password, String role, boolean enable) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId_roles() {
        return id_roles;
    }

    public void setId_roles(Long id_roles) {
        this.id_roles = id_roles;
    }

    public UUID getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
