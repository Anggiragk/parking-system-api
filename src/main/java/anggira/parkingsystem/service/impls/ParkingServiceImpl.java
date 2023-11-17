package anggira.parkingsystem.service.impls;

import anggira.parkingsystem.entity.Parking;
import anggira.parkingsystem.entity.constant.VehiclesType;
import anggira.parkingsystem.model.request.ParkingRequest;
import anggira.parkingsystem.model.response.ParkingResponse;
import anggira.parkingsystem.repository.ParkingRepository;
import anggira.parkingsystem.service.ParkingService;
import anggira.parkingsystem.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingServiceImpl implements ParkingService {
    private final ParkingRepository parkingRepository;
    private final ValidationService validationService;

    private static ParkingResponse toParkingResponse(Parking parking) {
        return ParkingResponse.builder()
                .id(parking.getId())
                .slot(parking.getSlot())
                .licensePlate(parking.getLicensePlate())
                .vehicleBrand(parking.getVehicleBrand())
                .vehicleColor(parking.getVehicleColor())
                .vehiclesType(parking.getVehiclesType())
                .parkingPricePerHour(parking.getParkingFee())
                .createdAt(parking.getCreatedAt())
                .isUsed(parking.getIsUsed())
                .build();
    }

    @Override
    public ParkingResponse inputParking(ParkingRequest parkingRequest) {
        validationService.validate(parkingRequest);

        Parking checkSlot = parkingRepository.findFirstBySlotAndIsUsed(parkingRequest.getSlot(), true).orElse(null);
        Parking checkLicensePlate = parkingRepository.findFirstBylicensePlateAndIsUsed(parkingRequest.getLicensePlate(), true).orElse(null);
        if (Objects.nonNull(checkSlot)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "slot in used please choose another slot");
        }
        if (Objects.nonNull(checkLicensePlate)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vehicle already in parking");
        }

        Parking parking = Parking.builder()
                .slot(parkingRequest.getSlot())
                .licensePlate(parkingRequest.getLicensePlate())
                .vehicleBrand(parkingRequest.getVehicleBrand())
                .vehicleColor(parkingRequest.getVehicleColor())
                .vehiclesType(parkingRequest.getVehiclesType())
                .parkingFee(parkingRequest.getVehiclesType().getPrice())
                .isUsed(true)
                .build();
        parkingRepository.save(parking);

        return toParkingResponse(parking);
    }

    @Override
    @Transactional
    public ParkingResponse updateParking(ParkingRequest parkingRequest) {
        validationService.validate(parkingRequest);
        Parking checkSlot = parkingRepository.findFirstBySlotAndIsUsed(parkingRequest.getSlot(), true).orElse(null);
        Parking checkLicensePlate = parkingRepository.findFirstBylicensePlateAndIsUsed(parkingRequest.getLicensePlate(), true).orElse(null);
        Parking currentParking = parkingRepository.findFirstByIdAndIsUsed(parkingRequest.getId(), true).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking in use not found")
        );
        if (Objects.nonNull(checkSlot) && (!checkSlot.getSlot().equals(currentParking.getSlot()))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "slot in used please choose another slot");
        }
        if (Objects.nonNull(checkLicensePlate) && (!checkLicensePlate.getLicensePlate().equals(currentParking.getLicensePlate()))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vehicle already in parking");
        }

        currentParking.setSlot(parkingRequest.getSlot());
        currentParking.setLicensePlate(parkingRequest.getLicensePlate());
        currentParking.setVehicleBrand(parkingRequest.getVehicleBrand());
        currentParking.setVehicleColor(parkingRequest.getVehicleColor());
        currentParking.setVehiclesType(parkingRequest.getVehiclesType());

        return toParkingResponse(currentParking);
    }

    @Override
    public ParkingResponse getByParkingId(String parkingId) {
        Parking parkingById = parkingRepository.findById(parkingId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "parking not found")
        );
        return toParkingResponse(parkingById);
    }

    @Override
    public Page<ParkingResponse> getAllUsedParking(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Parking> parkings = parkingRepository.findAllByIsUsed(true, pageable);
        List<ParkingResponse> parkingResponses = parkings.stream().map(parking -> {
            return toParkingResponse(parking);
        }).collect(Collectors.toList());
        return new PageImpl<>(parkingResponses, pageable, parkings.getTotalElements());
    }

    @Override
    public Page<ParkingResponse> getAllUsedParkingByVehicleType(Integer page, Integer size, VehiclesType vehiclesType) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Parking> parkings = parkingRepository.findAllByIsUsedAndVehiclesType(true,vehiclesType, pageable);
        List<ParkingResponse> parkingResponses = parkings.stream().map(parking -> {
            return toParkingResponse(parking);
        }).collect(Collectors.toList());
        return new PageImpl<>(parkingResponses, pageable, parkings.getTotalElements());
    }

    @Override
    public Page<ParkingResponse> getAllUnusedParking(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Parking> parkings = parkingRepository.findAllByIsUsed(false, pageable);
        List<ParkingResponse> parkingResponses = parkings.stream().map(parking -> {
            return toParkingResponse(parking);
        }).collect(Collectors.toList());
        return new PageImpl<>(parkingResponses, pageable, parkings.getTotalElements());
    }

    @Override
    public Page<ParkingResponse> getAllUnusedParkingByVehicleType(Integer page, Integer size, VehiclesType vehiclesType) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Parking> parkings = parkingRepository.findAllByIsUsedAndVehiclesType(false, vehiclesType ,pageable);
        List<ParkingResponse> parkingResponses = parkings.stream().map(parking -> {
            return toParkingResponse(parking);
        }).collect(Collectors.toList());
        return new PageImpl<>(parkingResponses, pageable, parkings.getTotalElements());
    }

}
