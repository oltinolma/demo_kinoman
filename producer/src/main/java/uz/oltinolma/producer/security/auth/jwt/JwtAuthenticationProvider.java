package uz.oltinolma.producer.security.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import uz.oltinolma.producer.security.auth.JwtAuthenticationToken;
import uz.oltinolma.producer.security.common.LogUtil;
import uz.oltinolma.producer.security.config.JwtSettings;
import uz.oltinolma.producer.security.exceptions.JwtExpiredTokenException;
import uz.oltinolma.producer.security.model.UserContext;
import uz.oltinolma.producer.security.mvc.permission.service.PermissionService;
import uz.oltinolma.producer.security.token.RawAccessJwtToken;

import java.util.Set;

@Component
@SuppressWarnings("unchecked")
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = LogUtil.getInstance();
    private final JwtSettings jwtSettings;
    @Autowired
    @Qualifier("permissionServiceH2Impl")
    private PermissionService permissionService;

    @Autowired
    public JwtAuthenticationProvider(JwtSettings jwtSettings) {
        this.jwtSettings = jwtSettings;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = extractLoginFromToken(authentication);
        Set<String> permissionNames = permissionService.getPermissionsForUser(login);

        UserContext context = UserContext.create(login, permissionNames);
        return new JwtAuthenticationToken(context);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }

    private String extractLoginFromToken(Authentication authentication) {
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();
        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtSettings.getTokenSigningKey());
        String login = jwsClaims.getBody().get("login", String.class);
        validateLogin(login);
        return login;
    }

    private void validateLogin(String login) {
        if (login == null || login.isEmpty()) {
            logger.error("Token contains invalid login.");
            throw new JwtExpiredTokenException("Your token is expired.");
        }
    }
}
