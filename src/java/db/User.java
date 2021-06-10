/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.util.ArrayList;
import java.sql.*;
import web.DbListener;

/**
 *
 * @author rlarg
 */
public class User {
    private String name;
    private String login;
    private String role;
    
    public static ArrayList<User> getUsers() throws Exception{
        ArrayList<User> list = new ArrayList<>();
        Connection con = DbListener.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * from users");
        while(rs.next()){
            list.add(new User(
                    rs.getString("name")
                    , rs.getString("login")
                    , rs.getString("role")
            ));
        }
        rs.close();
        stmt.close();
        con.close();
        return list;
    }
    
    public static User getUser(String login, String pass) throws Exception{
        User user = null;
        Connection con = DbListener.getConnection();
        String sql = "SELECT * from users WHERE login=? and password_hash=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, login);
        stmt.setLong(2, pass.hashCode());
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            user = new User(
                    rs.getString("name")
                    , rs.getString("login")
                    , rs.getString("role")
            );
        }
        rs.close();
        stmt.close();
        con.close();
        return user;
    }
    
    public static void addUser(String login, String name, String role, String password) throws Exception{
        Connection con = DbListener.getConnection();
        String sql = "INSERT INTO users(login, name, role, password_hash)"
                + "VALUES(?, ?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, login);
        stmt.setString(2, name);
        stmt.setString(3, role);
        stmt.setLong(4, password.hashCode());
        stmt.execute();
        stmt.close();
        con.close();
    }
    
    public static void removeUser(String login) throws Exception{
        Connection con = DbListener.getConnection();
        String sql = "DELETE FROM users WHERE login = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, login);
        stmt.execute();
        stmt.close();
        con.close();
    }
    
    public static void changePassword(String login, String newPassword) throws Exception{
        Connection con = DbListener.getConnection();
        String sql = "UPDATE users SET password_hash=? WHERE login = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setLong(1, newPassword.hashCode());
        stmt.setString(2, login);
        stmt.execute();
        stmt.close();
        con.close();
    }
    
    public User(String name, String login, String role) {
        this.name = name;
        this.login = login;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}