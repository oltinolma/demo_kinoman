package uz.oltinolma.producer.security.auth.ajax;


import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uz.oltinolma.producer.security.common.LogUtil;
import uz.oltinolma.producer.security.mvc.user.User;
import uz.oltinolma.producer.security.mvc.user.service.UserService;
import uz.oltinolma.producer.security.mvc.permission.service.PermissionService;
import uz.oltinolma.producer.security.model.UserContext;

import java.util.ArrayList;
import java.util.List;
@Component
@Lazy
public class AjaxAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = LogUtil.getInstance();
    private final PasswordEncoder encoder;
    private final UserService userService;
    private PermissionService permissionService;

    @Autowired
    @Qualifier("permissionServiceH2Impl")
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Autowired
    public AjaxAuthenticationProvider(@Qualifier("userServiceH2Impl") final UserService userService, final PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.err.println("AJAX authenticate");
        Assert.notNull(authentication, "No authentication data provided");

        String login = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        User employee = userService.findByLogin(login);
        validateEmployee(employee);
        validatePassword(login, password, employee.getPassword());

//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(employee.getRole()));
        UserContext userContext = UserContext.create(employee.getLogin(), permissionService.getByLogin(login));
        return new UsernamePasswordAuthenticationToken(userContext, null, new ArrayList<>());
    }

    private void validatePassword(String login, String password, String expectedPw) {
        if (!encoder.matches(password, expectedPw)) {
            logger.error("Wrong credentials: login = " + login
                    + ", expected password = " + expectedPw
                    + ", actual row password = " + password
                    + ", actual encoded password = " + encoder.encode(password));

            throw new BadCredentialsException("Incorrect username or password.");
        }
    }

    private void validateEmployee(User user) {
        if (user == null)
            throw new UsernameNotFoundException("Login yoki parol xato!");

        if (!user.isEnable())
            throw new UsernameNotFoundException("Siz uchun kirish yopilgan!");

        if (user.getRole().isEmpty())
            throw new InsufficientAuthenticationException("Foydalanuvchini huquqlar yo'q");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
