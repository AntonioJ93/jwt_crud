package com.example.jwt_crud.model.resquestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginDto(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
