package dev.medkit.server.model.DAO;

import dev.medkit.server.model.Exame;
import dev.medkit.server.model.Guia;
import dev.medkit.server.model.Hospital;
import dev.medkit.server.model.Paciente;
import dev.medkit.server.service.GuiasService;
import dev.medkit.server.service.HospitaisService;
import dev.medkit.server.service.PacienteService;
import dev.medkit.server.service.ProfissionalService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static dev.medkit.server.CommonMethods.conectar;

public class ExamesDAO {
    public boolean inserirExame(Exame exame){
        GuiasService guiasService = new GuiasService();
        Guia guia = guiasService.findGuiaById(String.valueOf(exame.getGuia().getId()));
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("INSERT INTO exames(tipo, idPaciente, idProfissional, data, resultado, realizado, idGuia, idHospital) values (?, ?, ?, ?, ?, ?, ?, ?)");
            pst.setString(1, exame.getTipo());
            pst.setString(2, String.valueOf(exame.getPaciente().getId()));
            pst.setString(3, String.valueOf(exame.getProfissional().getId()));
            pst.setString(4, String.valueOf(new Timestamp(exame.getData().getTime())));
            pst.setString(5, "");
            pst.setString(6, "0");
            pst.setString(7, String.valueOf(guia.getId()));
            pst.setString(8, String.valueOf(exame.getHospital().getId()));
            guia.setAtiva(false);
            guiasService.atualizarGuia(null, guia);
            pst.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletarExame(String id){
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("DELETE FROM exames WHERE id = ?");
            pst.setString(1, id);
            pst.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Exame> buscarExames(String idPaciente){
        List<Exame> exames = new ArrayList<>();
        PacienteService pacienteService = new PacienteService();
        ProfissionalService profissionalService = new ProfissionalService();
        GuiasService guiasService = new GuiasService();
        Paciente paciente = pacienteService.procurarPaciente(idPaciente);
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM exames WHERE idPaciente = ? ORDER BY data");
            pst.setString(1, idPaciente);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                Exame exame = new Exame();
                exame.setId(Integer.parseInt(resultSet.getString(1)));
                exame.setTipo(resultSet.getString(2));
                exame.setPaciente(paciente);
                exame.setProfissional(profissionalService.findProfissionalById(resultSet.getString(4)));
                exame.setData(new Date((resultSet.getTimestamp(5).getTime())));
                exame.setResultado(resultSet.getString(6));
                exame.setRealizado((resultSet.getString(7).equals("1")));
                exame.setGuia(guiasService.findGuiaById(resultSet.getString(8)));
                exame.setHospital(new HospitaisService().findHospitalById(resultSet.getString(9)));
                exames.add(exame);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return exames;
    }


    public boolean atualizarExame(Exame exame) {
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("UPDATE exames SET tipo = ?, idPaciente = ?, idProfissional = ?, data = ?, resultado = ?, realizado = ?, idGuia = ?, idHospital = ? WHERE id = ?");
            pst.setString(1, exame.getTipo());
            pst.setString(2, String.valueOf(exame.getPaciente().getId()));
            pst.setString(3, String.valueOf(exame.getProfissional().getId()));
            pst.setString(4, String.valueOf(new Timestamp(exame.getData().getTime())));
            pst.setString(5, String.valueOf(exame.getResultado()));
            pst.setString(6, exame.isRealizado()? "1" : "0");
            pst.setString(7, String.valueOf(exame.getGuia().getId()));
            pst.setString(8, String.valueOf(exame.getHospital().getId()));
            pst.setString(9, String.valueOf(exame.getId()));
            pst.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Exame buscarExame(String id) {
        PacienteService pacienteService = new PacienteService();
        ProfissionalService profissionalService = new ProfissionalService();
        GuiasService guiasService = new GuiasService();
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM exames WHERE id = ?");
            pst.setString(1, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                Exame exame = new Exame();
                exame.setId(Integer.parseInt(resultSet.getString(1)));
                exame.setTipo(resultSet.getString(2));
                exame.setPaciente(pacienteService.procurarPaciente(resultSet.getString(3)));
                exame.setProfissional(profissionalService.findProfissionalById(resultSet.getString(4)));
                exame.setData(new Date((resultSet.getTimestamp(5).getTime() )));
                exame.setResultado(resultSet.getString(6));
                exame.setRealizado(Boolean.parseBoolean(resultSet.getString(7)));
                exame.setGuia(guiasService.findGuiaById(resultSet.getString(8)));
                exame.setHospital(new HospitaisService().findHospitalById(resultSet.getString(9)));
                return exame;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
