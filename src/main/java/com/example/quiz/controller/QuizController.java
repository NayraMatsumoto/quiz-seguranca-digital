package com.example.quiz.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quiz.dto.AlunoInicioDto;
import com.example.quiz.dto.RespostaQuestaoDto;
import com.example.quiz.dto.ResultadoQuestaoDto;
import com.example.quiz.model.Aluno;
import com.example.quiz.model.Questao;
import com.example.quiz.service.QuizService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping
    public String home(Model model) {
        model.addAttribute("alunoInicioDto", new AlunoInicioDto());
        return "index";
    }

    @PostMapping("/iniciar")
    public String iniciarQuiz(@Valid @ModelAttribute("alunoInicioDto") AlunoInicioDto alunoInicioDto,
                              BindingResult bindingResult,
                              HttpSession session,
                              Model model) {
        if (bindingResult.hasErrors()) {
            return "index";
        }

        Aluno aluno = quizService.iniciarAluno(alunoInicioDto.getNome(), alunoInicioDto.getIdade());
        List<Questao> questoes = quizService.sortearQuestoes(5);
        List<String> respostas = quizService.inicializarRespostas(questoes.size());

        session.setAttribute("aluno", aluno);
        session.setAttribute("questoes", questoes);
        session.setAttribute("respostas", respostas);
        session.setAttribute("indiceAtual", 0);

        return "redirect:/quiz";
    }

    @GetMapping("/quiz")
    public String exibirPergunta(HttpSession session, Model model) {
        List<Questao> questoes = (List<Questao>) session.getAttribute("questoes");
        Integer indiceAtual = (Integer) session.getAttribute("indiceAtual");

        if (questoes == null || indiceAtual == null) {
            return "redirect:/";
        }

        if (indiceAtual >= questoes.size()) {
            return "redirect:/resultado";
        }

        model.addAttribute("questao", questoes.get(indiceAtual));
        model.addAttribute("indiceAtual", indiceAtual + 1);
        model.addAttribute("total", questoes.size());
        model.addAttribute("respostaQuestaoDto", new RespostaQuestaoDto());
        return "quiz";
    }

    @PostMapping("/quiz/responder")
    public String responderPergunta(@Valid @ModelAttribute("respostaQuestaoDto") RespostaQuestaoDto respostaQuestaoDto,
                                    BindingResult bindingResult,
                                    HttpSession session,
                                    Model model) {
        List<Questao> questoes = (List<Questao>) session.getAttribute("questoes");
        List<String> respostas = (List<String>) session.getAttribute("respostas");
        Integer indiceAtual = (Integer) session.getAttribute("indiceAtual");

        if (questoes == null || respostas == null || indiceAtual == null) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("questao", questoes.get(indiceAtual));
            model.addAttribute("indiceAtual", indiceAtual + 1);
            model.addAttribute("total", questoes.size());
            return "quiz";
        }

        respostas.set(indiceAtual, respostaQuestaoDto.getResposta());
        session.setAttribute("indiceAtual", indiceAtual + 1);

        if (indiceAtual + 1 >= questoes.size()) {
            return "redirect:/resultado";
        }

        return "redirect:/quiz";
    }

    @GetMapping("/resultado")
    public String resultado(HttpSession session, Model model) {
        Aluno aluno = (Aluno) session.getAttribute("aluno");
        List<Questao> questoes = (List<Questao>) session.getAttribute("questoes");
        List<String> respostas = (List<String>) session.getAttribute("respostas");

        if (aluno == null || questoes == null || respostas == null) {
            return "redirect:/";
        }

        int pontuacao = quizService.calcularPontuacao(respostas, questoes);
        aluno = quizService.finalizarQuiz(aluno, pontuacao);

        List<ResultadoQuestaoDto> resumo = new ArrayList<>();
        for (int i = 0; i < questoes.size(); i++) {
            Questao questao = questoes.get(i);
            String respostaMarcada = respostas.get(i);
            resumo.add(new ResultadoQuestaoDto(
                    questao.getEnunciado(),
                    respostaMarcada,
                    questao.getRespostaCorreta(),
                    questao.getExplicacao(),
                    questao.getOpcaoPorLetra(respostaMarcada)
            ));
        }

        model.addAttribute("aluno", aluno);
        model.addAttribute("pontuacao", pontuacao);
        model.addAttribute("total", questoes.size());
        model.addAttribute("mensagem", quizService.mensagemMotivacional(pontuacao, questoes.size()));
        model.addAttribute("resumo", resumo);

        session.invalidate();
        return "resultado";
    }
}
