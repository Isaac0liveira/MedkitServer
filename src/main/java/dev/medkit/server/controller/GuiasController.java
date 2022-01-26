package dev.medkit.server.controller;

import dev.medkit.server.service.GuiasService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.stream.Collectors;

import static dev.medkit.server.CommonMethods.*;

public class GuiasController extends HttpServlet {
    GuiasService guiasService;
    public GuiasController(){
        guiasService = new GuiasService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonResponse = this.guiasService.findGuiasByConsulta(request.getParameter("id"));
        outputResponse(response, jsonResponse, 200, "application/json");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reqBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        outputTextResponse(response, "Guia registrada com sucesso", "Erro ao registrar guia!", this.guiasService.inserirGuia(reqBody));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reqBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        outputTextResponse(response, "Guia atualizada com sucesso", "Erro ao atualizar guia!", this.guiasService.atualizarGuia(reqBody, null));
    }
}
