package br.com.totemAutoatendimento.aplicacao.mercadoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.dominio.mercadoria.DadosDeMercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;

public class BuscarTodasMercadorias {
    
    private MercadoriaRepository repository;

    public BuscarTodasMercadorias(MercadoriaRepository repository){
        this.repository = repository;
    }

    public Page<DadosDeMercadoria> executar(Pageable paginacao){
        return repository.buscarTodas(paginacao).map(DadosDeMercadoria::new);
    }
}