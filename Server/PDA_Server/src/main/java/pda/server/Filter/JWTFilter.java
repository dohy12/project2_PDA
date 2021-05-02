package pda.server.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import pda.server.Auth.JwtTokenUtil;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/connectTest/*"})
public class JWTFilter implements Filter {

    @Autowired
    JwtTokenUtil token;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String tk = ((HttpServletRequest)servletRequest).getHeader("JWT");
        if(!token.isTokenExpired(tk))
        {
            servletRequest.setAttribute("U_ID", token.getUIDFromToken(tk));
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
    }
}
