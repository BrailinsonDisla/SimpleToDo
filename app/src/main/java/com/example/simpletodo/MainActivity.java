package com.example.simpletodo;

// imports required android libraries
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// imports the List and ArrayList libraries for structured data
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    // list definition for items
    List<String> items;

    // references for components in view
    Button addButton;
    EditText addText;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializes the list for items
        loadItems();

        // linking references to respective components in view
        addButton = findViewById(R.id.addButton);
        addText = findViewById(R.id.addText);
        rvItems = findViewById(R.id.rvItems);

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                // delete item from model
                items.remove(position);

                // notify adapter
                itemsAdapter.notifyItemRemoved(position);

                saveItems();
            }
        };

        // initialize adapter
        itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = addText.getText().toString();

                // add item to model
                if (item.length() != 0) {
                    items.add(item);

                    // notify adapter that a new item was inserted
                    itemsAdapter.notifyItemInserted(items.size() - 1);
                    addText.setText("");

                    saveItems();

                    Toast.makeText(getApplicationContext(), "Task Added", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    // load items by reading
    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MianActivity",  "Error reading items", e);

            items = new ArrayList<>();
        }

    }
    // save items
    private void saveItems() {
        try {
           FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MianActivity",  "Error writing items", e);
        }
    }
}