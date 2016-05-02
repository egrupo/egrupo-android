package pt.egrupo.app.models;

/**
 * Created by ruie on 02/05/16.
 */
public class Token {

    String access_token;
    String token_type;
    int expires_in;
    String refres_token;

    public Token(){

    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefres_token() {
        return refres_token;
    }

    public void setRefres_token(String refres_token) {
        this.refres_token = refres_token;
    }
}
