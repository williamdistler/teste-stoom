package com.william.teste_stoom;

import com.william.teste_stoom.controller.MarcaController;
import com.william.teste_stoom.model.Marca;
import com.william.teste_stoom.service.MarcaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MarcaControllerTests {

    @Mock
    private MarcaService marcaService;

    @InjectMocks
    private MarcaController marcaController;

    private Marca marca;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        marca = new Marca();
        marca.setId(1L);
        marca.setNome("Dell");
        marca.setAtivo(true);
    }

    @Test
    void listarTodas() {
        when(marcaService.listarTodas()).thenReturn(Collections.singletonList(marca));

        List<Marca> marcas = marcaController.listarTodas();

        assertNotNull(marcas);
        assertEquals(1, marcas.size());
        assertEquals(marca.getNome(), marcas.get(0).getNome());
    }

    @Test
    void criar() {
        when(marcaService.salvar(any(Marca.class))).thenReturn(marca);

        Marca novaMarca = marcaController.criar(marca);

        assertNotNull(novaMarca);
        assertEquals(marca.getNome(), novaMarca.getNome());
    }

    @Test
    void atualizar() {
        when(marcaService.atualizar(eq(1L), any(Marca.class))).thenReturn(marca);

        Marca marcaAtualizada = new Marca();
        marcaAtualizada.setNome("Apple");

        Marca atualizada = marcaController.atualizar(1L, marcaAtualizada);

        assertNotNull(atualizada);
        assertEquals(marca.getNome(), atualizada.getNome());
    }

    @Test
    void inativar() {
        doNothing().when(marcaService).inativar(1L);

        assertDoesNotThrow(() -> {
            marcaController.inativar(1L);
        });

        verify(marcaService, times(1)).inativar(1L);
    }
}
