package com.example.quiz.exception;

import com.example.quiz.dto.AlunoInicioDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidation(MethodArgumentNotValidException ex, Model model) {
        model.addAttribute("alunoInicioDto", new AlunoInicioDto());
        model.addAttribute("erro", ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return "index";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneric(Exception ex, Model model) {
        model.addAttribute("alunoInicioDto", new AlunoInicioDto());
        model.addAttribute("erro", "Ocorreu um erro inesperado. Tente novamente.");
        return "index";
    }
}
