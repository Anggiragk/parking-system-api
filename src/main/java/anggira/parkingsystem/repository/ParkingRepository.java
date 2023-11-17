package anggira.parkingsystem.repository;

import anggira.parkingsystem.entity.Parking;
import anggira.parkingsystem.entity.constant.VehiclesType;
import anggira.parkingsystem.model.response.ParkingResponse;
import anggira.parkingsystem.model.response.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, String> {
    Optional<Parking> findFirstBySlotAndIsUsed(Integer slot, Boolean isUsed);
    Optional<Parking> findFirstByIdAndIsUsed(String parkingId, Boolean isUsed);
    Optional<Parking> findFirstBylicensePlateAndIsUsed(String slot, Boolean isUsed);
    Page<Parking> findAllByIsUsed(Boolean isUsed, Pageable pageable);
    Page<Parking> findAllByIsUsedAndVehiclesType(Boolean isUsed, VehiclesType vehiclesType, Pageable pageable);

}
