package pt.egrupo.app.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rsantos on 25/02/16.
 */
public class Atividade implements Parcelable {

    int divisao;
    int id;
    String nome;
    String ano;
    int trimestre;
    String local;
    String duracao;
    String performed_at;
    String infos;
    String descricao;
    int noites_campo;
    String programa;

    public Atividade(){

    }

    public Atividade(Parcel in){
        divisao = in.readInt();
        id = in.readInt();
        nome = in.readString();
        ano = in.readString();
        trimestre = in.readInt();
        local = in.readString();
        duracao = in.readString();
        performed_at = in.readString();
        infos = in.readString();
        descricao = in.readString();
        noites_campo = in.readInt();
        programa = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(divisao);
        parcel.writeInt(id);
        parcel.writeString(nome);
        parcel.writeString(ano);
        parcel.writeInt(trimestre);
        parcel.writeString(local);
        parcel.writeString(duracao);
        parcel.writeString(performed_at);
        parcel.writeString(infos);
        parcel.writeString(descricao);
        parcel.writeInt(noites_campo);
        parcel.writeString(programa);
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

    public String getInformacoes() {
        return infos;
    }

    public void setInformacoes(String informacoes) {
        this.infos = informacoes;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getNoites() {
        return noites_campo;
    }

    public void setNoites(int noites) {
        this.noites_campo = noites;
    }

    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }

    public int getNoites_campo() {
        return noites_campo;
    }

    public void setNoites_campo(int noites_campo) {
        this.noites_campo = noites_campo;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public static final Creator<Atividade> CREATOR = new Creator<Atividade>() {
        public Atividade createFromParcel(Parcel source) {
            return new Atividade(source);
        }

        public Atividade[] newArray(int size) {
            return new Atividade[size];
        }
    };

    public String toString(){
        return getNome()+" - "+getDuracao()+" - "+getPerformed_at()+"\n"
                +getInformacoes()+"\n"
                +getDescricao()+"\n"
                +getPrograma();
    }
}
