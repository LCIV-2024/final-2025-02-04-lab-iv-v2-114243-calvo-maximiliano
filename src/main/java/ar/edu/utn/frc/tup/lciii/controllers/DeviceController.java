package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.CreateDeviceRequest;
import ar.edu.utn.frc.tup.lciii.dtos.common.CreateTelemetryRequest;
import ar.edu.utn.frc.tup.lciii.model.Device;
import ar.edu.utn.frc.tup.lciii.model.DeviceType;
import ar.edu.utn.frc.tup.lciii.model.Telemetry;
import ar.edu.utn.frc.tup.lciii.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/device")
    @ResponseStatus(HttpStatus.CREATED)
    public Device createDevice(@RequestBody CreateDeviceRequest deviceDTO) {
        return deviceService.createDevice(deviceDTO);
    }


    @GetMapping("/device")
    public List<Device> getDevices(@RequestParam(required = false) DeviceType type) {
        if (type != null) {
            return deviceService.getDevicesByType(type);
        }
        return List.of();
    }

    @GetMapping("/device/tresh")
    public List<Device> getDevices(
            @RequestParam(required = false) DeviceType type,
            @RequestParam(required = false) Double lowThreshold,
            @RequestParam(required = false) Double upThreshold) {

        if (type != null) {
            return deviceService.getDevicesByType(type);
        }

        if (lowThreshold != null && upThreshold != null) {
            return deviceService.getDevicesByCpuUsageRange(lowThreshold, upThreshold);
        }

        return List.of();
    }

    @PostMapping("/save-consumed-devices")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveConsumedDevices() {
        deviceService.saveConsumedDevices();
    }

}