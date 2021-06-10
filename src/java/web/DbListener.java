/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import db.Disciplina;
import db.User;
import java.sql.*;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author rlarg
 */
public class DbListener implements ServletContextListener {
    public static final String CLASS_NAME = "org.sqlite.JDBC";
    public static final String URL = "jdbc:sqlite:provap1poo.db";
    
    public static String step = null;
    public static Exception exception = null;
    
    public static Connection getConnection() throws Exception{
        return DriverManager.getConnection(URL);
    }
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try{
            step = "Conectando com a base";
            Class.forName(CLASS_NAME);
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            //stmt.execute("DROP TABLE users");
            step = "Criando tabela de usuários";
            String sql = "CREATE TABLE IF NOT EXISTS users("
                    + "name VARCHAR(200) NOT NULL,"
                    + "login VARCHAR(50) UNIQUE NOT NULL,"
                    + "password_hash LONG)";
            stmt.execute(sql);
            if(User.getUsers().isEmpty()){
                step = "Inserindo usuários";
                sql = "INSERT INTO users(name, login, password_hash) "
                    + "VALUES('Professor', 'admin', '"+("1234".hashCode())+")";
                stmt.execute(sql);
                sql = "INSERT INTO users(name, login, password_hash, role) "
                    + "VALUES('Aluno', 'fulano', '"+("1234".hashCode())+")";
                stmt.execute(sql);
            }
            //nome, dia da semana, horário, quantidade de aulas, nota da p1 e nota da p2
            step = "Criando tabela de disciplinas";
            sql = "CREATE TABLE IF NOT EXISTS disciplinas("
                    + "nome VARCHAR(50) PRIMARY KEY,"
                    + "dia_da_semana VARCHAR(200) NOT NULL,"
                    + "horario VARCHAR(200),"
                    + "quantidade_de_aulas long,"
                    + "nota_p1 double,"
                    + "nota_p2 double"
                    + ")";
            stmt.execute(sql);
                        
            stmt.close();
            con.close();
        }catch(Exception ex){
            exception = ex;
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
