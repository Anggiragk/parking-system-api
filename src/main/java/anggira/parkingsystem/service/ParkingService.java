package anggira.parkingsystem.service;

import anggira.parkingsystem.entity.constant.VehiclesType;
import anggira.parkingsystem.model.request.ParkingRequest;
import anggira.parkingsystem.model.response.ParkingResponse;
import org.springframework.data.domain.Page;

public interface ParkingService {
    ParkingResponse inputParking(ParkingRequest parkingRequest);

    ParkingResponse updateParking(ParkingRequest parkingRequest);
    ParkingResponse getByParkingId(String parkingId);
    Page<ParkingResponse> getAllUsedParking(Integer page, Integer size);
    Page<ParkingResponse> getAllUsedParkingByVehicleType(Integer page, Integer size, VehiclesType vehiclesType);
    Page<ParkingResponse> getAllUnusedParking(Integer page, Integer size);
    Page<ParkingResponse> getAllUnusedParkingByVehicleType(Integer page, Integer size, VehiclesType vehiclesType);

}
