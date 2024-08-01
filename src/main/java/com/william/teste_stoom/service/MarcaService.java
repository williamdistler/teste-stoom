package com.william.teste_stoom.service;

import com.william.teste_stoom.model.Marca;
import com.william.teste_stoom.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    public List<Marca> listarTodas() {
        return marcaRepository.findByAtivoTrue();
    }

    public Marca salvar(Marca marca) {
        return marcaRepository.save(marca);
    }

    public Marca atualizar(Long id, Marca marcaAtualizada) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca não encontrada"));
        marca.setNome(marcaAtualizada.getNome());
        return marcaRepository.save(marca);
    }

    public void inativar(Long id) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca não encontrada"));
        marca.setAtivo(false);
        marcaRepository.save(marca);
    }
}
