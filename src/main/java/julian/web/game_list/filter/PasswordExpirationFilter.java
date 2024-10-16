package julian.web.game_list.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import julian.web.game_list.model.User;

public class PasswordExpirationFilter extends OncePerRequestFilter {

    private List<String> excludedUrls = Arrays.asList("/changePassword", "/css/", "/js/", "/icons/", "/images/", "h2-console", "/logout");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Force the user to change their password if it is required
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        // Exclude the excluded URLs from the filter
        String requestUrl = request.getRequestURI();
        if (excludedUrls.stream().anyMatch(requestUrl::contains)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof User) {
            User user = (User) auth.getPrincipal();
            if (user.isPasswordChangeRequired()) {
                //logout
                //new SecurityContextLogoutHandler().logout(request, response, auth);
                response.sendRedirect(request.getContextPath() + "/changePassword");
                return;
            }
        }
        
        filterChain.doFilter(request, response);
    }
}