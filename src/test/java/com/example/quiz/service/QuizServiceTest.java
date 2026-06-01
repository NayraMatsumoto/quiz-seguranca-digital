package com.example.quiz.service;

import com.example.quiz.model.Questao;
import com.example.quiz.repository.AlunoRepository;
import com.example.quiz.repository.QuestaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class QuizServiceTest {

    @InjectMocks
    private QuizService quizService;

    @Mock
    private QuestaoRepository questaoRepository;

    @Mock
    private AlunoRepository alunoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalcularPontuacao() {
        Questao q1 = new Questao();
        q1.setRespostaCorreta("A");

        Questao q2 = new Questao();
        q2.setRespostaCorreta("B");

        List<Questao> questoes = Arrays.asList(q1, q2);
        List<String> respostas = Arrays.asList("A", "B");

        int pontuacao = quizService.calcularPontuacao(respostas, questoes);

        assertEquals(2, pontuacao);
    }

    @Test
    public void testSortearQuestoesRetornaCincoSemRepeticao() {
        Questao q1 = new Questao(); q1.setId(1L);
        Questao q2 = new Questao(); q2.setId(2L);
        Questao q3 = new Questao(); q3.setId(3L);
        Questao q4 = new Questao(); q4.setId(4L);
        Questao q5 = new Questao(); q5.setId(5L);
        Questao q6 = new Questao(); q6.setId(6L);
        Questao q7 = new Questao(); q7.setId(7L);
        Questao q8 = new Questao(); q8.setId(8L);
        Questao q9 = new Questao(); q9.setId(9L);
        Questao q10 = new Questao(); q10.setId(10L);

        when(questaoRepository.findAll()).thenReturn(Arrays.asList(q1, q2, q3, q4, q5, q6, q7, q8, q9, q10));

        List<Questao> sorteadas = quizService.sortearQuestoes(5);

        assertEquals(5, sorteadas.size());
        assertEquals(5, sorteadas.stream().map(Questao::getId).distinct().count());
        assertTrue(sorteadas.stream().allMatch(questao -> questao.getId() >= 1L && questao.getId() <= 10L));
    }
}
