package appconsultacontas.com.br.ccs.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import appconsultacontas.com.br.ccs.R;
import appconsultacontas.com.br.ccs.enums.TipoMsg;
import appconsultacontas.com.br.ccs.modelo.Configuracao;
import appconsultacontas.com.br.ccs.modelo.Servico;
import appconsultacontas.com.br.ccs.util.Common;
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

        txtSalarioDesejado = findViewById(R.id.txtImpostoNfe);
        txtHoraDev = findViewById(R.id.txtDev);
        txtHoraAnalise = findViewById(R.id.txtFgtsInss);
        txtHoraTeste = findViewById(R.id.txtTestes);
        btnCalcular = findViewById(R.id.btnCalcular);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuracao configuracao = lerDados();
                Servico servico = new Servico();
                servico.setSalarioDesejado(Double.parseDouble(txtSalarioDesejado.getText().toString()));
                servico.setHoraAnalise(Integer.parseInt(txtHoraAnalise.getText().toString()));
                servico.setHoraDesenvolvimento(Integer.parseInt(txtHoraDev.getText().toString()));
                servico.setHoraTestes(Integer.parseInt(txtHoraTeste.getText().toString()));

                double salario = servico.getSalarioDesejado();
                double fgts = 0.0;
                double nf = 0.0;
                double irpf = 0.0;
                if(configuracao != null){
                    fgts = (salario * (configuracao.getImpostoFgts())) / 100;
                    nf = (salario * (configuracao.getImpostoNfe())) / 100;
                    Log.d("TAG","FGTS: " + fgts);
                }else{
                    fgts = retornaFgts(salario);
                }
                double decimoFerias = salario / 12;
                double feriasTerco = salario / 3;

                int analise = servico.getHoraAnalise();
                int dev = servico.getHoraDesenvolvimento();
                int teste = servico.getHoraTestes();

                int totalHora = analise + dev + teste;
                double valorHora = (salario + fgts + decimoFerias + feriasTerco) / 160;
                double valorAcobrar = totalHora * valorHora;

                StringBuilder info =  new StringBuilder();
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
                info.append("Valor total a cobrar " + Common.numeroformatadoEmReal.format(valorAcobrar) + "\n");
                info.append(Common.div);
                info.append("OBS: Não contém cálculo sobre a NF");
                Common.showMsgAlertOK(MainActivity.this, info.toString(), "Valor a cobrar", TipoMsg.INFO);

                txtHoraAnalise.setText("");
                txtHoraDev.setText("");
                txtHoraTeste.setText("");
                txtSalarioDesejado.setText("");

            }
        });
    }

    private double retornaFgts(double salario) {
        double fgts;
        if(salario <= 1399.12){
            fgts = salario * 0.08;
        }else if(salario <= 2331.88){
            fgts = salario * 0.09;
        }else if(salario <= 4663.75){
            fgts = salario * 0.11;
        }else{
            fgts = 513.01;
        }
        return fgts;
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
