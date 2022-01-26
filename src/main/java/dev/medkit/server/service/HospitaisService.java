package dev.medkit.server.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dev.medkit.server.model.DAO.HospitalDAO;
import dev.medkit.server.model.Hospital;
import org.json.simple.JSONObject;

import java.util.List;

import static dev.medkit.server.CommonMethods.toJson;

public class HospitaisService {

    HospitalDAO dao;

    public HospitaisService(){
        dao = new HospitalDAO();
    }

    public boolean inserirHospital(String jsonPayload){
        if (jsonPayload == null) return false;
        Gson gson = new Gson();
        try {
            Hospital hospital = gson.fromJson(jsonPayload, Hospital.class);
            if(hospital != null){
                return dao.inserirHospital(hospital);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String findHospitais(){
        JSONObject response = new JSONObject();
        List<Hospital> hospitais = dao.buscarHospitais();
        if(!hospitais.isEmpty()){
            response.put("hospitais", hospitais);
            response.put("message", "Hospitais Encontrados!");
        }else{
            response.put("message", "Não há hospitais");
        }
        return toJson(response);
    }

    public Hospital findHospitalById(String id){
        return dao.buscarHospital(id);
    }

    public boolean deletarHospital(String id) {
        return dao.deletarHospital(id);
    }

    public boolean atualizarHospital(String jsonPayload) {
        if (jsonPayload == null) return false;
        Gson gson = new Gson();
        try {
            Hospital hospital = gson.fromJson(jsonPayload, Hospital.class);
            if(hospital != null){
                return dao.atualizarHospital(hospital);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }
}
