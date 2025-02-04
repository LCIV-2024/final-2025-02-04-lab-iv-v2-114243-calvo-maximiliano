package ar.edu.utn.frc.tup.lciii.service.serviceImpl;

import ar.edu.utn.frc.tup.lciii.dtos.common.CreateDeviceRequest;
import ar.edu.utn.frc.tup.lciii.model.Device;
import ar.edu.utn.frc.tup.lciii.model.DeviceType;
import ar.edu.utn.frc.tup.lciii.model.Telemetry;
import ar.edu.utn.frc.tup.lciii.repository.DeviceRepository;
import ar.edu.utn.frc.tup.lciii.repository.TelemetryRepository;
import ar.edu.utn.frc.tup.lciii.service.DeviceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final TelemetryRepository telemetryRepository;

    private final RestTemplate restTemplate;

    public DeviceServiceImpl(DeviceRepository deviceRepository, TelemetryRepository telemetryRepository, RestTemplate restTemplate) {
        this.deviceRepository = deviceRepository;
        this.telemetryRepository = telemetryRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public Device createDevice(CreateDeviceRequest deviceDTO) {
        if (deviceRepository.existsByHostName(deviceDTO.getHostname())) {
            throw new RuntimeException("Device already exists with hostname: " + deviceDTO.getHostname());
        }
        if (deviceRepository.existsByMacAddress(deviceDTO.getMacAddress())) {
            throw new RuntimeException("Device already exists with MAC address: " + deviceDTO.getMacAddress());
        }

        Device device = Device.builder()
                .hostName(deviceDTO.getHostname())
                .type(deviceDTO.getType())
                .os(deviceDTO.getOs())
                .macAddress(deviceDTO.getMacAddress())
                .createdDate(LocalDateTime.now())
                .build();

        return deviceRepository.save(device);
    }

    @Override
    public List<Device> getDevicesByType(DeviceType type) {
        return deviceRepository.findByType(type);
    }

    @Override
    public List<Device> getDevicesByCpuUsageRange(Double lowThreshold, Double upThreshold) {
        if (lowThreshold > upThreshold) {
            throw new IllegalArgumentException("Lower threshold cannot be greater than upper threshold");
        }

        return deviceRepository.findAll().stream()
                .filter(device -> {
                    Optional<Telemetry> latestTelemetry = telemetryRepository
                            .findFirstByHostnameOrderByDataDateDesc(device.getHostName());
                    return latestTelemetry.map(telemetry ->
                            telemetry.getCpuUsage() >= lowThreshold &&
                                    telemetry.getCpuUsage() <= upThreshold
                    ).orElse(false);
                })
                .collect(Collectors.toList());
    }

    @Value("${api.device.url}")
    private String deviceApiUrl;

    @Override
    public void saveConsumedDevices() {
        ResponseEntity<List<CreateDeviceRequest>> response = restTemplate.exchange(
                deviceApiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CreateDeviceRequest>>() {}
        );

        List<CreateDeviceRequest> devices = response.getBody();
        if (devices == null || devices.isEmpty()) {
            return;
        }

        int devicesToSave = devices.size() / 2;
        Collections.shuffle(devices);

        devices.stream()
                .limit(devicesToSave)
                .map(dto -> Device.builder()
                        .hostName(dto.getHostname())
                        .type(dto.getType())
                        .os(dto.getOs())
                        .macAddress(dto.getMacAddress())
                        .createdDate(LocalDateTime.now())
                        .build())
                .forEach(deviceRepository::save);
    }

}
