package dev.medkit.server.model.DAO;

import dev.medkit.server.model.Cirurgia;
import dev.medkit.server.model.Profissional;
import dev.medkit.server.service.ExamesService;
import dev.medkit.server.service.HospitaisService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static dev.medkit.server.CommonMethods.conectar;

public class CirurgiasDAO {
    public boolean inserirCirurgia(Cirurgia cirurgia){
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("INSERT INTO cirurgias(resultado, tipo, idExame, realizada, data, ativa, idHospital) values (?, ?, ?, ?, ?, ?, ?, ?)");
            pst.setString(1, cirurgia.getResultado());
            pst.setString(2, cirurgia.getTipo());
            pst.setString(3, String.valueOf(cirurgia.getExame().getId()));
            pst.setString(4, cirurgia.isRealizada()? "1" : "0");
            pst.setString(5, String.valueOf(new Timestamp(cirurgia.getData().getTime())));
            pst.setString(6, "0");
            pst.setString(7, String.valueOf(cirurgia.getHospital().getId()));
            pst.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletarCirurgia(String id){
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("DELETE FROM cirurgias WHERE id = ?");
            pst.setString(1, id);
            pst.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Cirurgia> buscarCirurgias(String idExame, String idPaciente){
        List<Cirurgia> cirurgias = new ArrayList<>();
        ExamesService examesService = new ExamesService();
        Connection con = conectar();
        try {
            PreparedStatement pst = null;
            if(idExame == null){
                pst = con.prepareStatement("SELECT c.* FROM cirurgias c JOIN exames e on c.idExame = e.id JOIN pacientes p on e.idPaciente = p.id WHERE p.id = ?");
                pst.setString(1, idPaciente);
            }else{
                pst = con.prepareStatement("SELECT c.* FROM cirurgias c JOIN exames e on c.idExame = e.id WHERE e.id = ?");
                pst.setString(1, idExame);
            }
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                Cirurgia cirurgia = new Cirurgia();
                cirurgia.setId(Integer.parseInt(resultSet.getString(1)));
                cirurgia.setResultado(resultSet.getString(2));
                cirurgia.setTipo(resultSet.getString(3));
                cirurgia.setExame(examesService.findExameById(resultSet.getString(4)));
                cirurgia.setRealizada((resultSet.getString(5).equals("1")));
                cirurgia.setData(new Date((resultSet.getTimestamp(6).getTime())));
                cirurgia.setAtiva((resultSet.getString(7).equals("1")));
                cirurgia.setProfissionais(buscarEquipeCirurgia(cirurgia));
                cirurgia.setHospital(new HospitaisService().findHospitalById(resultSet.getString(8)));
                cirurgias.add(cirurgia);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return cirurgias;
    }



    public boolean atualizarCirurgia(Cirurgia cirurgia) {
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("UPDATE cirurgias SET resultado = ?, tipo = ?, idExame = ?,  realizada = ?, data = ?, ativa = ?, idHospital = ? WHERE id = ?");
            pst.setString(1, cirurgia.getResultado());
            pst.setString(2, cirurgia.getTipo());
            pst.setString(3, String.valueOf(cirurgia.getExame().getId()));
            pst.setString(4, cirurgia.isRealizada()? "1": "0");
            pst.setString(5, String.valueOf(new Timestamp(cirurgia.getData().getTime())));
            pst.setString(6, cirurgia.isAtiva()? "1" : "0");
            pst.setString(7, String.valueOf(cirurgia.getId()));
            pst.setString(8, String.valueOf(cirurgia.getHospital().getId()));
            inserirEquipeCirurgia(cirurgia);
            pst.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean inserirEquipeCirurgia(Cirurgia cirurgia){
        Connection con = conectar();
        try {
            for(Profissional p: cirurgia.getProfissionais()){
                PreparedStatement pst = con.prepareStatement("INSERT INTO equipecirurgias(idCirurgia, idProfissional) values (?, ?)");
                pst.setString(1, String.valueOf(cirurgia.getId()));
                pst.setString(2, String.valueOf(p.getId()));
                pst.executeUpdate();
            }
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Profissional> buscarEquipeCirurgia(Cirurgia cirurgia) {
        List<Profissional> profissionais = new ArrayList<>();
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT p.* FROM profissional p JOIN equipecirurgias ec ON ec.idProfissional = p.id WHERE idCirurgia = ?");
            pst.setString(1, String.valueOf(cirurgia.getId()));
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                Profissional profissional = new Profissional();
                profissional.setId(Integer.parseInt(resultSet.getString(1)));
                profissional.setNome(resultSet.getString(2));
                profissional.setArea(resultSet.getString(3));
                profissional.setTelefone(resultSet.getString(4));
                profissional.setEmail(resultSet.getString(5));
                profissionais.add(profissional);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return profissionais;
    }

    public Cirurgia buscarCirurgia(String id) {
        ExamesService examesService = new ExamesService();
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM cirurgias WHERE id = ?");
            pst.setString(1, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                Cirurgia cirurgia = new Cirurgia();
                cirurgia.setId(Integer.parseInt(resultSet.getString(1)));
                cirurgia.setResultado(resultSet.getString(2));
                cirurgia.setTipo(resultSet.getString(3));
                cirurgia.setExame(examesService.findExameById(resultSet.getString(4)));
                cirurgia.setRealizada((resultSet.getString(5).equals("1")));
                cirurgia.setData(new Date((resultSet.getTimestamp(6).getTime())));
                cirurgia.setAtiva((resultSet.getString(7).equals("1")));
                cirurgia.setHospital(new HospitaisService().findHospitalById(resultSet.getString(8)));
                return cirurgia;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
