package www.bostom.co.thealphabetmachinelearningcompanyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private int picPermissionCode=0;
    String currentImagePath = null;
    private static  final  int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    getSupportActionBar().setTitle("Capture Image Screen");

    }

    private void requestCameraPermission()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
        {
            new AlertDialog.Builder(this).setTitle("PERMISSION NEEDED").setMessage("Permission is needed because it has not been granted").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int intNumber) {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},picPermissionCode);

                }
            }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int intNumber) {
                    dialog.dismiss();
                }
            }).create().show();
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA}, picPermissionCode);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == picPermissionCode)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void CaptureImage(View view) {

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(MainActivity.this, "You have already been granted Camera Permission", Toast.LENGTH_SHORT).show();

            Intent imageTake = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (imageTake.resolveActivity(getPackageManager()) != null)
            {
                File imageFile = null;
                try{
                    imageFile = getImageFile();

                }
                catch(IOException e) {
                    e.printStackTrace();
                }
                if(imageFile!=null)
                {
                    Uri imageUri = FileProvider.getUriForFile(this, "com.example.android.fileprovider", imageFile);
                    imageTake.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(imageTake, REQUEST_IMAGE_CAPTURE);
                }
            }


        }
            else {
                requestCameraPermission();
                }

        }

    public void DisplayPicture(View view) {
        Intent intent = new Intent(this, DisplayImage.class);
        intent.putExtra("image_path", currentImagePath);
        startActivity(intent);
    }

    private File getImageFile()throws IOException
    {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "jpg_"+timestamp+"_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile = File.createTempFile(imageName, ".jpg",storageDir);
        currentImagePath = imageFile.getAbsolutePath();
        return imageFile;
    }

}





