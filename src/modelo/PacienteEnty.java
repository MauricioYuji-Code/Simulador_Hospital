/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import desmoj.core.simulator.*;

/**
 *
 * @author Mauricio
 */

/**
 *O SimProcess representa entidades com um próprio ciclo de vida ativo. 
 * Como os SimProcesses são de fato entidades especiais com recursos estendidos (especialmente o método lifeCycle ()), 
 * eles herdam da Entidade e, portanto, também podem ser usados ​​em conjunto com eventos. Para que possam ser tratados de ambos os modos, orientados a eventos e processos. 
 * Os clientes devem implementar o método lifeCycle () para especificar o comportamento individual de uma subclasse especial do SimProcess. 
 * Como a implementação de mecanismos de sincronização orientados a atividades e transações requer mudanças significativas nessa classe, os métodos implementados por Soenke Claassen foram marcados.
 */

public class PacienteEnty extends SimProcess{
    
    private int id; //identificador do paciente

     /**
     * Constructs 
     * @param model 
     * @param entityName Nome da entidade
     * @param showInTrace Sinalizador para mostrar este evento externo em mensagens de rastreamento
     */
    public PacienteEnty(Model model, String entityName, int id, boolean showInTrace) {
        super(model, entityName, showInTrace);
        this.id = id;
    }

    
    /**
    * @lifeCycle 
    * Comportamento específico desse SimProcess.
    * Este método inicia após a criação e ativação de um SimProcess. 
    * Observe que esse método será executado uma ou várias vezes, dependendo do status de repetição do SimProcess.
    */
    
    @Override
    public void lifeCycle(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
