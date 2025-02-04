package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.CreateDeviceRequest;
import ar.edu.utn.frc.tup.lciii.dtos.common.CreateTelemetryRequest;
import ar.edu.utn.frc.tup.lciii.model.Device;
import ar.edu.utn.frc.tup.lciii.model.Telemetry;
import ar.edu.utn.frc.tup.lciii.service.DeviceService;
import ar.edu.utn.frc.tup.lciii.service.TelemetryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TelemetryController {

    private final TelemetryService telemetryService;
    private final DeviceService deviceService;


    public TelemetryController(TelemetryService telemetryService, DeviceService deviceService) {
        this.telemetryService = telemetryService;
        this.deviceService = deviceService;
    }


    @PostMapping("/telemetry")
    @ResponseStatus(HttpStatus.CREATED)
    public Telemetry createTelemetry(@RequestBody CreateTelemetryRequest telemetryDTO) {
        return telemetryService.createTelemetry(telemetryDTO);
    }

    @GetMapping("/telemetry")
    public List<Telemetry> getTelemetry(@RequestParam(required = false) String hostname) {
        if (hostname != null) {
            return telemetryService.getTelemetryByHostname(hostname);
        }
        return telemetryService.getAllTelemetry();
    }

}