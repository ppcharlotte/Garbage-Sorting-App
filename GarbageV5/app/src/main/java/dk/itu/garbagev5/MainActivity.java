package dk.itu.garbagev5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static ItemsDB itemsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garbage);

        itemsDB = ItemsDB.get();
        setUpFragments();
    }

    private void setUpFragments() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragmentSearch= fm.findFragmentById(R.id.container_search);
        Fragment fragmentList= fm.findFragmentById(R.id.container_list);
        if ((fragmentSearch == null) && (fragmentList == null)) {
            fragmentSearch= new SearchFragment();
            fragmentList= new ListFragment();
            fm.beginTransaction()
                    .add(R.id.container_search, fragmentSearch)
                    .add(R.id.container_list, fragmentList)
                    .commit();
        }
    }
}