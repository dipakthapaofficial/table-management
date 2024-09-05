package com.demo.table_management.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TableDto {
  @NotNull(message = "Name is required")
  @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
  private String name;

  @NotNull(message = "Capacity is required")
  @Min(value = 1, message = "Capacity must be at least 1")
  private Integer capacity;

  @NotNull(message = "Floor ID is required")
  private Long floorId;
}
