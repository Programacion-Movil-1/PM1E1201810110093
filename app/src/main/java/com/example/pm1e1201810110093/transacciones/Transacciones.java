package com.example.pm1e1201810110093.transacciones;

public class Transacciones {
    /* Tablas */
    public static final String tblContactosPersonas = "tblContactosPersonas";

    /* Campos */
    public static final String cp_ID = "cp_ID";
    public static final String cp_Pais = "cp_Pais";
    public static final String cp_Nombre = "cp_Nombre";
    public static final String cp_Telefono = "cp_Telefono";
    public static final String cp_Nota = "cp_Nota";

    /* Tablas - CREATE & DROP */
    public static final String CreateTableContactosPersonas = "CREATE TABLE tblContactosPersonas(" +
            "cp_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            "cp_Pais TEXT, " +
            "cp_Nombre TEXT, " +
            "cp_Telefono INTEGER," +
            "cp_Nota TEXT)";

    public static final String DropTableContactosPersonas = "DROP TABLE IF EXISTS tblContactosPersonas";

    /* Creacion del nombre de la base de datos */
    public static final String NameDataBase = "DBPM1E1";
}
