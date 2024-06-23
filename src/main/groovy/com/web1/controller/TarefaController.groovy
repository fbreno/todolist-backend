package com.web1.controller

import com.web1.model.Tarefa
import com.web1.repository.TarefaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tarefas")
class TarefaControler {

    @Autowired
    TarefaRepository tarefaRepositorio

    @GetMapping
    List<Tarefa> listarTodas() {
        tarefaRepositorio.findAll()
    }

    @PostMapping
    Tarefa criarTarefa(@RequestBody Tarefa tarefa) {
        tarefaRepositorio.save(tarefa)
    }

    @GetMapping("/{id}")
    ResponseEntity<Tarefa> obterTarefa(@PathVariable Long id) {
        Tarefa tarefa = tarefaRepositorio.findById(id).orElse(null)
        if (tarefa == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(tarefa)
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaDetalhes) {
        Tarefa tarefa = tarefaRepositorio.findById(id).orElse(null)
        if (tarefa == null) {
            ResponseEntity.notFound().build()
        } else {
            tarefa.descricao = tarefaDetalhes.descricao
            tarefa.concluida = tarefaDetalhes.concluida
            Tarefa tarefaAtualizada = tarefaRepositorio.save(tarefa)
            ResponseEntity.ok(tarefaAtualizada)
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        Tarefa tarefa = tarefaRepositorio.findById(id).orElse(null)
        if (tarefa == null) {
            ResponseEntity.notFound().build()
        } else {
            tarefaRepositorio.delete(tarefa)
            ResponseEntity.noContent().build()
        }
    }
}