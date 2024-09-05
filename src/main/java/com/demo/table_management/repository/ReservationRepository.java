package com.demo.table_management.repository;

import com.demo.table_management.enums.ReservationType;
import com.demo.table_management.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  @Query("SELECT r FROM Reservation r WHERE r.startTime < :endTime AND r.endTime > :startTime AND r.status in :statuses ORDER BY r.noOfGuest DESC")
  List<Reservation> findAllByStartTimeBeforeAndEndTimeAfter(@Param("endTime") LocalDateTime endTime,
      @Param("startTime") LocalDateTime startTime, @Param("statuses") List<ReservationType> statuses);
}