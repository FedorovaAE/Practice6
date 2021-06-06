package ru.mirea.fedorova.notebook;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText etFileName;
    private EditText etText;
    private SharedPreferences preferences;
    final String SAVED = "saved_filepath";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private String fileName;
    private String text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etFileName = findViewById(R.id.editText);
        etText = findViewById(R.id.editText2);
        preferences = getPreferences(MODE_PRIVATE);

        new Thread(new Runnable() {
            public void run() {
                etText.post(new Runnable() {
                    public void run() {
                        etFileName.setText(preferences.getString(SAVED, "Myfile"));
                        etText.setText(getTextFromFile());
                    }
                });
            }
        }).start();
    }

    public void onSaveText(View view) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SAVED, etFileName.getText().toString());
        editor.apply();
        fileName = preferences.getString(SAVED, "Myfile");
        text = etText.getText().toString();
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(text.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show();
    }

    public void onLoadText(View view) {
        new Thread(new Runnable() {
            public void run() {
                etText.post(new Runnable() {
                    public void run() {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(SAVED, etFileName.getText().toString());
                        editor.apply();
                        etFileName.setText(preferences.getString(SAVED, "Myfile"));
                        etText.setText(getTextFromFile());
                    }
                });
            }
        }).start();
    }

    public String getTextFromFile() {
        fileName = preferences.getString(SAVED, "Myfile");
        FileInputStream fin = null;
        try {
            fin = openFileInput(fileName);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            Log.d(LOG_TAG, text);
            return text;
        } catch (IOException ex) {
            //Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Пустой файл", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }
}