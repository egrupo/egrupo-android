package pt.egrupo.app.models;

/**
 * Created by ruie on 07/05/16.
 */
public class Presenca {

    int id;
    int atividade_id;
    int user_id;
    int tipo;

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
}
