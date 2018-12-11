package tw.brad.sk_myphoto;

import android.content.Intent;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

public class ScanCodeActivity extends AppCompatActivity
        implements QRCodeReaderView.OnQRCodeReadListener {
    private QRCodeReaderView scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);

        scanner = findViewById(R.id.scanner);
        scanner.setOnQRCodeReadListener(this);
        scanner.setQRDecodingEnabled(true);
        scanner.setBackCamera();
        scanner.setAutofocusInterval(2000L);
    }

    @Override
    protected void onStart() {
        super.onStart();
        scanner.startCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        scanner.stopCamera();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        Log.v("brad", text);
        Intent intent = new Intent();
        intent.putExtra("code", text);
        setResult(RESULT_OK, intent);
        finish();
    }
}
