package com.demo.table_management.service;

import com.demo.table_management.model.Floor;

import java.util.List;

public interface FloorService {

  Floor createFloor(Floor floor);

  Floor getFloorById(Long id);

  List<Floor> getAllFloors();

  Floor updateFloor(Long id, Floor floor);

  void deleteFloor(Long id);
}
