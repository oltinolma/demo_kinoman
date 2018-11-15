package uz.oltinolma.producer.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.oltinolma.producer.security.config.JwtSettings;
import uz.oltinolma.producer.security.model.UserContext;

import java.util.Date;

@Component
public class JwtTokenFactory {
    private final JwtSettings settings;

    @Autowired
    public JwtTokenFactory(JwtSettings settings) {
        this.settings = settings;
    }

    public AccessJwtToken createAccessJwtToken(UserContext userContext) {
        DateTime now = new DateTime();
        return createAccessJwtToken(userContext,now.toDate(), now.plusMinutes(settings.getTokenExpirationTime()).toDate());

    }

    public AccessJwtToken createAccessJwtToken(UserContext userContext, Date issuedAt, Date expiresAt) {
        validateUserContext(userContext);
        Claims claims = Jwts.claims();
        claims.put("login", userContext.getLogin());
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(settings.getTokenIssuer())
                .setIssuedAt(issuedAt)
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
                .compact();

        return new AccessJwtToken(token, claims);
    }

    private void validateUserContext(UserContext userContext) {
        String login = userContext.getLogin();
        if (StringUtils.isBlank(login))
            throw new IllegalArgumentException("Cannot create JWT Token without username");
    }

}
