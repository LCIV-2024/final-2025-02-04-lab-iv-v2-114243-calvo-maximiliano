package ar.edu.utn.frc.tup.lciii.dtos.common;

import ar.edu.utn.frc.tup.lciii.model.DeviceType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class CreateDeviceRequest {
    private String hostname;
    private DeviceType type;
    private String os;
    private String macAddress;
}
