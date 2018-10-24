package uz.oltinolma.producer.security.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RTAuthenticationProcessingFilter extends GenericFilterBean {
    private SecurityPermission permission;

    public RTAuthenticationProcessingFilter(SecurityPermission permission) {
        this.permission = permission;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("RTAuthenticationProcessingFilter#filter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (StringUtils.isEmpty(request.getHeader(WebSecurityConfig.ROUTING_KEY_HEADER_PARAM))) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cannot find routing header param");
        } else if (!permission.hasPermission(request.getHeader(WebSecurityConfig.ROUTING_KEY_HEADER_PARAM))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission!!!");
        } else
            filterChain.doFilter(request, response);
    }
}
