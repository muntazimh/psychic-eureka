package com.example.task01;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button generatePDFbtn;
    int pageHeight = 1120;
    int pagewidth = 792;
    private PDFView pdfView;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generatePDFbtn = findViewById(R.id.idBtnGeneratePDF);
        pdfView = findViewById(R.id.pdfView);
        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }

        generatePDFbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePDFbtn.setVisibility(View.GONE);
               generatePDF();
            }
        });
    }

    private void generatePDF() {
        PdfDocument pdfDocument = new PdfDocument();
        Paint title = new Paint();
        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);
        Canvas canvas = myPage.getCanvas();
        title.setTextSize(19);
        canvas.drawText("हिन्दी", 50, 50, title);
        canvas.drawText("मनुस्य इस समाज मेंरहनेवाला सामाजि क प्राणी है। समाज की प्रगति का दायि त्व मनष्य के", 50, 100, title);
        canvas.drawText("व्यवहार पर नि र्भरर्भ करता है। आज अराजकता के कारण ईर्ष्या और दर्भार्भावना की सोच है। इस", 50, 125, title);
        canvas.drawText("कारण कछ इंसान स्वभि मान और इंसानि यत को भलू गए हैं। अराजकता के कई प्रकार हैं, जसै-", 50, 150, title);
        canvas.drawText("भ्रष्टाचार, शोषण, अत्याचार, बेईमान, डकैती चोरी, हत्या रि श्वतखोरी इत्यादि अपराधि क काम", 50, 175, title);
        canvas.drawText("हमारे समाज को खोखला कर रही है। असामाजि क तत्वों की शीघ्र पहचान करके उनके खि लाफ", 50, 200, title);
        canvas.drawText("क़ानूनी कार्रवाई की जानी चाहि ए। टेलीवि जन, इंटरनेट, सोशल मीडि या और अखबारों क", 50, 225, title);
        canvas.drawText("माध्यम सेजागरूकता पदै करनी चाहि ए। अराजक बनकर समाज दषिूषित करनेवालेके मन म", 50, 250, title);
        canvas.drawText("डर तभी होगा जागरूकता और काननू के मोर्चे पर सरकार को सशक्त कदम उठाना चाहि ए।", 50, 275, title);
        canvas.drawText("तब हम एक उन्नत समाज की स्थापना कर पाएंगे।", 50, 300, title);

        canvas.drawText("English", 50, 400, title);
        canvas.drawText("We all know that health is wealth. With its intricate network of bones, muscles,", 50, 450, title);
        canvas.drawText("and organs, a well-functioning human body is much like an orchestrated", 50, 475, title);
        canvas.drawText("symphony. To keep this orchestra playing well, we need physical exercise. It", 50, 500, title);
        canvas.drawText("may take the form of sports, yoga, or even regular walking. It is well-known that", 50, 525, title);
        canvas.drawText("people who engage in physical exercise stay happier and live longer.", 50, 550, title);

        pdfDocument.finishPage(myPage);
        File file = new File(Environment.getExternalStorageDirectory(), "Task01.pdf");

        try {

            pdfDocument.writeTo(new FileOutputStream(file));
            pdfView.fromAsset("Task01.pdf")
                    .load();
            Toast.makeText(MainActivity.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfDocument.close();
    }

    private boolean checkPermission() {
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}