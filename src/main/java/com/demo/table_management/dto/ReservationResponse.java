package com.demo.table_management.dto;

import com.demo.table_management.model.Reservation;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ReservationResponse {
  private Reservation reservation;
  private String message;
}
