package com.demo.table_management.service.impl;

import com.demo.table_management.dto.ReservationRequest;
import com.demo.table_management.dto.ReservationResponse;
import com.demo.table_management.enums.ReservationType;
import com.demo.table_management.model.Reservation;
import com.demo.table_management.model.RestaurantTable;
import com.demo.table_management.repository.ReservationRepository;
import com.demo.table_management.repository.TableRepository;
import com.demo.table_management.service.ReservationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

  private final ReservationRepository reservationRepository;
  private final TableRepository restaurantTableRepository;

  public ReservationServiceImpl(ReservationRepository reservationRepository,
      TableRepository restaurantTableRepository) {
    this.reservationRepository = reservationRepository;
    this.restaurantTableRepository = restaurantTableRepository;
  }

  @Override
  public ReservationResponse createReservation(ReservationRequest reservationRequest) {
    LocalDateTime startTime = reservationRequest.getStartTime();
    LocalDateTime endTime = reservationRequest.getEndTime();

    // If end time is not provided, set it to 2 hours after the start time
    if (endTime == null) {
      endTime = startTime.plusHours(2);
    }

    // Attempt to directly find available tables for the new reservation
    List<RestaurantTable> availableTables = restaurantTableRepository.findAvailableTablesWithStatuses(
        reservationRequest.getNumberOfSeats(), startTime, endTime,
        List.of(ReservationType.RESERVED, ReservationType.OCCUPIED));

    if (!availableTables.isEmpty()) {
      // Directly available, choose the best fitting table
      RestaurantTable selectedTable = selectBestFitTable(availableTables,
          reservationRequest.getNumberOfSeats());
      return createReservationForTable(selectedTable, reservationRequest);
    } else {
      if (rearrangeReservations(reservationRequest)) {  // if rearrange is successful
        availableTables = restaurantTableRepository.findAvailableTablesWithStatuses(
            reservationRequest.getNumberOfSeats(), startTime, endTime,
            List.of(ReservationType.RESERVED, ReservationType.OCCUPIED));
        RestaurantTable selectedTable = selectBestFitTable(availableTables,
            reservationRequest.getNumberOfSeats());
        return createReservationForTable(selectedTable, reservationRequest);
      }

    }
    return new ReservationResponse(null, "No available tables found");
  }

  @Override
  public List<Reservation> getAllReservations() {
    return reservationRepository.findAll();
  }

  private RestaurantTable selectBestFitTable(List<RestaurantTable> tables,
      Integer requiredSeats) {
    return tables.stream().filter(table -> table.getCapacity() >= requiredSeats)
        .min(Comparator.comparingInt(table -> table.getCapacity() - requiredSeats))
        .orElseThrow(() -> new IllegalStateException("No suitable table found"));
  }

  private ReservationResponse createReservationForTable(RestaurantTable table,
      ReservationRequest reservationRequest) {
    Reservation reservation = new Reservation();
    reservation.setStartTime(reservationRequest.getStartTime());
    reservation.setEndTime(reservationRequest.getEndTime());
    reservation.setCustomerName(reservationRequest.getCustomerName());
    reservation.setContactNumber(reservationRequest.getContactNumber());
    reservation.setNoOfGuest(reservationRequest.getNumberOfSeats());
    reservation.setTable(table);
    reservation.setStatus(ReservationType.RESERVED);
    reservationRepository.save(reservation);

    return new ReservationResponse(reservation, "Reservation created successfully");
  }

  private boolean rearrangeReservations(ReservationRequest newRequest) {
    LocalDateTime startTime = newRequest.getStartTime();
    LocalDateTime endTime = newRequest.getEndTime();
    int requiredSeats = newRequest.getNumberOfSeats();

    // Fetch all conflicting reservations during the requested time slot
    List<Reservation> overlappingReservations = reservationRepository.findAllByStartTimeBeforeAndEndTimeAfter(
        endTime, startTime, List.of(ReservationType.RESERVED));
    if (!overlappingReservations.isEmpty()) {
      overlappingReservations.add(
          Reservation.builder().contactNumber(newRequest.getContactNumber())
              .customerName(newRequest.getCustomerName()).status(ReservationType.RESERVED)
              .startTime(startTime).endTime(endTime).noOfGuest(requiredSeats).build());
    }
    overlappingReservations.sort(
        Comparator.comparingInt(Reservation::getNoOfGuest).reversed());

    List<RestaurantTable> allTables = restaurantTableRepository.findAvailableTablesWithStatuses(
        0, startTime, endTime, List.of(ReservationType.OCCUPIED));

    if (allTables.size() < overlappingReservations.size())
      return false;

    // Attempt to assign reservations to tables
    List<Reservation> newReservation = new ArrayList<>();
    for (Reservation reservation : overlappingReservations) {
      RestaurantTable assignedTable = selectBestFitTable(allTables,
          reservation.getNoOfGuest());
      if (assignedTable == null)
        return false;

      allTables.remove(assignedTable); // Remove the table from available tables list
      if (reservation.getTable() == null) //skip new reservation
        continue;

      reservation.setTable(assignedTable);
      newReservation.add(reservation);
    }

    reservationRepository.saveAll(newReservation);
    return true;
  }


  @Override
  public List<RestaurantTable> getAvailableTables(LocalDateTime time, Integer guest) {
    LocalDateTime endTime = time.plusHours(2);
    return restaurantTableRepository.findAvailableTablesWithStatuses(guest, time, endTime,
        List.of(ReservationType.RESERVED, ReservationType.OCCUPIED));
  }
}
