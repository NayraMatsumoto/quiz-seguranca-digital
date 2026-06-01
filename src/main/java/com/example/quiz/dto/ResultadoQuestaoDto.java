package com.example.quiz.dto;

public record ResultadoQuestaoDto(
        String enunciado,
        String respostaMarcada,
        String respostaCorreta,
        String explicacao,
        String textoRespostaMarcada
) {
}
