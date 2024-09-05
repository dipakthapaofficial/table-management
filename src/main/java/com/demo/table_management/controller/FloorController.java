package com.demo.table_management.controller;

import com.demo.table_management.model.Floor;
import com.demo.table_management.service.FloorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/floors")
public class FloorController {

  private final FloorService floorService;

  public FloorController(FloorService floorService) {
    this.floorService = floorService;
  }

  @PostMapping
  public ResponseEntity<Floor> createFloor(@RequestBody Floor floor) {
    Floor createdFloor = floorService.createFloor(floor);
    return ResponseEntity.ok(createdFloor);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Floor> getFloorById(@PathVariable Long id) {
    Floor floor = floorService.getFloorById(id);
    return ResponseEntity.ok(floor);
  }

  @GetMapping
  public ResponseEntity<List<Floor>> getAllFloors() {
    List<Floor> floors = floorService.getAllFloors();
    return ResponseEntity.ok(floors);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Floor> updateFloor(@PathVariable Long id,
      @RequestBody Floor floor) {
    Floor updatedFloor = floorService.updateFloor(id, floor);
    return ResponseEntity.ok(updatedFloor);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteFloor(@PathVariable Long id) {
    floorService.deleteFloor(id);
    return ResponseEntity.noContent().build();
  }
}
