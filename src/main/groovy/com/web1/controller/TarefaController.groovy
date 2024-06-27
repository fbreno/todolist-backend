package com.web1.controller

import com.web1.model.Tarefa
import com.web1.repository.TarefaRepository
import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/tarefas")
class TarefaController {

    @Autowired
    TarefaRepository tarefaRepositorio

    @GetMapping
    List<Tarefa> listarTodas() {
        tarefaRepositorio.findAll().sort { a, b ->
            int result = a.prioridade.compareTo(b.prioridade)
            if (result == 0) {
                result = a.dataCriacao.compareTo(b.dataCriacao)
            }
            result
        }.reverse()
    }

    @PostMapping
    Tarefa criarTarefa(@RequestBody Tarefa tarefa) {
        tarefaRepositorio.save(tarefa)
    }

    @GetMapping("/{id}")
    ResponseEntity<Tarefa> obterTarefa(@PathVariable("id") Long id) {
        Tarefa tarefa = tarefaRepositorio.findById(id).orElse(null)
        if (tarefa == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(tarefa)
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<Tarefa> atualizarTarefa(@PathVariable("id") Long id, @RequestBody Tarefa tarefaDetalhes) {
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
    ResponseEntity<Void> deletarTarefa(@PathVariable("id") Long id) {
        Tarefa tarefa = tarefaRepositorio.findById(id).orElse(null);
        if (tarefa == null) {
            return ResponseEntity.notFound().build()
        } else {
            tarefaRepositorio.delete(tarefa)
            return ResponseEntity.noContent().build()
        }
    }

    @PostMapping("/mudarPrioridade/{id}")
    ResponseEntity<List<Tarefa>> mudarPrioridade(@PathVariable("id") Long id, @RequestBody Map<String, Integer> request) {
        int novaPrioridade = request.get("novaPrioridade")
        Tarefa tarefa = tarefaRepositorio.findById(id).orElse(null)
        if (tarefa == null) {
            ResponseEntity.notFound().build()
        } else {
            tarefa.prioridade += novaPrioridade
            tarefaRepositorio.save(tarefa)
            ResponseEntity.ok(listarTodas())
        }
    }
}