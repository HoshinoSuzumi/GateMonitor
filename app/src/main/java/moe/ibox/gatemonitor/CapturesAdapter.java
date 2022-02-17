package moe.ibox.gatemonitor;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CapturesAdapter extends RecyclerView.Adapter<CapturesAdapter.ViewHolder> {

    private final ArrayList<Bitmap> bitmapArrayList;

    public CapturesAdapter(ArrayList<Bitmap> bitmapArrayList) {
        this.bitmapArrayList = bitmapArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.captures_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (this.getItemCount() > 0) {
            holder.getImageView_capture().setImageBitmap(this.bitmapArrayList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return bitmapArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView_capture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_capture = itemView.findViewById(R.id.imageView_capture);
        }

        public ImageView getImageView_capture() {
            return imageView_capture;
        }
    }
}
