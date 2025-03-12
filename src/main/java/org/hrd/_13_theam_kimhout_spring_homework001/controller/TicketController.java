package org.hrd._13_theam_kimhout_spring_homework001.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;

import org.hrd._13_theam_kimhout_spring_homework001.helper.PaginationInfo;
import org.hrd._13_theam_kimhout_spring_homework001.helper.TicketStatus;
import org.hrd._13_theam_kimhout_spring_homework001.model.Ticket;
import org.hrd._13_theam_kimhout_spring_homework001.response.ApiResponse;
import org.hrd._13_theam_kimhout_spring_homework001.model.dto.CreateTicketReqDto;
import org.hrd._13_theam_kimhout_spring_homework001.model.dto.UpdatePaymentStatusReqDto;

import org.hrd._13_theam_kimhout_spring_homework001.response.PageResponseListTicket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.*;
import java.util.stream.Collectors;

@OpenAPIDefinition(info = @Info(title = "Ticketing System for Public Transport", version = "1.0", description = "The Ticketing System for Public Transport is a Spring Boot application for managing ticket bookings, payments, and scheduling."))

@RestController
@RequestMapping("api/v1/tickets")
public class TicketController {
    ArrayList<Ticket> tickets = new ArrayList<>();

    public TicketController() {

        tickets.add(new Ticket(1, "John Doe", LocalDate.of(2023, 10, 1),
                "StationA", "StationB", 150.0, true, TicketStatus.COMPLETED, "1"));

        tickets.add(new Ticket(2, "Jane Smith", LocalDate.of(2023, 10, 2),
                "StationC", "StationD", 200.0, false, TicketStatus.CANCELLED, "3"));

        tickets.add(new Ticket(3, "Alice Johnson", LocalDate.of(2023, 10, 3),
                "StationE", "StationF", 175.0, true, TicketStatus.BOOKED, "6"));

        tickets.add(new Ticket(4, "Bob Brown", LocalDate.of(2023, 10, 4),
                "StationG", "StationH", 190.0, false, TicketStatus.CANCELLED, "2"));

        tickets.add(new Ticket(5, "Charlie Davis", LocalDate.of(2023, 10, 5),
                "StationI", "StationJ", 160.0, false, TicketStatus.CANCELLED, "8"));
    }


