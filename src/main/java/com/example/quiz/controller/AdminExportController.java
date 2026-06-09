package com.example.quiz.controller;

import com.example.quiz.model.Aluno;
import com.example.quiz.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/admin/export")
public class AdminExportController {

    @Autowired
    private AlunoRepository alunoRepository;

    @GetMapping(path = "/alunos", produces = "text/csv")
    @ResponseStatus(HttpStatus.OK)
    public void exportAlunosCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=alunos.csv");

        List<Aluno> alunos = alunoRepository.findAll();
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        try (PrintWriter w = response.getWriter()) {
            w.println("id,nome,idade,pontuacaoFinal,dataRealizacao");
            for (Aluno a : alunos) {
                String data = a.getDataRealizacao() == null ? "" : a.getDataRealizacao().format(fmt);
                w.print(safe(a.getId()));
                w.print(',');
                w.print(csvEscape(a.getNome()));
                w.print(',');
                w.print(safe(a.getIdade()));
                w.print(',');
                w.print(safe(a.getPontuacaoFinal()));
                w.print(',');
                w.println(csvEscape(data));
            }
        }
    }

    private String csvEscape(Object o) {
        if (o == null) return "";
        String s = String.valueOf(o);
        if (s.contains(",") || s.contains("\"") || s.contains("\n") || s.contains("\r")) {
            s = s.replace("\"", "\"\"");
            return "\"" + s + "\"";
        }
        return s;
    }

    private String safe(Object o) {
        return o == null ? "" : String.valueOf(o);
    }
}
