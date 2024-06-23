package com.web1.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id


@Entity
class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String descricao
    boolean concluida
}