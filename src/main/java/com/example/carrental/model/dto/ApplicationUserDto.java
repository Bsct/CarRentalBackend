package com.example.carrental.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUserDto {
    private Long id;
    private String username;
    private String name;
    private String surname;
    private boolean admin;
}
