package org.hrd._13_theam_kimhout_spring_homework001.model;

import lombok.*;
import org.hrd._13_theam_kimhout_spring_homework001.helper.TicketStatus;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    private int ticketId;
    private String passengerName;
    private LocalDate travelDate;
    private String sourceStation;
    private String destinationStation;
    private double price;
    private Boolean paymentStatus;
    private TicketStatus ticketStatus;
    private String seatNumber;
}
