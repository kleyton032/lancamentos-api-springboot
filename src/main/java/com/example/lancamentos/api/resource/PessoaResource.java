package com.example.lancamentos.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lancamentos.api.event.RecursoCriadoEvent;
import com.example.lancamentos.api.model.Pessoa;
import com.example.lancamentos.api.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Pessoa> findAll(){
		return pessoaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> create(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
	Pessoa pessoaSave = pessoaRepository.save(pessoa);
	
	publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSave.getCodigo()));
	
	return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSave);
	
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> findCodigo(@PathVariable Long codigo) {
		Pessoa pessoa = pessoaRepository.findOne(codigo);
		 return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
	}
	
	
	
}
