package pda.server.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import pda.server.Auth.JwtTokenUtil;
import pda.server.Controller.RestException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/connectTest/*", "/message/*", "/user/*", "/Community/*","/JoinGroup/*", "/payments/*","/GuestBook/*", "/group/*"})
public class JWTFilter implements Filter {

    @Autowired
    JwtTokenUtil token;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String tk = ((HttpServletRequest)servletRequest).getHeader("JWT");

        try {
            if (!token.isTokenExpired(tk)) {
                servletRequest.setAttribute("U_ID", token.getUIDFromToken(tk));
            } else {
                throw new Exception();
            }
            filterChain.doFilter(servletRequest,servletResponse);
        }
        catch (Exception e) {
            e.printStackTrace();
            ((HttpServletResponse)servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    public void destroy() {
    }
}
