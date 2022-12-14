package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.BuscarTodasSubcategorias;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.CriarSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.EditarSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.RemoverSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.UploadImagemDaSubcategoria;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria.MercadoriaEntityRepository;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria.subcategoria.SubcategoriaEntityRepository;

@Configuration
public class SubcategoriaBeanConfiguration {
    
    @Autowired
    private SubcategoriaEntityRepository repository;

    @Autowired
    private MercadoriaEntityRepository mercadoriaRepository;

    @Bean
    public CriarSubcategoria criarSubcategoria() {
        return new CriarSubcategoria(repository);
    }

    @Bean
    public RemoverSubcategoria removerSubcategoria() {
        return new RemoverSubcategoria(repository, mercadoriaRepository);
    }

    @Bean
    public EditarSubcategoria editarSubcategoria() {
        return new EditarSubcategoria(repository);
    }

    @Bean
    public BuscarTodasSubcategorias buscarTodasSubcategorias() {
        return new BuscarTodasSubcategorias(repository);
    }

    @Bean
    public UploadImagemDaSubcategoria uploadImagemDaSubcategoria() {
        return new UploadImagemDaSubcategoria(repository);
    }
}
