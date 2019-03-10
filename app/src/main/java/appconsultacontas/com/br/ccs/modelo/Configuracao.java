package appconsultacontas.com.br.ccs.modelo;

public class Configuracao {

    private int impostoNfe;
    private int impostoFgts;
    private double impostoIrpf;

    public Configuracao() {

    }

    public Configuracao(int impostoNfe, int impostoFgts, double impostoIrpf) {
        this.impostoNfe = impostoNfe;
        this.impostoFgts = impostoFgts;
        this.impostoIrpf = impostoIrpf;
    }

    public int getImpostoNfe() {
        return impostoNfe;
    }

    public void setImpostoNfe(int impostoNfe) {
        this.impostoNfe = impostoNfe;
    }

    public int getImpostoFgts() {
        return impostoFgts;
    }

    public void setImpostoFgts(int impostoFgts) {
        this.impostoFgts = impostoFgts;
    }

    public double getImpostoIrpf() {
        return impostoIrpf;
    }

    public void setImpostoIrpf(double impostoIrpf) {
        this.impostoIrpf = impostoIrpf;
    }
}
