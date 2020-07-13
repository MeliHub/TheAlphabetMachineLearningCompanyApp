package www.bostom.co.thealphabetmachinelearningcompanyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class DisplayImage extends AppCompatActivity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        getSupportActionBar().setTitle("Display Image Screen");
        imageView = findViewById(R.id.imageview);

        Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra("image_path"));
        imageView.setImageBitmap(bitmap);
    }
}
