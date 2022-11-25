package br.com.totemAutoatendimento.aplicacao.pessoa.usuario;

import java.util.Optional;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.UsuarioRepository;

public class BuscarUsuario {

	private UsuarioRepository repository;

	public BuscarUsuario(UsuarioRepository repository) {
		this.repository = repository;
	}

	public DadosDeUsuario executar(Long id) {
		Optional<Usuario> usuario = repository.buscar(id);
		if(usuario.isEmpty()) {
			throw new ObjetoNaoEncontradoException("Usuário com id " + id + " não encontrado!");
		}
		return new DadosDeUsuario(usuario.get());
	}

}
