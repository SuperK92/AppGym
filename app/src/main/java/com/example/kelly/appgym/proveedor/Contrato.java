package com.example.kelly.appgym.proveedor;

import android.net.Uri;
import android.provider.BaseColumns;

public class Contrato {

    public static final String AUTHORITY = "com.example.kelly.appgym.proveedor.ProveedorDeContenido";

    public static final class Musculo implements BaseColumns {

        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Musculo");

        // Table column
        public static final String NOMBRE = "Nombre";

    }

    public static final class Ejercicio implements BaseColumns {

        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Ejercicio");

        // Table column
        public static final String NOMBRE = "Nombre";
        public static final String ID_MUSCULO = "Id_Musculo";
    }

    public static final class Actividad implements BaseColumns {

        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Actividad");

        // Table column
        public static final String ID_EJERCICIO = "Id_Ejercicio";
        public static final String SERIES = "Series";
        public static final String REPETICIONES = "Repeticiones";
    }

}
