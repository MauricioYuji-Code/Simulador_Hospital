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
    
}
