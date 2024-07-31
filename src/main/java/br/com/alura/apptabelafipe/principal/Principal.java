package br.com.alura.apptabelafipe.principal;

import br.com.alura.apptabelafipe.domain.Dados;
import br.com.alura.apptabelafipe.domain.DadosFipe;
import br.com.alura.apptabelafipe.domain.DadosInteger;
import br.com.alura.apptabelafipe.domain.DadosModelos;
import br.com.alura.apptabelafipe.service.ConsumoApi;
import br.com.alura.apptabelafipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("SpellCheckingInspection")
public class Principal {
    private final Scanner S = new Scanner(System.in);
    private final ConverteDados converte = new ConverteDados();
    ConsumoApi consumoApi = new ConsumoApi();

    public void menuPrincipal() {
        //noinspection SpellCheckingInspection
        var menu = """
                               \s
                #### BOAS VINDAS AO APP TABELA FIPE ###
                               \s
                Qual tipo de veículo vamos pesquisar?
                - Carro
                - Moto
                - Caminhão
                               \s
                Escreva a opção desejada:
               \s""";

        System.out.print(menu);

        String resposta = S.nextLine();

        String endereco = " ";
        String urlBase = "https://parallelum.com.br/fipe/api/v1/";
        if (resposta.toLowerCase().contains("car")){
                endereco = urlBase + "carros/marcas";
            } else if (resposta.toLowerCase().contains("mot")){
                endereco = urlBase + "motos/marcas";
            } else if (resposta.toLowerCase().contains("camin")){
                endereco = urlBase + "caminhoes/marcas";
            } else {
                System.out.println("Esta opção não existe.");
            }

        var json = consumoApi.obterDados(endereco);
        System.out.println(json);

        var marcas = converte.obterLista(json, DadosInteger.class);

        marcas.stream()
                .sorted(Comparator.comparing(DadosInteger::codigo))
                .forEach(System.out::println);

        System.out.println("Digite o código da marca desejada: ");
        resposta = S.nextLine();
        var enderecoModelo = endereco + "/" + resposta + "/modelos";

        json = consumoApi.obterDados(enderecoModelo);
        System.out.println(json);

        var modelosLista = converte.obterDados(json, DadosModelos.class);

        modelosLista.modelos().
                stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Digite um trecho do modelo que você deseja: ");
        var trechoModelo = S.nextLine();

        modelosLista.modelos()
                .stream()
                .filter(dados -> dados.nome().toLowerCase().contains(trechoModelo.toLowerCase()))
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Digite o código do modelo desejado: ");
        var modelo = S.nextLine();

        var enderecoFinal = enderecoModelo + "/" + modelo + "/anos";
        json = consumoApi.obterDados(enderecoFinal);

        var anos = converte.obterLista(json, Dados.class);

        List<DadosFipe> listaDadosFipe = new ArrayList<>();

        for (Dados ano : anos) {
            enderecoFinal = enderecoModelo + "/" + modelo + "/anos/" + ano.codigo();
            json = consumoApi.obterDados(enderecoFinal);
            var dadosFipe = converte.obterDados(json, DadosFipe.class);
            listaDadosFipe.add(dadosFipe);
        }
        System.out.println("### Marca: " + listaDadosFipe.getFirst().marca() + " ###");
        System.out.println("### Modelo: " + listaDadosFipe.getFirst().modelo() + " ###");
        listaDadosFipe.stream()
                .sorted(Comparator.comparing(DadosFipe::ano).reversed())
                .forEach(dadosFipe -> System.out.println("Ano: " + dadosFipe.ano() + ", "
                        + "Combustível: " + dadosFipe.combustivel() + ", "
                + "Valor: " + dadosFipe.valor()));
    }
}
