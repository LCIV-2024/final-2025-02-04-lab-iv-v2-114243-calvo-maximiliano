package ar.edu.utn.frc.tup.lciii.service.serviceImpl;

import ar.edu.utn.frc.tup.lciii.dtos.common.CreateTelemetryRequest;
import ar.edu.utn.frc.tup.lciii.model.Device;
import ar.edu.utn.frc.tup.lciii.model.Telemetry;
import ar.edu.utn.frc.tup.lciii.repository.DeviceRepository;
import ar.edu.utn.frc.tup.lciii.repository.TelemetryRepository;
import ar.edu.utn.frc.tup.lciii.service.TelemetryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TelemetryServiceImpl implements TelemetryService {
    private final DeviceRepository deviceRepository;
    private final TelemetryRepository telemetryRepository;

    public TelemetryServiceImpl(DeviceRepository deviceRepository, TelemetryRepository telemetryRepository) {
        this.deviceRepository = deviceRepository;
        this.telemetryRepository = telemetryRepository;
    }

    @Override
    public Telemetry createTelemetry(CreateTelemetryRequest createTelemetryRequest) {
        if (!deviceRepository.existsByHostName(createTelemetryRequest.getHostname())) {
            throw new RuntimeException("Device not found with hostname: " + createTelemetryRequest.getHostname());
        }

        Telemetry telemetry = Telemetry.builder()
                .hostname(createTelemetryRequest.getHostname())
                .cpuUsage(createTelemetryRequest.getCpuUsage())
                .hostDiskFree(createTelemetryRequest.getHostDiskFree())
                .microphoneState(createTelemetryRequest.getMicrophoneState())
                .screenCaptureAllowed(createTelemetryRequest.getScreenCaptureAllowed())
                .audioCaptureAllowed(createTelemetryRequest.getAudioCaptureAllowed())
                .dataDate(LocalDateTime.now())
                .ip(createTelemetryRequest.getIp())
                .build();

        return telemetryRepository.save(telemetry);
    }

    @Override
    public List<Telemetry> getAllTelemetry() {
        return telemetryRepository.findAll();
    }

    @Override
    public List<Telemetry> getTelemetryByHostname(String hostname) {
        return telemetryRepository.findByHostname(hostname);
    }
}

