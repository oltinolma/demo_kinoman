package uz.oltinolma.producer.security.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Set;

public class UserContext {
    private final String login;
    private final Set<String> permissions;

    private UserContext(String login, Set<String> permissions) {
        this.login = login;
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "UserContext{" +
                "permissions=" + permissions +
                ", login='" + login + '\'' +
                '}';
    }

    public static UserContext create(String login, Set<String> permissions) {
        if (StringUtils.isBlank(login)) throw new IllegalArgumentException("Login is blank: " + login);
        return new UserContext(login, permissions);
    }

    public String getLogin() {
        return login;
    }

    public Set<String> getPermissions() {
        return permissions;
    }
}
