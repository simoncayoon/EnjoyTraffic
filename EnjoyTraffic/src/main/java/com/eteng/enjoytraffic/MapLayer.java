package com.eteng.enjoytraffic;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.eteng.entity.UsrInfoEntity;


public class MapLayer extends Activity {

    private ImageButton btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        btn1 = (ImageButton)findViewById(R.id.imageButton);
        btn2 = (ImageButton)findViewById(R.id.imageButton2);
        btn3 = (ImageButton)findViewById(R.id.imageButton3);
        btn4 = (ImageButton)findViewById(R.id.imageButton4);


        //通过头像进入聊天界面
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**
                 * 后台创建socket（）连接
                 */
                Log.d("handle_flow", "点击头像获得ID，进入对应的聊天界面");

                Intent intent = new Intent();
                DialogActivity targetClass = new DialogActivity();
                intent.setClass(MapLayer.this, targetClass.getClass());
                Bundle bundle  = new Bundle();
                bundle.putInt("id", getUsrInfo().getUserID());
                bundle.putString("name", getUsrInfo().getUsrName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /**
     * getUsrinfo()
     */
    public UsrInfoEntity getUsrInfo(){

        /**
         * 网络接口，返回ID、姓名
         */
        UsrInfoEntity usrInfo = new UsrInfoEntity();
        usrInfo.setUserID(5);
        usrInfo.setUsrName("DefaultName");
        return usrInfo;
    }







/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map_layer, menu);
        return true;
    }*/
    
}
