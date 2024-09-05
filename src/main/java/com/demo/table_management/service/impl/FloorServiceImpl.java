package com.demo.table_management.service.impl;

import com.demo.table_management.exception.ResourceNotFoundException;
import com.demo.table_management.model.Floor;
import com.demo.table_management.repository.FloorRepository;
import com.demo.table_management.service.FloorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FloorServiceImpl implements FloorService {

  private final FloorRepository floorRepository;

  public FloorServiceImpl(FloorRepository floorRepository) {
    this.floorRepository = floorRepository;
  }

  @Override
  public Floor createFloor(Floor floor) {
    return floorRepository.save(floor);
  }

  @Override
  public Floor getFloorById(Long id) {
    Optional<Floor> floor = floorRepository.findById(id);
    return floor.orElseThrow(
        () -> new ResourceNotFoundException("Floor not found with id: " + id));
  }

  @Override
  public List<Floor> getAllFloors() {
    return floorRepository.findAll();
  }

  @Override
  public Floor updateFloor(Long id, Floor updatedFloor) {
    Floor floor = getFloorById(id);
    floor.setName(updatedFloor.getName());
    floor.setTables(updatedFloor.getTables());
    return floorRepository.save(floor);
  }

  @Override
  public void deleteFloor(Long id) {
    Floor floor = getFloorById(id);
    floorRepository.delete(floor);
  }
}
