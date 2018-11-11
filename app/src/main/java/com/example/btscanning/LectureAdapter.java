package com.example.btscanning;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.LectureViewHolder>{

    private Context mCtx;
    private List<Lecture> lectureList;

    public LectureAdapter(Context mCtx, List<Lecture> lectureList) {
        this.mCtx = mCtx;
        this.lectureList = lectureList;
    }

    @NonNull
    @Override
    public LectureViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_listitem, null);
        return new LectureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LectureViewHolder lectureViewHolder, int i) {
        Lecture lecture = lectureList.get(i);

        lectureViewHolder.textView.setText(lecture.getClassName());

    }

    @Override
    public int getItemCount() {
        return lectureList.size();
    }

    class LectureViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public LectureViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.internet);
        }
    }
}
