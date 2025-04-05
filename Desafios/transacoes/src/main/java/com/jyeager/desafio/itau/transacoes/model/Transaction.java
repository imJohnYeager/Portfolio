package com.jyeager.desafio.itau.transacoes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@AllArgsConstructor
@Getter
public class Transaction {
    private double valor;
    private OffsetDateTime dataHora;
}
