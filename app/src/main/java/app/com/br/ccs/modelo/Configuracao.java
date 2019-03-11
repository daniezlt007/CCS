package app.com.br.ccs.modelo;

public class Configuracao {

    private int impostoNfe;
    private int impostoFgts;

    public Configuracao() {

    }

    public Configuracao(int impostoNfe, int impostoFgts) {
        this.impostoNfe = impostoNfe;
        this.impostoFgts = impostoFgts;
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

}
