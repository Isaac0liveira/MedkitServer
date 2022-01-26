package dev.medkit.server.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dev.medkit.server.model.DAO.LoginDAO;
import dev.medkit.server.model.Usuario;
import org.json.simple.JSONObject;

import static dev.medkit.server.CommonMethods.toJson;

public class LoginService {
    LoginDAO dao;

    public LoginService(){
        dao = new LoginDAO();
    }

    public boolean inserirUsuario(String jsonPayload){
        if (jsonPayload == null) return false;
        Gson gson = new Gson();
        try {
            Usuario usuario = gson.fromJson(jsonPayload, Usuario.class);
            if(usuario != null){
                return dao.inserirUsuario(usuario);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String findUsuario(String login, String senha){
        JSONObject response = new JSONObject();
        Usuario usuario = dao.buscarUsuario(login, senha);
        if(usuario != null){
            switch (usuario.getTipo()){
                case "Paciente":
                    response.put("paciente", new PacienteService().procurarPacientePorUsuario(String.valueOf(usuario.getId())));
                    break;
                case "Profissional":
                    response.put("profissional", new ProfissionalService().findProfissionalByUsuario(String.valueOf(usuario.getId())));
                    break;
            }
            response.put("message", "Seja Bem Vindo!");
        }else{
            response.put("message", "Credenciais Incorretas");
        }
        return toJson(response);
    }

    public boolean deletarUsuario(String id) {
        return dao.deletarUsuario(id);
    }

    public boolean atualizarUsuario(String jsonPayload) {
        if (jsonPayload == null) return false;
        Gson gson = new Gson();
        try {
            Usuario usuario = gson.fromJson(jsonPayload, Usuario.class);
            if(usuario != null){
                return dao.atualizarUsuario(usuario);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }
}
