package br.com.fiap.gsjava.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Coral {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Status é obrigatório")
    private String status;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dt_foto;

    @NotBlank(message = "Função é obrigatório")
    private String funcao;

    @NotBlank(message = "Localização é obrigatório")
    private String localizacao;

    @NotBlank(message = "Pais é obrigatório")
    private String pais;

    @NotBlank(message = "Estado é obrigatorio")
    private String estado;

}
