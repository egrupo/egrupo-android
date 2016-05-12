package pt.egrupo.app.models;

/**
 * Created by ruie on 27/02/16.
 */
public class Divisao {

    public static final int ALCATEIA = 1;
    public static final int TES = 2;
    public static final int TEX = 3;
    public static final int CLA = 4;
    public static final int CHEFIA = 5;
    public static final int GRUPO = 10;

    public static String getLabel(int divisao){
        switch (divisao){
            case ALCATEIA:
                return "Alcateia";
            case TES:
                return "TEs";
            case TEX:
                return "TEx";
            case CLA:
                return "Clã";
            case CHEFIA:
                return "Chefia";
            case GRUPO:
                return "Grupo";
        }
        return "";
    };

    public static String getExtendedLabel(int divisao){
        switch (divisao){
            case ALCATEIA:
                return "Alcateia";
            case TES:
                return "Tribo de Escoteiros";
            case TEX:
                return "Tribo de Exploradores";
            case CLA:
                return "Clã";
            case CHEFIA:
                return "Chefia";
        }
        return "";
    }

}
