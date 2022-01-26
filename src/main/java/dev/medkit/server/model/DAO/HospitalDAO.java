package dev.medkit.server.model.DAO;

import dev.medkit.server.model.Consulta;
import dev.medkit.server.model.Hospital;
import dev.medkit.server.model.Paciente;
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

public class HospitalDAO {
    public boolean inserirHospital(Hospital hospital){
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("INSERT INTO hospitais(nome, endereco, telefone) values (?, ?, ?)");
            pst.setString(1, hospital.getNome());
            pst.setString(2, hospital.getEndereco());
            pst.setString(3, hospital.getTelefone());
            pst.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletarHospital(String id){
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("DELETE FROM hospitais WHERE id = ?");
            pst.setString(1, id);
            pst.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Hospital> buscarHospitais(){
        List<Hospital> hospitais = new ArrayList<>();
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM hospitais");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                Hospital hospital = new Hospital();
                hospital.setId(Integer.parseInt(resultSet.getString(1)));
                hospital.setNome(resultSet.getString(2));
                hospital.setEndereco(resultSet.getString(3));
                hospital.setTelefone(resultSet.getString(4));
                hospitais.add(hospital);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return hospitais;
    }


    public boolean atualizarHospital(Hospital hospital) {
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("UPDATE hospitais SET nome = ?, endereco = ?, telefone = ? WHERE id = ?");
            pst.setString(1, hospital.getNome());
            pst.setString(2, hospital.getEndereco());
            pst.setString(3, hospital.getTelefone());
            pst.setString(4, String.valueOf(hospital.getId()));
            pst.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Hospital buscarHospital(String id) {
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM hospitais WHERE id = ?");
            pst.setString(1, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                Hospital hospital = new Hospital();
                hospital.setId(Integer.parseInt(resultSet.getString(1)));
                hospital.setNome(resultSet.getString(2));
                hospital.setEndereco(resultSet.getString(3));
                hospital.setTelefone(resultSet.getString(4));
                return hospital;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
