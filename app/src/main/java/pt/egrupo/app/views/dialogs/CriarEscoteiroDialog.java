package pt.egrupo.app.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.egrupo.app.App;
import pt.egrupo.app.R;
import pt.egrupo.app.models.Atividade;
import pt.egrupo.app.models.Escoteiro;
import pt.egrupo.app.utils.eventbus.RxBus;
import pt.egrupo.app.utils.eventbus.events.AtividadeCriadaEvent;
import pt.egrupo.app.utils.eventbus.events.EscoteiroCriadoEvent;
import pt.egrupo.app.views.HomeActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ruie on 12/05/16.
 */
public class CriarEscoteiroDialog extends DialogFragment {

    HomeActivity act;

    EditText etNome;
    EditText etIdAssociativo;

    @Override
    public void onStart(){
        super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null){
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //validar input

                    if("".equals(etIdAssociativo.getText().toString())){
                        Toast.makeText(act, "Tens de inserir um número associativo", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if("".equals(etNome.getText().toString())){
                        Toast.makeText(act, "Tens de inserir um nome", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    createEscoteiro();
                    dismiss();
                }
            });
        }
    }

    public void createEscoteiro(){
        HashMap<String,String> mParams = new HashMap<>();
        mParams.put("nome",etNome.getText().toString());
        mParams.put("id_associativo",etIdAssociativo.getText().toString());
        mParams.put("divisao", ""+App.getDivisao());

        act.app.api.createEscoteiro(mParams).enqueue(new Callback<List<Escoteiro>>() {
            @Override
            public void onResponse(Call<List<Escoteiro>> call, Response<List<Escoteiro>> response) {
                ArrayList<Escoteiro> temp = new ArrayList<>();
                temp.addAll(response.body());
                RxBus.instanceOf().send(new EscoteiroCriadoEvent(temp));
            }

            @Override
            public void onFailure(Call<List<Escoteiro>> call, Throwable t) {

            }
        });
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        act = (HomeActivity)getActivity();

        View v = LayoutInflater.from(act).inflate(R.layout.dialog_criar_escoteiro, null);

        etIdAssociativo = (EditText)v.findViewById(R.id.etIdAssociativo);
        etNome = (EditText)v.findViewById(R.id.etName);

        builder.setView(v);
        builder.setTitle("Criar Escoteiro");
        builder.setPositiveButton("Criar",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                //assinar stuffs
            }
        })
        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        return builder.create();
    }

}
