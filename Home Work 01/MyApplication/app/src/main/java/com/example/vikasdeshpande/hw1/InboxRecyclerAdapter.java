package com.example.vikasdeshpande.hw1;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Vikas Deshpande on 9/17/2017.
 */

public class InboxRecyclerAdapter extends RecyclerView.Adapter<InboxRecyclerAdapter.AdapterHolder>
{
    private List<InboxAttributes> listData;
    private LayoutInflater inflater;
    private Context mContext;
    private ItemClickCallback itemClickCallback;

    public void setListData(List<InboxAttributes> appslist) {
        this.listData.clear();
        this.listData.addAll(appslist);
    }
    public interface ItemClickCallback {
        void OnReadClick(int p);
    }
    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public InboxRecyclerAdapter(List<InboxAttributes> listData, Context c) {
        inflater = LayoutInflater.from(c);
        this.listData = listData;
        mContext = c;
        this.itemClickCallback = (InboxActivity)c;
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

        private TextView tvSenderName;
        private TextView tvTime;
        private TextView tvDate;
        private TextView tvSummary;
        private ImageView ivLockFlag;
        private ImageView ivReadFlag;
        private View container;

        public AdapterHolder(View itemView) {
            super(itemView);

            tvSenderName = (TextView) itemView.findViewById(R.id.tvSenderName);
            tvTime = (TextView) itemView.findViewById(R.id.tvTimeSent);
            tvDate = (TextView) itemView.findViewById(R.id.tvDateSent);
            tvSummary = (TextView) itemView.findViewById(R.id.tvMsgSummary);
            ivLockFlag = (ImageView) itemView.findViewById(R.id.ivLockFlag);
            ivReadFlag = (ImageView) itemView.findViewById(R.id.ivReadFlag);
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {

            {
                itemClickCallback.OnReadClick(getAdapterPosition());
            }
        }
    }

    @Override
    public void onBindViewHolder(AdapterHolder holder, final int position)
    {

        InboxAttributes inboxAttributes = listData.get(position);
        holder.tvSenderName.setText(inboxAttributes.getSenderFullName());
        holder.tvDate.setText(inboxAttributes.getDateSent()+",");
        holder.tvTime.setText(inboxAttributes.getTimeSent());
        holder.tvSummary.setText(inboxAttributes.getMsgSummary());


        if(inboxAttributes.isLocked())
        {
            holder.ivLockFlag.setImageResource(R.drawable.lock);
            holder.itemView.setClickable(false);


        }else{
            holder.ivLockFlag.setImageResource(R.drawable.lock_open);
            holder.itemView.setClickable(true);
        }

        if(inboxAttributes.isRead())
        {
            holder.ivReadFlag.setImageResource(R.drawable.circle_grey);
        }else{
            holder.ivReadFlag.setImageResource(R.drawable.circle_blue);
        }

    }
}
