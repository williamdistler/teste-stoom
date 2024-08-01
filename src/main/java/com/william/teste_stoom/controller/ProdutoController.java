package com.william.teste_stoom.controller;

import com.william.teste_stoom.dto.ProdutoDTO;
import com.william.teste_stoom.model.Produto;
import com.william.teste_stoom.service.ProdutoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Produto> listarTodos() {
        return produtoService.listarTodos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Produto buscarPorId(@PathVariable("id") Long id) {
        return produtoService.buscarPorId(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto criar(@RequestBody ProdutoDTO produto) {
        return produtoService.salvar(produto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Produto atualizar(@PathVariable Long id, @RequestBody ProdutoDTO produto) {
        return produtoService.atualizar(id, produto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long id) {
        produtoService.buscarPorId(id)
                .map(produto -> {
                    produtoService.inativar(produto.getId());
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }

    @GetMapping("/categoria")
    @ResponseStatus(HttpStatus.OK)
    public List<Produto> listarPorCategoria(@RequestParam("nome") String categoriaNome) {
        return produtoService.listarPorCategoria(categoriaNome);
    }

    @GetMapping("/marca")
    @ResponseStatus(HttpStatus.OK)
    public List<Produto> listarPorMarca(@RequestParam("nome") String marcaNome) {
        return produtoService.listarPorMarca(marcaNome);
    }
}