package anggira.parkingsystem.model.response;

import anggira.parkingsystem.entity.constant.VehiclesType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ParkingResponse {
    private String id;
    private Integer slot;
    private String licensePlate;
    private String vehicleBrand;
    private String vehicleColor;
    private VehiclesType vehiclesType;
    private Long parkingPricePerHour;
    private LocalDateTime createdAt;
    private Boolean isUsed;
}
