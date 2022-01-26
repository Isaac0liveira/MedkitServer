package dev.medkit.server.model.DAO;

import dev.medkit.server.model.Consulta;
import dev.medkit.server.model.Paciente;
import dev.medkit.server.service.HospitaisService;
import dev.medkit.server.service.PacienteService;
import dev.medkit.server.service.ProfissionalService;
import static dev.medkit.server.CommonMethods.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConsultasDAO {

    public boolean inserirConsulta(Consulta consulta){
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("INSERT INTO consultas(tipo, idPaciente, idProfissional, data, resultado, realizada, idHospital) values (?, ?, ?, ?, ?, ?, ?)");
            pst.setString(1, consulta.getTipo());
            pst.setString(2, String.valueOf(consulta.getPaciente().getId()));
            pst.setString(3, String.valueOf(consulta.getProfissional().getId()));
            pst.setString(4, String.valueOf(new Timestamp(consulta.getData().getTime())));
            pst.setString(5, "");
            pst.setString(6, "0");
            pst.setString(7, String.valueOf(consulta.getHospital().getId()));
            pst.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletarConsulta(String id){
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("DELETE FROM consultas WHERE id = ?");
            pst.setString(1, id);
            pst.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Consulta> buscarConsultas(String idPaciente){
        List<Consulta> consultas = new ArrayList<>();
        PacienteService pacienteService = new PacienteService();
        ProfissionalService profissionalService = new ProfissionalService();
        Paciente paciente = pacienteService.procurarPaciente(idPaciente);
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM consultas WHERE idPaciente = ? ORDER BY data");
            pst.setString(1, idPaciente);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                Consulta consulta = new Consulta();
                consulta.setId(Integer.parseInt(resultSet.getString(1)));
                consulta.setTipo(resultSet.getString(2));
                consulta.setPaciente(paciente);
                consulta.setProfissional(profissionalService.findProfissionalById(resultSet.getString(4)));
                consulta.setData(new Date((resultSet.getTimestamp(5).getTime() )));
                consulta.setResultado(resultSet.getString(6));
                consulta.setRealizada((resultSet.getString(7).equals("1")));
                consulta.setHospital(new HospitaisService().findHospitalById(resultSet.getString(8)));
                consultas.add(consulta);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return consultas;
    }


    public boolean atualizarConsulta(Consulta consulta) {
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("UPDATE consultas SET tipo = ?, idPaciente = ?, idProfissional = ?, data = ?, idHospital = ? WHERE id = ?");
            pst.setString(1, consulta.getTipo());
            pst.setString(2, String.valueOf(consulta.getPaciente().getId()));
            pst.setString(3, String.valueOf(consulta.getProfissional().getId()));
            pst.setString(4, String.valueOf(new Timestamp(consulta.getData().getTime())));
            pst.setString(5, String.valueOf(consulta.getId()));
            pst.setString(6, String.valueOf(consulta.getHospital().getId()));
            pst.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Consulta buscarConsulta(String id) {
        PacienteService pacienteService = new PacienteService();
        ProfissionalService profissionalService = new ProfissionalService();
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM consultas WHERE id = ?");
            pst.setString(1, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                Consulta consulta = new Consulta();
                consulta.setId(Integer.parseInt(resultSet.getString(1)));
                consulta.setTipo(resultSet.getString(2));
                consulta.setPaciente(pacienteService.procurarPaciente(resultSet.getString(3)));
                consulta.setProfissional(profissionalService.findProfissionalById(resultSet.getString(4)));
                consulta.setData(new Date((resultSet.getTimestamp(5).getTime() )));
                consulta.setResultado(resultSet.getString(6));
                consulta.setRealizada(Boolean.parseBoolean(resultSet.getString(7)));
                consulta.setHospital(new HospitaisService().findHospitalById(resultSet.getString(8)));
                return consulta;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
