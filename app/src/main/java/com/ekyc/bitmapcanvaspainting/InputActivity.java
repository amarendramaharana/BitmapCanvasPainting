package com.ekyc.bitmapcanvaspainting;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.Instrumentation;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class InputActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> imageLauncher;
private AppCompatEditText edName,edDepartment,edDob,edBloodGP,edNumber,edGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        viewInitialize();
        findViewById(R.id.btnGenerateIdCard).setOnClickListener(view->{
            generateIdCard();
        });
        imageLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
             if (result.getResultCode() ==RESULT_OK){
                Intent data=  result.getData();
                Uri uri=data.getData();
                 ContentResolver contentResolver = getContentResolver();
                 InputStream inputStream = null;
                 try {
                     inputStream = contentResolver.openInputStream(uri);
                 } catch (FileNotFoundException e) {
                     throw new RuntimeException(e);
                 }
                 Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                 if (result.getData()!=null){
                     Intent intent=new Intent(InputActivity.this,MainActivity.class);
                     intent.putExtra("DEPARTMENT",edDepartment.getText().toString());
                     intent.putExtra("NAME",edName.getText().toString());
                     intent.putExtra("GENDER",edGender.getText().toString());
                     intent.putExtra("DOB",edDob.getText().toString());
                     intent.putExtra("NUMBER",edNumber.getText().toString());
                     intent.putExtra("BLOOD_GROUP",edBloodGP.getText().toString());

                     // Convert Bitmap to ByteArray
                     ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
                     bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                     byte[] bytes=outputStream.toByteArray();
                    intent.putExtra("IMAGE",bytes);
                     startActivity(intent);
                 }
             }
            }
        });
    }

    private void viewInitialize() {
        edName=findViewById(R.id.edName);
        edDepartment=findViewById(R.id.edDepartment);
        edDob=findViewById(R.id.edDob);
        edBloodGP=findViewById(R.id.edBloodGroup);
        edNumber=findViewById(R.id.edNo);
        edBloodGP=findViewById(R.id.edBloodGroup);
        edGender=findViewById(R.id.edGender);
    }

    private void generateIdCard() {

        if (TextUtils.isEmpty(edDepartment.getText())){
            showToast("Department");
        }
        else if (TextUtils.isEmpty(edName.getText())){
            showToast("Name");
        } else if (TextUtils.isEmpty(edGender.getText())){
            showToast("Gender");
        } else if (TextUtils.isEmpty(edDob.getText())){
            showToast("Date of Birth");
        } else if (TextUtils.isEmpty(edNumber.getText())){
            showToast("Phone Number");
        }
        else if (TextUtils.isEmpty(edBloodGP.getText())){
            showToast("Blood Group");
        }else {
            pickImage();

        }
    }

    private void pickImage() {
        Intent intent=new Intent(MediaStore.ACTION_PICK_IMAGES);
        imageLauncher.launch(intent);
    }

    private void showToast(String msg) {
        Toast.makeText(this,"Please enter "+msg,Toast.LENGTH_SHORT).show();
    }
}