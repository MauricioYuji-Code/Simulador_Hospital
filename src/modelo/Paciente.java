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
 *Representa a superclasse para todas as entidades de um modelo. 
 * As entidades devem ser agendadas juntamente com um evento compatível em um determinado ponto do tempo de simulação ou em relação a outro evento no tempo de simulação presente ou futuro.
 * As entidades geralmente encapsulam todas as informações sobre uma entidade modelo relevante para o modelador.
 * Os eventos podem manipular essas informações quando o ponto planejado do tempo de simulação for atingido e, assim, alterar o estado do modelo. 
 */

public class Paciente extends Entity{
    
    private int id;
    
     /**
     * Pacientes constructor.
     * @param model
     * @param entityName Nome da entidade
     * @param showInTrace Sinalizador para mostrar este evento externo em mensagens de rastreamento
     */

    public Paciente(Model model, String entityName, int id, boolean showInTrace) {
        super(model, entityName, showInTrace);
        this.id = id;
    }
    
}
