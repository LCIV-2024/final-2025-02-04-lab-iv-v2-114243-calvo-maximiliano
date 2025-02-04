package ar.edu.utn.frc.tup.lciii.dtos.common;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class CreateTelemetryRequest {
    private String ip;

    private String hostname;
    private Double cpuUsage;
    private Double hostDiskFree;
    private String microphoneState;
    private Boolean screenCaptureAllowed;
    private Boolean audioCaptureAllowed;
    private LocalDateTime dataDate;
}
