package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

@Repository
public class UsuarioEntityRepository implements UsuarioRepository {
	
	@Autowired UsuarioEntityJpaRepository repository;

	@Override
	public Usuario criar(Usuario usuario) {
		return repository.save(new UsuarioEntity(usuario)).converterParaUsuario();
	}

	@Override
	public void remover(Usuario usuario) {
		repository.delete(new UsuarioEntity(usuario));
		
	}

	@Override
	public Usuario editar(Usuario usuarioAtualizado) {
		return repository.save(new UsuarioEntity(usuarioAtualizado)).converterParaUsuario();
	}

	@Override
	public Optional<Usuario> buscar(Long id) {
		Optional<UsuarioEntity> entity = repository.findById(id);
		if(entity.isPresent()) {
			return Optional.of(entity.get().converterParaUsuario());
		}
		return Optional.empty();
	}

	@Override
	public Optional<Usuario> buscarPorCpf(String cpf) {
		Optional<UsuarioEntity> entity = repository.findByCpf(cpf);
		if(entity.isPresent()) {
			return Optional.of(entity.get().converterParaUsuario());
		}
		return Optional.empty();
	}

	@Override
	public Optional<Usuario> buscarPorRegistro(String registro) {
		Optional<UsuarioEntity> entity = repository.findByRegistro(registro);
		if(entity.isPresent()) {
			return Optional.of(entity.get().converterParaUsuario());
		}
		return Optional.empty();
	}

	@Override
	public List<Usuario> buscarTodos() {
		return repository.findAll().stream().map(UsuarioEntity::converterParaUsuario).toList();
	}

}
