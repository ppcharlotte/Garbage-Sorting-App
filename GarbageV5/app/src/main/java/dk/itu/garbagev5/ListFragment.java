package dk.itu.garbagev5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ListFragment extends Fragment {


    private static ItemsDB itemsDB;
    private TextView listThings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsDB= ItemsDB.get();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_list, container, false);
        listThings = v.findViewById(R.id.listItems);
        listThings.setText(" Sorting List" + itemsDB.listItems());
        return v;
    }
        // Inflate the layout for this fragment
}