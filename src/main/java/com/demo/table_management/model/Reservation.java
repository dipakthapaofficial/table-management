package com.demo.table_management.model;

import com.demo.table_management.enums.ReservationType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "reservation")
public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String customerName;
  private String contactNumber;
  private int noOfGuest;
  private LocalDateTime startTime;
  private LocalDateTime endTime; // Default to 2 hours

  @ManyToOne
  @JsonBackReference
  private RestaurantTable table;

  @Enumerated(EnumType.STRING)
  private ReservationType status; // Enum to indicate reservation status

}