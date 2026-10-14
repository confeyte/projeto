package com.trabalho.trabalho.controllers;

import java.util.List;
import java.time.format.DateTimeFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trabalho.trabalho.entity.Projeto;
import com.trabalho.trabalho.enums.ProjetoPrioridade;
import com.trabalho.trabalho.enums.ProjetoStatus;
import com.trabalho.trabalho.service.ProjetoService;

@RestController 
@RequestMapping ("/projetos")
public class ProjetoController {

    private final ProjetoService projetoService;

    public ProjetoController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }

    @PostMapping("/criar")
    public ResponseEntity<Projeto> criarProjeto(
        @RequestParam String nome,
        @RequestParam String descricao,
        @RequestParam String dataInicio,
        @RequestParam String dataFim,
        @RequestParam ProjetoStatus status,        
        @RequestParam ProjetoPrioridade prioridade){
             
        Projeto projeto = new Projeto();

        projeto.setNome(nome);
        projeto.setDescricao(descricao);        
        projeto.setPrioridade(prioridade);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        projeto.setDataInicio(java.time.LocalDate.parse(dataInicio, formato));
        projeto.setDataFim(java.time.LocalDate.parse(dataFim, formato));
        projeto.setStatus(status);
           
        Projeto novoProjeto = projetoService.criarProjeto(projeto);
        return ResponseEntity.ok(novoProjeto);

        
    }

    @PutMapping ("/atualizar")
    public ResponseEntity<Projeto> atualizarProjeto(
        @RequestParam int id,
        @RequestParam String nome,
        @RequestParam String descricao,
        @RequestParam String dataInicio,
        @RequestParam String dataFim,
        @RequestParam ProjetoStatus status,        
        @RequestParam ProjetoPrioridade prioridade){
             
        Projeto projeto = new Projeto();
        projeto.setId(id);
        projeto.setNome(nome);
        projeto.setDescricao(descricao);        
        projeto.setPrioridade(prioridade);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        projeto.setDataInicio(java.time.LocalDate.parse(dataInicio, formato));
        projeto.setDataFim(java.time.LocalDate.parse(dataFim, formato));
        projeto.setStatus(status);
           
        Projeto novoProjeto = projetoService.criarProjeto(projeto);
        return ResponseEntity.ok(novoProjeto);

        
    }
    
    @GetMapping("/consultartodosprojetos")
    public ResponseEntity<List<Projeto>> consultarProjetos(){
        return ResponseEntity.ok(projetoService.consultarProjetos());
        
    }

    @GetMapping ("/consultarprojetoporid")
    public ResponseEntity<Projeto> consultarProjetoPorId(@RequestParam int id){
        return ResponseEntity.ok(projetoService.consultarProjetoPorId(id));
    }


    @GetMapping ("/consultardescricaoporid")
    public ResponseEntity<String> consultarDescricaoPorId(@RequestParam int id){
        Projeto projeto = projetoService.consultarProjetoPorId(id);
        if (projeto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projeto.getDescricao());
    }

    @PutMapping ("/alterardescricao")
    public ResponseEntity<Projeto> alterarDescricao(@RequestParam int id, @RequestParam String descricao) {
        Projeto projeto = projetoService.consultarProjetoPorId(id);
        if (projeto == null) {
            return ResponseEntity.notFound().build();
        }
        projeto.setDescricao(descricao);
        Projeto projetoAtualizado = projetoService.criarProjeto(projeto);
        return ResponseEntity.ok(projetoAtualizado);
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarProjeto(@RequestParam int id) {
        projetoService.deletarProjeto(id);
        return ResponseEntity.noContent().build();
    }

    

}
 

   



    
    


    

