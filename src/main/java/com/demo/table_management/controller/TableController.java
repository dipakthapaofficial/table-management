package com.demo.table_management.controller;

import com.demo.table_management.enums.ReservationType;
import com.demo.table_management.model.RestaurantTable;
import com.demo.table_management.dto.TableDto;
import com.demo.table_management.service.TableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tables")
public class TableController {

  private final TableService tableService;

  public TableController(TableService tableService) {
    this.tableService = tableService;
  }

  @PostMapping
  public ResponseEntity<RestaurantTable> createTable(@RequestBody TableDto table) {
    return ResponseEntity.ok(tableService.createTable(table));
  }

  @GetMapping
  public ResponseEntity<List<RestaurantTable>> getAllTables() {
    return ResponseEntity.ok(tableService.getAllTables());
  }

  @PutMapping("/{id}")
  public ResponseEntity<RestaurantTable> updateTable(@RequestBody TableDto table,
      @PathVariable Long id) {
    return ResponseEntity.ok(tableService.updateTable(table, id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTable(@PathVariable Long id) {
    tableService.deleteTable(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}/status")
  public ResponseEntity<ReservationType> getTableStatusOnDate(@PathVariable Long id, @RequestParam LocalDateTime date) {
    return ResponseEntity.ok(tableService.getTableStatusAt(id, date));
  }
}
