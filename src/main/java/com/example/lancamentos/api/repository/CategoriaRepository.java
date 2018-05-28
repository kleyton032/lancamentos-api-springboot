package com.example.lancamentos.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lancamentos.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
