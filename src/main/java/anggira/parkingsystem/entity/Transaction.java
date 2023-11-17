package anggira.parkingsystem.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
@EntityListeners({
        AuditingEntityListener.class
})
public class Transaction {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "payment_amount")
    private Long paymentAmount;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "payment_change")
    private Long paymentChange;

    @OneToOne
    @JoinColumn(name = "parking_id")
    private Parking parking;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;
}
