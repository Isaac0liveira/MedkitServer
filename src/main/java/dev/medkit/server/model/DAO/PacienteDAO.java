package dev.medkit.server.model.DAO;

import dev.medkit.server.model.Paciente;
import static dev.medkit.server.CommonMethods.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {


    public boolean inserirPaciente(Paciente paciente){
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("INSERT INTO pacientes(nome, cpf, telefone, email) values (?, ?, ?, ?)");
            pst.setString(1, paciente.getNome());
            pst.setString(2, paciente.getCpf());
            pst.setString(3, paciente.getTelefone());
            pst.setString(4, paciente.getEmail());
            pst.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarPaciente(Paciente paciente){
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("UPDATE pacientes SET nome = ?, cpf = ?, telefone = ?, email = ? WHERE id = ?");
            pst.setString(1, paciente.getNome());
            pst.setString(2, paciente.getCpf());
            pst.setString(3, paciente.getTelefone());
            pst.setString(4, paciente.getEmail());
            pst.setString(5, String.valueOf(paciente.getId()));
            pst.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Paciente> listarPacientes(){
        List<Paciente> listaPacientes = new ArrayList<>();
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM pacientes order by id");
            ResultSet result = pst.executeQuery();
            while(result.next()){
                Paciente paciente = new Paciente();
                paciente.setId(Integer.parseInt(result.getString(1)));
                paciente.setNome(result.getString(2));
                paciente.setCpf(result.getString(3));
                paciente.setTelefone(result.getString(4));
                paciente.setEmail(result.getString(5));
                listaPacientes.add(paciente);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaPacientes;
    }

    public Paciente procurarPaciente(String statement, String id){
        Paciente paciente = null;
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement(statement);
            pst.setString(1, id);
            ResultSet resultSet = pst.executeQuery();
            if(resultSet.next()){
                paciente = new Paciente();
                paciente.setId(Integer.parseInt(resultSet.getString(1)));
                paciente.setNome(resultSet.getString(2));
                paciente.setCpf(resultSet.getString(3));
                paciente.setTelefone(resultSet.getString(4));
                paciente.setEmail(resultSet.getString(5));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return paciente;
    }

    public Paciente procurarPacientePorUsuario(String id) {
        return procurarPaciente("SELECT p.* FROM pacientes p JOIN login l on p.idLogin = l.id WHERE l.id = ?", id);
    }

    public Paciente procurarPacientePorId(String id) {
        return procurarPaciente("SELECT * FROM pacientes WHERE id = ?", id);
    }
}
