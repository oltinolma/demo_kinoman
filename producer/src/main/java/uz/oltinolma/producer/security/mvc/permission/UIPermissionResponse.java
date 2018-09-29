package uz.oltinolma.producer.security.mvc.permission;

import java.util.List;

public class UIPermissionResponse {

    List<Permissions> menus;
    List<Permissions> actions;

    public List<Permissions> getMenus() {
        return menus;
    }

    public void setMenus(List<Permissions> menus) {
        this.menus = menus;
    }

    public List<Permissions> getActions() {
        return actions;
    }

    public void setActions(List<Permissions> actions) {
        this.actions = actions;
    }
}
