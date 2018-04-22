package pt.unl.novaims.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Activity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1);
    }

    public void convert(View view){

        final TextView result_tv= (TextView)findViewById(R.id.result_tv);
        EditText number_txt= (EditText)findViewById(R.id.number_txt);

        RadioGroup radioGroupCur= (RadioGroup)findViewById(R.id.radioGroupCur);

        String sInput = number_txt.getText().toString();
        final double number = Double.parseDouble(sInput);
        double rate=0, result=0;

        int selectedId = radioGroupCur.getCheckedRadioButtonId();
        RadioButton radioButton= (RadioButton) findViewById(selectedId);
        final String coin = radioButton.getText().toString();

        String url ="http://free.currencyconverterapi.com/api/v3/convert?q="+coin+"_EUR&compact=ultra";

        RequestQueue queue= Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String srate= response.getString(coin + "_EUR");

                            double rate = Double.parseDouble(srate);
                            double result = number * rate;

                            String sResult= String.format("%.2f €", result);
                            result_tv.setText(sResult);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            result_tv.setText("Json error!");
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        result_tv.setText("That didn't work!");

                    }
                });

        queue.add(jsonObjectRequest);



    }

    public void convert_old(View view){

        TextView result_tv= (TextView)findViewById(R.id.result_tv);
        EditText number_txt= (EditText)findViewById(R.id.number_txt);
        RadioButton dollar_rb= (RadioButton)findViewById(R.id.dollar_rb);
        RadioButton pound_rb= (RadioButton)findViewById(R.id.pound_rb);
        RadioButton yen_rb= (RadioButton)findViewById(R.id.yen_rb);

        String sInput = number_txt.getText().toString();
        double number = Double.parseDouble(sInput);
        double rate=0, result=0;

        if (dollar_rb.isChecked())
            rate= 0.82;
        else if (pound_rb.isChecked())
            rate = 1.12;
        else if (yen_rb.isChecked())
            rate= 0.0077;

        result= rate * number;

        String sResult= String.format("%.2f €", result);

        result_tv.setText(sResult);

    }



}
