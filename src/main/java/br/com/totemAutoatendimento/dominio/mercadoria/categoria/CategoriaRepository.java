package br.com.totemAutoatendimento.dominio.mercadoria.categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository {

	Categoria criar(Categoria categoria);
	
	void remover(Categoria categoria);
	
	Categoria editar(Long id, Categoria categoriaAtualizada);
	
	Optional<Categoria> buscar(Long id);
	
	Optional<Categoria> buscarCategoriaPorNome(String nome);
	
	List<Categoria> buscarTodas();
	
}