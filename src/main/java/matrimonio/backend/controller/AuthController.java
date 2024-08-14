package matrimonio.backend.controller;

import matrimonio.backend.DTO.InvitatoLoginDto;
import matrimonio.backend.exception.BadRequestException;
import matrimonio.backend.service.AuthService;
import matrimonio.backend.service.InvitatiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService; //verifica se è autenticato e posso dare token
    @Autowired
    private InvitatiService invitatiService; //registra l'utente


    @PostMapping("/auth/login")
    public String login (@RequestBody @Validated InvitatoLoginDto invitatoLoginDto, BindingResult bindingResult){  //se il metodo funziona torna un token

        if (bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().stream().
                    map(error-> error.getDefaultMessage()).
                    reduce("", (s, s2) -> s+s2));
        }

        return authService.authenticateUserAndCreateToken(invitatoLoginDto);
    }
}




