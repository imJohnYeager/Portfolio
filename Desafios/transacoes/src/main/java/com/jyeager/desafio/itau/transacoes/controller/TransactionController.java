package com.jyeager.desafio.itau.transacoes.controller;

import com.jyeager.desafio.itau.transacoes.dto.TransactionDto;
import com.jyeager.desafio.itau.transacoes.model.Transaction;
import com.jyeager.desafio.itau.transacoes.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/transacao")
// Classe responsável por
public class TransactionController {

    private final TransactionService transactionService;

    // Criação de transação.
    @PostMapping
    public ResponseEntity<Void> createTransaction(@Valid @RequestBody TransactionDto request) {
        // Se a dataHora for maior que a data e hora atual, retorna 422
        if(request.getDataHora().isAfter(OffsetDateTime.now())) {
            return ResponseEntity.unprocessableEntity().build();
        }

        // Adiciona uma transação.
        transactionService.addTransaction(new Transaction(request.getValor(), request.getDataHora()));

        // Retorna 201.
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Limpa todas as transações.
    @DeleteMapping
    public ResponseEntity<Void> clearTransactions(){
        transactionService.clearTransactions();
        return ResponseEntity.ok().build();
    }
}
