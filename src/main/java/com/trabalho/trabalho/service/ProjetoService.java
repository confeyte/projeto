package com.trabalho.trabalho.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trabalho.trabalho.entity.Projeto;
import com.trabalho.trabalho.enums.ProjetoStatus;
import com.trabalho.trabalho.repository.ProjetoRepository;

@Service 
public class ProjetoService {

    private final ProjetoRepository projetoRepository;

    public ProjetoService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    public Projeto criarProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public List<Projeto> consultarProjetos() {
        return projetoRepository.findAll();
    }

    public void deletarProjeto(int id) {
        projetoRepository.deleteById(id);
    }

     public Projeto consultarProjetoPorId(int id) {
        return projetoRepository.findById(id).orElse(null);
    }

    public String validarProjeto(Projeto projeto) {
        if (projeto.getNome() == null || projeto.getNome().isEmpty()) {
            return "Erro: o nome do projeto não pode ser vazio!";
        }
        if (projeto.getDescricao() == null || projeto.getDescricao().isEmpty()) {
            return "Erro: a descrição do projeto não pode ser vazia!";
        }
        if (projeto.getDataInicio() == null) {
            return "Erro: a data de início do projeto não pode ser nula!";
        }
        if (projeto.getDataFim() == null) {
            return "Erro: a data de fim do projeto não pode ser nula!";
        }
        if (projeto.getDataInicio().isAfter(projeto.getDataFim())) {
            return "Erro: a data de início não pode ser maior que a data de fim!";
        }
        return null; // Retorna null se todas as validações passarem
    }

    public String validarCriacao(Projeto projeto) {
        String erro = validarProjeto(projeto);
        if (erro != null) return erro;

        if (projeto.getStatus() == ProjetoStatus.CONCLUIDO) {
            return "Um projeto não pode ser criado com status 'Concluído'.";
        }
        return null;
    }

    public String validarAtualizacao(Projeto projeto) {
        String erro = validarProjeto(projeto);
        if (erro != null) return erro;

        if (projeto.getStatus() == ProjetoStatus.CONCLUIDO) {
            return "Um projeto não pode ser atualizado para o status 'Concluído'.";
        }
        return null;
    }
 
    
}
