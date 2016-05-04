package pt.egrupo.app.models;

/**
 * Created by ruie on 04/05/16.
 */
public class ProvaEtapa {

    int id;
    int prova;
    int etapa;
    int divisao;
    String concluded_at;

    public ProvaEtapa(){

    }

    public ProvaEtapa(int prova,int etapa,int divisao){
        this.id = 0;
        this.prova = prova;
        this.etapa = etapa;
        this.divisao = divisao;
        this.concluded_at = null;
    }

    //proper cloning;
    public ProvaEtapa(ProvaEtapa p){
        this.id = p.getId();
        this.prova = p.getProva();
        this.etapa = p.getEtapa();
        this.divisao = p.getEtapa();
        this.concluded_at = p.getConcluded_at();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProva() {
        return prova;
    }

    public void setProva(int prova) {
        this.prova = prova;
    }

    public int getEtapa() {
        return etapa;
    }

    public void setEtapa(int etapa) {
        this.etapa = etapa;
    }

    public int getDivisao() {
        return divisao;
    }

    public void setDivisao(int divisao) {
        this.divisao = divisao;
    }

    public String getConcluded_at() {
        return concluded_at;
    }

    public void setConcluded_at(String concluded_at) {
        this.concluded_at = concluded_at;
    }

    public boolean isConcluded(){
        return concluded_at != null && !equals("");
    }
}
