package matrimonio.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import matrimonio.backend.enums.Dieta;
import matrimonio.backend.enums.Partecipazione;
import matrimonio.backend.enums.Ruolo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Invitati implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String username;
    private String password; // should be stored as a hash

    @ElementCollection
    @CollectionTable(name = "invitati_allergies", joinColumns = @JoinColumn(name = "invitati_id"))
    @Column(name = "allergy")
    private List<String> allergies;

    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    @Enumerated(EnumType.STRING)
    private Dieta dieta;

    @Enumerated(EnumType.STRING)
    private Partecipazione partecipazione;

    private boolean plusOneAllowed;
    private boolean plusOneConfirmed;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "plus_one_id") // Specifica la colonna di join per chiarezza
    private PlusOne plusOne; // Questa relazione può essere null

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Assume che il campo 'ruolo' rappresenti il ruolo dell'utente, convertendolo in un'autorità di Spring Security
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + ruolo.name()));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implementare la logica se necessario
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implementare la logica se necessario
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implementare la logica se necessario
    }

    @Override
    public boolean isEnabled() {
        return true; // Implementare la logica se necessario
    }
}
