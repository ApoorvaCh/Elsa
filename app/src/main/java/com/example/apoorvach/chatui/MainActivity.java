package com.example.apoorvach.chatui;

import android.app.ProgressDialog;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.PCAIMLProcessorExtension;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements FileWriter.IWritinngCompletionCallback,TextToSpeech.OnInitListener{

    public static Chat chat;
    public Bot bot;
    Button mbutton;
    ListView mlist;
    EditText mtext;
ChatMsgAdapter madapter;
    private ProgressDialog progress;
    private TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
    }
    private void setupView() {
        tts = new TextToSpeech(this, this);
        mlist=(ListView)findViewById(R.id.list);
        mbutton=(Button)findViewById(R.id.send);
        mtext=(EditText)findViewById(R.id.edit_msg);
       final ArrayList<ChatMsg> msg_item=new ArrayList<ChatMsg>();
       madapter=new ChatMsgAdapter(this,msg_item);
       mlist.setAdapter(madapter);

       mbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              String message=mtext.getText().toString();
               String response = chat.multisentenceRespond(mtext.getText().toString());
               if (TextUtils.isEmpty(message)) {
                   return;
               }
              sendMsg(message);
               mimicOtherMsg(response);
              mtext.setText("");
               mlist.setSelection(madapter.getCount() - 1);
           }
       });
        writingFile();
    }

    private void sendMsg(String msg){
       ChatMsg chatMsg=new ChatMsg(msg,true);
       madapter.add(chatMsg);

    }

private void mimicOtherMsg(String msg){
        ChatMsg chatMsg=new ChatMsg(msg,false);
        madapter.add(chatMsg);
    speakOut(msg);
}

    private void sendMsg() {
        ChatMsg chatMsg = new ChatMsg(null, true);
        madapter.add(chatMsg);

        mimicOtherMessage();
    }

    private void mimicOtherMessage() {
        ChatMsg chatMsg = new ChatMsg(null, false);
        madapter.add(chatMsg);

    }

    @Override
    public void onWritingComplete() {
        AIMLProcessor.extension = new PCAIMLProcessorExtension();
        bot = new Bot("MY_BOT", MagicStrings.root_path, "chat");
        chat = new Chat(bot);
        String[] args = null;
        progress.dismiss();
    }


    public void writingFile() {
        progress = new ProgressDialog(this);
        progress.setMessage("loading bot files");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();
        FileWriter writer = new FileWriter(this, this);
        writer.execute();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            }
        } else {
            Log.e("TTS", "Initilization Failed");
        }

    }

    private void speakOut(String  message) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null,message);
        } else{
            tts.speak(message,TextToSpeech.QUEUE_ADD,null);
        }

    }
}


