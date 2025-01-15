package com.ekyc.bitmapcanvaspainting;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView idImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idImg = findViewById(R.id.idCardImage);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String department = bundle.getString("DEPARTMENT");
            String name = bundle.getString("NAME");
            String gender = bundle.getString("GENDER");
            String dob = bundle.getString("DOB");
            String number = bundle.getString("NUMBER");
            String blood = bundle.getString("BLOOD_GROUP");
            byte[] image = bundle.getByteArray("IMAGE");
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

            showIdCard(department, name, gender, dob, number, blood, imageBitmap);
        }

    }

    private void showIdCard(String department, String name, String gender, String dob, String number, String blood, Bitmap imageBitmap) {
        // Load the template image
        Bitmap templateBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_1);

        // Create a mutable bitmap from the template
        Bitmap mutableBitmap = templateBitmap.copy(Bitmap.Config.ARGB_8888, true);

        // Create a canvas to draw on the mutable bitmap
        Canvas canvas = new Canvas(mutableBitmap);

        // Set up paint for text
        Paint textPaint = new Paint();
        textPaint.setColor(Color.GRAY);
        textPaint.setTextSize(65);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        canvas.drawText(name, 340, 1172, textPaint);
        canvas.drawText(department, 740, 960, textPaint);
        canvas.drawText(gender, 380, 1275, textPaint);
        canvas.drawText(dob, 320, 1375, textPaint);
        canvas.drawText(number, 360, 1480, textPaint);
        canvas.drawText(blood, 540, 1582, textPaint);

        Bitmap resizedPhoto = Bitmap.createScaledBitmap(imageBitmap, 345, 395, true);

        canvas.drawBitmap(resizedPhoto, 440, 385, textPaint);
        idImg.setImageBitmap(mutableBitmap);
    }
}