package com.trabalho.trabalho.service;

import org.springframework.stereotype.Service;

import com.trabalho.trabalho.entity.Projeto;
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

}
