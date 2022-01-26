package dev.medkit.server.controller;

import dev.medkit.server.service.ConsultasService;
import dev.medkit.server.service.HospitaisService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.stream.Collectors;

import static dev.medkit.server.CommonMethods.outputResponse;
import static dev.medkit.server.CommonMethods.outputTextResponse;

public class HospitaisController extends HttpServlet {
    HospitaisService hospitaisService;

    public HospitaisController(){
        hospitaisService = new HospitaisService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String jsonResponse = this.hospitaisService.findHospitais();
        outputResponse(response, jsonResponse, 200, "application/json");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        outputTextResponse(resp, "Hospital registrado com sucesso!", "Erro ao registrar hospital.", this.hospitaisService.inserirHospital(reqBody));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        outputTextResponse(resp, "Hospital deletado com sucesso", "Erro ao deletar hospital!", this.hospitaisService.deletarHospital(req.getParameter("id")));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        outputTextResponse(resp, "Hospital editado com sucesso!", "Erro ao editar hospital!", this.hospitaisService.atualizarHospital(reqBody));
    }
}
