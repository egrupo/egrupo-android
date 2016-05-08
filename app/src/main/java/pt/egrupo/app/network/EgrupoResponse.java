package pt.egrupo.app.network;

/**
 * Created by ruie on 05/05/16.
 */
public class EgrupoResponse {

    int code;
    String message;

    public EgrupoResponse(){

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
