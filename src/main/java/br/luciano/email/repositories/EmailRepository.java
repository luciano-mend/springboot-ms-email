package br.luciano.email.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.luciano.email.models.EmailModel;

public interface EmailRepository extends JpaRepository<EmailModel, UUID>{

}
