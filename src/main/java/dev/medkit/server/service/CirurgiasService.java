package dev.medkit.server.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dev.medkit.server.model.Cirurgia;
import dev.medkit.server.model.DAO.CirurgiasDAO;
import org.json.simple.JSONObject;

import java.util.List;

import static dev.medkit.server.CommonMethods.toJson;

public class CirurgiasService {

    CirurgiasDAO dao;

    public CirurgiasService(){
        dao = new CirurgiasDAO();
    }

    public boolean inserirCirurgia(String jsonPayload){
        if (jsonPayload == null) return false;
        Gson gson = new Gson();
        try {
            Cirurgia cirurgia = gson.fromJson(jsonPayload, Cirurgia.class);
            if(cirurgia != null){
                return dao.inserirCirurgia(cirurgia);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String findCirurgiasById(String id){
        JSONObject response = new JSONObject();
        List<Cirurgia> cirurgias = dao.buscarCirurgias(id, null);
        if(!cirurgias.isEmpty()){
            response.put("cirurgias", cirurgias);
            response.put("message", "Cirurgias Encontradas!");
        }else{
            response.put("message", "Não há cirurgias para este usuário");
        }
        return toJson(response);
    }

    public Cirurgia findCirurgiaById(String id){
        return dao.buscarCirurgia(id);
    }

    public boolean deletarCirurgia(String id) {
        return dao.deletarCirurgia(id);
    }

    public boolean atualizarCirurgia(String jsonPayload) {
        if (jsonPayload == null) return false;
        Gson gson = new Gson();
        try {
            Cirurgia cirurgia = gson.fromJson(jsonPayload, Cirurgia.class);
            if(cirurgia != null){
                return dao.atualizarCirurgia(cirurgia);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String findCirurgiasByUsuario(String id) {
        JSONObject response = new JSONObject();
        List<Cirurgia> cirurgias = dao.buscarCirurgias(null, id);
        if(!cirurgias.isEmpty()){
            response.put("cirurgias", cirurgias);
            response.put("message", "Cirurgias Encontradas!");
        }else{
            response.put("message", "Não há cirurgias para este usuário");
        }
        return toJson(response);
    }
}