    @Operation(summary = "Get all tickets")
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponseListTicket>> getAllTickets(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        int startIndex = (page - 1) * size;
        int totalPages = (int) Math.ceil((double) tickets.size() / size);
        PaginationInfo pagination = new PaginationInfo(tickets.size(), page, size, totalPages);
        ArrayList<Ticket> subTickets = tickets.stream().skip(startIndex).limit(size).collect(Collectors.toCollection(ArrayList::new));

        ApiResponse<PageResponseListTicket> response = new ApiResponse<>(true, "Tickets Retrieved Successfully", HttpStatus.OK, new PageResponseListTicket(subTickets, pagination), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @Operation(summary = "Bulk update payment status for multiple tickets")
    @PutMapping
    public ResponseEntity<ApiResponse<ArrayList<Ticket>>> updateTicketPaymentStatus(@RequestBody UpdatePaymentStatusReqDto updatePaymentStatusReqDto) {

        ArrayList<Ticket> updatedTickets = tickets.stream().filter(ticket -> updatePaymentStatusReqDto.getTicketIds().contains(ticket.getTicketId())).peek(ticket -> ticket.setPaymentStatus(updatePaymentStatusReqDto.isPaymentStatus())).collect(Collectors.toCollection(ArrayList::new));
        ApiResponse<ArrayList<Ticket>> response = new ApiResponse<>(true, "Updated Tickets Payment Status Successfully", HttpStatus.OK, updatedTickets, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Create new ticket")
    @PostMapping
    public ResponseEntity<ApiResponse<Ticket>> createTicket(@RequestBody CreateTicketReqDto createTicketReqDto) {
        int latestId = tickets.stream().max(Comparator.comparingInt(Ticket::getTicketId)).map(Ticket::getTicketId).orElse(0);

        Ticket newTicket = new Ticket(latestId + 1, createTicketReqDto.getPassengerName(), createTicketReqDto.getTravelDate(), createTicketReqDto.getSourceStation(), createTicketReqDto.getDestinationStation(), createTicketReqDto.getPrice(), createTicketReqDto.isPaymentStatus(), createTicketReqDto.getTicketStatus(), createTicketReqDto.getSeatNumber());

        tickets.add(newTicket);

        ApiResponse<Ticket> response = new ApiResponse<>(true, "Create Ticket Successfully", HttpStatus.OK, newTicket, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get a ticket by ID")
    @GetMapping("/{ticket-id}")
    public ResponseEntity<ApiResponse<Ticket>> getTicketById(@PathVariable(name = "ticket-id") Integer ticketId) {
        Ticket foundTicket = tickets.stream().filter(t -> t.getTicketId() == ticketId).findFirst().orElse(null);
        ApiResponse<Ticket> response = new ApiResponse<>(true, "Retrieve Tickets Successfully", HttpStatus.OK, foundTicket, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Update existing ticket by ID")
    @PutMapping("/{ticket-id}")
    public ResponseEntity<ApiResponse<Ticket>> updateTicketById(@RequestBody CreateTicketReqDto createTicketReqDto, @PathVariable(name = "ticket-id") Integer ticketId) {
        Ticket updatedTicket = tickets.stream().filter(t -> t.getTicketId() == ticketId).peek(ticket -> {

            ticket.setPassengerName(createTicketReqDto.getPassengerName());
            ticket.setTravelDate(createTicketReqDto.getTravelDate());
            ticket.setSourceStation(createTicketReqDto.getSourceStation());
            ticket.setDestinationStation(createTicketReqDto.getDestinationStation());
            ticket.setPrice(createTicketReqDto.getPrice());
            ticket.setPaymentStatus(createTicketReqDto.isPaymentStatus());
            ticket.setTicketStatus(createTicketReqDto.getTicketStatus());
            ticket.setSeatNumber(createTicketReqDto.getSeatNumber());

        }).findFirst().orElse(null);


        ApiResponse<Ticket> response = new ApiResponse<>(true, "Update Ticket Successfully", HttpStatus.OK, updatedTicket, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Delete a ticket by ID")
    @DeleteMapping("/{ticket-id}")
    public ResponseEntity<Object> deleteTicketById(@PathVariable(name = "ticket-id") Integer ticketId) {
        boolean isDeleted = tickets.removeIf(ticket -> ticket.getTicketId() == ticketId);
        if (isDeleted) {
            ApiResponse<Ticket> response = new ApiResponse<>(true, "Delete Ticket Successfully", HttpStatus.OK, null, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        ApiResponse<Ticket> response = new ApiResponse<>(false, "No Ticket is found with ID : " + ticketId, HttpStatus.NOT_FOUND, null, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @Operation(summary = "Bulk create tickets")
    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<ArrayList<Ticket>>> bulkCreateTicket(@RequestBody ArrayList<CreateTicketReqDto> createTicketReqDtoList) {
        ArrayList<Ticket> addedTickets = new ArrayList<>();

        createTicketReqDtoList.forEach(createTicketReqDto -> {
            int latestId = tickets.stream().max(Comparator.comparingInt(Ticket::getTicketId)).map(Ticket::getTicketId).orElse(0);

            Ticket newTicket = new Ticket(latestId + 1, createTicketReqDto.getPassengerName(), createTicketReqDto.getTravelDate(), createTicketReqDto.getSourceStation(), createTicketReqDto.getDestinationStation(), createTicketReqDto.getPrice(), createTicketReqDto.isPaymentStatus(), createTicketReqDto.getTicketStatus(), createTicketReqDto.getSeatNumber());

            tickets.add(newTicket);
            addedTickets.add(newTicket);
        });
        ApiResponse<ArrayList<Ticket>> response = new ApiResponse<>(true, "Create Tickets Successfully", HttpStatus.OK, addedTickets, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Search ticket by passenger name")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<ArrayList<Ticket>>> searchTicketByPassengerName(@RequestParam String passengerName) {
        ArrayList<Ticket> foundTickets = tickets.stream().filter(t -> t.getPassengerName().equals(passengerName)).collect(Collectors.toCollection(ArrayList::new));
        if (foundTickets.isEmpty()) {
            ApiResponse<ArrayList<Ticket>> response = new ApiResponse<>(false, "Cannot find Ticket(s) with passenger name : " + passengerName, HttpStatus.NOT_FOUND, foundTickets, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        ApiResponse<ArrayList<Ticket>> response = new ApiResponse<>(true, "Retrieved Tickets Successfully", HttpStatus.OK, foundTickets, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Filter tickets by status and travel date")
    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<ArrayList<Ticket>>> searchTicketBySourceStation(@RequestParam TicketStatus ticketStatus, @RequestParam LocalDate travelDate) {
        ArrayList<Ticket> foundTickets = tickets.stream().filter(ticket -> ticket.getTicketStatus().toString().equals(ticketStatus.toString()) && ticket.getTravelDate().isEqual(travelDate)).collect(Collectors.toCollection(ArrayList::new));
        if (foundTickets.isEmpty()) {
            ApiResponse<ArrayList<Ticket>> response = new ApiResponse<>(false, "Cannot find Ticket(s) with Ticket Status : " + ticketStatus + " and Date : " + travelDate, HttpStatus.NOT_FOUND, foundTickets, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        ApiResponse<ArrayList<Ticket>> response = new ApiResponse<>(true, "Retrieved Tickets Successfully", HttpStatus.OK, foundTickets, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
