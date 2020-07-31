/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdk;

import bean.DPaciente;
import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mauricio Classe utilizada para comunicação BD
 */
public class PacienteSDK {

    /*Funcoes Testadas!*/
    //Add id do paciente ao banco de dados
    public void addPacienteId(DPaciente p) {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection con = connectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("insert into PACIENTE (IDPACIENTE) values (?)");
            stmt.setInt(1, p.getIdPaciente());

            stmt.executeUpdate();
            System.out.println("Salvo com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar!");
            Logger.getLogger(PacienteSDK.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

    public void resetDB() {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection con = connectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("delete from paciente");
            stmt.executeUpdate();
            System.out.println("Resetado com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao resetar!");
            Logger.getLogger(PacienteSDK.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

    //Retorna dados do banco referente ao paciente
    public String getClassificacaoById(int idPaciente) {
        System.out.println("Começando a projeção!");
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection con = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String classificacao = "";

        try {
            stmt = con.prepareStatement("select classificacao from paciente where idpaciente = ?");
            stmt.setInt(1, idPaciente);
            rs = stmt.executeQuery();
            while (rs.next()) {
                //DPaciente dp = new DPaciente();
                //dp.setIdPaciente(rs.getInt("idPaciente"));
                classificacao = rs.getString("classificacao");
                System.out.println("classificacao pega: " + rs.getString("classificacao"));
                System.out.println("Pego com sucesso!");

            }
        } catch (SQLException ex) {
            System.out.println("Falha na projeção!");
            Logger.getLogger(PacienteSDK.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
        System.out.println("A funcao read retornou = " + classificacao);
        return classificacao;
    }

    public void setClassificacaoById(int idPaciente, String classificacao) {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection con = connectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("update paciente set classificacao = ? where idpaciente = ?");
            stmt.setString(1, classificacao);
            stmt.setInt(2, idPaciente);

            stmt.executeUpdate();
            System.out.println("Classificacao atualizada com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar!");
            Logger.getLogger(PacienteSDK.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

    /*Funcoes não testadas!*/
    public String getExameById(int idPaciente) {
        System.out.println("Começando a projeção!");
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection con = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String confirmacaoExame = "";

        try {
            stmt = con.prepareStatement("select c_exame from paciente where idpaciente = ?");
            stmt.setInt(1, idPaciente);
            rs = stmt.executeQuery();
            while (rs.next()) {
                //DPaciente dp = new DPaciente();
                //dp.setIdPaciente(rs.getInt("idPaciente"));
                confirmacaoExame = rs.getString("c_exame");
                System.out.println("Confirma exame?: " + rs.getString("c_exame"));
                System.out.println("verificado com sucesso com sucesso!");

            }
        } catch (SQLException ex) {
            System.out.println("Falha na projeção!");
            Logger.getLogger(PacienteSDK.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
        System.out.println("A funcao read retornou = " + confirmacaoExame);
        return confirmacaoExame;
    }

    public void setExameById(int idPaciente, String confirmacao_exame) {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection con = connectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("update paciente set c_exame = ? where idpaciente = ?");
            stmt.setString(1, confirmacao_exame);
            stmt.setInt(2, idPaciente);

            stmt.executeUpdate();
            System.out.println("confirmacao do exame atualizado com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar!");
            Logger.getLogger(PacienteSDK.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

}
