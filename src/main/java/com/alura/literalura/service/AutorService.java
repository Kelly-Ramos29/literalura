package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> listarAutores() {
        return autorRepository.findAllComLivros();
    }

    // Método único para autores vivos (removida duplicação)
    public List<Autor> listarAutoresVivosNoAno(int ano) {
        return autorRepository.findAutoresVivosNoAnoComLivros(ano);
    }

    // CORRIGIDO: removido static
    public Autor criarAutor(Autor autor) {
        return autorRepository.save(autor);
    }

    public Optional<Autor> obterAutorPorId(Long id) {
        return autorRepository.findById(id);
    }

    // MELHORADO: adicionado IgnoreCase para busca case-insensitive
    public Optional<Autor> obterAutorPorNome(String nome) {
        return autorRepository.findByNomeIgnoreCase(nome);
    }

    public Autor atualizarAutor(Long id, Autor autorDetalhe) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não Encontrado"));
        autor.setNome(autorDetalhe.getNome());
        autor.setAnoNascimento(autorDetalhe.getAnoNascimento());
        autor.setAnoFalecimento(autorDetalhe.getAnoFalecimento());
        return autorRepository.save(autor);
    }

    public void excluirAutor(Long id) {
        autorRepository.deleteById(id);
    }
}