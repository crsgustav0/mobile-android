package br.agr.mobile.mobileandroid.ui.act001;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import br.agr.mobile.mobileandroid.R;
import br.agr.mobile.mobileandroid.dao.PessoasDao;
import br.agr.mobile.mobileandroid.model.Pessoas;
import br.agr.mobile.mobileandroid.ui.act000.ConsultaPessoasActivity;
import br.agr.mobile.mobileandroid.util.Constantes;
import br.agr.mobile.mobileandroid.util.ToolBox;

public class FormularioPessoaActivity extends AppCompatActivity {
    private Context context;

    private int id = -1;
    private String nome = "";
    private String telefone = "";
    private String email = "";

    private String tipo = "";

    private TextInputLayout txt_nome;
    private TextInputLayout txt_telefone;
    private TextInputLayout txt_email;
    private Button btn_salvar;

    private PessoasDao pessoasDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_ugb);
        incializarVaiaveis();
        inicializarAcoes();
    }

    private void incializarVaiaveis() {
        context = FormularioPessoaActivity.this;

        txt_nome = findViewById(R.id.txt_nome);
        txt_telefone = findViewById(R.id.txt_telefone);
        txt_email = findViewById(R.id.txt_email);
        btn_salvar = findViewById(R.id.btn_salvar);

        pessoasDao = new PessoasDao(context);

        tipo = getIntent().getStringExtra(PessoasDao.TIPO);
        if (tipo.equals("A")) {//Alteral
            id = Integer.parseInt(getIntent().getStringExtra(PessoasDao.ID));
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private void inicializarAcoes() {
        ajustarTela();

        carregarCampos(tipo, id);

        btn_salvar.setOnClickListener(v -> {
            if (validarCampos()) {
                ToolBox.exibirMessagem(context, Constantes.TITULO_ACTIVITY_FORMULARIO, "Deseja importar dados?", 0, true,
                        "Sim", (dialog, which) -> gravarPessoas(tipo, id),
                        "Nao", null);
            }
        });

    }

    private void carregarCampos(String tipo, int id) {
        if (tipo.equals("A")) {//Alterar
            Pessoas auxPessoas = pessoasDao.obterPessoaByID(id);
            if (auxPessoas != null) {
                nome = auxPessoas.getNome();
                telefone = auxPessoas.getTelefone();
                email = auxPessoas.getEmail();

                txt_nome.getEditText().setText(nome);
                txt_telefone.getEditText().setText(telefone);
                txt_email.getEditText().setText(email);
            }
        }
    }

    private void gravarPessoas(String tipo, int id) {
        ArrayList<Pessoas> listaPessoas = new ArrayList<>();
        String nome;
        String telefone;
        String email;


        if (tipo.equals("I")) {//Incluir
            Pessoas cAux = null;

            if (cAux == null) {

                id = pessoasDao.proximoID();

                nome = txt_nome.getEditText().getText().toString().trim();
                telefone = txt_telefone.getEditText().getText().toString().trim();
                email = txt_email.getEditText().getText().toString().trim();

                Pessoas pessoas = new Pessoas(id, nome, telefone, email);

                listaPessoas.add(pessoas);

                pessoasDao.inserirPessoa(listaPessoas);

            }

        } else if (tipo.equals("A")) {//Alterar
            nome = txt_nome.getEditText().getText().toString().trim();
            telefone = txt_telefone.getEditText().getText().toString().trim();
            email = txt_email.getEditText().getText().toString().trim();

            Pessoas auxPessoas = pessoasDao.obterPessoaByID(id);
            if (auxPessoas != null) {
                auxPessoas.setId(id);
                auxPessoas.setNome(nome);
                auxPessoas.setTelefone(telefone);
                auxPessoas.setEmail(email);

                pessoasDao.alterarPessoa(auxPessoas);
            }

        }


        ToolBox.exibirMessagem(context, Constantes.TITULO_ACTIVITY_FORMULARIO, "Informa????es salvas com sucesso!", R.drawable.ic_launcher_background,
                true, "Ok",
                (dialog, which) -> abrirConsultaFormularioActivity(context),
                "",
                null
        );
    }

    private void abrirConsultaFormularioActivity(Context context) {
        Intent getIntent = new Intent(context, ConsultaPessoasActivity.class);
        startActivity(getIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    private void ajustarTela() {
        getSupportActionBar().setElevation(0);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setTitle(Constantes.TITULO_ACTIVITY_FORMULARIO);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private boolean validarCampos() {
        //Nome
        if (txt_nome.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(context, "Campos obrigat??rios n??o preenchidos!", Toast.LENGTH_SHORT).show();

            return false;
        }

        //Telefone
        if (txt_telefone.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(context, "Campos obrigat??rios n??o preenchidos!", Toast.LENGTH_SHORT).show();

            return false;
        }

        //Email
        if (txt_email.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(context, "Campos obrigat??rios n??o preenchidos!", Toast.LENGTH_SHORT).show();

            return false;
        }

        return true;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                fecharFormularioPessoas(context);
                break;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        fecharFormularioPessoas(context);
    }

    private void fecharFormularioPessoas(Context context) {
        Intent getIntent = new Intent(context, ConsultaPessoasActivity.class);
        startActivity(getIntent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

}

