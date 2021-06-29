package br.agr.mobile.mobileandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.agr.mobile.mobileandroid.R;
import br.agr.mobile.mobileandroid.util.HMAux;


public class AdapterOption extends BaseAdapter {
    private Context context;

    private int resource;
    private List<HMAux> dados;

    private LayoutInflater mInflater;

    private long idselecionado = -1L;

    public void setIdselecionado(long idselecionado) {

        if (idselecionado != this.idselecionado) {
            this.idselecionado = idselecionado;
        } else {
            this.idselecionado = idselecionado;
        }

        notifyDataSetChanged();
    }

    public AdapterOption(Context context, int resource, List<HMAux> dados) {
        this.context = context;
        this.resource = resource;
        this.dados = dados;

        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dados.size();
    }

    @Override
    public Object getItem(int position) {
        return dados.get(position);
    }

    @Override
    public long getItemId(int position) {
        HMAux item = dados.get(position);
        return Long.parseLong(item.get("id"));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(resource, parent, false);
        }

        HMAux item = dados.get(position);

        LinearLayout ll_list_01 = convertView.findViewById(R.id.ll_list_01);

        ImageView iv_list_01_imagem = convertView.findViewById(R.id.iv_list_01_imagem);

        TextView tv_list_01_descricao = convertView.findViewById(R.id.tv_list_01_descricao);

        iv_list_01_imagem.setImageResource(Integer.parseInt(item.get("imagem")));
        tv_list_01_descricao.setText(item.get("descricao"));

        if (getItemId(position) == idselecionado) {
            ll_list_01.setBackgroundColor(context.getColor(R.color.cinza_claro_tv));
        } else {
            ll_list_01.setBackgroundColor(context.getColor(R.color.branco_tv));
            iv_list_01_imagem.setColorFilter(context.getColor(R.color.verde_tv));
            tv_list_01_descricao.setTextColor(context.getColor(R.color.cinza_tv));
        }

        return convertView;
    }
}
