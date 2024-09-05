package com.demo.table_management.service;

import com.demo.table_management.dto.ReservationRequest;
import com.demo.table_management.dto.ReservationResponse;
import com.demo.table_management.enums.ReservationType;
import com.demo.table_management.model.Reservation;
import com.demo.table_management.model.RestaurantTable;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {
  ReservationResponse createReservation(ReservationRequest reservationRequest);
  List<Reservation> getAllReservations();
  List<RestaurantTable> getAvailableTables(LocalDateTime time, Integer guest);
}
