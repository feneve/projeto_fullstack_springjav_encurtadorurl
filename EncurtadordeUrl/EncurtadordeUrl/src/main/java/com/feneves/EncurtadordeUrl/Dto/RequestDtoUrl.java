package com.feneves.EncurtadordeUrl.Dto;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestDtoUrl {
    @NotBlank(message = "A URL não pode estar vazia")
    private String longUrl;

    @Future(message = "A data de expiração deve estar no futuro")
    private LocalDateTime expiration;
}
