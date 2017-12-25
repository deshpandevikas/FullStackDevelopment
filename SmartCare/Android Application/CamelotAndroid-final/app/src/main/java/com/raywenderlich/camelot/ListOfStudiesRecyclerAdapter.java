package com.raywenderlich.camelot;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Vikas Deshpande on 12/10/2017.
 */

public class ListOfStudiesRecyclerAdapter extends RecyclerView.Adapter<ListOfStudiesRecyclerAdapter.AdapterHolder>
{
    private List<StudiesAttributes> listData;
    private LayoutInflater inflater;
    private Context mContext;
    private ItemClickCallback itemClickCallback;

    public void setListData(List<StudiesAttributes> appslist) {
        this.listData.clear();
        this.listData.addAll(appslist);
    }
    public interface ItemClickCallback {
        void OnReadClick(int p);
    }
    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public ListOfStudiesRecyclerAdapter(List<StudiesAttributes> listData, Context c) {
        inflater = LayoutInflater.from(c);
        this.listData = listData;
        mContext = c;
        this.itemClickCallback = (StudyList)c;
    }

    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_saved, parent, false);
        return new AdapterHolder(view);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class AdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView studyName;
        private TextView studyDescription;
        private View container;
       // private final View.OnClickListener mOnClickListener = new MyOnClickListener();

        public AdapterHolder(View itemView) {
            super(itemView);

            studyName = (TextView) itemView.findViewById(R.id.tvStudyName);
            studyDescription = (TextView) itemView.findViewById(R.id.tvStudyDescription);
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            {
                itemClickCallback.OnReadClick(getAdapterPosition());
                Log.d("maincheck",getAdapterPosition()+"");
            }
        }
    }

    @Override
    public void onBindViewHolder(AdapterHolder holder, final int position)
    {
        StudiesAttributes studiesAttributes = listData.get(position);
        holder.studyName.setText(studiesAttributes.getStudyName());
        holder.studyDescription.setText(studiesAttributes.getStudyDesc());

        //itemClickCallback.OnReadClick(studiesAttributes.getSid());
        Log.d("thisismain",studiesAttributes.getSid()+"");
    }
}
