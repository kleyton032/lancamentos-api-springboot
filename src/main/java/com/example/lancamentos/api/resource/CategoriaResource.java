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
import com.example.lancamentos.api.model.Categoria;
import com.example.lancamentos.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Categoria> findAll() {

		return categoriaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Categoria> create(@Valid @RequestBody Categoria categoria, HttpServletResponse reponse) {
	Categoria categoriaSave = categoriaRepository.save(categoria);
	
	publisher.publishEvent(new RecursoCriadoEvent(this, reponse, categoriaSave.getCodigo()));
	
	return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSave);
	
	}
	
	@GetMapping("/{codigo}")
	public Categoria findCodigo(@PathVariable Long codigo) {
		return categoriaRepository.findOne(codigo);
	}

}
