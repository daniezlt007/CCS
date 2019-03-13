package app.com.br.ccs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import app.com.br.ccs.R;
import app.com.br.ccs.modelo.Servico;
import app.com.br.ccs.util.Common;

public class ServicoAdapter extends ArrayAdapter<Servico> {

    private final Context context;
    private final List<Servico> listaElementos;

    public ServicoAdapter(Context context, List<Servico> listaElementos) {
        super(context, R.layout.linha, listaElementos);
        this.context = context;
        this.listaElementos = listaElementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha, parent, false);
        TextView titulo = (TextView) rowView.findViewById(R.id.txtValorTotal1);
        TextView ano = (TextView) rowView.findViewById(R.id.txtValorHora1);
        TextView autor = (TextView) rowView.findViewById(R.id.txtValorFgts1);
        titulo.setText("Nome Empresa: " + listaElementos.get(position).getNomeEmpresa());
        autor.setText("Valor por Hora: " + Common.numeroformatadoEmReal.format(listaElementos.get(position).getValorPorHora()));
        ano.setText("Valor Total C/ NF: " + Common.numeroformatadoEmReal.format(listaElementos.get(position).getValorTotalComNf()));
        return rowView;
    }

}
