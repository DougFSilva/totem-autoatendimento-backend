package br.com.totemAutoatendimento.aplicacao.usuario;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.dominio.Email;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.usuario.CodificadorDeSenha;
import br.com.totemAutoatendimento.dominio.usuario.Password;
import br.com.totemAutoatendimento.dominio.usuario.Perfil;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

public class CriarUsuario {

	private UsuarioRepository repository;

	private CodificadorDeSenha codificador;

	public CriarUsuario(UsuarioRepository repository, CodificadorDeSenha codificador) {
		this.repository = repository;
		this.codificador = codificador;
	}

	 @PreAuthorize("hasRole('ADMIN')")
	 @Transactional
	public Usuario executar(DadosCriarUsuario dados) {
		if (repository.buscarPorCpf(dados.cpf()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException("Usuário com cpf " + dados.cpf() + " já cadastrado!");
		}
		if (repository.buscarPorRegistro(dados.registro()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					"Usuário com registro " + dados.registro() + " já cadastrado!");
		}
		Email email = new Email(dados.email());
		Password password = new Password(dados.senha(), this.codificador);
		List<Perfil> perfis = dados.tipoPerfil().stream().map(Perfil::new).toList();
		Usuario usuario = new Usuario(null, dados.nome(), dados.cpf(), dados.registro(), email, password, perfis);
		return repository.criar(usuario);
	}
}
