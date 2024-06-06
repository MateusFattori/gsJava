package br.com.fiap.gsjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.gsjava.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
