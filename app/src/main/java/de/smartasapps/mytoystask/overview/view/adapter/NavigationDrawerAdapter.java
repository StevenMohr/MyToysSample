package de.smartasapps.mytoystask.overview.view.adapter;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.smartasapps.mytoystask.R;
import de.smartasapps.mytoystask.network.NavigationEntry;
import de.smartasapps.mytoystask.network.NavigationEntryType;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.DrawerVH> {

    private List<NavigationEntry> data = Collections.emptyList();
    private final ItemClickListener listener;

    public NavigationDrawerAdapter(@NonNull List<NavigationEntry> entries, ItemClickListener listener) {
        data = entries;
        this.listener = listener;
    }

    @Override
    public DrawerVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_drawer_item, parent, false);
        return new DrawerVH(view);
    }

    @Override
    public void onBindViewHolder(DrawerVH holder, int position) {
        final NavigationEntry navigationEntry = data.get(position);
        holder.title.setText(navigationEntry.label);
        switch (navigationEntry.type) {
            case SECTION:
                holder.title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                holder.chevron.setVisibility(View.INVISIBLE);
                break;
            case LINK:
                holder.title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                holder.chevron.setVisibility(View.INVISIBLE);
                break;
            case NODE:
                holder.title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                holder.chevron.setVisibility(View.VISIBLE);
                break;
        }
        if (!NavigationEntryType.SECTION.equals(navigationEntry.type)) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.itemClicked(navigationEntry);
                }
            });
        } else {
            holder.itemView.setOnClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class DrawerVH extends RecyclerView.ViewHolder {
        @BindView(R.id.titleView)
        TextView title;

        @BindView(R.id.nodeIndicator)
        ImageView chevron;

        DrawerVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ItemClickListener {
        void itemClicked(NavigationEntry entry);
    }
}
