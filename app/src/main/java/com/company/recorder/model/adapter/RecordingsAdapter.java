package com.company.recorder.model.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.recorder.R;
import com.company.recorder.model.dto.MusicModel;
import com.company.recorder.model.interfaces.IMessages;
import com.company.recorder.model.utils.Messages;

import java.util.List;

public class RecordingsAdapter extends RecyclerView.Adapter<RecordingsAdapter.viewHolder> {

    private Context context;
    private List<MusicModel> audioArrayList;
    private OnItemClickListener onItemClickListener;
    private IMessages messages;

    public RecordingsAdapter(Context context, List<MusicModel> audioArrayList) {
        this.context = context;
        this.audioArrayList = audioArrayList;
        messages = new Messages(context);
    }

    @NonNull
    @Override
    public RecordingsAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recordings_list, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecordingsAdapter.viewHolder holder, final int i) {
        if (audioArrayList.get(i).getDate() != null) {
            holder.title.setText(audioArrayList.get(i).getTitle());
            holder.date.setText(audioArrayList.get(i).getDate());
            holder.duration.setText(audioArrayList.get(i).getDuration());
        } else {
            holder.title.setText(audioArrayList.get(i).getTitle());
            holder.date.setText(audioArrayList.get(i).getAlbum());
            holder.duration.setText(messages.calculateMin(Long.parseLong(audioArrayList.get(i).getDuration())));
        }
    }

    @Override
    public int getItemCount() {
        return audioArrayList.size();
    }



    class viewHolder extends RecyclerView.ViewHolder {
        TextView title, date, duration;

        viewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            duration = itemView.findViewById(R.id.duration);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition(), v);
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, View v);
    }
}