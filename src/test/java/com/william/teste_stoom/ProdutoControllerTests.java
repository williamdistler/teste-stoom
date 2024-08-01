package com.william.teste_stoom;

import com.william.teste_stoom.controller.ProdutoController;
import com.william.teste_stoom.dto.ProdutoDTO;
import com.william.teste_stoom.model.Produto;
import com.william.teste_stoom.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoControllerTest {

    @Mock
    private ProdutoService produtoService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProdutoController produtoController;

    private Produto produto;
    private ProdutoDTO produtoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Notebook Dell");
        produto.setPreco(BigDecimal.valueOf(4999.99));
        produto.setAtivo(true);

        produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Notebook Dell");
        produtoDTO.setPreco(BigDecimal.valueOf(4999.99));
    }

    @Test
    void listarTodos() {
        when(produtoService.listarTodos()).thenReturn(Collections.singletonList(produto));

        List<Produto> produtos = produtoController.listarTodos();

        assertNotNull(produtos);
        assertEquals(1, produtos.size());
        assertEquals(produto.getNome(), produtos.get(0).getNome());
    }

    @Test
    void buscarPorId() {
        when(produtoService.buscarPorId(1L)).thenReturn(Optional.of(produto));

        Produto encontrado = produtoController.buscarPorId(1L);

        assertNotNull(encontrado);
        assertEquals(produto.getNome(), encontrado.getNome());
    }

    @Test
    void buscarPorIdNaoEncontrado() {
        when(produtoService.buscarPorId(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            produtoController.buscarPorId(1L);
        });

        assertEquals("Produto não encontrado.", exception.getReason());
    }

    @Test
    void criar() {
        when(produtoService.salvar(any(ProdutoDTO.class))).thenReturn(produto);

        Produto criado = produtoController.criar(produtoDTO);

        assertNotNull(criado);
        assertEquals(produto.getNome(), criado.getNome());
    }

    @Test
    void atualizar() {
        when(produtoService.atualizar(eq(1L), any(ProdutoDTO.class))).thenReturn(produto);

        Produto atualizado = produtoController.atualizar(1L, produtoDTO);

        assertNotNull(atualizado);
        assertEquals(produto.getNome(), atualizado.getNome());
    }

    @Test
    void inativar() {
        when(produtoService.buscarPorId(1L)).thenReturn(Optional.of(produto));
        doNothing().when(produtoService).inativar(1L);

        assertDoesNotThrow(() -> {
            produtoController.inativar(1L);
        });

        verify(produtoService, times(1)).inativar(1L);
    }

    @Test
    void inativarNaoEncontrado() {
        when(produtoService.buscarPorId(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            produtoController.inativar(1L);
        });

        assertEquals("Produto não encontrado.", exception.getReason());
    }

    @Test
    void listarPorCategoria() {
        when(produtoService.listarPorCategoria("Eletrônicos")).thenReturn(Collections.singletonList(produto));

        List<Produto> produtos = produtoController.listarPorCategoria("Eletrônicos");

        assertNotNull(produtos);
        assertEquals(1, produtos.size());
        assertEquals(produto.getNome(), produtos.get(0).getNome());
    }

    @Test
    void listarPorMarca() {
        when(produtoService.listarPorMarca("Dell")).thenReturn(Collections.singletonList(produto));

        List<Produto> produtos = produtoController.listarPorMarca("Dell");

        assertNotNull(produtos);
        assertEquals(1, produtos.size());
        assertEquals(produto.getNome(), produtos.get(0).getNome());
    }
}
