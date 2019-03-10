package appconsultacontas.com.br.ccs.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import appconsultacontas.com.br.ccs.R;
import appconsultacontas.com.br.ccs.enums.TipoMsg;
import appconsultacontas.com.br.ccs.modelo.Configuracao;
import appconsultacontas.com.br.ccs.util.Common;
import io.paperdb.Paper;

public class ConfigActivity extends AppCompatActivity {

    EditText txtImpostoNfe,txtFgtsInss, txtIrpf;
    Configuracao configuracao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Paper.init(this);

        txtFgtsInss = findViewById(R.id.txtFgtsInss);
        txtImpostoNfe = findViewById(R.id.txtImpostoNfe);
        txtIrpf = findViewById(R.id.txtIrpf);
        lerDados();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_config, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.action_save:
                configuracao = new Configuracao();
                int impostoNf = Integer.parseInt(txtImpostoNfe.getText().toString());
                int impostoFgtsInss = Integer.parseInt(txtFgtsInss.getText().toString());
                double impostoIrpf = Double.parseDouble(txtIrpf.getText().toString());

                configuracao.setImpostoFgts(impostoFgtsInss);
                configuracao.setImpostoNfe(impostoNf);
                configuracao.setImpostoIrpf(impostoIrpf);
                gravarDados(configuracao);
                break;
            case R.id.action_instrucoes:
                Intent intent = new Intent(ConfigActivity.this, InstrucoesActivity.class);
                startActivity(intent);
                break;
            case R.id.action_excluir:
                    Common.showMsgConfirm(ConfigActivity.this, "Remover Configurações", "Deseja realmente remover essa configuração?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Paper.book().delete("configuracao");
                            Paper.book().destroy();
                            txtImpostoNfe.setText("0");
                            txtFgtsInss.setText("0");
                            txtIrpf.setText("0");
                            Common.showMsgToast(ConfigActivity.this, "Alterado para configuração padrão.");
                        }
                    });
                break;
            case R.id.action_exit:
                lerDados();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void gravarDados(Configuracao configuracao) {
        Paper.book().write("configuracao", configuracao);
        StringBuilder configMsg = new StringBuilder();
        configMsg.append(Common.div);
        configMsg.append("Imposto FGTS/INSS: " + configuracao.getImpostoFgts()+"\n");
        configMsg.append("Imposto Nota Fiscal: " + configuracao.getImpostoNfe()+"\n");
        configMsg.append("Imposto IRPF: " + configuracao.getImpostoIrpf()+"\n");
        configMsg.append(Common.div);
        Common.showMsgAlertOK(ConfigActivity.this, configMsg.toString(), "Configuração", TipoMsg.SUCESSO);
    }

    private void lerDados(){
        Configuracao configuracao = Paper.book().read("configuracao");
        if(configuracao == null){
            txtFgtsInss.setText(String.valueOf(0));
            txtImpostoNfe.setText(String.valueOf(0));
        }else{
            txtFgtsInss.setText(String.valueOf(configuracao.getImpostoFgts()));
            txtImpostoNfe.setText(String.valueOf(configuracao.getImpostoNfe()));
        }
    }
}
