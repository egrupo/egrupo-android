package pt.egrupo.app.models;

/**
 * Created by ruie on 27/02/16.
 */
public class User {

    String name;
    String user;
    String email;
    int id_associativo;
    String organization_slug;
    int divisao;

    public User(){

    }

    public int getDivisao() {
        return divisao;
    }

    public void setDivisao(int divisao) {
        this.divisao = divisao;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId_associativo() {
        return id_associativo;
    }

    public void setId_associativo(int id_associativo) {
        this.id_associativo = id_associativo;
    }

    public String getOrganization_slug() {
        return organization_slug;
    }

    public void setOrganization_slug(String organization_slug) {
        this.organization_slug = organization_slug;
    }
}
