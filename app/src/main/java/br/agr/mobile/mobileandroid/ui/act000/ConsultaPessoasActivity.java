package br.agr.mobile.mobileandroid.ui.act000;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import br.agr.mobile.mobileandroid.R;
import br.agr.mobile.mobileandroid.dao.PessoasDao;
import br.agr.mobile.mobileandroid.ui.act001.FormularioPessoaActivity;
import br.agr.mobile.mobileandroid.util.HMAux;

public class ConsultaPessoasActivity extends AppCompatActivity {
    private Context context;

    private EditText et_consulta;
    private ListView lv_consulta;
    private FloatingActionButton btn_consulta_formulario;

    private PessoasDao pessoasDao;

    private SimpleAdapter adapter;

    String[] De = {PessoasDao.NOME, PessoasDao.TELEFONE, PessoasDao.ID};
    int[] Para = {R.id.tv_celula_03_descricao01, R.id.tv_celula_03_descricao02, R.id.tv_celula_03_descricao03};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        inicializarVariaveis();
        inicializarAcoes();
    }

    private void inicializarVariaveis() {
        context = ConsultaPessoasActivity.this;

        pessoasDao = new PessoasDao(context);

        et_consulta = findViewById(R.id.et_consulta_pesquisa);
        lv_consulta = findViewById(R.id.lv_consulta_pessoas);
        btn_consulta_formulario = findViewById(R.id.btn_consulta_formulario);

        SimpleAdapter adapter = new SimpleAdapter(context, carregarLista(), R.layout.celula_03, De, Para);
        lv_consulta.setAdapter(adapter);

    }

    @SuppressLint("SourceLockedOrientationActivity")
    private void inicializarAcoes() {
        ajustarTela();

        et_consulta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                atualizarLista(context);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

        lv_consulta.setOnItemClickListener((parent, view, position, id) -> abrirItemPessoaUGB(parent, position));

        btn_consulta_formulario.setOnClickListener(v -> {
            abriFormularioPessoasActivity(context);
        });

    }

    private void abriFormularioPessoasActivity(Context context) {
        Intent getIntent = new Intent(context, FormularioPessoaActivity.class);
        getIntent.putExtra(PessoasDao.TIPO, "I");

        startActivity(getIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    private void abrirItemPessoaUGB(AdapterView<?> parent, int position) {
        HMAux retorno = (HMAux) parent.getItemAtPosition(position);
        String idConsulta = "";

        if (retorno != null) {
            idConsulta = String.valueOf(retorno.get(PessoasDao.ID));

            Intent getIntent = new Intent(context, FormularioPessoaActivity.class);

            getIntent.putExtra(PessoasDao.ID, idConsulta);
            getIntent.putExtra(PessoasDao.TIPO, "A");

            startActivity(getIntent);
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    private void atualizarLista(Context context) {
        adapter = new SimpleAdapter(context, carregarLista(), R.layout.celula_03, De, Para);
        lv_consulta.setAdapter(adapter);
    }

    private void ajustarTela() {
        getSupportActionBar().setElevation(0);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setTitle("Consulta Pessoas");

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
    }


    private ArrayList<HMAux> carregarLista() {
        ArrayList<HMAux> retorno = pessoasDao.obterListaPessoas();
        ArrayList<HMAux> pessoas = new ArrayList<>();

        String texto = et_consulta.getText().toString().toLowerCase();
        String nome = "";
        String telefone = "";


        for (int i = 0; i < retorno.size(); i++) {
            HMAux item = (HMAux) retorno.get(i);
            if (item != null) {

                nome = item.get(PessoasDao.NOME).toLowerCase();
                telefone = item.get(PessoasDao.TELEFONE);

                if ((nome.contains(texto)) || (telefone.contains(texto))) {
                    HMAux pessoa = new HMAux();

                    pessoa.put(PessoasDao.ID, item.get(PessoasDao.ID));
                    pessoa.put(PessoasDao.NOME, item.get(PessoasDao.NOME));
                    pessoa.put(PessoasDao.TELEFONE, item.get(PessoasDao.TELEFONE));

                    pessoas.add(pessoa);
                }
            }
        }

        return pessoas;
    }

}
