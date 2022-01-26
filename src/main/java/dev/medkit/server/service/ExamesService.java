package dev.medkit.server.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dev.medkit.server.model.DAO.ExamesDAO;
import dev.medkit.server.model.Exame;
import org.json.simple.JSONObject;

import java.util.List;

import static dev.medkit.server.CommonMethods.toJson;

public class ExamesService {

    ExamesDAO dao;

    public ExamesService(){
        dao = new ExamesDAO();
    }

    public boolean inserirExame(String jsonPayload){
        if (jsonPayload == null) return false;
        Gson gson = new Gson();
        try {
            Exame exame = gson.fromJson(jsonPayload, Exame.class);
            if(exame != null){
                return dao.inserirExame(exame);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String findExamesById(String id){
        JSONObject response = new JSONObject();
        List<Exame> exames = dao.buscarExames(id);
        if(!exames.isEmpty()){
            response.put("exames", exames);
            response.put("message", "Exames Encontrados!");
        }else{
            response.put("message", "Não há exames para este usuário");
        }
        return toJson(response);
    }

    public Exame findExameById(String id){
        return dao.buscarExame(id);
    }

    public boolean deletarExame(String id) {
        return dao.deletarExame(id);
    }

    public boolean atualizarExame(String jsonPayload) {
        if (jsonPayload == null) return false;
        Gson gson = new Gson();
        try {
            Exame exame = gson.fromJson(jsonPayload, Exame.class);
            if(exame != null){
                return dao.atualizarExame(exame);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }
}
