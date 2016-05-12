package pt.egrupo.app.views.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;
import pt.egrupo.app.App;
import pt.egrupo.app.models.Progresso;
import pt.egrupo.app.models.ProvaEtapa;
import pt.egrupo.app.network.EgrupoResponse;
import pt.egrupo.app.utils.ELog;
import pt.egrupo.app.views.EscoteiroProfileActivity;
import retrofit2.Call;
import retrofit2.Callback;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    DateCallback mCallback;

    public void setParams(DateCallback callback){
        this.mCallback = callback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        mCallback.onDateSet(year+"-"+(month+1)+"-"+day);
    }

    interface DateCallback {
        void onDateSet(String date);
    }
}