package moe.ibox.gatemonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class CapturesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captures);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_captures);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
        String fileDir = getExternalFilesDir("capture").toString();
        File filesAll = new File(fileDir);
        File[] files = filesAll.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.getName().endsWith(".png")) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                bitmapArrayList.add(bitmap);
            }
        }

        recyclerView.setAdapter(new CapturesAdapter(bitmapArrayList));
    }
}