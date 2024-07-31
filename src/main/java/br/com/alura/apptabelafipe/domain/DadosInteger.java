package br.com.alura.apptabelafipe.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosInteger (Integer codigo, String nome) {
    @Override
    public String toString() {
        return  codigo + ": " + nome;
    }
}
