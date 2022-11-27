package com.binar.finalproject.BEFlightTicket.dto;

import com.binar.finalproject.BEFlightTicket.model.*;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
public class OrderRequest {
    @NotEmpty(message = "Total ticket is required.")
    private Integer totalTicket;
    @NotEmpty(message = "Total price is required.")
    private Float totalPrice;
    @NotEmpty(message = "PNR code is required.")
    private String pnrCode;
    @NotEmpty(message = "Order status is required.")
    private String status;
    @NotEmpty(message = "User ID is required.")
    private UUID userId;
    @NotEmpty(message = "Payment ID is required.")
    private Integer paymentId;
    public Orders toOrders (Users users, PaymentMethods paymentMethods)
    {
        return Orders.builder()
                .totalTicket(this.totalTicket)
                .totalPrice(this.totalPrice)
                .pnrCode(this.pnrCode)
                .status(this.status)
                .usersOrder(users)
                .paymentMethodsOrder(paymentMethods)
                .build();
    }
}