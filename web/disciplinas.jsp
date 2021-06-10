<%-- 
    Document   : disciplinas
    Created on : 11/04/2021, 21:32:21
    Author     : samantamartins
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="db.Disciplina"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Disciplinas</title>
        <%@include file="WEB-INF/jspf/header.jspf"%>
    </head>
    <body>
        <%
        String requestError = null;
        if(request.getParameter("add")!=null){
            String nome = request.getParameter("nome");
            String diaDaSemana = request.getParameter("diaDaSemana");
            String horario = request.getParameter("horario");
            int quantidadeDeAulas = Integer.parseInt(request.getParameter("quantidadeDeAulas"));
            Double notaP1 = Double.parseDouble(request.getParameter("notaP1"));
            Double notaP2 = Double.parseDouble(request.getParameter("notaP2"));
            try{
                Disciplina.addDisciplina(nome, diadDaSemana, horario, quantidadeDeAulas, notaP1, notaP2);
                response.sendRedirect(request.getRequestURI());
            }catch(Exception ex){
                requestError = "Falha na criação da disciplina ["+ex.getMessage()+"]";
            }
        }else if(request.getParameter("update")!=null){
            String name = request.getParameter("nome");
            Double notaP1 = Double.parseDouble(request.getParameter("notaP1"));
            Double notaP2 = Double.parseDouble(request.getParameter("notaP2"));
            try{
                Category.updateDisciplina(nome, notaP1, notaP2);
                response.sendRedirect(request.getRequestURI());
            }catch(Exception ex){
                requestError = "Falha na edição da disciplina ["+ex.getMessage()+"]";
            }
        }else if(request.getParameter("remove")!=null){
            String name = request.getParameter("nome");
            try{
                Category.removeCategory(name);
                response.sendRedirect(request.getRequestURI());
            }catch(Exception ex){
                requestError = "Falha na exclusão da disciplina ["+ex.getMessage()+"]";
            }
        }
        %>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <%if(requestError != null){%>
            <div style="color: red">
                Erro ao manipular usuário: <%= requestError %>
            </div>
        <%}%>
        <h2>Categorias</h2>
        <% String login = (String) session.getAttribute("user.login");%>
        <%if(login == null){%>
            <div>Esta página é restrita a usuários logados.</div>
        <%}else{%>
        <fieldset>
            <legend>Nova categoria:</legend>
            <form method="post">
                Nome: 
                <br/><input type="text" name="name"/>
                <br/>Dia da semana:
                <br/><input type="text" name="diaDaSemana"/>
                <br/>Horário:
                <br/><input type="text" name="horario"/>
                <br/>Quantidade de aulas:
                <br/><input type="text" name="quantidadeDeAulas"/>
                <br/>Nota P1
                <br/><input type="text" name="notaP1"/>
                <br/>Nota P2:
                <br/><input type="text" name="notaP2"/>
                <br/><br/>
                <input type="submit" name="add" value="Adicionar"/>
            </form>
        </fieldset>
        <br/>
        <table border="1">
            <tr>
                <th>Nome</th>
                <th>Dia da semana</th>
                <th>Horário</th>
                <th>Quantidade de aulas</th>
                <th>Nota P1</th>
                <th>Nota P2</th>
                <th>Edição</th>
                <th>Exclusão</th>
            </tr>
            <%for(Disciplina disciplina: Disciplinas.getDisciplinas()){%>
                <tr>
                    <td><%= disciplina.getNome() %></td>
                    <td><%= disciplina.getDiaDaSemana()%></td>
                    <td><%= disciplina.getHorario()%></td>
                    <td><%= disciplina.getQuantidadeDeAulas()%></td>
                    <td><%= disciplina.getNotaP1()%></td>
                    <td><%= disciplina.getNotaP2()%></td>
                    <td>
                        <form method="post">
                            <input type="hidden" name="name" value="<%= disciplina.getNome()%>"/>
                            <input type="text" name="notaP1"/>
                            <input type="text" name="notaP2"/>
                            <input type="submit" name="update" value="Editar"/>
                        </form>
                    </td>
                    <td>
                        <form method="post">
                            <input type="hidden" name="name" value="<%= disciplina.getNome()%>"/>
                            <input type="submit" name="remove" value="Remover"/>
                        </form>
                    </td>
                </tr>
            <%}%>
        </table>
        <%}%>
    </body>
</html>
