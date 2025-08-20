package com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class AutorDTO {

        @JsonProperty("name")
        private String nome;
        @JsonProperty("birth_year")
        private int anoNascimento;
        @JsonProperty("death_year")
        private int anoFalecimento;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public int getAnoNascimento() {
            return anoNascimento;
        }

        public void setAnoNascimento(int anoNascimento) {
            this.anoNascimento = anoNascimento;
        }

        public int getAnoFalecimento() {
            return anoFalecimento;
        }

        public void setAnoFalecimento(int anoFalecimento) {
            this.anoFalecimento = anoFalecimento;
        }
    }

