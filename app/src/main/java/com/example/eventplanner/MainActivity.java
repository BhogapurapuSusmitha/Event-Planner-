package com.example.eventplanner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private ImageButton btnSelectedImage;
    private Button btnSelctedDate,btnSubmit;
    private TextView tvSelectedDate;
    private RadioGroup radioGroupElement;
    private CheckBox checkCatering,checkPhotography,checkLiveMusic;
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSelectedImage=findViewById(R.id.btnSelectImage);
        btnSelctedDate=findViewById(R.id.btnSelectDate);
        btnSubmit=findViewById(R.id.btnSubmit);
        tvSelectedDate=findViewById(R.id.tvSelectedDate);
        radioGroupElement=findViewById(R.id.radioGroupEvent);
        checkCatering=findViewById(R.id.cbCatering);
        checkLiveMusic=findViewById(R.id.cbLiveMusic);
        checkPhotography=findViewById(R.id.cbPhotography);
        btnSelectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChoose();
            }
        });
        btnSelctedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processForm();
            }
        });
    }
    private void openImageChoose(){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null){
            imageUri=data.getData();
            try{
                Bitmap bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                btnSelectedImage.setImageBitmap(bitmap);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private void showDatePicker(){
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(this,(view,selectedYear,selectedMonth,selectedDay)->{
            tvSelectedDate.setText("Date:"+selectedDay+"/"+(selectedMonth+1)+"/"+selectedYear);
        },year,month,day).show();

    }
    private void processForm(){
        int selectedEventId=radioGroupElement.getCheckedRadioButtonId();
        String eventType=selectedEventId != -1 ?((RadioButton)findViewById(selectedEventId)).getText().toString():"Not Selected";
        String service="";
        if(checkCatering.isChecked()) service+=" Catering";
        if(checkLiveMusic.isChecked()) service+=" Live Music";
        if(checkPhotography.isChecked()) service+=" Photography";
        Toast.makeText(this,"Event:"+eventType+"\n"+"Services:"+service,Toast.LENGTH_LONG).show();
    }


}