package com.example.quiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RespostaQuestaoDto {

    @NotBlank(message = "Selecione uma alternativa")
    @Pattern(regexp = "[ABCD]", message = "Resposta inválida")
    private String resposta;

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }
}
