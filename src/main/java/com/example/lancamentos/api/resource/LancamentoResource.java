package com.example.lancamentos.api.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.lancamentos.api.event.RecursoCriadoEvent;
import com.example.lancamentos.api.exceptionhandler.LancamentosExceptionHandler.Erro;
import com.example.lancamentos.api.model.Lancamento;
import com.example.lancamentos.api.repository.LancamentoRepository;
import com.example.lancamentos.api.repository.filter.LancamentoFilter;
import com.example.lancamentos.api.service.LancamentoService;
import com.example.lancamentos.api.service.exception.PessoaInexistenteOuInativaException;


@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private LancamentoService LancamentoService;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping
	public List<Lancamento> findAll(LancamentoFilter lancamentoFilter) {

		return lancamentoRepository.filtrar(lancamentoFilter);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> findCodigo(@PathVariable Long codigo) {
		Lancamento lancamento = lancamentoRepository.findOne(codigo);
		return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Lancamento> create(@Valid @RequestBody Lancamento lancamento, HttpServletResponse reponse) {

		Lancamento lancamentoSave = LancamentoService.salvarLancamento(lancamento);

		publisher.publishEvent(new RecursoCriadoEvent(this, reponse, lancamentoSave.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSave);

	}
	
	@ExceptionHandler({ PessoaInexistenteOuInativaException.class })
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long codigo) {
		lancamentoRepository.delete(codigo);

	}

}
