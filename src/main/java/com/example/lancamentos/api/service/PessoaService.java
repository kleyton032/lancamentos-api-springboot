package com.example.lancamentos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.lancamentos.api.model.Pessoa;
import com.example.lancamentos.api.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa update(Long codigo, Pessoa pessoa) {

		Pessoa pessoaSave = pessoaRepository.findOne(codigo);
		if (pessoaSave == null) {
			throw new EmptyResultDataAccessException(1);
		}

 		BeanUtils.copyProperties(pessoa, pessoaSave, "codigo");
		pessoaRepository.save(pessoaSave);

		return pessoaRepository.save(pessoaSave);

	}

}
