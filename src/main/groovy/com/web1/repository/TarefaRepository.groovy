package com.web1.repository

import com.web1.model.Tarefa
import org.springframework.data.jpa.repository.JpaRepository

interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}
