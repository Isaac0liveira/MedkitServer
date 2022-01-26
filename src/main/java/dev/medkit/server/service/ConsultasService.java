package dev.medkit.server.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dev.medkit.server.model.Consulta;
import dev.medkit.server.model.DAO.ConsultasDAO;
import static dev.medkit.server.CommonMethods.*;
import org.json.simple.JSONObject;

import java.util.List;

public class ConsultasService {

    ConsultasDAO dao;

    public ConsultasService(){
        dao = new ConsultasDAO();
    }

    public boolean inserirConsulta(String jsonPayload){
        if (jsonPayload == null) return false;
        Gson gson = new Gson();
        try {
            Consulta consulta = gson.fromJson(jsonPayload, Consulta.class);
            if(consulta != null){
                return dao.inserirConsulta(consulta);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String findConsultasById(String id){
        JSONObject response = new JSONObject();
        List<Consulta> consultas = dao.buscarConsultas(id);
        if(!consultas.isEmpty()){
            response.put("consultas", consultas);
            response.put("message", "Consultas Encontradas!");
        }else{
            response.put("message", "Não há consultas para este usuário");
        }
        return toJson(response);
    }

    public Consulta findConsultaById(String id){
        return dao.buscarConsulta(id);
    }

    public boolean deletarConsulta(String id) {
        return dao.deletarConsulta(id);
    }

    public boolean atualizarConsulta(String jsonPayload) {
        if (jsonPayload == null) return false;
        Gson gson = new Gson();
        try {
            Consulta consulta = gson.fromJson(jsonPayload, Consulta.class);
            if(consulta != null){
                return dao.atualizarConsulta(consulta);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }
}
