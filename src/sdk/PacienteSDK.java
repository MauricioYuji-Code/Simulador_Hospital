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
 * @author Mauricio
 * Classe utilizada para comunicação BD
 */
public class PacienteSDK {
    
    public void create (DPaciente p){
        
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection con =  connectionFactory.getConnection();
        
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("insert into PACIENTE (IDPACIENTE) values (?)");
            stmt.setInt(1, p.getIdPaciente());
            
            stmt.executeUpdate();
            System.out.println("Salvo com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar!");
            Logger.getLogger(PacienteSDK.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        
    }
                /*Teste(ainda não fuciona)*/
    public int read(int idPaciente){
        System.out.println("Começando a projeção!");
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection con =  connectionFactory.getConnection();
        PreparedStatement stmt = null;    
        ResultSet rs = null;
        int id = 0;
        
        try {
            stmt = con.prepareStatement("select * from paciente where idpaciente = ?");
            stmt.setInt(1, idPaciente);
            rs = stmt.executeQuery();
            while(rs.next()){
            //DPaciente dp = new DPaciente();
            //dp.setIdPaciente(rs.getInt("idPaciente"));
            id = rs.getInt("IDPACIENTE");
            System.out.println("Numero pego: "+rs.getInt("IDPACIENTE"));
            System.out.println("Pego com sucesso!");
            
            }
        } catch (SQLException ex) {
            System.out.println("Falha na projeção!");
            Logger.getLogger(PacienteSDK.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        System.out.println("A funcao read retornou = "+id);
        return id;
    }
    
}
