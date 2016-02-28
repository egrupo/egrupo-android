package pt.egrupo.app.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rsantos on 25/02/16.
 */
public class Escoteiro implements Parcelable {

    @SerializedName("id")int id;
    @SerializedName("id_associativo")int id_associativo;
    @SerializedName("nome")String nome;
    @SerializedName("divisao")int divisao;
    @SerializedName("patrulha")String patrulha;
    @SerializedName("nome_completo")String nome_completo;
    @SerializedName("totem")String totem;
    @SerializedName("cargo")String cargo;

    public Escoteiro(){

    }

    private Escoteiro(Parcel in){
        this.id = in.readInt();
        this.id_associativo = in.readInt();
        this.nome = in.readString();
        this.divisao = in.readInt();
        this.nome_completo = in.readString();
        this.totem = in.readString();
        this.cargo = in.readString();
        this.patrulha = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_associativo() {
        return id_associativo;
    }

    public void setId_associativo(int id_associativo) {
        this.id_associativo = id_associativo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDivisao() {
        return divisao;
    }

    public void setDivisao(int divisao) {
        this.divisao = divisao;
    }

    public String getNome_completo() {
        return nome_completo;
    }

    public void setNome_completo(String nome_completo) {
        this.nome_completo = nome_completo;
    }

    public String getTotem() {
        return totem;
    }

    public void setTotem(String totem) {
        this.totem = totem;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getPatrulha() {
        return patrulha;
    }

    public void setPatrulha(String patrulha) {
        this.patrulha = patrulha;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.id_associativo);
        dest.writeString(this.nome);
        dest.writeInt(this.divisao);
        dest.writeString(this.nome_completo);
        dest.writeString(this.totem);
        dest.writeString(this.cargo);
        dest.writeString(this.patrulha);
    }

    public static final Creator<Escoteiro> CREATOR = new Creator<Escoteiro>() {
        public Escoteiro createFromParcel(Parcel source) {
            return new Escoteiro(source);
        }

        public Escoteiro[] newArray(int size) {
            return new Escoteiro[size];
        }
    };
}
