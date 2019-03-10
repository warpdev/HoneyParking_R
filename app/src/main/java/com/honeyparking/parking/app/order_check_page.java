package com.honeyparking.parking.app;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class order_check_page extends AppCompatActivity {

    private WindowManager.LayoutParams params;
    private float brightness; // 밝기값은 float형으로 저장되어 있습니다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_check_page);
        generateRQCode("'sjb98'");
        params = getWindow().getAttributes();

    }
    @Override protected void onResume() {
        super.onResume();

        // 기존 밝기 저장
        brightness = params.screenBrightness;
        // 최대 밝기로 설정
        params.screenBrightness = 1f;
        // 밝기 설정 적용
        getWindow().setAttributes(params);
    }
    @Override protected void onPause() {
        super.onPause();

        // 기존 밝기로 변경
        params.screenBrightness = brightness;
        getWindow().setAttributes(params);
    }
    public void generateRQCode(String contents) {
        ImageView QR_form;
        QR_form=findViewById(R.id.code_form);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            Bitmap bitmap = toBitmap(qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, 700, 700));
            QR_form.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
    public static Bitmap toBitmap(BitMatrix matrix) {
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }

}
