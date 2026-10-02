package com.trabalho.trabalho.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity 
public class Atividade {
    
   @Id
   @GeneratedValue 
   private int id;
   private String nome;
   private String descricao;
   private int duracao;
   private LocalDate dataInicio;
   private LocalDate dataFim;
   
    public Atividade() {
    }

    public Atividade(int id, String nome, String descricao, int duracao, LocalDate dataInicio, LocalDate dataFim) {
     this.id = id;
     this.nome = nome;
     this.descricao = descricao;
     this.duracao = duracao;
     this.dataInicio = dataInicio;
     this.dataFim = dataFim;
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
   public int getDuracao() {
    return duracao;
   }
   public void setDuracao(int duracao) {
    this.duracao = duracao;
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

   
    
}
