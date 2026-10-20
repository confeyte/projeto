
package com.trabalho.trabalho.controllers;

import java.util.List;
import java.time.format.DateTimeFormatter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trabalho.trabalho.entity.Atividade;
import com.trabalho.trabalho.entity.Projeto;
import com.trabalho.trabalho.enums.AtividadeStatus;
import com.trabalho.trabalho.enums.ProjetoPrioridade;
import com.trabalho.trabalho.service.AtividadeService;
import com.trabalho.trabalho.service.ProjetoService;

@RestController
@RequestMapping("/atividades")
public class AtividadeController {

    private final AtividadeService atividadeService;
    private final ProjetoService projetoService;

    public AtividadeController(AtividadeService atividadeService, ProjetoService projetoService) {
        this.atividadeService = atividadeService;
        this.projetoService = projetoService;
    }

    @PostMapping("/criar")
    public ResponseEntity<?> criarAtividade(
        @RequestParam int projetoId,
        @RequestParam String nome,
        @RequestParam String descricao,
        @RequestParam String dataInicio,
        @RequestParam String dataFim,
        @RequestParam ProjetoPrioridade prioridade) {

        Projeto projeto = projetoService.consultarProjetoPorId(projetoId);

        if (projeto == null) {
            return ResponseEntity.badRequest()
                .body("Erro: projeto não encontrado!");
        }

        if (nome == null || nome.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                .body("Erro: o nome da atividade não pode ser vazio!");
        }

        Atividade atividade = new Atividade();

        atividade.setNome(nome);
        atividade.setDescricao(descricao);
        atividade.setPrioridade(prioridade);
        atividade.setProjeto(projeto);
        atividade.setStatus(AtividadeStatus.PENDENTE);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            atividade.setDataInicio(java.time.LocalDate.parse(dataInicio, formato));
            atividade.setDataFim(java.time.LocalDate.parse(dataFim, formato));
        } catch (java.time.format.DateTimeParseException e) {
            return ResponseEntity.badRequest()
                .body("Erro: utilize o formato dd/MM/yyyy!");
        }

        if (atividade.getDataInicio().isAfter(atividade.getDataFim())) {
            return ResponseEntity.badRequest()
                .body("Erro: a data de início não pode ser maior que a data de fim!");
        }

        Atividade novaAtividade = atividadeService.criarAtividade(atividade);

        return ResponseEntity.ok(novaAtividade);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarAtividade(
        @PathVariable int id,
        @RequestParam String nome,
        @RequestParam String descricao,
        @RequestParam String dataInicio,
        @RequestParam String dataFim,
        @RequestParam ProjetoPrioridade prioridade) {

        Atividade atividade = atividadeService.consultarAtividadePorId(id);

        if (atividade == null) {
            return ResponseEntity.notFound().build();
        }

        if (nome == null || nome.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                .body("Erro: o nome não pode ser vazio!");
        }

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            atividade.setDataInicio(java.time.LocalDate.parse(dataInicio, formato));
            atividade.setDataFim(java.time.LocalDate.parse(dataFim, formato));
        } catch (java.time.format.DateTimeParseException e) {
            return ResponseEntity.badRequest()
                .body("Erro: utilize o formato dd/MM/yyyy!");
        }

        if (atividade.getDataInicio().isAfter(atividade.getDataFim())) {
            return ResponseEntity.badRequest()
                .body("Erro: a data de início não pode ser maior que a data de fim!");
        }

        atividade.setNome(nome);
        atividade.setDescricao(descricao);
        atividade.setPrioridade(prioridade);

        Atividade atividadeAtualizada = atividadeService.criarAtividade(atividade);

        return ResponseEntity.ok(atividadeAtualizada);
    }

    @GetMapping("/consultartodasatividades")
    public ResponseEntity<List<Atividade>> consultarAtividades() {
        return ResponseEntity.ok(atividadeService.consultarAtividades(null));
    }

    @GetMapping("/consultaratividadeporid/{id}")
    public ResponseEntity<?> consultarAtividadePorId(@PathVariable int id) {

        if (id <= 0) {
            return ResponseEntity.badRequest()
                .body("O ID deve ser maior que zero.");
        }

        Atividade atividade = atividadeService.consultarAtividadePorId(id);

        if (atividade == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(atividade);
    }

    @GetMapping("/consultarporprojeto")
    public ResponseEntity<?> consultarPorProjeto(@RequestParam int projetoId) {

        Projeto projeto = projetoService.consultarProjetoPorId(projetoId);

        if (projeto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
            atividadeService.consultarPorProjeto(projetoId, null));
    }

    @GetMapping("/consultarporstatus")
    public ResponseEntity<List<Atividade>> consultarPorStatus(
        @RequestParam AtividadeStatus status) {

        return ResponseEntity.ok(atividadeService.consultarAtividades(status));
    }

    @PutMapping("/alterarstatus")
    public ResponseEntity<?> alterarStatus(
        @RequestParam int id,
        @RequestParam AtividadeStatus status) {

        Atividade atividade = atividadeService.consultarAtividadePorId(id);

        if (atividade == null) {
            return ResponseEntity.notFound().build();
        }

        String erro = atividadeService.validarAlteracaoStatus(atividade, status);

        if (erro != null) {
            return ResponseEntity.badRequest().body(erro);
        }

        atividade.setStatus(status);

        Atividade atividadeAtualizada = atividadeService.criarAtividade(atividade);

        return ResponseEntity.ok(atividadeAtualizada);
    }

    @GetMapping("/consultaratrasadas")
    public ResponseEntity<List<Atividade>> consultarAtrasadas() {
        return ResponseEntity.ok(atividadeService.consultarAtrasadas());
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarAtividade(@PathVariable int id) {

        Atividade atividade = atividadeService.consultarAtividadePorId(id);

        if (atividade == null) {
            return ResponseEntity.notFound().build();
        }

        String erro = atividadeService.validarExclusao(atividade);

        if (erro != null) {
            return ResponseEntity.badRequest().body(erro);
        }

        atividadeService.deletarAtividade(id);

        return ResponseEntity.noContent().build();
    }
}
