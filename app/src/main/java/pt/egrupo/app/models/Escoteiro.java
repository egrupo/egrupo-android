package pt.egrupo.app.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import pt.egrupo.app.App;

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
    @SerializedName("nome_ee_1")String nome_ee_1;
    @SerializedName("nome_ee_2")String nome_ee_2;
    @SerializedName("email_ee_1")String email_ee_1;
    @SerializedName("email_ee_2")String email_ee_2;
    @SerializedName("telem_ee_1")String telem_ee_1;
    @SerializedName("telem_ee_2")String telem_ee_2;
    @SerializedName("nivel_escotista")String nivel_escotista;
    @SerializedName("email")String email;
    @SerializedName("telemovel")String telemovel;
    @SerializedName("bi")String bi;
    @SerializedName("morada")String morada;
    @SerializedName("autoriza_imagem")int autoriza_imagem;
    @SerializedName("ficha_inscricao")int ficha_inscricao;
    @SerializedName("descricao")String descricao;
    @SerializedName("notas")String notas;
    List<Progresso> progresso;

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
        this.nome_ee_1 = in.readString();
        this.nome_ee_2 = in.readString();
        this.email_ee_1 = in.readString();
        this.email_ee_2 = in.readString();
        this.telem_ee_1 = in.readString();
        this.telem_ee_2 = in.readString();
        this.nivel_escotista = in.readString();
        this.email = in.readString();
        this.telemovel = in.readString();
        this.bi = in.readString();
        this.morada = in.readString();
        this.autoriza_imagem = in.readInt();
        this.ficha_inscricao = in.readInt();
        this.descricao = in.readString();
        this.notas = in.readString();
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

    public String getNome_ee_1() {
        return nome_ee_1;
    }

    public void setNome_ee_1(String nome_ee_1) {
        this.nome_ee_1 = nome_ee_1;
    }

    public String getNome_ee_2() {
        return nome_ee_2;
    }

    public void setNome_ee_2(String nome_ee_2) {
        this.nome_ee_2 = nome_ee_2;
    }

    public String getEmail_ee_1() {
        return email_ee_1;
    }

    public void setEmail_ee_1(String email_ee_1) {
        this.email_ee_1 = email_ee_1;
    }

    public String getEmail_ee_2() {
        return email_ee_2;
    }

    public void setEmail_ee_2(String email_ee_2) {
        this.email_ee_2 = email_ee_2;
    }

    public String getTelem_ee_1() {
        return telem_ee_1;
    }

    public void setTelem_ee_1(String telem_ee_1) {
        this.telem_ee_1 = telem_ee_1;
    }

    public String getTelem_ee_2() {
        return telem_ee_2;
    }

    public void setTelem_ee_2(String telem_ee_2) {
        this.telem_ee_2 = telem_ee_2;
    }

    public String getNivel_escotista() {
        return nivel_escotista;
    }

    public void setNivel_escotista(String nivel_escotista) {
        this.nivel_escotista = nivel_escotista;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelemovel() {
        return telemovel;
    }

    public void setTelemovel(String telemovel) {
        this.telemovel = telemovel;
    }

    public String getBi() {
        return bi;
    }

    public void setBi(String bi) {
        this.bi = bi;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public int getAutoriza_imagem() {
        return autoriza_imagem;
    }

    public void setAutoriza_imagem(int autoriza_imagem) {
        this.autoriza_imagem = autoriza_imagem;
    }

    public int getFicha_inscricao() {
        return ficha_inscricao;
    }

    public void setFicha_inscricao(int ficha_inscricao) {
        this.ficha_inscricao = ficha_inscricao;
    }

    public List<Progresso> getProgresso() {
        return progresso;
    }

    public void setProgresso(List<Progresso> progresso) {
        this.progresso = progresso;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getAvatarUrl(){
        return "http://api." + App.getOrganizationSlug()+".egrupo.pt/v1.0/avatar/"+id+"?w=100&h=100&access_token="+App.getAccessToken();
    }

    public String getBigAvatarUrl(){
        return "http://api." + App.getOrganizationSlug()+".egrupo.pt/v1.0/avatar/"+id+"?access_token="+App.getAccessToken();
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
        dest.writeString(this.nome_ee_1);
        dest.writeString(this.nome_ee_2);
        dest.writeString(this.email_ee_1);
        dest.writeString(this.email_ee_2);
        dest.writeString(this.telem_ee_1);
        dest.writeString(this.telem_ee_2);
        dest.writeString(this.nivel_escotista);
        dest.writeString(this.email);
        dest.writeString(this.telemovel);
        dest.writeString(this.bi);
        dest.writeString(this.morada);
        dest.writeInt(this.autoriza_imagem);
        dest.writeInt(this.ficha_inscricao);
        dest.writeString(this.descricao);
        dest.writeString(this.notas);
    }

    public static final Creator<Escoteiro> CREATOR = new Creator<Escoteiro>() {
        public Escoteiro createFromParcel(Parcel source) {
            return new Escoteiro(source);
        }

        public Escoteiro[] newArray(int size) {
            return new Escoteiro[size];
        }
    };

    public boolean temProva(int divisao,int etapa,int prova){
        if(progresso == null)
            return false;

        return true;
    }
}
