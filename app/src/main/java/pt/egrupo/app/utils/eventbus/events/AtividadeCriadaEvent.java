package pt.egrupo.app.utils.eventbus.events;

import java.util.ArrayList;

import pt.egrupo.app.models.Atividade;

/**
 * Created by ruie on 14/05/16.
 */
public class AtividadeCriadaEvent {

    ArrayList<Atividade> atividades;

    public AtividadeCriadaEvent(ArrayList<Atividade> atividades){
        this.atividades = atividades;
    }

    public ArrayList<Atividade> getAtividades() {
        return atividades;
    }
}
