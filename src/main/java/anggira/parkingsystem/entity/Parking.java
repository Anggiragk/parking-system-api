package anggira.parkingsystem.entity;

import anggira.parkingsystem.entity.constant.VehiclesType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "parkings")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
@EntityListeners({
        AuditingEntityListener.class
})
public class Parking {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "slot")
    private Integer slot;

    @Column(name = "license_plate")
    private String licensePlate;

    @Column(name = "vehicle_brand")
    private String vehicleBrand;

    @Column(name = "vehicle_color")
    private String vehicleColor;

    @Column(name = "vehicle_type")
    @Enumerated(EnumType.STRING)
    private VehiclesType vehiclesType;

    @Column(name = "parking_fee")
    private Long parkingFee;

    @Column(name = "is_used")
    private Boolean isUsed;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
