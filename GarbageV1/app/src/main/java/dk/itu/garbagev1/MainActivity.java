package dk.itu.garbagev1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText inputGarbage;
    private ItemsDB itemsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputGarbage = findViewById(R.id.input_garbage);
        Button Where = findViewById(R.id.where_button);

        itemsDB= new ItemsDB();

        Where.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = inputGarbage.getText().toString().trim();
                if (itemName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter an item!", Toast.LENGTH_SHORT).show();
                } else{
                    String category = itemsDB.findCategory(itemName);
                    inputGarbage.setText(category);
                }
            }
        });
    }
}