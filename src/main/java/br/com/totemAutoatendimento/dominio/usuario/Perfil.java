package br.com.totemAutoatendimento.dominio.usuario;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "tipo")
public class Perfil implements GrantedAuthority {
	
	private TipoPerfil tipo;

	@Override
	public String getAuthority() {
		return tipo.getDescricao();
	}
}
