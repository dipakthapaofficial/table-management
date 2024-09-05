package com.demo.table_management.service;

import com.demo.table_management.enums.ReservationType;
import com.demo.table_management.model.RestaurantTable;
import com.demo.table_management.dto.TableDto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TableService {
  RestaurantTable createTable(TableDto table);
  ReservationType getTableStatusAt(Long id, LocalDateTime date);
  List<RestaurantTable> getAllTables();
  RestaurantTable updateTable(TableDto table, Long id);
  void deleteTable(Long id);
}
