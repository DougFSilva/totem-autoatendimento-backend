package br.com.totemAutoatendimento.infraestrutura.web;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.totemAutoatendimento.aplicacao.pessoa.usuario.BuscarUsuario;
import br.com.totemAutoatendimento.aplicacao.pessoa.usuario.CriarUsuario;
import br.com.totemAutoatendimento.aplicacao.pessoa.usuario.DadosCriarUsuario;
import br.com.totemAutoatendimento.aplicacao.pessoa.usuario.DadosDoUsuario;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.CodificadorDeSenha;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.UsuarioRepository;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private CodificadorDeSenha codificador;

	@PostMapping
	@Transactional
	public ResponseEntity<Usuario> criarUsuario(@RequestBody @Valid DadosCriarUsuario dados) {
		CriarUsuario criarUsuario = new CriarUsuario(repository, codificador);
		Usuario usuario = criarUsuario.executar(dados);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/id={id}")
				.buildAndExpand(usuario.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<DadosDoUsuario> buscarUsuario(@PathVariable Long id){
		BuscarUsuario buscarUsuario = new BuscarUsuario(repository);
		return ResponseEntity.ok().body(buscarUsuario.executar(id));
	}
}
