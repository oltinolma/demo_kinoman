package uz.oltinolma.producer.security.model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class UserContext {
    private final String login;
    private final List<GrantedAuthority> authorities;
    private final Set<String> permissions;
    private UUID id_user;

    private UserContext(String login, List<GrantedAuthority> authorities, Set<String> permissions) {
        this.login = login;
        this.authorities = authorities;
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "UserContext{" +
                "permissions=" + permissions +
                ", authorities=" + authorities +
                ", login='" + login + '\'' +
                '}';
    }

    public static UserContext create(String login, List<GrantedAuthority> authorities, Set<String> permissions) {
        if (StringUtils.isBlank(login)) throw new IllegalArgumentException("Login is blank: " + login);
        return new UserContext(login, authorities, permissions);
    }

    public String getLogin() {
        return login;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public UUID getId_user() {
        return id_user;
    }

    public void setId_user(UUID id_user) {
        this.id_user = id_user;
    }
}
