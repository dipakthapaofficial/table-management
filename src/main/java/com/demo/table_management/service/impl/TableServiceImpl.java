package com.demo.table_management.service.impl;

import com.demo.table_management.dto.TableDto;
import com.demo.table_management.enums.ReservationType;
import com.demo.table_management.exception.TableNotFoundException;
import com.demo.table_management.model.Floor;
import com.demo.table_management.model.Reservation;
import com.demo.table_management.model.RestaurantTable;
import com.demo.table_management.repository.TableRepository;
import com.demo.table_management.service.FloorService;
import com.demo.table_management.service.TableService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TableServiceImpl implements TableService {

  private final TableRepository tableRepository;
  private final FloorService floorService;

  public TableServiceImpl(TableRepository tableRepository, FloorService floorService) {
    this.tableRepository = tableRepository;
    this.floorService = floorService;
  }

  @Override
  public RestaurantTable createTable(TableDto tableDto) {
    Floor floor = floorService.getFloorById(tableDto.getFloorId());
    RestaurantTable table = new RestaurantTable();
    table.setName(tableDto.getName());
    table.setCapacity(tableDto.getCapacity());
    table.setFloor(floor);
    return tableRepository.save(table);
  }

  @Override
  public ReservationType getTableStatusAt(Long id, LocalDateTime date) {
    Optional<RestaurantTable> table = tableRepository.findById(id);
    if (table.isEmpty())
      throw new TableNotFoundException("Table not found");

    List<Reservation> reservationsList = table.get().getReservations();
    Optional<Reservation> reservation = reservationsList.stream().filter(
        res -> res.getStatus().equals(ReservationType.RESERVED)
            && res.getStartTime().isBefore(date) && res.getEndTime()
            .isAfter(date)).findFirst();
    return reservation.isPresent() ? reservation.get().getStatus() : ReservationType.AVAILABLE;
  }

  @Override
  public List<RestaurantTable> getAllTables() {
    return tableRepository.findAll();
  }

  @Override
  public RestaurantTable updateTable(TableDto table, Long id) {
    RestaurantTable restaurantTable = tableRepository.findById(id)
        .orElseThrow(() -> new TableNotFoundException("Table not found"));
    restaurantTable.setName(table.getName());
    restaurantTable.setCapacity(table.getCapacity());
    restaurantTable.setFloor(floorService.getFloorById(table.getFloorId()));
    return tableRepository.save(restaurantTable);
  }

  @Override
  public void deleteTable(Long id) {
    RestaurantTable table = tableRepository.findById(id)
        .orElseThrow(() -> new TableNotFoundException("Table not found"));
    tableRepository.delete(table);
  }
}
