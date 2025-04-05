package com.jyeager.desafio.itau.transacoes.dto;

import lombok.Getter;

import java.util.DoubleSummaryStatistics;

@Getter
public class StatisticsResponseDto {

    private long count; // Quantidade de transações.
    private double sum; // Soma total do valor transicionado.
    private double avg; // Média do valor transicionado.
    private double min; // Menor valor transicionado.
    private double max; // Maior valor transicionado.

    public StatisticsResponseDto(DoubleSummaryStatistics stats){
        this.count = stats.getCount();
        this.sum = stats.getSum();
        this.avg = stats.getAverage();
        this.min = stats.getMin();
        this.max = stats.getMax();
    }
}
