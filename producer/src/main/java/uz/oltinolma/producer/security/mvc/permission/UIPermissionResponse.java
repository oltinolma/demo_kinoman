package uz.oltinolma.producer.security.mvc.permission;

import java.util.List;

public class UIPermissionResponse {

    List<Permission> menus;
    List<Permission> actions;

    public List<Permission> getMenus() {
        return menus;
    }

    public void setMenus(List<Permission> menus) {
        this.menus = menus;
    }

    public List<Permission> getActions() {
        return actions;
    }

    public void setActions(List<Permission> actions) {
        this.actions = actions;
    }
}
