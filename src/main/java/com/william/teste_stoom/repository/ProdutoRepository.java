package com.william.teste_stoom.repository;

import com.william.teste_stoom.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByAtivoTrue();
    List<Produto> findByCategoriaNomeAndAtivoTrue(String categoriaNome);
    List<Produto> findByMarcaNomeAndAtivoTrue(String marcaNome);
}
