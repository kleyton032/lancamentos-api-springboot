package com.example.lancamentos.api.repository.lancamento;

import java.util.List;

import com.example.lancamentos.api.model.Lancamento;
import com.example.lancamentos.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	
	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
	
}
