package dev.medkit.server.controller;

import dev.medkit.server.service.PacienteService;
import static dev.medkit.server.CommonMethods.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet()
public class PacienteController extends HttpServlet {

    private PacienteService pacienteService;

    public PacienteController(){
        this.pacienteService = new PacienteService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonResponse = this.pacienteService.findAllPacientes();
        outputResponse(resp, jsonResponse, 200, "application/json");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        outputTextResponse(resp,"Paciente inserido com sucesso!", "Erro ao completar a requisição!", this.pacienteService.novoPaciente(reqBody));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        outputTextResponse(resp, "Paciente atualizado com sucesso!", "Erro ao completar a requisição!", this.pacienteService.atualizarPaciente(reqBody));
    }
}
