package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CreateTelemetryRequest;
import ar.edu.utn.frc.tup.lciii.model.Telemetry;

import java.util.List;

public interface TelemetryService {

    Telemetry createTelemetry(CreateTelemetryRequest createTelemetryRequest);
    List<Telemetry> getAllTelemetry();
    List<Telemetry> getTelemetryByHostname(String hostname);
}
