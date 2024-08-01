package com.william.teste_stoom;

import com.william.teste_stoom.controller.CategoriaController;
import com.william.teste_stoom.model.Categoria;
import com.william.teste_stoom.service.CategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriaControllerTests {

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private CategoriaController categoriaController;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Eletrônicos");
        categoria.setAtivo(true);
    }

    @Test
    void listarTodas() {
        when(categoriaService.listarTodas()).thenReturn(Collections.singletonList(categoria));

        List<Categoria> categorias = categoriaController.listarTodas();

        assertNotNull(categorias);
        assertEquals(1, categorias.size());
        assertEquals(categoria.getNome(), categorias.get(0).getNome());
    }

    @Test
    void criar() {
        when(categoriaService.salvar(any(Categoria.class))).thenReturn(categoria);

        Categoria novaCategoria = categoriaController.criar(categoria);

        assertNotNull(novaCategoria);
        assertEquals(categoria.getNome(), novaCategoria.getNome());
    }

    @Test
    void atualizar() {
        when(categoriaService.atualizar(eq(1L), any(Categoria.class))).thenReturn(categoria);

        Categoria categoriaAtualizada = new Categoria();
        categoriaAtualizada.setNome("Computadores");

        Categoria atualizada = categoriaController.atualizar(1L, categoriaAtualizada);

        assertNotNull(atualizada);
        assertEquals(categoria.getNome(), atualizada.getNome());
    }

    @Test
    void inativar() {
        doNothing().when(categoriaService).inativar(1L);

        assertDoesNotThrow(() -> {
            categoriaController.inativar(1L);
        });

        verify(categoriaService, times(1)).inativar(1L);
    }
}

