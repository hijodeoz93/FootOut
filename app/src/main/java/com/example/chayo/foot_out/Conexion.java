package com.example.chayo.foot_out;

import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    public void conn(){

        String driver="com.mysql.jdbc.Driver";
        String urlMySQL = "jdbc:mysql://127.0.0.1:3306/";
        Connection cone=null;
        String base="mydb", user="root", pass="";
        boolean statusCon=false;
        try{
            Class.forName(driver).newInstance();
            cone= DriverManager.getConnection( "jdbc:mysql://127.0.0.1:3306/mydb?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT");
            if(!cone.isClosed()){
                statusCon=true;
                System.out.println("Conexion a la base de datos correcta");
            }
        }catch (Exception ex){
            System.out.println("Conexion errornea"+ ex);
        }
    }
}
