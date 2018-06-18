package com.example.lancamentos.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lancamentos.api.model.Lancamento;
import com.example.lancamentos.api.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery{

	
	
}
