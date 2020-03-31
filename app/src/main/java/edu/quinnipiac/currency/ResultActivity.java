package edu.quinnipiac.currency;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);



        value = getIntent().getStringExtra(MainActivity.key);

        ResultFragment frag = (ResultFragment) getSupportFragmentManager().findFragmentById(R.id.result_fragment);
        frag.setValue(value);
    }
}
