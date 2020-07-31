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
import sdk.PacienteSDK;

/**
 *
 * @author Mauricio
 */
public class Triagem extends SimProcess {

    private HospitalModel myModel;

    public Triagem(Model model, String nome, boolean showInTrace) {
        super(model, nome, showInTrace);
        this.myModel = (HospitalModel) model;
    }

    @Override
    public void lifeCycle() throws SuspendExecution {
        //o servidor está sempre em serviço e nunca para de funcionar 

        while (true) {
            // verifica se há alguém esperando
            if (myModel.filaPacientesTriagem.isEmpty()) {

                // NÃO, não há ninguém aguardando 
                // insira-se na fila de recepcao inativa 
                myModel.osciosidadeTriagem.insert(this);
                // e aguarde que as coisas aconteçam 
                passivate();

            } else {
                // SIM, existe um cliente (paciente) aguardando 

                // obter uma referência ao primeiro caminhão da fila de pacientes
                Paciente proximoPaciente = myModel.filaPacientesTriagem.first();
                // remove o paciente da fila
                myModel.filaPacientesTriagem.remove(proximoPaciente);
                
                //Add classificao do paciente ao banco
                //OBS: ADPTAR A MELHOR FORMA DE CLASSIFICAR !!
                int idProximoPaciente = (int) (proximoPaciente.getIdentNumber() - 11);
                System.out.println("Id do proximo paciente (Triagem): " + idProximoPaciente);
                setClassificacaoSDK(idProximoPaciente, "vermelho");

                // agora atenda 
                // o tempo de serviço é representado por uma retenção da retenção do processo 
                hold(new TimeSpan(myModel.getServiceTimeTriagem(), TimeUnit.MINUTES));
                // de dentro para fora ... 
                // ... desenha um novo período de tempo de serviço 
                // ... cria um objeto TimeSpan com ele 
                // ... e mantém por esse período de tempo 

                // nós o reativaremos, para permitir que ele termine seu ciclo de vida 
                proximoPaciente.activate();
                // a recepcao pode voltar ao topo e procurar um novo cliente
            }

        }

    }

    public void setClassificacaoSDK(int idProximoPaciente, String classificacao) {
        PacienteSDK psdk = new PacienteSDK();
        psdk.setClassificacaoById(idProximoPaciente, classificacao);
    }

}
