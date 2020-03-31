package edu.quinnipiac.currency;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class MainFragment extends Fragment {
    public TextView currency1;
    public TextView currency2;
    public TextView amount;
    public Button convert;
    boolean userSelect = false;
    private String url1 = "https://currency-converter5.p.rapidapi.com/currency/historical/2018-02-09?format=json&to=";
    private String APIkey = "dc4cd93089mshadc84df6e4dac75p1980a2jsn4690236bb8a1";
    public String cur1;
    public String cur2;
    public String amt;
    private OnFragmentAListener mListener;
    public static final String key = "mykey";
    public static final int REQUEST_CODE = 0;

    public MainFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.main_fragment, container, false);
        currency1 = v.findViewById(R.id.currency);
        currency2 = v.findViewById(R.id.currency2);
        amount = v.findViewById(R.id.amount2);
        convert = v.findViewById(R.id.convert);
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cur1 = currency1.getText().toString();
                cur2 = currency2.getText().toString();
                amt = amount.getText().toString();
                Log.d(TAG, "onClick: works");
                String[] array = {cur1, cur2, amt};
                new getExchange().execute(array);
            }
        });
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentAListener) {
            mListener = (OnFragmentAListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement fragA");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentAListener {
        void setString(String s);
    }


    private class getExchange extends AsyncTask<String,Void,String>  {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mListener.setString(s);
        }

        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection urlConnection = null;
            String ExchangeAmount = null;
            BufferedReader reader = null;


            try {
                URL url = new URL(url1 + cur2 + "&from=" + cur1 + "&amount=" + amt);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("X-RapidAPI-Key",APIkey);
                urlConnection.connect();


                InputStream in = urlConnection.getInputStream();
                if (in==null) {
                    Log.d("nulltag","not working");
                    return null;
                }

                reader = new BufferedReader((new InputStreamReader(in)));
                ExchangeAmount = getStringFromBuffer(reader);


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection!=null) {
                    urlConnection.disconnect();
                }

            }
            return  ExchangeAmount;
        }


        public String getExchangeRate(String exchangeJsonStr) throws JSONException {
            String curto = currency2.getText().toString();
            JSONObject exchangeJSONObj = new JSONObject(exchangeJsonStr).getJSONObject("rates").getJSONObject(curto);
            String cur = exchangeJSONObj.getString("rate_for_amount");
            Log.d("given currency", cur);



            return cur;
        }

        private String getStringFromBuffer(BufferedReader bufferedReader) throws Exception {
            StringBuffer buffer = new StringBuffer();
            String line;

            while((line = bufferedReader.readLine()) != null){
                buffer.append(line + '\n');

            }
            if (bufferedReader !=null){
                try{
                    bufferedReader.close();
                }catch (IOException e){
                    Log.e("MainActivity","Error" + e.getMessage());
                    return null;
                }
            }
            return  getExchangeRate(buffer.toString());
        }
    }
}
