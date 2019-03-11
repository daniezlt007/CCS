package app.com.br.ccs.modelo;

import java.io.Serializable;

public class Servico implements Serializable {

    private double salarioDesejado;
    private int horaAnalise;
    private int horaDesenvolvimento;
    private int horaTestes;

    public Servico() {

    }

    public Servico(double salarioDesejado, int horaAnalise, int horaDesenvolvimento, int horaTestes) {
        this.salarioDesejado = salarioDesejado;
        this.horaAnalise = horaAnalise;
        this.horaDesenvolvimento = horaDesenvolvimento;
        this.horaTestes = horaTestes;
    }

    public double getSalarioDesejado() {
        return salarioDesejado;
    }

    public void setSalarioDesejado(double salarioDesejado) {
        this.salarioDesejado = salarioDesejado;
    }

    public int getHoraAnalise() {
        return horaAnalise;
    }

    public void setHoraAnalise(int horaAnalise) {
        this.horaAnalise = horaAnalise;
    }

    public int getHoraDesenvolvimento() {
        return horaDesenvolvimento;
    }

    public void setHoraDesenvolvimento(int horaDesenvolvimento) {
        this.horaDesenvolvimento = horaDesenvolvimento;
    }

    public int getHoraTestes() {
        return horaTestes;
    }

    public void setHoraTestes(int horaTestes) {
        this.horaTestes = horaTestes;
    }
}
