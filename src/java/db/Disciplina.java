/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import web.DbListener;

/**
 *
 * @author 002921631
 */
public class Disciplina {
    
    private String nome;
    private String diaDaSemana;
    private String horario;
    private int quantidadeDeAulas;
    private double notaP1;
    private double notaP2;
    
    public static ArrayList<Disciplina> getDisciplinas() throws Exception{
        ArrayList<Disciplina> list = new ArrayList<>();
        Connection con = DbListener.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * from disciplinas");
        while(rs.next()){
            list.add(new Disciplina(
                    rs.getString("nome"),
                    rs.getString("diaDaSemana"),
                    rs.getString("horario"),
                    rs.getInt("quantidadeDeAulas"),
                    rs.getDouble("notaP1"),
                    rs.getDouble("notaP2")
            ));
        }
        rs.close();
        stmt.close();
        con.close();
        return list;
    }
    
    public static Disciplina getDisciplina(String nome) throws Exception{
        Disciplina disciplina = null;
        Connection con = DbListener.getConnection();
        String sql = "SELECT * from disciplinas WHERE nome=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, nome);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            disciplina = new Disciplina(
                    rs.getString("nome"),
                    rs.getString("diaDaSemana"),
                    rs.getString("horario"),
                    rs.getInt("quantidadeDeAulas"),
                    rs.getDouble("notaP1"),
                    rs.getDouble("notaP2")
            );
        }
        rs.close();
        stmt.close();
        con.close();
        return disciplina;
    }
    
    public static void addDisciplina(String nome, String diaDaSemana, String horario, int quantidadeDeAulas, double notaP1, double notaP2) throws Exception{
        Connection con = DbListener.getConnection();
        String sql = "INSERT INTO disciplinas(nome, dia_da_semana, horario, quatidade_de_aulas, nota_p1, nota_p2)"
                + "VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, nome);
        stmt.setString(2, diaDaSemana);
        stmt.setString(3, horario);
        stmt.setInt(4, quantidadeDeAulas);
        stmt.setDouble(5, notaP1);
        stmt.setDouble(6, notaP2);
        stmt.execute();
        stmt.close();
        con.close();
    }
    
    public static void updateDisciplina(String nome, double notaP1, double notaP2) throws Exception{
        Connection con = DbListener.getConnection();
        String sql = "UPDATE disciplinas set nota_p1 = ?, nota_p2 = ? WHERE nome = ?"
                + "VALUES(?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setDouble(1, notaP1);
        stmt.setDouble(2, notaP2);
        stmt.setString(3, nome);
        stmt.execute();
        stmt.close();
        con.close();
    }
    
    public static void removeDisciplina(String nome) throws Exception{
        Connection con = DbListener.getConnection();
        String sql = "DELETE FROM disciplinas WHERE nome = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, nome);
        stmt.execute();
        stmt.close();
        con.close();
    }

    public Disciplina(String nome, String diaDaSemana, String horario, int quantidadeDeAulas, double notaP1, double notaP2) {
        this.nome = nome;
        this.diaDaSemana = diaDaSemana;
        this.horario = horario;
        this.quantidadeDeAulas = quantidadeDeAulas;
        this.notaP1 = notaP1;
        this.notaP2 = notaP2;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDiaDaSemana() {
        return diaDaSemana;
    }

    public void setDiaDaSemana(String diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public int getQuantidadeDeAulas() {
        return quantidadeDeAulas;
    }

    public void setQuantidadeDeAulas(int quantidadeDeAulas) {
        this.quantidadeDeAulas = quantidadeDeAulas;
    }

    public double getNotaP1() {
        return notaP1;
    }

    public void setNotaP1(double notaP1) {
        this.notaP1 = notaP1;
    }

    public double getNotaP2() {
        return notaP2;
    }

    public void setNotaP2(double notaP2) {
        this.notaP2 = notaP2;
    }
    
    
    
}
