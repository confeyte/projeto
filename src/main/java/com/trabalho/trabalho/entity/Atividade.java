
package com.trabalho.trabalho.entity;

import java.time.LocalDate;

import com.trabalho.trabalho.enums.AtividadeStatus;
import com.trabalho.trabalho.enums.ProjetoPrioridade;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Atividade {

    @Id
    @GeneratedValue
    private int id;

    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    @Enumerated(EnumType.STRING)
    private AtividadeStatus status;

    @Enumerated(EnumType.STRING)
    private ProjetoPrioridade prioridade;

    @ManyToOne
    @JoinColumn(name = "projeto_id")
    private Projeto projeto;

    public Atividade() {
    }

    public Atividade(int id, String nome, String descricao, LocalDate dataInicio) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public AtividadeStatus getStatus() {
        return status;
    }

    public void setStatus(AtividadeStatus status) {
        this.status = status;
    }

    public ProjetoPrioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(ProjetoPrioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }
}
