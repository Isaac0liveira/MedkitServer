package dev.medkit.server.model.DAO;

import dev.medkit.server.model.Paciente;
import dev.medkit.server.model.Profissional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static dev.medkit.server.CommonMethods.*;

public class ProfissionalDAO {

    public boolean inserirProfissional(Profissional profissional){
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("INSERT INTO profissional(nome, area, telefone, email, senha) values (?, ?, ?, ?, ?)");
            pst.setString(1, profissional.getNome());
            pst.setString(2, profissional.getArea());
            pst.setString(3, profissional.getTelefone());
            pst.setString(4, profissional.getEmail());
            pst.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Profissional findProfissional(String statement, String parameter){
        Profissional profissional = null;
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement(statement);
            pst.setString(1, parameter);
            ResultSet resultSet = pst.executeQuery();
            if(resultSet.next()){
                profissional = new Profissional();
                profissional.setId(Integer.parseInt(resultSet.getString(1)));
                profissional.setNome(resultSet.getString(2));
                profissional.setArea(resultSet.getString(3));
                profissional.setTelefone(resultSet.getString(4));
                profissional.setEmail(resultSet.getString(5));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return profissional;
    }

    public Profissional findProfissionalById(String id) {
       return findProfissional("SELECT * FROM profissional WHERE id = ?", id);
    }

    public Profissional findProfissionalByUsuario(String id) {
        return findProfissional("SELECT p.* FROM profissional p JOIN login l on p.idLogin = l.id WHERE l.id = ?", id);
    }

    public List<Profissional> findProfissionaisByArea(String area) {
        List<Profissional> profissionais = new ArrayList<>();
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM profissional WHERE area = ?");
            pst.setString(1, area);
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return profissionais;
    }
}
