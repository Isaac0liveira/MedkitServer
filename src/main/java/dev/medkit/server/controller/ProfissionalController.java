package dev.medkit.server.controller;

import dev.medkit.server.service.ProfissionalService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

import static dev.medkit.server.CommonMethods.outputResponse;
import static dev.medkit.server.CommonMethods.outputTextResponse;

public class ProfissionalController extends HttpServlet {

    ProfissionalService profissionalService;

    public ProfissionalController(){
        profissionalService = new ProfissionalService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonResponse = this.profissionalService.findProfissionalByArea(request.getParameter("area"));
        outputResponse(response, jsonResponse, 200, "application/json");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        outputTextResponse(resp, "Profissional inserido com sucesso!", "Erro ao completar a requisição!", this.profissionalService.novoProfissional(reqBody));
    }
}
