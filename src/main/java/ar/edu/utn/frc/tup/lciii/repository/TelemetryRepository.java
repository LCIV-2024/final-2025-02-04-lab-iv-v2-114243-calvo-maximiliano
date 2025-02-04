package ar.edu.utn.frc.tup.lciii.repository;

import ar.edu.utn.frc.tup.lciii.model.Telemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TelemetryRepository extends JpaRepository<Telemetry, Long> {
    List<Telemetry> findByHostname(String hostname);
    Optional<Telemetry> findFirstByHostnameOrderByDataDateDesc(String hostname);
}