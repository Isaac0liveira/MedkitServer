package dev.medkit.server.controller;

import dev.medkit.server.service.CirurgiasService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

import static dev.medkit.server.CommonMethods.outputResponse;
import static dev.medkit.server.CommonMethods.outputTextResponse;

@WebServlet(urlPatterns = {"/", "/byUsuario"})
public class CirurgiasController extends HttpServlet {

    CirurgiasService cirurgiasService;
    public CirurgiasController(){
        cirurgiasService = new CirurgiasService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String jsonResponse = request.getServletPath().equals("/cirurgias/byUsuario")?   this.cirurgiasService.findCirurgiasByUsuario(request.getParameter("id"))
                :   this.cirurgiasService.findCirurgiasById(request.getParameter("id"));

        outputResponse(response, jsonResponse, 200, "application/json");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        outputTextResponse(resp, "Cirurgia marcada com sucesso!", "Erro ao marcar cirurgia!", this.cirurgiasService.inserirCirurgia(reqBody));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        outputTextResponse(resp, "Cirurgia deletada com sucesso!", "Erro ao deletar cirurgia!", this.cirurgiasService.deletarCirurgia(req.getParameter("id")));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        outputTextResponse(resp, "Cirurgia foi remarcada com sucesso!", "Erro ao remarcar cirurgia!", this.cirurgiasService.atualizarCirurgia(reqBody));
    }
}
