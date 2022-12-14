package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;

public class BuscarCategoriaPorNome {

	private CategoriaRepository repository;

	public BuscarCategoriaPorNome(CategoriaRepository repository) {
		this.repository = repository;
	}

	@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
	public Categoria executar(String nome) {
		Optional<Categoria> categoria = repository.buscarPorNome(nome);
		return categoria
				.orElseThrow(() -> new ObjetoNaoEncontradoException("Categoria com nome " + nome + " não encontrada!"));
	}
}
