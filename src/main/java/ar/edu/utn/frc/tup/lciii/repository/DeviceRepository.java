package ar.edu.utn.frc.tup.lciii.repository;

import ar.edu.utn.frc.tup.lciii.model.Device;
import ar.edu.utn.frc.tup.lciii.model.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {
    List<Device> findByType(DeviceType type);
    boolean existsByMacAddress(String macAddress);
    boolean existsByHostName(String hostname);
}
