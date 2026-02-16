package br.com.amisahdev.meetapi.repository;

import br.com.amisahdev.meetapi.model.CertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CertificateRepository extends JpaRepository<CertificateEntity, UUID> {
}
