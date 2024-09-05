package com.demo.table_management.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ReservationRequest {
  @NotNull(message = "Number of guest is required")
  private Integer numberOfSeats;

  @NotNull(message = "Guest name is required")
  @Max(value = 50, message = "Guest name cannot be more than 50 character")
  private String customerName;

  @NotNull(message = "Guest contact is required")
  private String contactNumber;

  @NotNull(message = "Start time is required")
  @Future(message = "Start time must be in the future")
  private LocalDateTime startTime;

  @NotNull(message = "End time is required")
  @Future(message = "End time must be in the future")
  private LocalDateTime endTime;
}
