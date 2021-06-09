package com.example.tinybank.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payment{
    @NotNull(message = "enter id")
    private Integer targetId;
    @NotNull(message = "enter value")
    private Long value;
}
