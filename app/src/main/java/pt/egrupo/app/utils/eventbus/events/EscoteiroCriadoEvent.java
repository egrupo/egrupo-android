package pt.egrupo.app.utils.eventbus.events;

import java.util.ArrayList;

import pt.egrupo.app.models.Atividade;
import pt.egrupo.app.models.Escoteiro;

/**
 * Created by ruie on 14/05/16.
 */
public class EscoteiroCriadoEvent {

    ArrayList<Escoteiro> escoteiros;

    public EscoteiroCriadoEvent(ArrayList<Escoteiro> escoteiros){
        this.escoteiros = escoteiros;
    }

    public ArrayList<Escoteiro> getEscoteiros() {
        return escoteiros;
    }
}
