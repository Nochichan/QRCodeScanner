package com.etiqdor.qrcodescanner;

import java.util.ArrayList;

/**
 * Enum contenant tous les numéros de téléphones enregistrés de base dans l'application (la ils ont tous 0 mais si on maisun numéro ca marche)
 * @author etiqdor
 */
public enum TelephoneNum {
        GAYA("0", "Gaya"),
        MAXIME("0", "Maxime"),
        AXEL("0", "Axel"),
        DORYAN("0", "Doryan"),
        KARIM("0", "Karim"),
        LEO("0", "Léo"),
        SEVAN("0", "Sevan"),
        RYAN("0", "Ryan"),
        UBALDO("0", "Ubaldo"),
        FLORIAN("0", "Florian");

        String name;
        String num;

        /**
         * Constructeur de TelephoneNum
         * @param num Le numéro sous forme de chaîne de caractère
         * @param name Le nom du contact
         */
        TelephoneNum(String num, String name){
            this.num = num;
            this.name = name;
        }

        /**
         * @return Le numéro sous forme de chaîne de caractère
         */
        public String getNum() {
                return num;
        }

        /**
         * @return Le nom associé au numéro
         */
        public String getName(){
                return name;
        }

        /**
         * @return Une liste de tous les numéros de l'Enum
         */
        public static ArrayList<TelephoneNum> getAllNum(){
                ArrayList<TelephoneNum> list = new ArrayList<>();
                for(TelephoneNum num : TelephoneNum.values())
                        list.add(num);
                return list;
        }

        // Le numéro actuellement sélectionné
        public static TelephoneNum currentNum;
}