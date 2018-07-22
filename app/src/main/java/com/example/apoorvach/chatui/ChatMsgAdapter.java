package com.example.apoorvach.chatui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

public class ChatMsgAdapter extends ArrayAdapter<ChatMsg> {
private static final int MY_MESSAGE=0,OTHER_MESSAGE=1;
    public ChatMsgAdapter(Context context, List<ChatMsg> data){
        super(context,R.layout.item_mine_msg,data);
    }
@Override
    public int getViewTypeCount(){
return 2;      //MY_MESSAGE and OTHER_MESSAGE
    }

    @Override
    public int getItemViewType(int position){
        ChatMsg item=getItem(position);
        if(item.isMine()){
return MY_MESSAGE;
        }

        return OTHER_MESSAGE;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       int viewType=getItemViewType(position);
       if(viewType==MY_MESSAGE){

           convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_mine_msg,parent,false);
           TextView textView=(TextView)convertView.findViewById(R.id.mine);
           textView.setText(getItem(position).getContent());
       }
       else if(viewType==OTHER_MESSAGE){
           convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_other_msg,parent,false);
            TextView textView=(TextView)convertView.findViewById(R.id.other);
            textView.setText(getItem(position).getContent());
       }
       return convertView;
    }
}

