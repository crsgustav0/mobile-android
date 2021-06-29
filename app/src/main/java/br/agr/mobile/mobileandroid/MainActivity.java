package br.agr.mobile.mobileandroid;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarVariaveis();
        inicializarAcoes();
    }

    private void inicializarVariaveis() {
        context = MainActivity.this;
    }

    private void inicializarAcoes() {
    }
}