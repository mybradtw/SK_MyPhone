package tw.brad.sk_myphoto;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private File sdroot, picfile;
    private ImageView img;
    private TextView scanresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,},
                    123);
        }else{
            init();
        }

    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull
            int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        init();

    }

    private void init(){
        sdroot = Environment.getExternalStorageDirectory();
        picfile = new File(sdroot, "brad.png");

        img = findViewById(R.id.img);
        scanresult = findViewById(R.id.scanresult);
    }

    public void test1(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Uri uri = Uri.fromFile(picfile);
        Uri photoUri = FileProvider.getUriForFile(this,
                getPackageName() + ".provider", picfile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent,2);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 2){
            Bitmap bmp = BitmapFactory.decodeFile(picfile.getAbsolutePath());
            img.setImageBitmap(bmp);
        }else if (requestCode == 1 && resultCode == RESULT_OK){
            String result = data.getStringExtra("code");
            scanresult.setText(result);
        }
    }

    public void test2(View view) {
        Intent intent = new Intent(this,
                ScanCodeActivity.class);
        //startActivity(intent);
        startActivityForResult(intent, 1);
    }
}
