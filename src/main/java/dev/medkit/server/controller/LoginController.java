package dev.medkit.server.controller;

import dev.medkit.server.service.ConsultasService;
import dev.medkit.server.service.LoginService;
import dev.medkit.server.service.PacienteService;
import static dev.medkit.server.CommonMethods.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.stream.Collectors;

public class LoginController extends HttpServlet {

    LoginService loginService;

    public LoginController(){
        loginService = new LoginService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String jsonResponse = this.loginService.findUsuario(request.getParameter("login"), request.getParameter("senha"));
        outputResponse(response, jsonResponse, 200, "application/json");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        outputTextResponse(resp, "Usuário registrado com sucesso!", "Erro ao registrar usuário", this.loginService.inserirUsuario(reqBody));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        outputTextResponse(resp, "Usuário deletado com sucesso!", "Erro ao deletar usuário!", this.loginService.deletarUsuario(req.getParameter("id")));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        outputTextResponse(resp, "Usuário editado com sucesso", "Erro ao editar usuário!", this.loginService.atualizarUsuario(reqBody));
    }
}
