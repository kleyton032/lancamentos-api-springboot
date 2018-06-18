package com.example.lancamentos.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.lancamentos.api.model.Lancamento;
import com.example.lancamentos.api.model.Pessoa;
import com.example.lancamentos.api.repository.LancamentoRepository;
import com.example.lancamentos.api.repository.PessoaRepository;
import com.example.lancamentos.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	private PessoaRepository pessoaRepository; 
	
	@Autowired
	private LancamentoRepository lancamentoRepository;

	public Lancamento salvarLancamento(Lancamento lancamento) {
		
		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		return lancamentoRepository.save(lancamento);
	}

	
	
	
	
}
