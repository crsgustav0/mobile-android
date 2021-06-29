package br.agr.mobile.mobileandroid.data;

public class ScriptDDL {

    //Pessoas
    public static String getCreateTablePessoas() {
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE IF NOT EXISTS [pessoas] (\n" +
                " [id] ID NOT NULL, \n" +
                " [nome] VARCHAR (60) NOT NULL, \n" +
                " [telefone] VARCHAR (15) NOT NULL, \n" +
                " [email] VARCHAR (40) NOT NULL, \n" +
                " CONSTRAINT [] PRIMARY KEY ([id]));");

        return sb.toString();
    }


}
