package com.example.quiz.service;

import com.example.quiz.model.Aluno;
import com.example.quiz.model.Questao;
import com.example.quiz.repository.AlunoRepository;
import com.example.quiz.repository.QuestaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuestaoRepository questaoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    public List<Questao> carregarQuestoes() {
        return questaoRepository.findAll();
    }

    public List<Questao> sortearQuestoes(int quantidade) {
        List<Questao> questoes = new ArrayList<>(carregarQuestoes());

        if (questoes.size() < quantidade) {
            throw new IllegalStateException("Não há questões suficientes para sortear " + quantidade + " itens.");
        }

        Collections.shuffle(questoes);
        return questoes.subList(0, quantidade);
    }

    public Aluno iniciarAluno(String nome, Integer idade) {
        Aluno aluno = new Aluno();
        aluno.setNome(nome);
        aluno.setIdade(idade);
        aluno.setPontuacaoFinal(0);
        aluno.setDataRealizacao(LocalDateTime.now());
        return alunoRepository.save(aluno);
    }

    public int calcularPontuacao(List<String> respostas, List<Questao> questoes) {
        int pontuacao = 0;
        for (int i = 0; i < questoes.size(); i++) {
            String resposta = i < respostas.size() ? respostas.get(i) : "";
            if (resposta != null && resposta.equalsIgnoreCase(questoes.get(i).getRespostaCorreta())) {
                pontuacao++;
            }
        }
        return pontuacao;
    }

    public Aluno finalizarQuiz(Aluno aluno, int pontuacao) {
        aluno.setPontuacaoFinal(pontuacao);
        aluno.setDataRealizacao(LocalDateTime.now());
        return alunoRepository.save(aluno);
    }

    public String mensagemMotivacional(int pontuacao, int totalQuestoes) {
        double percentual = (pontuacao * 100.0) / totalQuestoes;
        if (percentual >= 80) {
            return "Excelente! Você já domina boas práticas de segurança digital.";
        }
        if (percentual >= 50) {
            return "Muito bom! Continue praticando para ficar ainda mais seguro online.";
        }
        return "Você está começando bem. Revise o gabarito e tente novamente!";
    }

    public List<String> inicializarRespostas(int totalQuestoes) {
        List<String> respostas = new ArrayList<>();
        for (int i = 0; i < totalQuestoes; i++) {
            respostas.add("");
        }
        return respostas;
    }
}
