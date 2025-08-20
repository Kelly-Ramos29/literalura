package com.alura.literalura.principal;

import com.alura.literalura.dto.AutorDTO;
import com.alura.literalura.dto.LivroDTO;
import com.alura.literalura.dto.RespostaLivroDTO;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import com.alura.literalura.service.AutorService;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConverteDados;
import com.alura.literalura.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Principal {
    @Autowired
    private LivroService livroService;
    @Autowired
    private AutorService autorService;
    @Autowired
    private ConsumoAPI consumoAPI;
    @Autowired
    private ConverteDados converteDados;
    private static final String BASE_URL = "https://gutendex.com/books/";

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("***** LITERALURA *****");
            System.out.println("1 - Buscar livro pelo título:");
            System.out.println("2 - Buscar livro registrados:");
            System.out.println("3 - Listar autores registrados:");
            System.out.println("4 - Listar autores vivos em determinado ano:");
            System.out.println("5 - Listar livros por idioma:");
            System.out.println("0 - Sair");
            System.out.println("Selecione uma das opções: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o titulo do livro: ");
                    String titulo = scanner.nextLine();
                    try{
                        String encodedTitulo = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
                        String json = consumoAPI.obterDados( BASE_URL + "?search=" + encodedTitulo);
                        RespostaLivroDTO respostaLivroDTO = converteDados.obterDados(json, RespostaLivroDTO.class);
                        List<LivroDTO> livrosDTO = respostaLivroDTO.getLivros();
                        if (livrosDTO.isEmpty()) {
                            System.out.println("Livro não encontrado na API");
                        } else {
                            boolean livroRegistrado = false;

                            for (LivroDTO livroDTO : livrosDTO) {
                                if (livroDTO.getTitulo().equalsIgnoreCase(titulo)) {
                                    Optional<Livro> livroExistente = livroService.obterLivroPorTitulo(titulo);

                                    if (livroExistente.isPresent()) {
                                        System.out.println("Detalhe: Chave (titulo)=(" + titulo + ") já existe");
                                        System.out.println("Não é possível registrar o mesmo livro mais de uma vez");
                                        livroRegistrado = true;
                                        break;
                                    } else {
                                        Livro livro = new Livro();
                                        livro.setTitulo(livroDTO.getTitulo());
                                        livro.setIdioma(livroDTO.getIdiomas().get(0));
                                        livro.setNumeroDeDownloads(livroDTO.getNumeroDeDownloads());

                                        // Buscar ou criar o Autor
                                        AutorDTO primeiroAutorDTO = livroDTO.getAutores().get(0);
                                         Autor autor = autorService.obterAutorPorNome(primeiroAutorDTO.getNome())
                                                .orElseGet(() -> {
                                                    Autor novoAutor = new Autor();
                                                    novoAutor.setNome(primeiroAutorDTO.getNome());
                                                    novoAutor.setAnoNascimento(primeiroAutorDTO.getAnoNascimento());
                                                    novoAutor.setAnoFalecimento(primeiroAutorDTO.getAnoFalecimento());
                                                    return autorService.criarAutor(novoAutor);
                                                });

                                        // Associar o Autor ao Livro
                                        livro.setAutor(autor);

                                        livroService.criarLivro(livro);
                                        System.out.println("Livro registrado: " + livro.getTitulo());
                                        mostrarDetalhesLivro(livroDTO);
                                        livroRegistrado = true;
                                        break;
                            }
                        }
                        }
                        if (!livroRegistrado){
                            System.out.println("Não foi encontrado nenhum livro com o título ' " + titulo + " ' na API");
                                    }
                        }
                    } catch (Exception e)  {
                        System.out.println("Error ao obter dados da API: " + e.getMessage());
                    };
                    break;

                case 2:
                    livroService.listarLivros().forEach(livro -> {
                        System.out.println("---LIVRO---");
                        System.out.println("Título: " + livro.getTitulo());
                        System.out.println("Autor: " + (livro.getAutor() != null ? livro.getAutor().getNome() : "N/A"));
                        System.out.println("Idioma: " + livro.getIdioma());
                        System.out.println("Número de downloads: " + livro.getNumeroDeDownloads());
                    });
                    break;

                case 3:
                    autorService.listarAutores().forEach(autor -> {
                        System.out.println("---AUTOR---");
                        System.out.println("Autor: " + autor.getNome());
                        System.out.println("Data de nascimento: " + autor.getAnoNascimento());
                        System.out.println("Data de falecimento: " + (autor.getAnoFalecimento() != null ? autor.getAnoFalecimento() : "N/A"));
                        String livros = autor.getLivros().stream()
                                .map(Livro::getTitulo)
                                .collect(Collectors.joining(", "));
                        System.out.println("Livros: [ " + livros + " ]");
                    });
                    break;

                case 4:
                    System.out.print("Digite o ano para buscar autor(es) vivos naquele ano: ");
                    int ano = scanner.nextInt();
                    scanner.nextLine(); // Consumir o salto de linha

                    List<Autor> autoresVivos = autorService.listarAutoresVivosNoAno(ano);

                    if (autoresVivos.isEmpty()) {
                        System.out.println("Não foram encontrados autores vivos no ano " + ano);
                    } else {
                        autoresVivos.forEach(autor -> {
                            System.out.println("---AUTOR---");
                            System.out.println("Autor: " + autor.getNome());
                            System.out.println("Data de nascimento: " + autor.getAnoNascimento());
                            System.out.println("Data de falecimento: " + (autor.getAnoFalecimento() != null ? autor.getAnoFalecimento() : "N/A"));
                            System.out.println("Livros: " + autor.getLivros().size());
                        });
                    }
                    break;


                case 5:
                    System.out.println("Digite o idioma:");
                    System.out.println("es - Espanhol");
                    System.out.println("en - Inglês");
                    System.out.println("fr - Francês");
                    System.out.println("pt - Português");
                    String idioma = scanner.nextLine();

                    if ("es".equalsIgnoreCase(idioma) || "en".equalsIgnoreCase(idioma) ||
                            "fr".equalsIgnoreCase(idioma) || "pt".equalsIgnoreCase(idioma)) {

                        LivroService.listarLivrosPorIdioma(idioma).forEach(livro -> {
                            System.out.println("---LIVRO---");
                            System.out.println("Título: " + livro.getTitulo());
                            System.out.println("Autor: " + (livro.getAutor() != null ? livro.getAutor().getNome() : "N/A"));
                            System.out.println("Idioma: " + livro.getIdioma());
                            System.out.println("Número de downloads: " + livro.getNumeroDeDownloads());
                        });
                    } else {
                        System.out.println("Idioma não válido. Tente novamente.");
                    }
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção não válida. Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private void mostrarDetalhesLivro(LivroDTO livroDTO) {  // 1 uso
        System.out.println("---LIVRO---");
        System.out.println("Título: " + livroDTO.getTitulo());
        System.out.println("Autor: " + (livroDTO.getAutores().isEmpty() ? "Desconhecido" : livroDTO.getAutores().get(0).getNome()));
        System.out.println("Idioma: " + livroDTO.getIdiomas().get(0));
        System.out.println("Número de downloads: " + livroDTO.getNumeroDeDownloads());

    }
}