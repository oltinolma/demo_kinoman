package uz.oltinolma.producer.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RTAuthenticationProcessingFilter extends GenericFilterBean {

    private SecurityPermission permission;

    @Autowired
    public void setPermission(SecurityPermission permission) {
        this.permission = permission;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
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
