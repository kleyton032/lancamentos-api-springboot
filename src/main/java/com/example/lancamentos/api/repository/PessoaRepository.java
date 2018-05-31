package com.example.lancamentos.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lancamentos.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
