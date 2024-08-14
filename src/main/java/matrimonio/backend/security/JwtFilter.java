package matrimonio.backend.security;

import matrimonio.backend.entity.Invitati;
import matrimonio.backend.exception.NotFoundException;
import matrimonio.backend.exception.UnauthorizedException;
import matrimonio.backend.service.InvitatiService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private InvitatiService invitatiService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Estrae il token
            try {
                jwtTool.verifyToken(token); // Verifica il token

                int invitatoId = jwtTool.getIdFromToken(token);
                Optional<Invitati> invitatiOptional = invitatiService.getInvitatoById(invitatoId);

                if (invitatiOptional.isPresent()) {
                    Invitati invitati = invitatiOptional.get();
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            invitati,
                            null,
                            invitati.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new NotFoundException("User with id=" + invitatoId + " not found");
                }
            } catch (Exception e) {
                // Gestione delle eccezioni generiche per il token
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Esclude i percorsi di autenticazione dal filtro
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
