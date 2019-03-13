package app.com.br.ccs.modelo;

import java.io.Serializable;

public class Servico implements Serializable {

    private String nomeEmpresa;
    private double salarioDesejado;
    private int horaAnalise;
    private int horaDesenvolvimento;
    private int horaTestes;
    private double fgts;
    private double decimoTerceiro;
    private double parteFerias;
    private double valorPorHora;
    private double valorImpostoNf;
    private double valorTotalSemNf;
    private double valorTotalComNf;

    public Servico() {

    }

    public Servico(String nomeEmpresa, double salarioDesejado, int horaAnalise, int horaDesenvolvimento, int horaTestes, double fgts, double decimoTerceiro, double parteFerias, double valorPorHora, double valorImpostoNf, double valorTotalSemNf, double valorTotalComNf) {
        this.nomeEmpresa = nomeEmpresa;
        this.salarioDesejado = salarioDesejado;
        this.horaAnalise = horaAnalise;
        this.horaDesenvolvimento = horaDesenvolvimento;
        this.horaTestes = horaTestes;
        this.fgts = fgts;
        this.decimoTerceiro = decimoTerceiro;
        this.parteFerias = parteFerias;
        this.valorPorHora = valorPorHora;
        this.valorImpostoNf = valorImpostoNf;
        this.valorTotalSemNf = valorTotalSemNf;
        this.valorTotalComNf = valorTotalComNf;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
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

    public double getFgts() {
        return fgts;
    }

    public void setFgts(double fgts) {
        this.fgts = fgts;
    }

    public double getDecimoTerceiro() {
        return decimoTerceiro;
    }

    public void setDecimoTerceiro(double decimoTerceiro) {
        this.decimoTerceiro = decimoTerceiro;
    }

    public double getValorPorHora() {
        return valorPorHora;
    }

    public void setValorPorHora(double valorPorHora) {
        this.valorPorHora = valorPorHora;
    }

    public double getValorImpostoNf() {
        return valorImpostoNf;
    }

    public void setValorImpostoNf(double valorImpostoNf) {
        this.valorImpostoNf = valorImpostoNf;
    }

    public double getValorTotalSemNf() {
        return valorTotalSemNf;
    }

    public void setValorTotalSemNf(double valorTotalSemNf) {
        this.valorTotalSemNf = valorTotalSemNf;
    }

    public double getValorTotalComNf() {
        return valorTotalComNf;
    }

    public void setValorTotalComNf(double valorTotalComNf) {
        this.valorTotalComNf = valorTotalComNf;
    }

    public double getParteFerias() {
        return parteFerias;
    }

    public void setParteFerias(double parteFerias) {
        this.parteFerias = parteFerias;
    }

    public int totalHoras(){
        return this.horaAnalise + this.horaDesenvolvimento + this.horaTestes;
    }
}
