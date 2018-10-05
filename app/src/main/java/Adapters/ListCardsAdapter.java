package Adapters;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.org.magiclibary.magiclibary.R;

import Models.Deck;

public class ListCardsAdapter extends RecyclerView.Adapter<ListCardsAdapter.ItemCardAdapter> {

    private Deck deck;

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    @NonNull
    @Override
    public ItemCardAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemCardAdapter(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCardAdapter holder, int position) {
        String name = deck.cards.get(position).name;
        holder.name.setText(deck.cards.get(position).name);
        holder.description.setText(this.deck.cards.get(position).text);
        Uri uri = Uri.parse(this.deck.cards.get(position).imageUrl);
        holder.simpleDraweeView.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return deck != null ? deck.cards.size() : 0;
    }

   public class ItemCardAdapter extends RecyclerView.ViewHolder {

        public SimpleDraweeView simpleDraweeView;
        public TextView name;
        public TextView description;

        public ItemCardAdapter(@NonNull View itemView) {
            super(itemView);
            this.simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.my_image_view);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.description = (TextView) itemView.findViewById(R.id.description);
        }
    }
}
