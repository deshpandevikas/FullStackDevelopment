package com.raywenderlich.camelot;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Vikas Deshpande on 11/16/2017.
 */

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.AdapterHolder>
{
    private List<Questions> listData;
    private LayoutInflater inflater;
    private Context mContext;
    private ItemClickCallback itemClickCallback;

    public void setListData(List<Questions> appslist) {
        //this.listData.clear();
        this.listData.addAll(appslist);
    }
    public interface ItemClickCallback {
        void OnReadClick(int qid,int itemid,int optionid);
        void onSurveyClick(int pos);
    }
    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public QuestionsAdapter(List<Questions> listData, Context c) {
        inflater = LayoutInflater.from(c);
        this.listData = listData;
        mContext = c;
        this.itemClickCallback = (QuesAndSurvey)c;
    }

    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_layout, parent, false);
        return new AdapterHolder(view);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class AdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvQuestion;
        private TextView tvqid;
        private TextView tvtimeid;
        private TextView tvSurvey;
        private TextView tvOptions;
        private TextView tvdtime;
        private RadioGroup rg;
        private View container;
        private Button surveyBtn;
        private CircleImageView imageView;

        public AdapterHolder(View itemView) {
            super(itemView);

            tvQuestion = (TextView) itemView.findViewById(R.id.tvQuestion);
            tvqid = (TextView) itemView.findViewById(R.id.tvqid);
            tvtimeid = (TextView) itemView.findViewById(R.id.tvtimeid);
            tvOptions = (TextView) itemView.findViewById(R.id.tvoptions);
            rg = (RadioGroup) itemView.findViewById(R.id.rg);
            tvdtime = (TextView) itemView.findViewById(R.id.tvdtime);
            tvSurvey = (TextView) itemView.findViewById(R.id.tvSurvey);
            surveyBtn = (Button) itemView.findViewById(R.id.surveybtn);
            imageView = (CircleImageView) itemView.findViewById(R.id.profile_image);
            container = (LinearLayout) itemView.findViewById(R.id.customLayoutLinear);


            surveyBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {



            itemClickCallback.onSurveyClick(getAdapterPosition());
        }
    }

    @Override
    public void onBindViewHolder(AdapterHolder holder, final int position)
    {
        final Questions question = listData.get(position);

        Date dtime = question.getDtime();
        PrettyTime ptime = new PrettyTime();
        String datetime= ptime.format(dtime);
        holder.tvdtime.setText(datetime);


        String datatype = question.getDatatype();

        if(question.getResponse().equals("yes"))
        {
            holder.container.setBackgroundColor(Color.rgb(184,190,216));
        }

        if(datatype.equals("question"))
        {
            holder.surveyBtn.setVisibility(View.GONE);
            holder.tvQuestion.setText(question.getQuestion());
            holder.tvqid.setText(question.getId()+"");
            holder.tvtimeid.setText(question.getTimeId()+"");
            holder.imageView.setImageResource(R.mipmap.ques);

            HashMap<Integer,String> omap = question.getOptions();
            if(question.getResponse().equals("yes"))
            {
                String option="";
                for(int key : omap.keySet())
                {
                    option = omap.get(key);
                }

                holder.rg.setVisibility(View.GONE);
                holder.tvOptions.setText("You have answered "+option);
            }
            else if(question.getResponse().equals("no"))
            {
                holder.tvOptions.setText("Options are");

                RadioButton[] rb = new RadioButton[omap.size()];

                holder.rg.setVisibility(View.VISIBLE);

                int k=0;
                for(int key : omap.keySet())
                {

                    rb[k]  = new RadioButton(holder.container.getContext());
                    rb[k].setText(omap.get(key));
                    rb[k].setId(key);
                    holder.rg.addView(rb[k]);

                    k++;
                }

                holder.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int optionid) {
                        //Log.d("test radio",i+"");
                        // NavigationBarActivity.submitResponse(question.getId(),question.getTimeId(),optionid);
                        itemClickCallback.OnReadClick(question.getId(),question.getTimeId(),optionid);
                    }
                });

            }
            else if(question.getResponse().equals("NA"))
            {
                holder.tvQuestion.setText(question.getQuestion());
                holder.imageView.setImageResource(R.mipmap.info);
            }
        }
        else if(datatype.equals("survey"))
        {
            holder.imageView.setImageResource(R.mipmap.sur);
            holder.tvqid.setText(question.getSurveyId()+"");
            if(question.getResponse().equals("no"))
            {
                holder.tvQuestion.setText("You are invited to take " +question.getSurveyName());
                holder.surveyBtn.setVisibility(View.VISIBLE);
                holder.surveyBtn.setText("Take " + question.getSurveyName());

                holder.tvOptions.setVisibility(View.GONE);
            }
            else if(question.getResponse().equals("yes"))
            {

                holder.tvQuestion.setText(question.getSurveyName());
                holder.tvOptions.setText("You have already taken this survey");
            }
        }



    }
}
