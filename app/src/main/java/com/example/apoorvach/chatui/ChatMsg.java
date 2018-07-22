package com.example.apoorvach.chatui;

public class
ChatMsg {
    private String mContent;
    private Boolean mMine;

    public ChatMsg(String content,Boolean mine){
        mContent=content;
        mMine=mine;
    }

    public String getContent(){
        return mContent;
    }

    public void setContent(String mContent){
        this.mContent=mContent;
    }

    public Boolean isMine(){
        return mMine;
    }

    public void setMine(Boolean mine){
        this.mMine=mine;
    }

}
