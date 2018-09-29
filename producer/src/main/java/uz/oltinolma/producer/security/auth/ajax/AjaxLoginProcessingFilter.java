package uz.oltinolma.producer.security.auth.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import uz.oltinolma.producer.security.common.LogUtil;
import uz.oltinolma.producer.security.common.WebUtil;
import uz.oltinolma.producer.security.exceptions.AuthMethodNotSupportedException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private static final Logger logger = LogUtil.getInstance();


    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;

    private final ObjectMapper objectMapper;
    
    public AjaxLoginProcessingFilter(String defaultProcessUrl, AuthenticationSuccessHandler successHandler, 
            AuthenticationFailureHandler failureHandler, ObjectMapper mapper) {
        super(defaultProcessUrl);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.objectMapper = mapper;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        validateRequest(request);
        LoginRequest loginRequest = extractLoginRequestFromBody(request);

        validateLoginAndPasswordInput(loginRequest);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword());
        return this.getAuthenticationManager().authenticate(token);
    }

    private LoginRequest extractLoginRequestFromBody(HttpServletRequest request) throws IOException {
        return objectMapper.readValue(request.getReader(), LoginRequest.class);
    }

    private void validateRequest(HttpServletRequest request) {
        if (!HttpMethod.POST.name().equals(request.getMethod()) || !WebUtil.isAjax(request)) {
            logger.error("Authentication method not supported. Request method: " + request.getMethod());
            throw new AuthMethodNotSupportedException("Authentication method not supported.");
        }
    }

    private void validateLoginAndPasswordInput(LoginRequest loginRequest) {
        if (StringUtils.isBlank(loginRequest.getLogin()) || StringUtils.isBlank(loginRequest.getPassword())) {
            throw new AuthenticationServiceException("username yoki password ko'rsatilmagan");
        }
    }

}
