package pt.egrupo.app.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruie on 04/05/16.
 */
public class Progresso implements Cloneable {

    int divisao;
    int etapa;
    int total;
    List<ProvaEtapa> provas;

    public Progresso(){

    }

    //proper cloning
    public Progresso(Progresso p){
        divisao = p.getDivisao();
        etapa = p.getEtapa();
        total = p.getTotal();
        provas = new ArrayList<>();
        for(ProvaEtapa prova : p.getProvas()){
            provas.add(new ProvaEtapa(prova));
        }
    }

    public int getDivisao() {
        return divisao;
    }

    public void setDivisao(int divisao) {
        this.divisao = divisao;
    }

    public int getEtapa() {
        return etapa;
    }

    public void setEtapa(int etapa) {
        this.etapa = etapa;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ProvaEtapa> getProvas() {
        return provas;
    }

    public void setProvas(List<ProvaEtapa> provas) {
        this.provas = provas;
    }

    public boolean temProva(int prova){
        for(int i = 0 ; i < provas.size() ; i++){
            if(provas.get(i).getProva() == prova){
                return true;
            }
        }

        return false;
    }

}
