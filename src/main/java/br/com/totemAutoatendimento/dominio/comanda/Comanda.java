package br.com.totemAutoatendimento.dominio.comanda;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.totemAutoatendimento.dominio.pessoa.cliente.Cliente;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Comanda {

	private Long id;
	
	private String cartao;
	
	private Cliente cliente;
	
	private List<Pedido> pedidos = new ArrayList<>();
	
	private LocalDateTime abertura;
	
	private LocalDateTime fechamento;
	
	private Boolean aberta;
	
	private TipoPagamento tipoPagamento;
	
	private Integer desconto;
	
}
