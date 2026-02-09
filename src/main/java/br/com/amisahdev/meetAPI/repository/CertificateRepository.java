package br.com.amisahdev.meetAPI.repository;

import br.com.amisahdev.meetAPI.model.CertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CertificateRepository extends JpaRepository<CertificateEntity, UUID> {
}
