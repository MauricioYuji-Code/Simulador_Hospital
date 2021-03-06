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
public class AtendimentoMedico extends SimProcess {

    private HospitalModel myModel;

    public AtendimentoMedico(Model model, String nome, boolean showInTrace) {
        super(model, nome, showInTrace);
        this.myModel = (HospitalModel) model;
    }

    @Override
    public void lifeCycle() throws SuspendExecution {

        while (true) {
            // verifica se há alguém esperando
            if (myModel.filaPacientesAtendimentoMedico.isEmpty()
                    && myModel.filaPacientesLaranja.isEmpty()
                    && myModel.filaPacientesAmarelo.isEmpty()
                    && myModel.filaPacientesVerde.isEmpty()
                    && myModel.filaPacientesAzul.isEmpty()) {
                // NÃO, não há ninguém aguardando 
                // insira-se na fila de recepcao inativa 
                myModel.osciosidadeAtendimentoMedico.insert(this);
                // e aguarde que as coisas aconteçam 
                passivate();

            } else {
                // SIM, existe um cliente (paciente) aguardando 

                // obter uma referência ao primeiro caminhão da fila de pacientes 
                //Paciente proximoPaciente = myModel.filaPacientesAtendimentoMedico.first();
                Paciente proximoPaciente = getProximoPacienteByQueueClassification();
                // remove o paciente da fila 
                myModel.filaPacientesAtendimentoMedico.remove(proximoPaciente);

                //Add confirmacao do exame/medicacao do paciente ao banco
                //OBS: ADPTAR A MELHOR FORMA DE CONFIRMAR !!
                int idProximoPaciente = (int) (proximoPaciente.getIdentNumber() - 11);
                System.out.println("Id do proximo paciente (Triagem): " + idProximoPaciente);
                setExameSDK(idProximoPaciente, "1");
                setMedicacaoSDK(idProximoPaciente, "1");

                // agora atenda 
                // o tempo de serviço é representado por uma retenção da retenção do processo 
                hold(new TimeSpan(myModel.getServiceTimeAtendimentoMedico(), TimeUnit.MINUTES));
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

    public void setExameSDK(int idProximoPaciente, String confirma_exame) {

        PacienteSDK psdk = new PacienteSDK();
        psdk.setExameById(idProximoPaciente, confirma_exame);

    }

    public void setMedicacaoSDK(int idProximoPaciente, String confirma_medicacao) {

        PacienteSDK psdk = new PacienteSDK();
        psdk.setMedicamentoById(idProximoPaciente, confirma_medicacao);

    }

    public Paciente getProximoPacienteByQueueClassification() {

        if (!myModel.filaPacientesLaranja.isEmpty()) {
            Paciente paciente = myModel.filaPacientesLaranja.first();
            return paciente;
        } else if (!myModel.filaPacientesLaranja.isEmpty()) {
            Paciente paciente = myModel.filaPacientesAmarelo.first();
            return paciente;
        } else if (!myModel.filaPacientesLaranja.isEmpty()) {
            Paciente paciente = myModel.filaPacientesVerde.first();
            return paciente;
        } else if (!myModel.filaPacientesLaranja.isEmpty()) {
            Paciente paciente = myModel.filaPacientesAzul.first();
            return paciente;
        }
            Paciente paciente = myModel.filaPacientesAtendimentoMedico.first();
            return paciente;
    }
}
