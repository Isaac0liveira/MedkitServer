package dev.medkit.server.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dev.medkit.server.model.DAO.PacienteDAO;
import dev.medkit.server.model.Paciente;
import org.json.simple.JSONObject;
import static dev.medkit.server.CommonMethods.*;

public class PacienteService {

    PacienteDAO dao;

    public PacienteService(){
        dao = new PacienteDAO();
    }

    public String findAllPacientes(){
        return toJson(dao.listarPacientes());
    }


    public boolean novoPaciente(String jsonPayload){
        if (jsonPayload == null) return false;
        Gson gson = new Gson();
        try {
            Paciente paciente = gson.fromJson(jsonPayload, Paciente.class);
            if(paciente != null){
                return dao.inserirPaciente(paciente);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Paciente procurarPaciente(String id){
        return dao.procurarPacientePorId(id);
    }

    public Paciente procurarPacientePorUsuario(String id) { return dao.procurarPacientePorUsuario(id); }

    public boolean atualizarPaciente(String jsonPayload){
        if (jsonPayload == null) return false;
        Gson gson = new Gson();
        try {
            Paciente paciente = gson.fromJson(jsonPayload, Paciente.class);
            if(paciente != null){
                return dao.atualizarPaciente(paciente);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }
}
