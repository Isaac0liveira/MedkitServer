package dev.medkit.server.controller;

import dev.medkit.server.service.ConsultasService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

import static dev.medkit.server.CommonMethods.outputResponse;
import static dev.medkit.server.CommonMethods.outputTextResponse;

public class ConsultasController extends HttpServlet {

    ConsultasService consultasService;

    public ConsultasController(){
        consultasService = new ConsultasService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonResponse = this.consultasService.findConsultasById(request.getParameter("id"));
        outputResponse(response, jsonResponse, 200, "application/json");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        outputTextResponse(resp, "Consulta marcada com sucesso!", "Erro ao marcar consulta!", this.consultasService.inserirConsulta(reqBody));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        outputTextResponse(resp, "Consulta deletada com sucesso!", "Erro ao deletar consulta!", this.consultasService.deletarConsulta(req.getParameter("id")));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        outputTextResponse(resp, "Consulta foi remarcada com sucesso!", "Erro ao remarcar consulta!", this.consultasService.atualizarConsulta(reqBody));
    }
}
