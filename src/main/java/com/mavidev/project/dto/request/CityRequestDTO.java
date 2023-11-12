package com.mavidev.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityRequestDTO {
    @NotNull(message = "This field cannot be null!")
    @NotBlank(message = "This field cannot be left blank!")
    @NotEmpty(message = "This field cannot be empty!")
    private String name;
}