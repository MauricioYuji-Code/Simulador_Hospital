/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Mauricio
 */

/** 
 * Esta classe representa uma recepcao no 
 modelo Modelo Hospital *. 
 * a recepcao aguarda até que um paciente solicite seu serviço. 
 * Se houver outro paciente esperando, ele começará a servir este 
 * paciente. Caso contrário, aguarda novamente a chegada do próximo paciente.
 * 
 */
public class Recepcao extends SimProcess{   
    /**
     * uma referência ao modelo em que este processo faz parte atalho útil para
     * acessar os componentes estáticos do modelo
     */
    private HospitalModel myModel; 
        /**
     * @param model ao qual esta recepcao pertence
     * @param nome o nome da recepcao
     * @param showInTrace sinalizador para indicar se esse processo deve
     * produzir saída para o rastreio
     */

    public Recepcao(Model model, String nome, boolean showInTrace) {
        super(model, nome, showInTrace);
        this.myModel = (HospitalModel) model;
    }

    /** 
    * Descreve o ciclo de vida desta recepcao. 
    * 
    * Ele percorrerá continuamente as seguintes etapas: 
    * Verifique se há um cliente esperando. 
    * Se houver alguém esperando 
    * a) remover o cliente da fila 
    * b) atender o cliente 
    * c) retornar ao topo 
    * Se não houver ninguém esperando  
    */ 
    
  
    @Override
    public void lifeCycle() throws SuspendExecution {
        
        //o servidor está sempre em serviço e nunca para de funcionar 
        
         while(true) { 
         // verifica se há alguém esperando
         if (myModel.filaPacientes.isEmpty ()) {
             
             // NÃO, não há ninguém aguardando 
             
             // insira-se na fila de recepcao inativa 
            myModel.filaRecepcao.insert (this); 
            // e aguarde que as coisas aconteçam 
            passivate (); 
             
         }else{
             // SIM, existe um cliente (paciente) aguardando 
             
             // obter uma referência ao primeiro caminhão da fila de caminhões 
            Paciente proximoPaciente = (Paciente) myModel.filaPacientes.first(); 
            // remove o caminhão da fila 
            myModel.filaPacientes.remove (proximoPaciente);
    
              // agora atenda 
            // o tempo de serviço é representado por uma retenção da retenção do processo 
             hold(new TimeSpan(myModel.getServiceTime(), TimeUnit.MINUTES));
            // de dentro para fora ... 
            // ... desenha um novo período de tempo de serviço 
            // ... cria um objeto TimeSpan com ele 
            // ... e mantém por esse período de tempo 

            // nós o reativaremos, para permitir que ele termine seu ciclo de vida 
            proximoPaciente.activate (); 
            // a recepcao pode voltar ao topo e procurar um novo cliente
         }
             
             
         }
        
       
    }
    
    
    
}
