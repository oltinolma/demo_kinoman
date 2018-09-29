package uz.oltinolma.producer.security.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.oltinolma.producer.security.model.UserContext;

@Component("SecurityPermission")
public class SecurityPermission {
    public  boolean hasPermission(String permission) {
        UserContext userDetails = (UserContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getPermissions().contains(permission);
    }
}