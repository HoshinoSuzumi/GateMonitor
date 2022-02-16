package moe.ibox.gatemonitor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.ViewHolder> {

    private final ArrayList<QuaChannelData> quaChannelData;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewItemTemperature, textViewItemHumidity, textViewItemCo2, textViewItemNoise, textViewItemTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewItemTemperature = itemView.findViewById(R.id.textView_item_temperature);
            textViewItemHumidity = itemView.findViewById(R.id.textView_item_humidity);
            textViewItemCo2 = itemView.findViewById(R.id.textView_item_co2);
            textViewItemNoise = itemView.findViewById(R.id.textView_item_noise);
            textViewItemTime = itemView.findViewById(R.id.textView_item_time);
        }

        public TextView getTextViewItemTemperature() {
            return textViewItemTemperature;
        }

        public TextView getTextViewItemHumidity() {
            return textViewItemHumidity;
        }

        public TextView getTextViewItemCo2() {
            return textViewItemCo2;
        }

        public TextView getTextViewItemNoise() {
            return textViewItemNoise;
        }

        public TextView getTextViewItemTime() {
            return textViewItemTime;
        }
    }

    public RecordsAdapter(ArrayList<QuaChannelData> quaChannelData) {
        this.quaChannelData = quaChannelData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.records_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (this.getItemCount() > 0) {
            holder.getTextViewItemTemperature().setText(String.valueOf(quaChannelData.get(position).getTemperature()));
            holder.getTextViewItemHumidity().setText(String.valueOf(quaChannelData.get(position).getHumidity()));
            holder.getTextViewItemCo2().setText(String.valueOf(quaChannelData.get(position).getCo2()));
            holder.getTextViewItemNoise().setText(String.valueOf(quaChannelData.get(position).getNoise()));
            holder.getTextViewItemTime().setText(String.valueOf(quaChannelData.get(position).getTime()));
        }
    }

    @Override
    public int getItemCount() {
        return quaChannelData.size();
    }
}
