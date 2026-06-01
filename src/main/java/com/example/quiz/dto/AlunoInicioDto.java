package com.example.quiz.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class AlunoInicioDto {

    @NotBlank(message = "Informe seu nome")
    private String nome;

    @Min(value = 8, message = "Idade mínima: 8 anos")
    @Max(value = 120, message = "Idade inválida")
    private Integer idade;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }
}
