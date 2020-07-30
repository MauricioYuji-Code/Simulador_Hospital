/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import bean.DPaciente;
import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;
import java.util.concurrent.TimeUnit;
import sdk.PacienteSDK;

/**
 *
 * @author Mauricio
 */
/**
 * Esta classe representa uma fonte de processo, que gera continuamente
 * pacientes para manter a simulação em execução.
 *
 * Ele criará um novo paciente, ative-o (para que ele chegue a terminal) e
 * aguarde até a próxima chegada do paciente vencimento.
 */
public class GeradorPaciente extends SimProcess {

    /**
     * @param model ao qual este gerador de paciente pertence
     * @param nome o nome deste gerador de paciente
     * @param showInTrace sinalizador para indicar se esse processo deve
     * produzir saída para o rastreio
     */
    public GeradorPaciente(Model model, String nome, boolean showInTrace) {
        super(model, nome, showInTrace);
    }

    /**
     * descreve o ciclo de vida desse processo: gere continuamente novos
     * pacientes.
     */
    @Override
    public void lifeCycle() throws SuspendExecution {

        // obtém uma referência ao modelo
        HospitalModel model = (HospitalModel) getModel();

        // loop sem fim:
        while (true) {

            // cria um novo paciente
            // Parâmetros:
            // model = faz parte desse modelo
            // "Paciente" = nome do objeto
            // id do paciente -> parametro que o mauricio colocou
            // true = mostre o paciente no arquivo de rastreamento
            Paciente paciente = new Paciente(model, "Paciente", true);
            
            
            //*Testes banco de dados*//
            int idPaciente = (int)(paciente.getIdentNumber() - 11);
            System.out.println("id do paciente: "+ idPaciente);
            savePaciente(idPaciente);
            

            // agora deixe o paciente recém-criado passar pelo hospital
            // o que significa que vamos ativá-lo após esse gerador de paciente
            // Isso garante que o novo paciente seja ativado no ponto atual no tempo de simulação
            paciente.activateAfter(this);
            
            
            //outra alternativa...
            //Isso também agenda uma nota de ativação para o processo do paciente no ponto atual no tempo da simulação. A única diferença é que o processo do paciente pode não ser ativado 
            //diretamente depois que o gerador do paciente passa o controle para o planejador.
         
            //paciente.activate(new TimeSpan(0));
            

            // aguarde até a próxima chegada do caminhão
            hold(new TimeSpan(model.getPacienteArrivalTime(), TimeUnit.MINUTES));
            // de dentro para fora ...
            // desenhamos um novo horário de chegada
            // criamos um objeto TimeSpan e
            // esperamos exatamente esse período de tempo

        }

    }
    
    public void savePaciente(int idPaciente){
        
        DPaciente p = new DPaciente();
        PacienteSDK psdk = new PacienteSDK();
        p.setIdPaciente(idPaciente);
        psdk.addPacienteId(p);
        
        
    }
}
