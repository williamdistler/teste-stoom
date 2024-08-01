package com.william.teste_stoom.service;

import com.william.teste_stoom.dto.ProdutoDTO;
import com.william.teste_stoom.model.Categoria;
import com.william.teste_stoom.model.Marca;
import com.william.teste_stoom.model.Produto;
import com.william.teste_stoom.repository.CategoriaRepository;
import com.william.teste_stoom.repository.MarcaRepository;
import com.william.teste_stoom.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Produto> listarTodos() {
        return produtoRepository.findByAtivoTrue();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    @Transactional
    public Produto salvar(ProdutoDTO produtoDto) {
        if (!marcaRepository.existsById(produtoDto.getMarcaId())) {
            throw new RuntimeException("Marca Inexistente");
        } else if (!categoriaRepository.existsById(produtoDto.getCategoriaId())) {
            throw new RuntimeException("Categoria Inexistente");
        }
        Marca marca = marcaRepository.findById(produtoDto.getMarcaId()).get();
        Categoria categoria = categoriaRepository.findById(produtoDto.getCategoriaId()).get();
        Produto produto = new Produto(
                null,
                produtoDto.getNome(),
                categoria,
                marca,
                produtoDto.getPreco(),
                true
        );
        return produtoRepository.save(produto);
    }

    public void inativar(Long id) {
        buscarPorId(id).ifPresent(produto -> {
            produto.setAtivo(false);
            produtoRepository.save(produto);
        });
    }

    public List<Produto> listarPorCategoria(String categoriaNome) {
        return produtoRepository.findByCategoriaNomeAndAtivoTrue(categoriaNome);
    }

    public List<Produto> listarPorMarca(String marcaNome) {
        return produtoRepository.findByMarcaNomeAndAtivoTrue(marcaNome);
    }

    @Transactional
    public Produto atualizar(Long id, ProdutoDTO produtoDto) {

        if (!marcaRepository.existsById(produtoDto.getMarcaId())) {
            throw new RuntimeException("Marca Inexistente");
        } else if (!categoriaRepository.existsById(produtoDto.getCategoriaId())) {
            throw new RuntimeException("Categoria Inexistente");
        }

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));

        Marca marca = marcaRepository.findById(produtoDto.getMarcaId()).get();
        Categoria categoria = categoriaRepository.findById(produtoDto.getCategoriaId()).get();
        produto.setNome(produtoDto.getNome());
        produto.setCategoria(categoria);
        produto.setMarca(marca);
        produto.setPreco(produtoDto.getPreco());

        return produtoRepository.save(produto);
    }
}
