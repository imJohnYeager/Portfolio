package com.jyeager.desafio.itau.transacoes.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor
public class TransactionDto {

    @NotNull
    @DecimalMin("0.0")
    private Double valor;

    @NotNull
    private OffsetDateTime dataHora;
}
