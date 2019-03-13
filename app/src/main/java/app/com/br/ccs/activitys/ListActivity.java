package app.com.br.ccs.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import app.com.br.ccs.R;
import app.com.br.ccs.adapter.ServicoAdapter;
import app.com.br.ccs.enums.TipoMsg;
import app.com.br.ccs.modelo.Servico;
import app.com.br.ccs.util.Common;
import io.paperdb.Paper;

public class ListActivity extends AppCompatActivity {

    ListView lista;
    ServicoAdapter servicoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        lista = findViewById(R.id.lista);

        Paper.init(ListActivity.this);

        final ArrayList<Servico> listaServico = Paper.book().read("listaServico");
        servicoAdapter = new ServicoAdapter(getBaseContext(), listaServico);
        lista.setAdapter(servicoAdapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Servico servico = listaServico.get(position);
                mostrarResultado(servico);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                Common.showMsgConfirm(ListActivity.this, "Exclusão", "Deseja realmente excluir?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listaServico.remove(position);
                        servicoAdapter.notifyDataSetChanged();
                        Paper.book().write("listaServico", listaServico);
                    }
                });
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_lista, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.action_main:
                Intent main = new Intent(ListActivity.this, MainActivity.class);
                startActivity(main);
                finish();
                break;
            case R.id.action_settings:
                Intent intent = new Intent(ListActivity.this, ConfigActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.action_instrucoes:
                Intent instrucao = new Intent(ListActivity.this, InstrucoesActivity.class);
                startActivity(instrucao);
                finish();
                break;
            case R.id.action_exit:
                finishAffinity();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void mostrarResultado(Servico servico) {
        StringBuilder info = new StringBuilder();
        info.append("Nome empresa - " + servico.getNomeEmpresa() + "\n");
        info.append("Salário Desejado - " + Common.numeroformatadoEmReal.format(servico.getSalarioDesejado()) + "\n");
        info.append("FGTS - " + Common.numeroformatadoEmReal.format(servico.getFgts()) + "\n");
        info.append("1/12 13º - " + Common.numeroformatadoEmReal.format(servico.getDecimoTerceiro()) + "\n");
        info.append("1/3 Férias - " + Common.numeroformatadoEmReal.format(servico.getParteFerias()) + "\n");
        info.append("Valor por Hora - " + Common.numeroformatadoEmReal.format(servico.getValorPorHora()) + "\n");
        info.append(Common.div);
        info.append("Análise: " + servico.getHoraAnalise() + "\n");
        info.append("Desenvolvimento: " + servico.getHoraDesenvolvimento() + "\n");
        info.append("Testes: " + servico.getHoraTestes() + "\n");
        info.append("Total de horas: " + servico.totalHoras() + "\n");
        info.append(Common.div);
        info.append("Valor NF " + Common.numeroformatadoEmReal.format(servico.getValorImpostoNf()) + "\n");
        info.append(Common.div);
        info.append("Valor Total S/ NF " + Common.numeroformatadoEmReal.format(servico.getValorTotalSemNf()) + "\n");
        info.append("Valor Total C/ NF " + Common.numeroformatadoEmReal.format(servico.getValorTotalComNf()) + "\n");
        info.append(Common.div);
        Common.showMsgAlertOK(ListActivity.this, info.toString(), "Valor a cobrar", TipoMsg.INFO);
    }


}
