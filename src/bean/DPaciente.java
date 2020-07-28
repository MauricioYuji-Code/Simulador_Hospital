/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author Mauricio
 */
public class DPaciente {
    
    private int IdPaciente;
    private String classificacao;
    private char c_exame;
    private char c_medicamento;
    private char c_retorno;

    public int getIdPaciente() {
        return IdPaciente;
    }

    public void setIdPaciente(int IdPaciente) {
        this.IdPaciente = IdPaciente;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public char getC_exame() {
        return c_exame;
    }

    public void setC_exame(char c_exame) {
        this.c_exame = c_exame;
    }

    public char getC_medicamento() {
        return c_medicamento;
    }

    public void setC_medicamento(char c_medicamento) {
        this.c_medicamento = c_medicamento;
    }

    public char getC_retorno() {
        return c_retorno;
    }

    public void setC_retorno(char c_retorno) {
        this.c_retorno = c_retorno;
    }
    
}
