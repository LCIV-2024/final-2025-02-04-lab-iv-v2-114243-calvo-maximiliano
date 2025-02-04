package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CreateDeviceRequest;
import ar.edu.utn.frc.tup.lciii.model.Device;
import ar.edu.utn.frc.tup.lciii.model.DeviceType;

import java.util.List;

public interface DeviceService {
    Device createDevice(CreateDeviceRequest createDeviceRequest);
    List<Device> getDevicesByType(DeviceType type);
    List<Device> getDevicesByCpuUsageRange(Double lowThreshold, Double upThreshold);
    void saveConsumedDevices();

}
