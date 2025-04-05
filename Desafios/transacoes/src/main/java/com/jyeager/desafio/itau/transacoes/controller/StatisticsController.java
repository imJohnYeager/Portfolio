package com.jyeager.desafio.itau.transacoes.controller;

import com.jyeager.desafio.itau.transacoes.dto.StatisticsResponseDto;
import com.jyeager.desafio.itau.transacoes.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.DoubleSummaryStatistics;


@RestController
@RequestMapping("/estatistica")
@AllArgsConstructor
public class StatisticsController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<StatisticsResponseDto> getStatistics(){
        DoubleSummaryStatistics stats = transactionService.getStatistics();
        return ResponseEntity.ok(new StatisticsResponseDto(stats));
    }

}