package com.trabalho.trabalho.service;

import java.util.List;

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

    public List<Projeto> consultarProjetos() {
        return projetoRepository.findAll();
    }

    public void deletarProjeto(int id) {
        projetoRepository.deleteById(id);
    }

     public Projeto consultarProjetoPorId(int id) {
        return projetoRepository.findById(id).orElse(null);
    }
}
