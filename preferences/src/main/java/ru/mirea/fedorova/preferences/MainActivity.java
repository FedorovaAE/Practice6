package ru.mirea.fedorova.preferences;


import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private EditText editText2;
    private TextView textView;
    private SharedPreferences preferences;
    final String SAVED_TEXT = "saved_text";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        textView = findViewById(R.id.textViewData);
        preferences = getPreferences(MODE_PRIVATE);
    }

    public void onSaveText(View view) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SAVED_TEXT, editText.getText().toString() + "\n" +
                editText2.getText().toString());
        editor.apply();
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
    }

    public void onLoadText(View view) {
        String text = preferences.getString(SAVED_TEXT, "Empty");
        textView.setText(text);
    }
}