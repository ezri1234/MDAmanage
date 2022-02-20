package com.ezrimo.mdamanage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class Assign extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    EditText name;
    TextView date;
    Button btEnter;
    Date finalDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign);
        name = findViewById(R.id.etName);
        date = findViewById(R.id.date);
        btEnter = findViewById(R.id.btEnter);

        Intent get = getIntent();
        String thisUid = get.getStringExtra("uid");

        findViewById(R.id.btCalander).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(finalDate.equals(null))
                    date.setError("enter date");
                Event myEvent = new Event(finalDate, thisUid);
            }
        });



    }
    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.DATE),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String chosenDate = " "+i+"/"+i1+"/"+i2;
        date.setText(chosenDate);
        finalDate = new Date(i, i1, i2);
        Toast.makeText(this,  finalDate.toString(), Toast.LENGTH_SHORT).show();
    }
}