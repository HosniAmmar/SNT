package com.img.snt.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.img.snt.Adapter.ItemAdapter;
import com.img.snt.ArticleView;
import com.img.snt.DAO.AppDatabase;
import com.img.snt.MainActivity;
import com.img.snt.Model.Item;
import com.img.snt.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private RecyclerView recyclerView;
    public static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    int index;

    List<Item> items;
    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
         index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);



    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        items = new ArrayList<Item>();
        if(index==10){
            items=AppDatabase.getAppDatabase(getContext()).itemDAO().getAll();
        }else{
            items=AppDatabase.getAppDatabase(getContext()).itemDAO().loadAllByLanguage(MainActivity.language);

        }
        recyclerView = root.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ItemAdapter(items,getContext(),new ItemAdapter.OnItemClickListener() {
            @Override public void onItemClick(Item item) {
                Toast.makeText(getContext(), "Item Clicked"+item.id, Toast.LENGTH_LONG).show();
                Intent newIntent = new Intent(getContext(), ArticleView.class);
                Bundle bundle =new Bundle();
                bundle.putString("id",item.id);
                newIntent.putExtras(bundle);

                startActivityForResult(newIntent,0);
            }
        });
        recyclerView.setAdapter(mAdapter);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }

}