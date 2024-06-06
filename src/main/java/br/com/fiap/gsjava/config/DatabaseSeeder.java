package br.com.fiap.gsjava.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.fiap.gsjava.model.Cliente;
import br.com.fiap.gsjava.model.Coral;
import br.com.fiap.gsjava.repository.ClienteRepository;
import br.com.fiap.gsjava.repository.CoralRepository;


@Configuration
@Profile("dev")
public class DatabaseSeeder implements CommandLineRunner {
    
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    CoralRepository coralRepository;

    @Override
    public void run(String... args) throws Exception {

        clienteRepository.saveAll(
            List.of(
                Cliente.builder().id(1L).nome("joao").email("joao@gmail.com").senha("123").confirmarSenha("123").build(),
                Cliente.builder().id(2L).nome("gabriel").email("gabriel@gmail.com").senha("12345").confirmarSenha("12345").build(),
                Cliente.builder().id(3L).nome("ana").email("ana@gmail.com").senha("678").confirmarSenha("678").build(),
                Cliente.builder().id(4L).nome("magali").email("magali@gmail.com").senha("456789").confirmarSenha("456789").build()
            ));
        
        coralRepository.saveAll(
            List.of(
                Coral.builder().id(1L).status("Esbranquiçado").dt_foto(LocalDate.parse("2024-04-21")).funcao("adiministrador").localizacao("-42.865 52.987").pais("Brasil").estado("Bahia").build(),
                Coral.builder().id(2L).status("Esbranquiçado").dt_foto(LocalDate.parse("2023-09-12")).funcao("Juiz").localizacao("-42.865 52.987").pais("Brasil").estado("Parana").build(),
                Coral.builder().id(3L).status("Esbranquiçado").dt_foto(LocalDate.parse("2018-06-30")).funcao("Desenvolvedor").localizacao("-42.865 52.987").pais("Brasil").estado("Rio de Janeiro").build(),
                Coral.builder().id(4L).status("Esbranquiçado").dt_foto(LocalDate.parse("2015-10-14")).funcao("Engenheiro").localizacao("32.815 -12.747").pais("Brasil").estado("Sáo Paulo").build()
                ));
    }

}
