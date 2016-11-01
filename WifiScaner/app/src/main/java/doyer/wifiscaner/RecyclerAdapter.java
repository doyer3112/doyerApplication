package doyer.wifiscaner;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jie.du on 16/11/1.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<String> scanStrings;

    public RecyclerAdapter(ArrayList scanStrings) {
        this.scanStrings = scanStrings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wifi_result_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String wifiresult = scanStrings.get(position);
        holder.bindWifi(wifiresult, position);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (scanStrings != null) {
            count = scanStrings.size();
        }
        return count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView wifiItem;

        public ViewHolder(View itemView) {
            super(itemView);
            wifiItem = (TextView) itemView.findViewById(R.id.wifi_item);
            itemView.setOnClickListener(this);
        }

        public void bindWifi(final String text, int position) {
            wifiItem.setText(text);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            String scanString = scanStrings.get(position);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(scanString.toString()).append("  POI" + position);
            Snackbar.make(v, stringBuilder, Snackbar.LENGTH_LONG)
                    .show();
        }
    }

}
