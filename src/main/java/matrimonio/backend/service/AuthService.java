package matrimonio.backend.service;

import matrimonio.backend.DTO.InvitatoLoginDto;
import matrimonio.backend.entity.Invitati;
import matrimonio.backend.exception.UnauthorizedException;
import matrimonio.backend.repository.InvitatiRepository;
import matrimonio.backend.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private InvitatiService invitatiService;
    @Autowired
    private JwtTool jwtTool;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticateUserAndCreateToken(InvitatoLoginDto invitatoLoginDto) {
        Invitati invitati = invitatiService.getInvitatoByUsername(invitatoLoginDto.getUsername())
                .orElseThrow(() -> new UnauthorizedException("User not found"));

        if (passwordEncoder.matches(invitatoLoginDto.getPassword(), invitati.getPassword())) {
            return jwtTool.createToken(invitati);
        } else {
            throw new UnauthorizedException("Invalid credentials");
        }
    }
}
