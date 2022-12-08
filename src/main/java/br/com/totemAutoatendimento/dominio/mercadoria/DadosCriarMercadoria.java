package br.com.totemAutoatendimento.dominio.mercadoria;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record DadosCriarMercadoria(

		@NotBlank String codigo,

		@NotBlank String categoria,

		@NotBlank String subcategoria,

		@NotBlank String descricao,

		@NotNull Integer quantidade,

		@NotNull BigDecimal preco,

		@NotNull Boolean promocao,

		@NotNull BigDecimal precoPromocional) {

}
