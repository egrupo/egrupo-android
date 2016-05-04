package pt.egrupo.app.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pt.egrupo.app.R;
import pt.egrupo.app.models.ProvaEtapa;
import pt.egrupo.app.utils.GridAutofitLayoutManager;
import pt.egrupo.app.utils.Utils;
import pt.egrupo.app.views.EscoteiroProfileActivity;

/**
 * Created by ruie on 04/05/16.
 */
public class AssinarProvaDialog extends DialogFragment {

    EscoteiroProfileActivity act;
    int etapa;

    RecyclerView rvProvas;

    AssinarProvasAdapter adapter;

    public AssinarProvaDialog(){

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        act = (EscoteiroProfileActivity)getActivity();
        etapa = getArguments().getInt("etapa");

        View v = LayoutInflater.from(act).inflate(R.layout.dialog_assinar_prova, null);

        /* Recyclerview */
        rvProvas = (RecyclerView)v.findViewById(R.id.rvProvas);
        GridLayoutManager layoutManager = new GridAutofitLayoutManager(getActivity(), Utils.convertDpToPixel(48,act));
        rvProvas.setLayoutManager(layoutManager);
        adapter = new AssinarProvasAdapter(act.getAdapterItems(etapa));
        rvProvas.setAdapter(adapter);

        builder.setView(v);
        builder.setTitle(etapa+"ª Etapa");
        builder.setPositiveButton("Assinar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        })
        .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // Create the AlertDialog object and return it
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
            vh.tvProva.setText(""+prova.getProva());

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
                ButterKnife.bind(this,v);
            }

            @OnClick(R.id.rlContainer)
            public void onProvaClick(View v){
                ProvaEtapa prova = (ProvaEtapa)v.getTag();
                Toast.makeText(act,"Cliquei na prova "+prova.getProva(),Toast.LENGTH_SHORT).show();
            }

        }

    }
}
