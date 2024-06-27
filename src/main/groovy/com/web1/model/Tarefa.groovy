package com.web1.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

import java.time.LocalDate
import java.time.LocalDateTime


@Entity
class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String descricao
    boolean concluida
    int prioridade = 0
    LocalDateTime dataCriacao = LocalDateTime.now()
}