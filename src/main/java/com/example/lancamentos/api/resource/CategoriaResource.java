package com.example.lancamentos.api.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.lancamentos.api.model.Categoria;
import com.example.lancamentos.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public List<Categoria> listar() {

		return categoriaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Categoria> create(@RequestBody Categoria categoria, HttpServletResponse httpServletResponse) {
	Categoria categoriaSave = categoriaRepository.save(categoria);
	
	URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
			.buildAndExpand(categoriaSave.getCodigo()).toUri();
	
	httpServletResponse.setHeader("Location", uri.toASCIIString());
	return ResponseEntity.created(uri).body(categoriaSave);
	
	}
	
	@GetMapping("/{codigo}")
	public Categoria findCodigo(@PathVariable Long codigo) {
		return categoriaRepository.findOne(codigo);
	}

}