package ar.edu.utn.frc.tup.lciii.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "DEVICE")
public class Device {

    @Id
    @Column(name = "HOSTNAME", unique = true)
    private String hostName;
    private LocalDateTime createdDate;
    private String os;
    @Column(unique = true)
    private String macAddress;

    @OneToOne(mappedBy = "device")
    private Telemetry telemetry;

  @Column(name = "TYPE")
  @Enumerated(EnumType.STRING)
  private DeviceType type;

}
