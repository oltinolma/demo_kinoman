package uz.oltinolma.producer.security.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import uz.oltinolma.producer.security.common.LogUtil;
import uz.oltinolma.producer.security.mvc.user.User;
import uz.oltinolma.producer.security.mvc.user.service.UserService;
import uz.oltinolma.producer.security.mvc.permission.service.PermissionService;
import uz.oltinolma.producer.security.auth.JwtAuthenticationToken;
import uz.oltinolma.producer.security.config.JwtSettings;
import uz.oltinolma.producer.security.model.UserContext;
import uz.oltinolma.producer.security.token.RawAccessJwtToken;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@SuppressWarnings("unchecked")
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = LogUtil.getInstance();
    private final JwtSettings jwtSettings;
    @Autowired
    @Qualifier("permissionServiceH2Impl")
    private PermissionService permissionService;

    @Autowired
    @Qualifier("userServiceH2Impl")
    private UserService userService;

    @Autowired
    public JwtAuthenticationProvider(JwtSettings jwtSettings) {
        this.jwtSettings = jwtSettings;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();
        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtSettings.getTokenSigningKey());
        String login = jwsClaims.getBody().getSubject();
        String id_employee_str = String.valueOf(jwsClaims.getBody().get("id_employee"));
        UUID id_employee = null;
        if (id_employee_str.equals("null")) {
            throw new AuthenticationServiceException("Your token is expired!");
        } else if (!StringUtils.isEmpty(id_employee_str)) {
            id_employee = UUID.fromString(String.valueOf(id_employee_str));
        }
        User employee = userService.findByLogin(login);
        List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
        List<GrantedAuthority> authorities = scopes.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        List<GrantedAuthority> inFactAuthList = new ArrayList<>();
        GrantedAuthority inFactAuth = new SimpleGrantedAuthority(employee.getRole());
        inFactAuthList.add(inFactAuth);
        if (!authorities.retainAll(inFactAuthList)) {
            UserContext context = UserContext.create(login, authorities, permissionService.getByLogin(login));
            context.setId_employee(id_employee);
            return new JwtAuthenticationToken(context, context.getAuthorities());
        } else return null;//causes 401
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
