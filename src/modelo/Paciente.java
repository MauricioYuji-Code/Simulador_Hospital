/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Mauricio
 */
/**
 * O SimProcess representa entidades com um próprio ciclo de vida ativo. Como os
 * SimProcesses são de fato entidades especiais com recursos estendidos
 * (especialmente o método lifeCycle ()), eles herdam da Entidade e, portanto,
 * também podem ser usados ​​em conjunto com eventos. Para que possam ser
 * tratados de ambos os modos, orientados a eventos e processos. Os clientes
 * devem implementar o método lifeCycle () para especificar o comportamento
 * individual de uma subclasse especial do SimProcess. Como a implementação de
 * mecanismos de sincronização orientados a atividades e transações requer
 * mudanças significativas nessa classe, os métodos implementados por Soenke
 * Claassen foram marcados.
 *
 *
 */
public class Paciente extends SimProcess {

    /**
     * uma referência ao modelo em que este processo faz parte atalho útil para
     * acessar os componentes estáticos do modelo
     */
    private HospitalModel myModel;
    private int id; //identificador do paciente

    /**
     * Constructs
     *
     * Usado para criar um novo paciente para ser atendido.
     *
     * @param model o modelo ao qual esta entidade pertenci
     * @param entityName Nome da entidade
     * @param showInTrace Sinalizador para mostrar este evento externo em
     * mensagens de rastreamento
     */
    public Paciente(Model model, String entityName, boolean showInTrace) {
        super(model, entityName, showInTrace);
        this.id = id;
        this.myModel = (HospitalModel) model;
    }

    /**
     * @lifeCycle Comportamento específico desse SimProcess. Este método inicia
     * após a criação e ativação de um SimProcess. Observe que esse método será
     * executado uma ou várias vezes, dependendo do status de repetição do
     * SimProcess.
     *
     * descreve o ciclo de vida desse processo:
     *
     * Chegada do cliente Entra na fila continua...
     *
     */
    @Override
    public void lifeCycle() throws SuspendExecution {
        /**
         * *Descrever o ciclo de vida do paciente**
         */
        
        /*RECEPCAO*/
        // entra na fila
        // Ao inserir o Paciente na fila, fazemos a fila mantendo o controle dos dados estatísticos automaticamente. 
        myModel.filaPacientesRecepcao.insert(this);
        // usada para inserir texto adicional na saída do arquivo de rastreamento. Essa é uma maneira muito útil de tornar o arquivo de rastreamento mais compreensível para o leitor. 
        sendTraceNote("PacienteQueuelength:" + myModel.filaPacientesRecepcao.length());

        //Adicionaro condição para ver se o proximo serviço está disponivel
        //Testarei com a recepcção
        
        //...Efetuando condição de disponibilidade do serviço!
        //Verificação se a "fila" de servico da recepção está disponivel.
        if (!myModel.osciosidadeRecepcao.isEmpty()) {
         // Sim, ele é

         // obtém uma referência ao primeiro funcionario 
         // da recepcao da fila de recepcionistas ociosos
         Recepcao recepcionista = myModel.osciosidadeRecepcao.first ();
         // remova a recepcionista da fila
         myModel.osciosidadeRecepcao.remove (recepcionista);

         // coloque o funcionario da recepcao na lista de eventos logo depois de mim,
         // para garantir que eu seja o próximo cliente a receber manutenção
         recepcionista.activateAfter (this);
      }
         
        // espera pelo serviço 
        passivate();

        // Ok, estou novamente online, o que significa que fui atendido pela recepção 
        // Posso ir para o proximo servidor do sistema 
        // uma mensagem para o arquivo de rastreamento
        sendTraceNote("O paciente foi atendido pela recepcao");
        
        /*TRIAGEM*/
        myModel.filaPacientesTriagem.insert(this);
        sendTraceNote("PacienteQueuelength:" + myModel.filaPacientesTriagem.length());
        if (!myModel.osciosidadeTriagem.isEmpty()) {
         Triagem enfermeira = myModel.osciosidadeTriagem.first ();
         myModel.osciosidadeTriagem.remove (enfermeira);
         enfermeira.activateAfter (this);
      }
        passivate();
        sendTraceNote("O paciente foi atendido pela triagem");

    }

}
