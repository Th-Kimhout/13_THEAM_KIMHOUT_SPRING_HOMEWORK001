package org.hrd._13_theam_kimhout_spring_homework001.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;


@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor

public class UpdatePaymentStatusReqDto {
    private ArrayList<Integer> ticketIds;
    private boolean paymentStatus;
}
