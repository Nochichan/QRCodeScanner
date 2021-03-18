package com.etiqdor.qrcodescanner;

/**
 * Enum contenant tous les numéros de téléphones enregistré de base dans l'application
 */
public enum TelephoneNum {
        GAYA("0793285016"),
        MAXIME("0754122198"),
        AXEL("0788283858"),
        DORYAN("0795069747"),
        KARIM("0798953480"),
        LEO("0796218180"),
        SEVAN("0783485341"),
        RYAN("0767464322"),
        UBALDO("0792683638"),
        FLORIAN("0799500057");

        String num;

        /**
         * Constructeur de TelephoneNum
         * @param num Le numéro sous forme de chaîne de caractère
         */
        TelephoneNum(String num){
            this.num = num;
        }

        /**
         * @return Le numéro sous forme de chaîne de caractère
         */
        public String getNum() {
                return num;
        }

        public static TelephoneNum currentNum;
}


