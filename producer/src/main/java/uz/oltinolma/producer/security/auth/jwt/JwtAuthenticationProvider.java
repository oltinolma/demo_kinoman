package uz.oltinolma.producer.security.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import uz.oltinolma.producer.security.auth.JwtAuthenticationToken;
import uz.oltinolma.producer.security.config.JwtSettings;
import uz.oltinolma.producer.security.model.UserContext;
import uz.oltinolma.producer.security.mvc.permission.service.PermissionService;
import uz.oltinolma.producer.security.token.RawAccessJwtToken;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@SuppressWarnings("unchecked")
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationProvider.class.getName());
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
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();
        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtSettings.getTokenSigningKey());
        String login = jwsClaims.getBody().get("login", String.class);

        Set<String> permissionNames = permissionService.getByLogin(login);

        List<GrantedAuthority> authorities = permissionNames
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserContext context = UserContext.create(login, permissionNames);
        return new JwtAuthenticationToken(context, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
