package com.org.magiclibary.magiclibary;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Adapters.ListSetsAdapter;
import Interfaces.OnGetItemAdapter;
import Models.Inventory;
import Models.Set;
import Services.MagicGathering.MagicGatheringService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaSets extends AppCompatActivity  implements OnGetItemAdapter<Set> {
    Map<String, String> filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_sets);
        this.eventsToolBar();
        Fresco.initialize(this);
        this.filter = new HashMap<>();
        this.getSets();

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                filter.clear();
                getSets();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cards_menu, menu);
        return true;
    }

    private void eventsToolBar() {
        Toolbar toolbar = findViewById(R.id.action_bar);
        toolbar.setTitle(R.string.sets_list);
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListaSets.this);
                View view = getLayoutInflater().inflate(R.layout.activity_dialog_filter_sets, null);
                final Button btn = view.findViewById(R.id.button);
                final EditText name = view.findViewById(R.id.content_name);
                final EditText code = view.findViewById(R.id.content_code);

                if (!filter.isEmpty()) {
                    name.setText(!filter.get("name").isEmpty() ? filter.get("name") : "");
                    code.setText(!filter.get("code").isEmpty() ? filter.get("code") : "");
                }



                builder.setView(view);
                final AlertDialog alertDialog = builder.create();

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (name.getText().toString() != "") {
                            filter.put("name", name.getText().toString());
                        }

                        if (code.getText().toString() != "") {
                            filter.put("code", code.getText().toString());
                        }
//                        if (pagesize.getText().toString() != "") {
//                            filter.put("pageSize", pagesize.getText().toString());
//                        }
                        final ProgressBar progressBar = findViewById(R.id.progressbar);
                        progressBar.setElevation(100);
                        progressBar.setVisibility(View.VISIBLE);
                        getSets();
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
                return true;
            }
        });
    }

    private void getSets() {
        MagicGatheringService magicGatheringService = new MagicGatheringService();
        final ProgressBar progressBar = findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);
        progressBar.setElevation(999);

        Callback<Inventory> inventoryCallback = new Callback<Inventory>() {
            @Override
            public void onResponse(Call<Inventory> call, Response<Inventory> response) {
                if (response.isSuccessful()) {
                    Inventory inventory = response.body();
                    Gson gson = new Gson();
                    TextView setstnotFound = findViewById(R.id.setsnotfound);

                    if (inventory.sets.isEmpty()) {
                        setstnotFound.setVisibility(View.VISIBLE);
                    } else {
                        setstnotFound.setVisibility(View.GONE);
                    }

                    RecyclerView recyclerView = findViewById(R.id.recly);
                    ListSetsAdapter listSetsAdapter = new ListSetsAdapter(ListaSets.this);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ListaSets.this, LinearLayoutManager.VERTICAL, false);
                    listSetsAdapter.setInventory(inventory);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(listSetsAdapter);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Inventory> call, Throwable t) {
                Toast.makeText(ListaSets.this, getString(R.string.failedtoconnect), Toast.LENGTH_LONG).show();
            }
        };
        magicGatheringService.getSets(filter, inventoryCallback);
    }

    @Override
    public void getItem(Set item) {

    }
}
