/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import desmoj.core.dist.ContDistExponential;
import desmoj.core.dist.ContDistNormal;
import desmoj.core.dist.DiscreteDistUniform;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import desmoj.core.simulator.TimeInstant;
import java.util.Scanner;

/**
 *
 * @author Mauricio
 */
/**
 * No model são definidos: Parametros de entrada -> Média de chegadas, filas,
 * chegadas de pacientes e etc..
 *
 *
 */
public class HospitalModel extends Model {

    //Enfileirar SimProcess (Entidades) - Ordem padrão ->FIFO
    public ProcessQueue filaPacientes;

    //Fluxo negativo-exponencial distribuído de números pseudo-aleatórios do tipo double.
    //Fluxo de números aleatórios para os horários de chegada
    public ContDistExponential chegadas;
    
    //Fluxo de números aleatórios 
    //Fluxo uniformemente distribuído de números pseudo-aleatórios do tipo long.
    //Os valores produzidos por esta distribuição são distribuídos uniformemente no intervalo especificado como parâmetros do construtor.
    public DiscreteDistUniform discreteDistUniform;
    
    // Fluxo de números aleatórios para a duração do serviço (Tempo necessário)
    //Essa classe híbrida é capaz de produzir um fluxo distribuído normalmente "Gaussiano" de números pseudo-aleatórios do tipo double
    //Também conhecido como "distribuição normal simétrica" ​​para maior clareza
    //ou uma "distribuição normal assimétrica" ​​na qual são assumidos diferentes valores de variação padrão nos dois lados do modo.
    public ContDistNormal contDistNormal;


    //constantes
    //dados de entrada da simulação
    static double tempoSimulação;

    /**
     * Hospital constructor.
     *
     * Creates a new Hospital model via calling the constructor of the
     * superclass.
     *
     * @param model do qual este modelo faz parte (set null se este modelo não
     * existe)
     * @param modelName Nome do modelo
     * @param showInReport indica se este modelo ira gerar o relatório de saída
     * @param showInTrace indica se este modelo ira gerar o relatório de
     * rastreio
     */
    public HospitalModel(Model model, String modelName, boolean showInReport, boolean showInTrace) {
        super(model, modelName, showInReport, showInTrace);
    }

    /**
     * retorna uma descrição do modelo a ser usado no relatório.
     *
     * @return String
     */
    @Override
    public String description() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * ativa componentes dinâmicos do modelo (processos de simulação). Este
     * método é usado para colocar todos os eventos ou processos na lista
     * interna de eventos do simulador, necessários para iniciar a simulação.
     *
     */
    @Override
    public void doInitialSchedules() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        /*
        * Instancia-se o fator externo para esta função.
         */

 /*
     * TimeSpan -> Representa períodos de tempo de simulação.
     * Cada intervalo de tempo de simulação é representado por um objeto individual desta classe e oferece seus próprios métodos para operações aritméticas e comparação.
     * Garante que apenas intervalos de tempo válidos sejam gerados.
         */
    }

    /**
     * inicializa os componentes do modelo estático, como distribuições e filas.
     *
     * Deve-se criar novas instancias para a inicialização do modelo ->
     * ProcessQueue e ContDistExponential.
     */
    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * run the model *
     */
    public static void main(String[] args) {

        //Informações da simulação
        Scanner sc = new Scanner(System.in);
        System.out.println("Tempo de simulação (em minutos):");
        tempoSimulação = sc.nextDouble();

        HospitalModel model = new HospitalModel(null, "Modelo Hospital", true, true);
        // criando o modelo e o experimento
        Experiment exp = new Experiment("Experimento Hospital");
        //Experiment exp = new Experiment("ExampleExperiment", TimeUnit.SECONDS, TimeUnit.MINUTES, null);

        // conectando...
        model.connectToExperiment(exp);

        // set parametros do experimento
        exp.setShowProgressBar(true);  // display de progressão da simulação
        exp.stop(new TimeInstant(1500));   // set fim da simulação 1500 minutos
        exp.tracePeriod(new TimeInstant(0), new TimeInstant(100));  // set período de tempo da simulação em tempo real
        exp.debugPeriod(new TimeInstant(0), new TimeInstant(50));   // debug output

        // starta a experiência com o horário de início 0.0
        exp.start();

        // --> agora a simulação está sendo executada até atingir seu critério final
        // ...
        // ...
        // <-- depois de atingir o critério final, o thread principal retorna aqui
        // gerar o relatório (e outros arquivos de saída)
        exp.report();

        // interrompa todos os threads ainda ativos e feche todos os arquivos de saída
        exp.finish();
    }

}
