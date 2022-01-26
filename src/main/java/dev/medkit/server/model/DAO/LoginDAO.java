package dev.medkit.server.model.DAO;

import dev.medkit.server.model.Hospital;
import dev.medkit.server.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static dev.medkit.server.CommonMethods.conectar;

public class LoginDAO {
    public boolean inserirUsuario(Usuario usuario){
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("INSERT INTO login(login, senha, tipo) values (?, ?, ?)");
            pst.setString(1, usuario.getLogin());
            pst.setString(2, usuario.getSenha());
            pst.setString(3, usuario.getTipo());
            pst.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletarUsuario(String id){
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("DELETE FROM login WHERE id = ?");
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


    public boolean atualizarUsuario(Usuario usuario) {
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("UPDATE hospitais SET login = ?, senha = ?, tipo = ? WHERE id = ?");
            pst.setString(1, usuario.getLogin());
            pst.setString(2, usuario.getSenha());
            pst.setString(3, usuario.getTipo());
            pst.setString(4, String.valueOf(usuario.getId()));
            pst.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario buscarUsuario(String login, String senha) {
        Connection con = conectar();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM login WHERE login = ? AND senha = ?");
            pst.setString(1, login);
            pst.setString(2, senha);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                Usuario usuario = new Usuario();
                usuario.setId(Integer.parseInt(resultSet.getString(1)));
                usuario.setTipo(resultSet.getString(4));
                return usuario;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
