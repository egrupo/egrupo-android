package pt.egrupo.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import pt.egrupo.app.R;

/**
 * Created by ruie on 28/02/16.
 */
public class Info extends RelativeLayout {

    TextView tvValue;
    TextView tvLabel;

    public Info(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.info, 0, 0);
        String label = a.getString(R.styleable.info_label);
        a.recycle();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v  = inflater.inflate(R.layout.row_info, this, true);

        tvLabel = (TextView)v.findViewById(R.id.tvLabel);
        tvValue = (TextView)v.findViewById(R.id.tvValue);

        tvLabel.setText(label);

    }

    public Info(Context context){
        super(context,null);
    }

    public void setLabel(String label){
        tvLabel.setText(label);
    }

    public void setValue(String value){
        tvValue.setText(value);
    }


}
