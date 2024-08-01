package com.william.teste_stoom.repository;

import com.william.teste_stoom.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
    List<Marca> findByAtivoTrue();
}
