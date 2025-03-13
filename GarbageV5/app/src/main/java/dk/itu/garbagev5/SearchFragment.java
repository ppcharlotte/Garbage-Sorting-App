package dk.itu.garbagev5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class SearchFragment extends Fragment {

    private static ItemsDB itemsDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsDB = ItemsDB.get();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_search, container, false);

        //Text Fields
        TextView itemWhat = v.findViewById(R.id.input_garbage);

        Button Where = v.findViewById(R.id.where_button);


        Where.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = itemWhat.getText().toString().trim();
                if (itemName.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter an item!", Toast.LENGTH_SHORT).show();
                } else{
                    String category = itemsDB.findCategory(itemName);
                    itemWhat.setText(category);
                }
                itemWhat.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });
        return v;
    }

}