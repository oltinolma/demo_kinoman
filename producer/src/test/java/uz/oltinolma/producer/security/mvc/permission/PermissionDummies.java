package uz.oltinolma.producer.security.mvc.permission;

import java.util.ArrayList;
import java.util.List;

public class PermissionDummies {
    private List<Permission> all;

    public PermissionDummies() {
        all = new ArrayList<>();
        Permission p1 = new Permission();
        p1.setName("test.permission.1");
        p1.setId(1);
        Permission p2 = new Permission();
        p2.setName("test.permission.2");
        p2.setId(2);
        all.add(p1);
        all.add(p2);
    }

    public List<Permission> getAll() {
        return all;
    }
}
