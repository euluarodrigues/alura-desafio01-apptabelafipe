package br.com.alura.apptabelafipe.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosFipe(@JsonAlias("Marca") String marca,
                        @JsonAlias("Modelo") String modelo,
                        @JsonAlias("AnoModelo") Integer ano,
                        @JsonAlias("Combustivel") String combustivel,
                        @JsonAlias("Valor") String valor) {
}
