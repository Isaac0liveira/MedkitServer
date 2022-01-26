package dev.medkit.server.model.DAO;

import dev.medkit.server.model.Consulta;
import dev.medkit.server.model.Guia;
import dev.medkit.server.service.ConsultasService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static dev.medkit.server.CommonMethods.conectar;

public class GuiasDAO {
    public List<Guia> buscarGuias(String id) {
        ConsultasService consultaService = new ConsultasService();
        List<Guia> guias = new ArrayList<>();
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM guias WHERE idConsulta = ?");
            pst.setString(1, id);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                Guia guia = new Guia();
                guia.setId(Integer.valueOf(resultSet.getString(1)));
                guia.setConsulta(consultaService.findConsultaById(resultSet.getString(2)));
                guia.setTipo(resultSet.getString(3));
                guia.setAtiva((resultSet.getString(4).equals("1")));
                guias.add(guia);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return guias;
    }

    public Guia buscarGuia(String id) {
        ConsultasService consultaService = new ConsultasService();
        Guia guia = null;
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM guias WHERE id = ?");
            pst.setString(1, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                guia = new Guia();
                guia.setId(Integer.valueOf(resultSet.getString(1)));
                guia.setConsulta(consultaService.findConsultaById(resultSet.getString(2)));
                guia.setTipo(resultSet.getString(3));
                guia.setAtiva((resultSet.getString(4).equals(1)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return guia;
    }

    public boolean inserirGuia(Guia guia) {
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("INSERT INTO guias(idConsulta, tipo, ativa) values (?, ?, ?)");
            pst.setString(1, String.valueOf(guia.getConsulta().getId()));
            pst.setString(2, String.valueOf(guia.getTipo()));
            pst.setString(3, "1");
            pst.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarGuia(Guia guia){
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("UPDATE guias set idConsulta = ?, tipo = ?, ativa = ? WHERE id = ?");
            pst.setString(1, String.valueOf(guia.getConsulta().getId()));
            pst.setString(2, String.valueOf(guia.getTipo()));
            pst.setString(3, String.valueOf(guia.isAtiva()? 1 : 0));
            pst.setString(4, String.valueOf(guia.getId()));
            pst.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
