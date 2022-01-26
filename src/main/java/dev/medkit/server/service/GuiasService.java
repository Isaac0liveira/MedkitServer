package dev.medkit.server.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dev.medkit.server.model.Consulta;
import dev.medkit.server.model.DAO.GuiasDAO;
import dev.medkit.server.model.Guia;
import org.json.simple.JSONObject;
import static dev.medkit.server.CommonMethods.*;
import java.util.List;

import static dev.medkit.server.CommonMethods.toJson;

public class GuiasService {

    GuiasDAO dao;

    public GuiasService(){
        dao = new GuiasDAO();
    }

    public String findGuiasByConsulta(String id) {
        JSONObject response = new JSONObject();
        List<Guia> guias = dao.buscarGuias(id);
        if(!guias.isEmpty()){
            response.put("guias", guias);
            response.put("message", "Guias da consulta encontradas!");
        }else{
            response.put("message", "Não há guias para esta consulta");
        }
        return toJson(response);
    }

    public Guia findGuiaById(String id){
        return dao.buscarGuia(id);
    }

    public boolean inserirGuia(String jsonPayload) {
        if (jsonPayload == null) return false;
        Gson gson = new Gson();
        try {
            Guia guia = gson.fromJson(jsonPayload, Guia.class);
            if(guia != null){
                return dao.inserirGuia(guia);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean atualizarGuia(String jsonPayload, Guia guia){
            if(jsonPayload == null && guia == null) return false;
            if (jsonPayload != null) {
                Gson gson = new Gson();
                guia = gson.fromJson(jsonPayload, Guia.class);
            }
            try {
                if(guia != null){
                    return dao.atualizarGuia(guia);
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
            return false;
    }
}
