package com.eteng.enjoytraffic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eteng.entity.CustomAdapter;
import com.eteng.entity.UsrInfoEntity;
import com.eteng.interactionServer.ClientHandler;
import com.eteng.interactionServer.ConnServer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 13-9-5.
 */
public class DialogActivity extends Activity{

    private Button returnBtn;
    private ImageButton sendBtn;
    private ListView listView;
    private EditText sendText;
    private List<UsrInfoEntity> msgList, tmpList;
    private int dialogID;
    private UsrInfoEntity initEntity;
    public CustomAdapter myAdapter;
    private TextView nameTitle;
    private Intent intent;
    public ConnServer conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_view);

        //实例化成员变量
        nameTitle = (TextView)findViewById(R.id.name_title);
        msgList = new ArrayList<UsrInfoEntity>();
        myAdapter = new CustomAdapter(this, getCacheData());
        sendText = (EditText)findViewById(R.id.et_sendmessage);
        returnBtn = (Button)findViewById(R.id.btn_back);
        sendBtn = (ImageButton)findViewById(R.id.cleanBtn);
        listView = (ListView)findViewById(R.id.listview);
        intent = this.getIntent();
        conn = new ConnServer(myAdapter);



        conn.createConnection();
        nameTitle.setText(intent.getExtras().getString("name"));
        dialogID = intent.getExtras().getInt("id");
        Log.d("test", "id number is:   " + String.valueOf(dialogID));

        //return btn& btnClickListener
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //保存缓存
                updateCache();
                //释放socket连接
                Thread handlerThread = new Thread(new MyThread());
                handlerThread.start();

                Intent intent = new Intent();
                MapLayer pre;
                pre = new MapLayer();
                intent.setClass(DialogActivity.this, pre.getClass());
                startActivity(intent);
            }
        });

        //发送消息按钮以及监听器
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsrInfoEntity msg = new UsrInfoEntity();
                if(!"".equals(sendText.getText().toString().trim())){
                    UsrInfoEntity newMsg = new UsrInfoEntity();
                    newMsg.setMsgContent(sendText.getText().toString());
                    newMsg.setType(CustomAdapter.VALUE_RIGHT_TEXT);
                    Log.d("handle_flow", "发送消息,跟新界面 : " + sendText.getText());
                    try{
                        conn.handler.sendMessage(dialogID, newMsg);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), "请输入内容", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        //listView & setAdapter
        listView.setAdapter(myAdapter);

        /**
         * 后台接收消息的服务，动态更新对话列表。
         * 向updateArray<UsrInfoEntity>()数组插入数据
         * getMsgService()
         */
        ConnServer conn = new ConnServer(myAdapter);
        conn.createConnection();
    }

    /**
     * 查找缓存与ID匹配，获得缓存数据，保存至数组
     * @return
     */
    private List<UsrInfoEntity> getCacheData(){

        /**
         * 根据ID获得Cache数据，初始化initEntity
         */

        Log.d("handle flow", "首先，根据ID查找缓存数据，加载到UI");
        UsrInfoEntity msg;
        msg = new UsrInfoEntity();
        msg.setType(CustomAdapter.VALUE_LEFT_TEXT);
        msg.setMsgContent("你好，我想请问一下，安云路方向路况如何？");
        msgList.add(msg);

        msg = new UsrInfoEntity();
        msg.setType(CustomAdapter.VALUE_RIGHT_TEXT);
        msg.setMsgContent("现在通行拥堵,如果您要走中华北路世纪商务城的话，我可以给你带路");
        msgList.add(msg);

        msg = new UsrInfoEntity();
        msg.setType(CustomAdapter.VALUE_LEFT_TEXT);
        msg.setMsgContent("非常感谢，那我还是绕行吧");
        msgList.add(msg);

        msg = new UsrInfoEntity();
        msg.setType(CustomAdapter.VALUE_LEFT_TEXT);
        msg.setMsgContent("谢谢！");
        msgList.add(msg);

        msg = new UsrInfoEntity();
        msg.setType(CustomAdapter.VALUE_RIGHT_TEXT);
        msg.setMsgContent("太客气了～");
        msgList.add(msg);

        msg = new UsrInfoEntity();
        msg.setType(CustomAdapter.VALUE_RIGHT_TEXT);
        msg.setMsgContent("您走好～");
        msgList.add(msg);

        return msgList;
    }

    /**
     * 保存新的对话数据、更新cache
     */
    public void updateCache(){

        Log.d("handle_flow", "保存新添加的数据到缓存和数据库");
    }

    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d("thread_test", "handler action");
            conn.handler.releaseConn();
        }
    };

    class MyThread implements Runnable{
        @Override
        public void run() {
            Message msg = new Message();
            myHandler.sendMessage(msg);
        }
    }
}
