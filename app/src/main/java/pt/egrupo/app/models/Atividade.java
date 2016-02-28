package pt.egrupo.app.models;

/**
 * Created by rsantos on 25/02/16.
 */
public class Atividade {

    int divisao;
    int id;
    String nome;
    String ano;
    int trimestre;
    String local;
    String duracao;
    String performed_at;

    public Atividade(){

    }

    public int getDivisao() {
        return divisao;
    }

    public void setDivisao(int divisao) {
        this.divisao = divisao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public int getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getPerformed_at() {
        return performed_at;
    }

    public void setPerformed_at(String performed_at) {
        this.performed_at = performed_at;
    }
}
