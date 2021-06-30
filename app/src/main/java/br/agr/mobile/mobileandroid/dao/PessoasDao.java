package br.agr.mobile.mobileandroid.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import br.agr.mobile.mobileandroid.data.Dao;
import br.agr.mobile.mobileandroid.model.Pessoas;
import br.agr.mobile.mobileandroid.util.HMAux;


public class PessoasDao extends Dao {

    public static final String TABELA = "pessoas";

    public static final String ID = "id";
    public static final String NOME = "nome";
    public static final String TELEFONE = "telefone";
    public static final String EMAIL = "email";

    public static final String TIPO = "tipo";

    public PessoasDao(Context context) {
        super(context);
    }

    public void inserirPessoa(ArrayList<Pessoas> usuarios) {

        abrirBanco();

        db.beginTransaction();

        try {

            //db.delete(TABELA, null, null);

            ContentValues cv = new ContentValues();

            for (Pessoas pAux : usuarios) {
                cv.put(PessoasDao.ID, pAux.getId());
                cv.put(PessoasDao.NOME, pAux.getNome());
                cv.put(PessoasDao.TELEFONE, pAux.getTelefone());
                cv.put(PessoasDao.EMAIL, pAux.getEmail());

                db.insert(TABELA, null, cv);
            }

            db.setTransactionSuccessful();

        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }

        fecharBanco();
    }

    public int proximoID() {

        int proId = -1;

        abrirBanco();

        Cursor cursor = null;

        try {
            String sqlQuery = " select ifnull(max(id)+1,1) as id from pessoas; ";

            cursor = db.rawQuery(sqlQuery, null);

            while (cursor.moveToNext()) {
                proId = cursor.getInt(cursor.getColumnIndex("id"));
            }

            cursor.close();

        } catch (Exception e) {
        }

        fecharBanco();

        return proId;
    }

    public ArrayList<HMAux> obterListaPessoas() {

        ArrayList<HMAux> pessoas = new ArrayList<>();

        abrirBanco();

        try {

            StringBuilder sb = new StringBuilder();

            sb.append("select id, nome, telefone, email from " + TABELA);
            sb.append(" order by nome ");

            Cursor cursor = db.rawQuery(sb.toString(), null);

            while (cursor.moveToNext()) {
                HMAux pAux = new HMAux();

                pAux.put(PessoasDao.ID, cursor.getString(cursor.getColumnIndex(PessoasDao.ID)));
                pAux.put(PessoasDao.NOME, cursor.getString(cursor.getColumnIndex(PessoasDao.NOME)));
                pAux.put(PessoasDao.TELEFONE, cursor.getString(cursor.getColumnIndex(PessoasDao.TELEFONE)));
                pAux.put(PessoasDao.EMAIL, cursor.getString(cursor.getColumnIndex(PessoasDao.EMAIL)));

                pessoas.add(pAux);
            }

            cursor.close();

        } catch (Exception e) {

        }

        fecharBanco();

        return pessoas;
    }

    public Pessoas obterPessoaByID(int id) {

        Pessoas cAux = null;

        abrirBanco();

        Cursor cursor = null;

        try {

            String[] argumentos = {String.valueOf(id)};

            StringBuilder sb = new StringBuilder();
            sb.append(" select id, nome, telefone, email from " + TABELA);
            sb.append(" where id = ? ");

            cursor = db.rawQuery(sb.toString(), argumentos);

            while (cursor.moveToNext()) {
                cAux = new Pessoas();

                cAux.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PessoasDao.ID))));
                cAux.setNome(cursor.getString(cursor.getColumnIndex(PessoasDao.NOME)));
                cAux.setTelefone(cursor.getString(cursor.getColumnIndex(PessoasDao.TELEFONE)));
                cAux.setEmail(cursor.getString(cursor.getColumnIndex(PessoasDao.EMAIL)));
            }

            cursor.close();

        } catch (Exception e) {

        }

        fecharBanco();

        return cAux;
    }

    public void alterarPessoa(Pessoas pessoas) {

        abrirBanco();

        ContentValues cv = new ContentValues();

        String filtro = " id = ? ";
        String[] argumentos = {String.valueOf(pessoas.getId())};

        try {
            cv.put(PessoasDao.NOME, pessoas.getNome());
            cv.put(PessoasDao.TELEFONE, pessoas.getTelefone());
            cv.put(PessoasDao.EMAIL, pessoas.getEmail());

            db.update(TABELA, cv, filtro, argumentos);
        } catch (Exception e) {
            String.valueOf(e);
        }

        fecharBanco();
    }

}
