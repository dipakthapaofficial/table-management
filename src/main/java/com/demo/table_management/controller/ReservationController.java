package com.demo.table_management.controller;

import com.demo.table_management.dto.ReservationRequest;
import com.demo.table_management.dto.ReservationResponse;
import com.demo.table_management.model.Reservation;
import com.demo.table_management.model.RestaurantTable;
import com.demo.table_management.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

  private final ReservationService reservationService;

  @Autowired
  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  /**
   * Endpoint to create a new reservation.
   *
   * @param request the reservation request containing time and number of seats
   * @return the created reservation response
   */
  @PostMapping
  public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest request) {
    ReservationResponse response = reservationService.createReservation(request);
    return ResponseEntity.ok(response);
  }

  /**
   * Endpoint to get all reservations.
   *
   * @return list of all reservation responses
   */
  @GetMapping
  public ResponseEntity<List<Reservation>> getAllReservations() {
    List<Reservation> reservations = reservationService.getAllReservations();
    return ResponseEntity.ok(reservations);
  }

  /**
   * Endpoint to get available tables at the given time.
   *
   * @param time the requested reservation time in ISO format (e.g., "2024-09-01T17:00:00")
   * @return list of available tables
   */
  @GetMapping("/available")
  public ResponseEntity<List<RestaurantTable>> getAvailableTables(@RequestParam LocalDateTime time, @RequestParam(value = "guest", defaultValue = "0") Integer guest) {
    List<RestaurantTable> availableTables = reservationService.getAvailableTables(time, guest);
    return ResponseEntity.ok(availableTables);
  }

}