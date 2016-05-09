package pt.egrupo.app.models;

import pt.egrupo.app.R;

/**
 * Created by ruie on 07/05/16.
 */
public class Presenca {

    int id;
    int atividade_id;
    int user_id;
    int tipo;
    Escoteiro escoteiro;

    public Presenca(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAtividade_id() {
        return atividade_id;
    }

    public void setAtividade_id(int atividade_id) {
        this.atividade_id = atividade_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getLabel(){
        if(tipo == 1)
            return "Presente";
        else
            return "Falta";
    }

    public int getColor(){
        if(tipo == 1){
            return R.color.presente;
        } else {
            return R.color.falta;
        }
    }

    public Escoteiro getEscoteiro() {
        return escoteiro;
    }

    public void setEscoteiro(Escoteiro escoteiro) {
        this.escoteiro = escoteiro;
    }
}
