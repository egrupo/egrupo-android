package pt.egrupo.app;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by rsantos on 25/02/16.
 */
public class EgrupoActivity extends AppCompatActivity {

    public AppBarLayout appBarLayout;
    public CoordinatorLayout mCoord;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public String getRandomColor(){
        String returnColor = getResources().getString(R.string.card_atividade_bg);
//        int returnColor = getResources().getColor(R.color.card_atividade_bg);

        int arrayId = getResources().getIdentifier("mdcolor_500", "array", getApplicationContext().getPackageName());

        if (arrayId != 0){
            TypedArray colors = getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getString(index);
        }

        return returnColor;
    }

    public static boolean useBlackText(String color){

        int intval = Color.parseColor(color);

        int r = (intval >> 16) & 0xFF;
        int g = (intval >> 8) & 0xFF;
        int b = (intval >> 0) & 0xFF;

        return ((r*299 + g*587 + b*114)/1000) < 125 ? false : true;
    }

}
