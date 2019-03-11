package app.com.br.ccs.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import app.com.br.ccs.R;
import app.com.br.ccs.enums.TipoMsg;
import app.com.br.ccs.modelo.Configuracao;
import app.com.br.ccs.modelo.Servico;
import app.com.br.ccs.util.Common;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    Button btnCalcular;
    private EditText txtSalarioDesejado, txtHoraDev, txtHoraAnalise, txtHoraTeste;
    private AlertDialog alerta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Paper.init(this);

        txtSalarioDesejado = findViewById(R.id.txtSalarioDesejado);
        txtHoraDev = findViewById(R.id.txtDev);
        txtHoraAnalise = findViewById(R.id.txtFgtsInss);
        txtHoraTeste = findViewById(R.id.txtTestes);
        btnCalcular = findViewById(R.id.btnCalcular);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuracao configuracao = lerDados();
                Servico servico = new Servico();

                if(!validarCampos()) {
                    servico.setSalarioDesejado(Double.parseDouble(txtSalarioDesejado.getText().toString()));
                    servico.setHoraAnalise(Integer.parseInt(txtHoraAnalise.getText().toString()));
                    servico.setHoraDesenvolvimento(Integer.parseInt(txtHoraDev.getText().toString()));
                    servico.setHoraTestes(Integer.parseInt(txtHoraTeste.getText().toString()));

                    double salario = servico.getSalarioDesejado();
                    double fgts = 0.0;
                    double notaFiscal = 0.0;
                    double decimoFerias = salario / 12;
                    double feriasTerco = salario / 3;
                    int analise = servico.getHoraAnalise();
                    int dev = servico.getHoraDesenvolvimento();
                    int teste = servico.getHoraTestes();
                    int totalHora = analise + dev + teste;
                    double valorNF = 0.0;

                    if (configuracao != null) {
                        fgts = (salario * (configuracao.getImpostoFgts())) / 100;
                        notaFiscal = configuracao.getImpostoNfe();
                        valorNF = notaFiscal;
                    } else {
                        fgts = Common.retornaFgts(salario);
                    }
                    double valorHora = (salario + fgts + decimoFerias + feriasTerco) / 160;

                    double valorTotalSemNota = totalHora * valorHora;
                    valorNF = (valorTotalSemNota * notaFiscal) / 100;
                    double valorTotalComNota = valorTotalSemNota + valorNF;
                    StringBuilder info = new StringBuilder();
                    info.append("Salário Desejado - " + Common.numeroformatadoEmReal.format(servico.getSalarioDesejado()) + "\n");
                    info.append("FGTS - " + Common.numeroformatadoEmReal.format(fgts) + "\n");
                    info.append("1/12 13º - " + Common.numeroformatadoEmReal.format(decimoFerias) + "\n");
                    info.append("1/3 Férias - " + Common.numeroformatadoEmReal.format(feriasTerco) + "\n");
                    info.append("Valor por Hora - " + Common.numeroformatadoEmReal.format(valorHora) + "\n");
                    info.append(Common.div);
                    info.append("Análise: " + analise + "\n");
                    info.append("Desenvolvimento: " + dev + "\n");
                    info.append("Testes: " + teste + "\n");
                    info.append("Total de horas: " + totalHora + "\n");
                    info.append(Common.div);
                    info.append("Valor NF " + Common.numeroformatadoEmReal.format(valorNF) + "\n");
                    info.append(Common.div);
                    info.append("Valor Total S/ NF " + Common.numeroformatadoEmReal.format(valorTotalSemNota) + "\n");
                    info.append("Valor Total C/ NF " + Common.numeroformatadoEmReal.format(valorTotalComNota) + "\n");
                    info.append(Common.div);
                    Common.showMsgAlertOK(MainActivity.this, info.toString(), "Valor a cobrar", TipoMsg.INFO);

                    txtHoraAnalise.setText("");
                    txtHoraDev.setText("");
                    txtHoraTeste.setText("");
                    txtSalarioDesejado.setText("");

                }
            }
        });
    }

    public boolean validarCampos(){
        String salario = txtSalarioDesejado.getText().toString();
        String horaAnalise = txtHoraAnalise.getText().toString();
        String horaDev = txtHoraDev.getText().toString();
        String horaTeste = txtHoraTeste.getText().toString();
        if(salario.equals("") || salario.equals(null)){
            txtSalarioDesejado.setError("Campo obrigatório");
            return true;
        }

        if(horaAnalise.equals("") || horaAnalise.equals(null)){
            txtHoraAnalise.setError("Campo obrigatório");
            return true;
        }

        if(horaDev.equals("") || horaDev.equals(null)){
            txtHoraDev.setError("Campo obrigatório");
            return true;
        }

        if(horaTeste.equals("") || horaTeste.equals(null)){
            txtHoraTeste.setError("Campo obrigatório");
            return true;
        }
        return false;
    }

    private Configuracao lerDados(){
        Configuracao configuracao = Paper.book().read("configuracao");
        return configuracao;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, ConfigActivity.class);
                startActivity(intent);
                break;
            case R.id.action_instrucoes:
                Intent instrucao = new Intent(MainActivity.this, InstrucoesActivity.class);
                startActivity(instrucao);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
