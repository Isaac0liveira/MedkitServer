package dev.medkit.server.controller;

import dev.medkit.server.service.ExamesService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.stream.Collectors;

import static dev.medkit.server.CommonMethods.outputResponse;
import static dev.medkit.server.CommonMethods.outputTextResponse;

public class ExamesController extends HttpServlet {
    ExamesService examesService;

    public ExamesController(){
        examesService = new ExamesService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonResponse = this.examesService.findExamesById(request.getParameter("id"));
        outputResponse(response, jsonResponse, 200, "application/json");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        outputTextResponse(resp, "Exame marcado com sucesso!", "Erro ao marcar exame!", this.examesService.inserirExame(reqBody));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        outputTextResponse(resp, "Exame deletado com sucesso!", "Erro ao deletar exame!", this.examesService.deletarExame(req.getParameter("id")));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        outputTextResponse(resp, "Exame foi remarcado com sucesso!", "Erro ao remarcar exame!", this.examesService.atualizarExame(reqBody));
    }
}
