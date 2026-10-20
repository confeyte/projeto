
package com.trabalho.trabalho.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.trabalho.trabalho.entity.Atividade;
import com.trabalho.trabalho.entity.Projeto;
import com.trabalho.trabalho.enums.AtividadeStatus;
import com.trabalho.trabalho.enums.ProjetoStatus;
import com.trabalho.trabalho.repository.AtividadeRepository;

@Service
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;

    public AtividadeService(AtividadeRepository atividadeRepository) {
        this.atividadeRepository = atividadeRepository;
    }

    public Atividade criarAtividade(Atividade atividade) {
        return atividadeRepository.save(atividade);
    }

    public List<Atividade> consultarAtividades(AtividadeStatus status) {
        if (status == null) {
            return atividadeRepository.findAll();
        }

        return atividadeRepository.findAll().stream()
            .filter(a -> a.getStatus() == status)
            .collect(Collectors.toList());
    }

    public void deletarAtividade(int id) {
        atividadeRepository.deleteById(id);
    }

    public Atividade consultarAtividadePorId(int id) {
        return atividadeRepository.findById(id).orElse(null);
    }

    public List<Atividade> consultarPorProjeto(int projetoId, AtividadeStatus status) {
        return atividadeRepository.findAll().stream()
            .filter(a -> a.getProjeto() != null)
            .filter(a -> a.getProjeto().getId() == projetoId)
            .filter(a -> status == null || a.getStatus() == status)
            .collect(Collectors.toList());
    }

    public List<Atividade> consultarAtrasadas() {
        return atividadeRepository.findAll().stream()
            .filter(a -> a.getDataFim() != null)
            .filter(a -> a.getDataFim().isBefore(LocalDate.now()))
            .filter(a -> a.getStatus() != AtividadeStatus.CONCLUIDA)
            .collect(Collectors.toList());
    }

    public String validarAtividade(Atividade atividade) {

        if (atividade.getNome() == null || atividade.getNome().isBlank()) {
            return "Erro: o nome da atividade não pode ser vazio!";
        }

        if (atividade.getDescricao() == null || atividade.getDescricao().isBlank()) {
            return "Erro: a descrição da atividade não pode ser vazia!";
        }

        if (atividade.getDataInicio() == null || atividade.getDataFim() == null) {
            return "Erro: as datas são obrigatórias!";
        }

        if (atividade.getDataInicio().isAfter(atividade.getDataFim())) {
            return "Erro: a data de início não pode ser maior que a data de fim!";
        }

        if (atividade.getPrioridade() == null) {
            return "Erro: a prioridade é obrigatória!";
        }

        if (atividade.getProjeto() == null) {
            return "Erro: a atividade precisa pertencer a um projeto!";
        }

        Projeto projeto = atividade.getProjeto();

        if (projeto.getStatus() == ProjetoStatus.CONCLUIDO ||
            projeto.getStatus() == ProjetoStatus.CANCELADO) {
            return "Erro: o projeto não aceita alterações!";
        }

        if (projeto.getDataInicio() != null &&
            projeto.getDataFim() != null) {

            if (atividade.getDataInicio().isBefore(projeto.getDataInicio()) ||
                atividade.getDataFim().isAfter(projeto.getDataFim())) {
                return "Erro: as datas da atividade devem estar dentro do período do projeto!";
            }
        }

        return null;
    }

    public String validarAlteracaoStatus(Atividade atividade, AtividadeStatus status) {

        if (status == null) {
            return "Erro: o status é obrigatório!";
        }

        Projeto projeto = atividade.getProjeto();

        if (projeto == null) {
            return "Erro: projeto não encontrado!";
        }

        if (projeto.getStatus() == ProjetoStatus.CANCELADO) {
            return "Erro: o projeto está cancelado!";
        }

        if (projeto.getStatus() == ProjetoStatus.CONCLUIDO &&
            status != AtividadeStatus.CONCLUIDA) {
            return "Erro: não é possível reabrir atividades de um projeto concluído!";
        }

        return null;
    }

    public String validarExclusao(Atividade atividade) {

        if (atividade.getProjeto() != null &&
            atividade.getProjeto().getStatus() == ProjetoStatus.CONCLUIDO) {
            return "Erro: não é possível excluir atividades de um projeto concluído!";
        }

        if (atividade.getStatus() == AtividadeStatus.EM_ANDAMENTO) {
            return "Erro: não é possível excluir uma atividade em andamento!";
        }

        return null;
    }
}
