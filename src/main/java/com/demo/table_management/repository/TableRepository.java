package com.demo.table_management.repository;

import com.demo.table_management.enums.ReservationType;
import com.demo.table_management.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<RestaurantTable, Long> {
  @Query("SELECT t FROM RestaurantTable t WHERE t.capacity >= :minSeats AND t.id NOT IN "
      + "(SELECT r.table.id FROM Reservation r WHERE "
      + "(r.startTime < :endTime AND r.endTime > :startTime) AND r.status IN :statuses) "
      + "ORDER BY t.capacity DESC")
  List<RestaurantTable> findAvailableTablesWithStatuses(
      @Param("minSeats") Integer minSeats, @Param("startTime") LocalDateTime startTime,
      @Param("endTime") LocalDateTime endTime,
      @Param("statuses") List<ReservationType> statuses);
}
