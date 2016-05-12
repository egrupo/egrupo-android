package pt.egrupo.app.views.dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pt.egrupo.app.R;
import pt.egrupo.app.models.Progresso;
import pt.egrupo.app.models.ProvaEtapa;
import pt.egrupo.app.utils.ELog;
import pt.egrupo.app.utils.GridAutofitLayoutManager;
import pt.egrupo.app.utils.Utils;
import pt.egrupo.app.views.EscoteiroProfileActivity;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by ruie on 04/05/16.
 */
public class AssinarProvaDialog extends DialogFragment {

    EscoteiroProfileActivity act;
    int etapa;

    RecyclerView rvProvas;

    AssinarProvasAdapter adapter;

    ArrayList<ProvaEtapa> desafiosParaAssinar;
    ArrayList<ProvaEtapa> desafiosParaRemover;

    public AssinarProvaDialog(){

    }

    @Override
    public void onStart(){
        super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null){
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (desafiosParaAssinar.size() == 0 && desafiosParaRemover.size() == 0) {
                        AssinarProvaDialog.this.dismiss();
                    } else if (desafiosParaAssinar.size() == 0) {
                        dismiss();
                        postProgresso(null);
                    } else {
                        DatePickerFragment f = new DatePickerFragment();
                        f.setParams(new DatePickerFragment.DateCallback() {
                            @Override
                            public void onDateSet(String date) {
                                postProgresso(date);
                                dismiss();
                            }
                        });

                        f.show(act.getSupportFragmentManager(), "dialog_data");
                    }

                }
            });
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        desafiosParaAssinar = new ArrayList<>();
        desafiosParaRemover = new ArrayList<>();

        act = (EscoteiroProfileActivity)getActivity();
        etapa = getArguments().getInt("etapa");

        View v = LayoutInflater.from(act).inflate(R.layout.dialog_assinar_prova, null);

        /* Recyclerview */
        rvProvas = (RecyclerView)v.findViewById(R.id.rvProvas);
        GridLayoutManager layoutManager = new GridAutofitLayoutManager(getActivity(), Utils.convertDpToPixel(48,act));
        rvProvas.setLayoutManager(layoutManager);
        adapter = new AssinarProvasAdapter(act.getAdapterItems(etapa));
        rvProvas.setAdapter(adapter);
        rvProvas.setOverScrollMode(View.OVER_SCROLL_NEVER);

        builder.setView(v);
        builder.setTitle(etapa + "ª Etapa");
        builder.setPositiveButton("Gravar",new DialogInterface.OnClickListener() {
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

    class AssinarProvasAdapter extends RecyclerView.Adapter<AssinarProvasAdapter.AssinarProvasViewHolder> {

        ArrayList<ProvaEtapa> mItems;

        public AssinarProvasAdapter(ArrayList<ProvaEtapa> items){
            this.mItems = items;
        }

        @Override
        public AssinarProvasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_prova, parent, false);
            return new AssinarProvasViewHolder(v);
        }

        @Override
        public void onBindViewHolder(AssinarProvasViewHolder vh, int position) {
            ProvaEtapa prova = mItems.get(position);
            vh.tvProva.setText("" + prova.getProva());

            if(prova.isConcluded()){
                vh.rlColor.setBackgroundColor(getResources().getColor(R.color.prova_done));
            } else {
                vh.rlColor.setBackgroundColor(getResources().getColor(R.color.prova_undone));
            }

            vh.rlContainer.setTag(prova);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public class AssinarProvasViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.rlContainer)RelativeLayout rlContainer;
            @Bind(R.id.rlColor) RelativeLayout rlColor;
            @Bind(R.id.tvProva)TextView tvProva;

            public AssinarProvasViewHolder(View v){
                super(v);
                ButterKnife.bind(this, v);
            }

            @OnClick(R.id.rlContainer)
            public void onProvaClick(View v){
                ProvaEtapa prova = (ProvaEtapa)v.getTag();

                if(prova.isConcluded()){

                    if(desafiosParaRemover.contains(prova)){
                        v.findViewById(R.id.rlColor).setBackgroundResource(R.color.prova_done);
                        desafiosParaRemover.remove(prova);
                    } else {
                        desafiosParaRemover.add(prova);
                        v.findViewById(R.id.rlColor).setBackgroundResource(R.color.prova_undone);
                    }

                } else {

                    if(desafiosParaAssinar.contains(prova)){
                        desafiosParaAssinar.remove(prova);
                        v.findViewById(R.id.rlColor).setBackgroundResource(R.color.prova_undone);
                    } else {
                        desafiosParaAssinar.add(prova);
                        v.findViewById(R.id.rlColor).setBackgroundResource(R.color.prova_done);
                    }
                }
            }
        }
    }

    public void postProgresso(String date){
        HashMap<String,String> mParams = new HashMap<>();
        if(desafiosParaAssinar.size() > 0) {
            String provas = "";
            for (int i = 0; i < desafiosParaAssinar.size(); i++) {
                provas += "" + desafiosParaAssinar.get(i).getProva() + ",";
            }
            provas = provas.substring(0, provas.length() - 1);
            mParams.put("desafios", provas);
            mParams.put("etapa",""+desafiosParaAssinar.get(0).getEtapa());
            mParams.put("divisao",""+desafiosParaAssinar.get(0).getDivisao());
        }

        if(desafiosParaRemover.size() > 0) {
            ELog.d("Assinar",desafiosParaRemover.get(0).toString());

            String provasRemover = "";
            for (int j = 0; j < desafiosParaRemover.size(); j++) {
                provasRemover += "" + desafiosParaRemover.get(j).getProva() + ",";
            }
            provasRemover = provasRemover.substring(0, provasRemover.length() - 1);
            mParams.put("desafios_remover", provasRemover);

            if(desafiosParaAssinar.size() == 0){
                ELog.d("Assinar prova","Tou a por etapa: "+desafiosParaRemover.get(0).getEtapa()+" e divisao: "+desafiosParaRemover.get(0).getDivisao());
                mParams.put("etapa",""+desafiosParaRemover.get(0).getEtapa());
                mParams.put("divisao",""+desafiosParaRemover.get(0).getDivisao());
            }
        }

        if(date != null)
            mParams.put("concluded_at",date);

        act.app.api.postProgresso(act.e.getId(),mParams).enqueue(new Callback<List<Progresso>>() {
            @Override
            public void onResponse(Call<List<Progresso>> call, retrofit2.Response<List<Progresso>> response) {
                act.p = new ArrayList<>();
                act.p.addAll(response.body());
                act.e.setProgresso(act.p);
                act.createArrayListItems();

                Toast.makeText(act,"Desafios assinados.",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Progresso>> call, Throwable t) {
                Toast.makeText(act,"Ocorreu um erro.",Toast.LENGTH_SHORT).show();
            }
        });
    }



}
