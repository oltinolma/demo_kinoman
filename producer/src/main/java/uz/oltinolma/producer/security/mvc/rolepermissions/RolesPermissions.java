package uz.oltinolma.producer.security.mvc.rolepermissions;

public class RolesPermissions {
    private Integer id;
    private Integer id_role;
    private Integer id_permission;
    private String permission_name;
    private String role_name;

    public RolesPermissions() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_role() {
        return id_role;
    }

    public void setId_role(Integer id_role) {
        this.id_role = id_role;
    }

    public Integer getId_permission() {
        return id_permission;
    }

    public void setId_permission(Integer id_permission) {
        this.id_permission = id_permission;
    }

    public String getPermission_name() {
        return permission_name;
    }

    public void setPermission_name(String permission_name) {
        this.permission_name = permission_name;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    @Override
    public String toString() {
        return "RolesPermissions{" +
                "role_name='" + role_name + '\'' +
                ", permission_name='" + permission_name + '\'' +
                ", id_permission=" + id_permission +
                ", id_role=" + id_role +
                ", id=" + id +
                '}';
    }
}

