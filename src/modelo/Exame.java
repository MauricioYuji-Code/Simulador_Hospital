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
 * @author Samsung
 */
public class Exame extends SimProcess{

    private HospitalModel myModel;

    public Exame(Model model, String nome, boolean showInTrace) {
        super(model, nome, showInTrace);
        this.myModel = (HospitalModel) model;
    }
    @Override
    public void lifeCycle() throws SuspendExecution {
        while (true) {
            
            if (myModel.filaPacientesExame.isEmpty()) {

                
                myModel.osciosidadeExame.insert(this);
        
                passivate();

            } else {
           
                Paciente proximoPaciente = myModel.filaPacientesExame.first();

                myModel.filaPacientesExame.remove(proximoPaciente);

                int idProximoPaciente = (int) (proximoPaciente.getIdentNumber() - 11);
                System.out.println("Id do proximo paciente (Exame): " + idProximoPaciente);

                //hold(new TimeSpan(myModel.getServiceTime(), TimeUnit.MINUTES));
  
                proximoPaciente.activate();

            }

        }
    }
    
}
