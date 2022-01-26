package dev.medkit.server;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

public class CommonMethods {

    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/medkit?useTimezone=true&serverTimezone=UTC";
    private static String user = "root";
    private static String password = "1234";


    public static void outputResponse(HttpServletResponse response, String payload, int status, String responseType){
        response.setHeader("Content-Type", responseType);
        try{
            response.setStatus(status);
            if(payload != null){
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(payload.getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void outputTextResponse(HttpServletResponse response, String messageOk, String messageError, boolean feedback){
        if(feedback){
           outputResponse(response, messageOk, HttpServletResponse.SC_OK, "text/plain");
        }else{
            outputResponse(response, messageError, HttpServletResponse.SC_BAD_REQUEST, "text/plain");
        }
    }

    public static String toJson(Object list){
        if(list == null) return null;
        Gson gson = new Gson();
        String json = null;
        try{
            json = gson.toJson(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public static Connection conectar() {
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
