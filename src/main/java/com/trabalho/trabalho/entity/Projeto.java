package com.trabalho.trabalho.entity;

import java.time.LocalDate;

import com.trabalho.trabalho.enums.ProjetoPrioridade;
import com.trabalho.trabalho.enums.ProjetoStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity 
public class Projeto {
    @Id 
    @GeneratedValue 
    private int id;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    @Enumerated 
    private ProjetoStatus status;
    @Enumerated 
    private ProjetoPrioridade prioridade;


   
    public Projeto() {
    }

    public Projeto(int id, String nome, String descricao, LocalDate dataInicio) {
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
    public ProjetoStatus getStatus() {
        return status;
    }
    public void setStatus(ProjetoStatus status) {
        this.status = status;
    }

}
