package edu.quinnipiac.currency;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ResultFragment extends Fragment {
    private String value;
    public ResultFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.result_fragment, container, false);
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view!=null){
            TextView returnVal = (TextView) view.findViewById(R.id.amount3);
            returnVal.setText(value);
        }

    }
}
