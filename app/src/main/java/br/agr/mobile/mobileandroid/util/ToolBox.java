package br.agr.mobile.mobileandroid.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import br.agr.mobile.mobileandroid.R;


public class ToolBox {

    public static void exibirMessagem(Context context, String titulo, String mensagem, int icon) {
        exibirMessagem(context, titulo, mensagem, icon, false, "Ok", null, "", null);
    }

    public static void exibirMessagem(Context context, String titulo, String mensagem, int icon, boolean tipo, String textoPositivo, DialogInterface.OnClickListener ActionPositivo, String textoNegativo, DialogInterface.OnClickListener ActionNegativo) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(context);
        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);

        if (icon > 0) {
            alerta.setIcon(icon);
        } else {
            alerta.setIcon(R.drawable.ic_launcher_background);
        }

        alerta.setCancelable(false);
        alerta.setPositiveButton(textoPositivo, ActionPositivo);

        if (tipo) {
            alerta.setNegativeButton(textoNegativo, ActionNegativo);
        }

        alerta.show();
    }

    public static ProgressDialog exibirProgressDialog(Context context, ProgressDialog progressDialog, int icon, String titulo, String mensagem, String controle) {
        if (controle.equals("A")) {//Abertura
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            if (icon == 0) {
                progressDialog.setIcon(R.drawable.ic_launcher_background);
            } else {
                progressDialog.setIcon(icon);
            }

            progressDialog.setTitle(titulo);
            progressDialog.setMessage(mensagem);
            progressDialog.show();

        } else {//Fechar
            ToolBox.exibirMessagem(context, titulo, mensagem, R.drawable.ic_launcher_background);
            progressDialog.dismiss();
        }

        return progressDialog;
    }

}
