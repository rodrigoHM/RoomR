package com.example.ryan.roomrep.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ryan.roomrep.Classes.ChatMessage;
import com.example.ryan.roomrep.R;

import java.util.List;

public class MessagrRecycleViewAdapter extends RecyclerView.Adapter<MessagrRecycleViewAdapter.ViewHolder>{

    private static final String TAG = "MessagrRecyclerView";
    private List<ChatMessage> mData;
    private Context context;
    private LayoutInflater mInflater;


    // data is passed into the constructor
    public MessagrRecycleViewAdapter(Context context, List<ChatMessage> data) {
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;

    }




    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        //String animal = mData.get(position);
        //holder.myTextView.setText(animal);
        //String name = "Ryan";

        //holder.userName.setText(name);
        //holder.time.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
        //        infoMessage.getMessageTime()));

        ChatMessage chatMessage = mData.get(position);
        holder.myTextView.setText(chatMessage.getMessageText());
        holder.userName.setText(chatMessage.getMessageUser());
        holder.time.setText(chatMessage.getMessageTime());


    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = mInflater.inflate(R.layout.addtenant_row, parent, false);
        View view = mInflater.inflate(R.layout.message_structure, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            //implements View.OnClickListener
    {
        TextView myTextView;
        TextView userName;
        TextView time;

        ViewHolder(View itemView) {
            super(itemView);
            //myTextView = itemView.findViewById(R.id.txtMessage);
            myTextView = itemView.findViewById(R.id.message_text);
            userName = itemView.findViewById(R.id.message_user);
            time = itemView.findViewById(R.id.message_time);
            //itemView.setOnClickListener(this);
        }
//        public MessagrRecycleViewAdapter(List<ChatMessage> www){
//            mData = www;
//        }

        // @Override
        //public void onClick(View view) {
        //  if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        //}
    }

    // convenience method for getting data at click position
    //String getItem(int id) {
     //   return mData.get(id);
    //}

    // allows clicks events to be caught
    //void setClickListener(ItemClickListener itemClickListener) {
     //   this.mClickListener = itemClickListener;
    //}

    // parent activity will implement this method to respond to click events
    //public interface ItemClickListener {
     //   void onItemClick(View view, int position);
    //}

}
