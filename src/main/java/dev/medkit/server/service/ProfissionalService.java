package dev.medkit.server.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import static dev.medkit.server.CommonMethods.*;
import dev.medkit.server.model.DAO.ProfissionalDAO;
import dev.medkit.server.model.Profissional;
import org.json.simple.JSONObject;

import java.util.List;

public class ProfissionalService {

    ProfissionalDAO dao;

    public ProfissionalService(){
        dao = new ProfissionalDAO();
    }

    public boolean novoProfissional(String jsonPayload){
        if (jsonPayload == null) return false;
        Gson gson = new Gson();
        try {
            Profissional profissional = gson.fromJson(jsonPayload, Profissional.class);
            if(profissional != null){
                return dao.inserirProfissional(profissional);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Profissional findProfissionalById(String id){
        return dao.findProfissionalById(id);
    }

    public String findProfissionalByArea(String area){
        JSONObject response = new JSONObject();
        List<Profissional> profissionais = dao.findProfissionaisByArea(area);;
        if(!profissionais.isEmpty()){
            response.put("profissionais", profissionais);
            response.put("message", "Profissionais da Área " + area + " Encontrados!");
        }else{
            response.put("message", "Não há profissionais dessa área disponíveis");
        }
        return toJson(response);
    }

    public Profissional findProfissionalByUsuario(String id) {
        return dao.findProfissionalByUsuario(id);
    }
}
