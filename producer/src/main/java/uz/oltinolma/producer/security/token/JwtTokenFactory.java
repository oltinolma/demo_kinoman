package uz.oltinolma.producer.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import uz.oltinolma.producer.security.mvc.user.User;
import uz.oltinolma.producer.security.mvc.user.service.UserService;
import uz.oltinolma.producer.security.config.JwtSettings;
import uz.oltinolma.producer.security.model.UserContext;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenFactory {
    private final JwtSettings settings;
    private UserService userService;

    @Autowired
    @Qualifier("userServiceH2Impl")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public JwtTokenFactory(JwtSettings settings) {
        this.settings = settings;
    }

    public AccessJwtToken createAccessJwtToken(UserContext userContext) {
        validateUserContext(userContext);

        Claims claims = Jwts.claims().setSubject(userContext.getLogin());
        claims.put("scopes", userContext.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));
        DateTime currentTime = new DateTime();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(settings.getTokenIssuer())
                .setIssuedAt(currentTime.toDate())
                .setExpiration(currentTime.plusMinutes(settings.getTokenExpirationTime()).toDate())
                .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
                .compact();

        return new AccessJwtToken(token, claims);
    }

    private void validateUserContext(UserContext userContext) {
        String login = userContext.getLogin();
        if (StringUtils.isBlank(login))
            throw new IllegalArgumentException("Cannot create JWT Token without username");

        if (userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty())
            throw new IllegalArgumentException("User doesn't have any privileges");
    }

    public AccessJwtToken createAccessJwtToken(UserContext userContext, Date issuedAt, Date expiresAt) {
        validateUserContext(userContext);
        Claims claims = Jwts.claims().setSubject(userContext.getLogin());
        claims.put("scopes", userContext.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(settings.getTokenIssuer())
                .setIssuedAt(issuedAt)
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
                .compact();

        return new AccessJwtToken(token, claims);
    }

}
