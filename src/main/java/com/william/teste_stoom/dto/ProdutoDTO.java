package com.william.teste_stoom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoDTO {

    private Long id;
    private String nome;
    private Long categoriaId;
    private Long marcaId;
    private BigDecimal preco;

}
