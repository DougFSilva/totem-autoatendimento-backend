package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import java.util.Optional;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;

public class BuscarCategoria {
	
private CategoriaRepository repository;
	
	public BuscarCategoria(CategoriaRepository repository) {
		this.repository = repository;
	}
	
	public Categoria executar(Long id) {
		Optional<Categoria> categoria = repository.buscar(id);
		return categoria.orElseThrow(() -> new ObjetoNaoEncontradoException("Categoria com id " + id + " não encontrada!"));
	}

}