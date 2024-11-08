package com.orderiFy.app.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="payment_details")
public class Payment {



    @Id
    @Column(name="order_id")
     private Long orderId;




    @Column(name ="payment_id")
    private long paymentId;

    @Enumerated(EnumType.STRING)
    @Column(name="payment_mode")
    private PaymentMode paymentMode;


    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;






    public enum PaymentMode{
        UPI,NET_BANKING,DEBIT,CREDIT

    }

    public enum PaymentStatus{
        SUCCESS , FAILED ,PENDING ,REJECTED
    }


}
