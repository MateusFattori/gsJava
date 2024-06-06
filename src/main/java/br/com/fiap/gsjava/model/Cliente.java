package br.com.fiap.gsjava.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;

     @NotBlank(message = "{cliente.nome.notblank}")
     private String nome;
     
     @NotBlank(message = "{cliente.email.notblank}")
     private String email;

     @NotBlank(message = "{cliete.senha.notblank}")
     private String senha;

     @NotBlank(message = "{cliente.confirmarSenha.notblank}")
     private String confirmarSenha;
}
