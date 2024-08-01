package com.william.teste_stoom.controller;

import com.william.teste_stoom.model.Marca;
import com.william.teste_stoom.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public List<Marca> listarTodas() {
        return marcaService.listarTodas();
    }

    @PostMapping
    public Marca criar(@RequestBody Marca marca) {
        return marcaService.salvar(marca);
    }

    @PutMapping("/{id}")
    public Marca atualizar(@PathVariable Long id, @RequestBody Marca marca) {
        return marcaService.atualizar(id, marca);
    }

    @DeleteMapping("/{id}")
    public void inativar(@PathVariable Long id) {
        marcaService.inativar(id);
    }
}
