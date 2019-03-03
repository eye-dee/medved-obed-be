package com.igor.medvedobedbe.controller.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Metric {

  @NotEmpty
  private String name;
  @NotNull
  private Double value;

}
