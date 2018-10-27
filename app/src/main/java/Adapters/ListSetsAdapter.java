package Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.org.magiclibary.magiclibary.R;

import Interfaces.OnGetItemAdapter;
import Models.Inventory;
import Models.Set;

public class ListSetsAdapter extends RecyclerView.Adapter<ListSetsAdapter.ItemSetAdapter> {

    private OnGetItemAdapter onGetItemAdapter;

    public ListSetsAdapter(OnGetItemAdapter onGetItemAdapter) {
        this.onGetItemAdapter = onGetItemAdapter;
    }

    private Inventory inventory;

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @NonNull
    @Override
    public ItemSetAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemSetAdapter(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_set, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemSetAdapter holder, int position) {
        Set set = inventory.sets.get(position);

        holder.name.setText(set.name);
        holder.code.setText(set.code);
        holder.content_release.setText(set.releaseDate);
        holder.mkm_name.setText(set.mkm_name);
        holder.type.setText(set.type);
        holder.border.setText(set.border);

        if (set.mkm_id != null)
            holder.mkm_id.setText(set.mkm_id.toString());
    }

    @Override
    public int getItemCount() {
        return inventory != null ? inventory.sets.size() : 0;
    }


    public class ItemSetAdapter extends RecyclerView.ViewHolder {

        public TextView content_release;
        public TextView name;
        public TextView mkm_name;
        public TextView mkm_id;
        public TextView border;
        public TextView type;
        public TextView code;

        public ItemSetAdapter(@NonNull View itemView) {
            super(itemView);
            this.content_release = (TextView) itemView.findViewById(R.id.content_release);
            this.mkm_id = (TextView) itemView.findViewById(R.id.content_mkm_id);
            this.code = (TextView) itemView.findViewById(R.id.content_code);
            this.name = (TextView) itemView.findViewById(R.id.content_name);
            this.mkm_name = (TextView) itemView.findViewById(R.id.content_mkm_name);
            this.type = (TextView) itemView.findViewById(R.id.content_type);
            this.border = (TextView) itemView.findViewById(R.id.content_border);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Set set = inventory.sets.get(position);
                    onGetItemAdapter.getItem(set);
                }
            });
        }
    }
}
