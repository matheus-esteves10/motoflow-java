package br.com.fiap.motoflow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Table(name = "t_mtf_operador")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Operador implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_id_registro")
    private Long id;

    @NotBlank
    @Size(max = 120)
    @Column(name = "nm_operador", nullable = false, length = 120)
    private String nome;

    @NotBlank
    @Size(max = 255)
    @Column(name = "ds_senha", nullable = false, length = 255)
    private String senha;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cd_id_patio", nullable = false)
    private Patio patio;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return String.valueOf(id);
    }
}

