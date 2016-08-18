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

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.DrawerVH> {

    private List<NavigationEntry> data = Collections.emptyList();

    public NavigationDrawerAdapter(@NonNull List<NavigationEntry> entries) {
        data = entries;
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
            case LABEL:
                holder.title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                holder.chevron.setVisibility(View.INVISIBLE);
                break;
            case NODE:
                holder.title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                holder.chevron.setVisibility(View.VISIBLE);
                break;
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
}
