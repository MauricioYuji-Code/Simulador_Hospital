/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import desmoj.core.dist.ContDistExponential;
import desmoj.core.dist.ContDistNormal;
import desmoj.core.dist.ContDistUniform;
import desmoj.core.dist.DiscreteDistUniform;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import desmoj.core.simulator.TimeInstant;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
    //Ex: Toda vez que um peciente chega, ele é inserido nessa fila 
    //e será removido por um servidor.
    protected ProcessQueue <Paciente> filaPacientesRecepcao;
    protected ProcessQueue <Paciente> filaPacientesTriagem;
    
    //Fluxo de números aleatórios para os horários de chegada
    //Fluxo de números aleatórios para modelar o intervalo de tempo entre a chegada
    //Fluxo de número aleatório usado para desenhar uma hora de chegada para o próximo paciente.
    //Ex:Supomos que um paciente chegue a cada três minutos, 
    //o valor médio dessa distribuição ContDistExponential será 3,0.
    //paciente arrival time
    public ContDistExponential pacienteArrivalTime;

                /************Servidores************/
    // Pode ser utilizado para as entidades internas/servidores do modelo
    //Se não houver áciente aguardando , a recepcap retornará aqui
    //e aguarde o próximo paciente chegar.
    protected ProcessQueue<Recepcao> osciosidadeRecepcao;
    protected ProcessQueue<Triagem> osciosidadeTriagem;

    //Fluxo de números aleatórios usado para desenhar um tempo de serviço para um paciente.
    //Provê o tempo de um serviço.
    //Exemplo: atendimento leva-se de um 3 a 7 min
    //service time
    public ContDistUniform serviceTimeRecepcao;
    public ContDistUniform serviceTimeTriagem;

    //Fluxo de números aleatórios 
    //Fluxo uniformemente distribuído de números pseudo-aleatórios do tipo long.
    //Os valores produzidos por esta distribuição são distribuídos uniformemente no intervalo especificado como parâmetros do construtor.
    //public DiscreteDistUniform discreteDistUniform;

    // Fluxo de números aleatórios para a duração do serviço (Tempo necessário)
    //Essa classe híbrida é capaz de produzir um fluxo distribuído normalmente "Gaussiano" de números pseudo-aleatórios do tipo double
    //Também conhecido como "distribuição normal simétrica" ​​para maior clareza
    //ou uma "distribuição normal assimétrica" ​​na qual são assumidos diferentes valores de variação padrão nos dois lados do modo.
    //public ContDistNormal contDistNormal;

    //constantes
    protected static int NUM_RECEPCIONISTA = 2;
    protected static int NUM_ENFERMEIRAS = 1;

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
     * Retorna uma descrição do modelo a ser usado no relatório.
     *
     * @return String
     */
    @Override
    public String description() {
        return "Este modelo representa um hospital blablabla....";
    }

    /**
     * ativa componentes dinâmicos do modelo (processos de simulação). Este
     * método é usado para colocar todos os eventos ou processos na lista de
     * eventos internos do simulador necessários para iniciar a simulação.
     *
     * Nesse caso, o gerador do paciente e as entidades internas/servidores
     * devem ser criado e ativado.
     *
     */
    @Override
    public void doInitialSchedules() {
        //**Utilizado no video
        /*AMBOS TEORICAMENTE FAZEM A MESMA COISA  
     // cria e ativa o processo de gerador de paciente 
     Pacientes generator = new Pacientes(this,"ChegadaPaciente",false);
     //generator.activate();
     generator.schedule(new TimeSpan(0.0));
        //**Utilizado no video
     // cria e ativa o processo de gerador de paciente
     //Talvez use
     GeradorPaciente paciente = new GeradorPaciente(this,"ChegadaPaciente",false);
     paciente.activate();
        AMBOS TEORICAMENTE FAZEM A MESMA COISA*/

 /*
     * TimeSpan -> Representa períodos de tempo de simulação.
     * Cada intervalo de tempo de simulação é representado por um objeto individual desta classe e oferece seus próprios métodos para operações aritméticas e comparação.
     * Garante que apenas intervalos de tempo válidos sejam gerados.
         */
        // cria e ativa 
        for (int i = 0; i < NUM_RECEPCIONISTA; i++) {
            Recepcao recepcionista = new Recepcao(this, "Recepcionista", true);
            recepcionista.activate();
            // Use TimeSpan para ativar um processo após um período de tempo relativo ao tempo real da simulação,
            // ou use TimeInstant para ativar o processo em um ponto absoluto no tempo.
        }

        // cria e ativa o processo de gerador de pacientes
        GeradorPaciente geradorPaciente = new GeradorPaciente(this, "ChegadaPaciente", false);
        geradorPaciente.activate();

    }

    /**
     * inicializa os componentes do modelo estático, como distribuições e filas.
     *
     * Deve-se criar novas instancias para a inicialização do modelo ->
     * ProcessQueue e ContDistExponential.
     */
    @Override
    public void init() {

        // inicializa o serviceTimeStream
        // Parâmetros:
        // this = pertence a este modelo
        // "serviceTimeRecepcao" = o nome do fluxo
        // 3.0 = tempo mínimo em minutos de atendimento
        // 7.0 = tempo máximo em minutos de atendimento
        // true = mostra no relatório?
        // false = mostra no rastreamento?
        serviceTimeRecepcao = new ContDistUniform(this, "Recepcao ServiceTimeStream",
                3.0, 7.0, true, false);

        // ... init () continua
        // inicializa o ArrivalTimeStream
        // Parâmetros:
        // this = pertence a este modelo
        // "pacienteArrivalTime" = o nome do fluxo
        // 3.0 = tempo médio em minutos entre a chegada de pacientes
        // true = mostra no relatório?
        // false = mostra no rastreamento?
        pacienteArrivalTime = new ContDistExponential(this, "ChegadaPaciente TimeStream",
                3.0, true, false);

        // necessário porque o horário de chegada não pode ser negativo, mas
        // uma amostra de uma distribuição exponencial pode ...
        pacienteArrivalTime.setNonNegative(true);

        // inicializa o pacienteQueue
        // Parâmetros:
        // this = pertence a este modelo
        // "Fila de pacientes" = o nome da fila
        // true = mostra no relatório?
        // true = mostra no rastreamento?
        filaPacientesRecepcao = new ProcessQueue<Paciente>(this, "Fila de pacientes", true, true);

        /*Exemplo "Pode ser utilizado para as entidades internas/servidores do modelo"*/
        // inicializa o osciosidadeRecepcao (recepcionista prontos para o serviço)
        // Parâmetros:
        // this = pertence a este modelo
        // "fila ociosa de paciente" = o nome da fila
        // true = mostra no relatório?
        // true = mostra no rastreamento?
        //Objeto:
        osciosidadeRecepcao = new ProcessQueue <Recepcao> (this, "Fila de espera de atendimento (osciosidade recepcao)", true, true);
    }

    /**
     * **Getter's and  Setter's***
     */
    //Retorna um intervalo de tempo para o tempo de serviço 
    protected double getServiceTimeRecepcao() {
        return serviceTimeRecepcao.sample();
    }
    
    protected double getServiceTimeTriagem() {
        return serviceTimeTriagem.sample();
    }

    //Retorna um intervalo de tempo para o próximo tempo entre chegadas de um paciente
    //Retorna uma amostra do fluxo aleatório usado para determinar
    protected double getPacienteArrivalTime() {
        return pacienteArrivalTime.sample();
    }

    //Intervalo de tempo de um serviço 
    //protected double getServidorServiceTime() {
        //return serviceTimeRecepcao.sample();
    //}

    /**
     * run the model *
     *
     * instanciar um experimento instanciar o modelo conectar o modelo ao
     * experimento determinar o comprimento da execução da simulação ou definir
     * um critério final para a execução da simulação defina o horário de início
     * e término para o arquivo de rastreio iniciar o planejador iniciar
     * relatórios limpar após o término da simulação
     */
    public static void main(String[] args) {

        //Informações da simulação
        Scanner sc = new Scanner(System.in);
        System.out.println("Tempo de simulacão (em minutos):");
        tempoSimulação = sc.nextDouble();

        // null como primeiro parâmetro, porque é o modelo principal e não possui modelo mestre
        HospitalModel model = new HospitalModel(null, "Modelo Hospital", true, true);

        // criando o modelo e o experimento
        // ATENÇÃO, já que o nome do experimento é usado nos nomes dos
        // arquivos de saída, você deve especificar uma string que seja compatível com o
        // restrições de nome de arquivo do sistema operacional do seu computador.
        Experiment exp = new Experiment("Experimento Hospital");
        //Experiment exp = new Experiment("ExampleExperiment", TimeUnit.SECONDS, TimeUnit.MINUTES, null);

        // conecta ambos
        model.connectToExperiment(exp);

        // set parametros do experimento
        exp.setShowProgressBar(true);  // display de progressão da simulação/exibe uma barra de progresso
        exp.stop(new TimeInstant(tempoSimulação, TimeUnit.MINUTES));   // define o fim da simulação em 1500 minutos
        exp.tracePeriod(new TimeInstant(0), new TimeInstant(200, TimeUnit.MINUTES));  // define o período do rastreio, que a simulacao abc ira parar
        exp.debugPeriod(new TimeInstant(0), new TimeInstant(50, TimeUnit.MINUTES));   // e saída de depuração
        // ATENÇÃO!
        // Não use períodos muito longos. Caso contrário, uma enorme página HTML será
        // ser criado, travando o Netscape :-)

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
