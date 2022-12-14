package br.com.totemAutoatendimento.infraestrutura.web;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.totemAutoatendimento.aplicacao.mercadoria.BuscarImagem;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.BuscarTodasSubcategorias;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.CriarSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.EditarSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.RemoverSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.UploadImagemDaSubcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "mercadoria/subcategoria")
public class SubcategoriaController {

    @Value("${imagens.path}")
    private String path;

    @Autowired
    private CriarSubcategoria criarSubcategoria;

    @Autowired
    private RemoverSubcategoria removerSubcategoria;

    @Autowired
    private EditarSubcategoria editarSubcategoria;

    @Autowired
    private BuscarTodasSubcategorias buscarTodasSubcategorias;

    @Autowired
    private UploadImagemDaSubcategoria uploadImagemDaSubcategoria;

    @PostMapping(value = "/{nome}")
    @CacheEvict(value = "buscarTodasSubcategorias", allEntries = true)
    @Operation(summary = "Criar subcategoria", description = "Cria uma subcategoria para cadastrar as mercadorias")
    public ResponseEntity<Subcategoria> criarSubcategoria(@PathVariable String nome) {
        Subcategoria subcategoria = criarSubcategoria.executar(nome);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{nome}").buildAndExpand(subcategoria.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/{id}")
    @CacheEvict(value = "buscarTodasSubcategorias", allEntries = true)
        @Operation(summary = "Remover subcategoria", description = "Remove alguma categoria existente")
    public ResponseEntity<Void> removerSubcategoria(@PathVariable Long id) {
        removerSubcategoria.executar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @CacheEvict(value = "buscarTodasSubcategorias", allEntries = true)
        @Operation(summary = "Editar subcategoria", description = "Edita alguma categoria existente")
    public ResponseEntity<Subcategoria> editarSubcategoria(@RequestBody Subcategoria subcategoriaAtualizada) {
        return ResponseEntity.ok().body(editarSubcategoria.executar(subcategoriaAtualizada));
    }

    @GetMapping
    @Cacheable(value = "buscarTodasSubcategorias")
        @Operation(summary = "Buscar todas subcategorias", description = "Busca todas subcategorias existentes")
    public ResponseEntity<Page<Subcategoria>> buscarTodasSubcategorias(Pageable paginacao) {
        return ResponseEntity.ok().body(buscarTodasSubcategorias.executar(paginacao));
    }

    @PostMapping(value = "/{id}/imagem")
        @Operation(summary = "Adicionar imagem ?? subcategoria", description = "Adiciona uma imagem em jpg ou png ?? uma subcategoria existente")
    public ResponseEntity<Void> adicionarImagemASubcategoria(@PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        String nomeDaImagem = id + "-" + file.getOriginalFilename();
        String pathLocal = this.path + "/mercadoria/subcategoria/" + nomeDaImagem;
        String urlServidor = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()
                + "/mercadoria/subcategoria/imagem/" + nomeDaImagem;
        uploadImagemDaSubcategoria.executar(id, file, pathLocal, urlServidor);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/imagem/{nomeDaImagem}")
    public ResponseEntity<byte[]> buscarImagemDaSubcategoria(@PathVariable String nomeDaImagem) {
        String extensao = nomeDaImagem.split("\\.")[1];
        BuscarImagem buscarImagem = new BuscarImagem();
        byte[] imagem = buscarImagem.executar(path + "mercadoria/subcategoria/" + nomeDaImagem);
        HttpHeaders httpHeaders = new HttpHeaders();
        switch (extensao.toLowerCase()) {
            case "jpg":
                httpHeaders.setContentType(MediaType.IMAGE_JPEG);
                break;
            case "png":
                httpHeaders.setContentType(MediaType.IMAGE_PNG);
                break;
        }
        return ResponseEntity.ok().headers(httpHeaders).body(imagem);
    }

}
