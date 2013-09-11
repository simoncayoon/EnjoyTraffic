package com.eteng.entity;

import android.provider.MediaStore;
import android.widget.ImageView;

import com.eteng.enjoytraffic.R;

/**
 * Created by apple on 13-9-5.
 */
public class UsrInfoEntity {

    private int type;
    private int usrID;
    private String usrName;
    private String msgContent;

    public UsrInfoEntity(){

    }
    public UsrInfoEntity(int id, String msgContent){
        this.usrID = id;
        this.msgContent = msgContent;
    }

    public void setUserID(int id){

        this.usrID = id;
    }
    public int getUserID(){

        return this.usrID;
    }

    public void setUsrName(String str){

        this.usrName = str;
    }
    public String getUsrName(){

        return this.usrName;
    }
    public void setType(int type){

        this.type = type;
    }
    public int getType(){

        return this.type;
    }
    public void setMsgContent(String str){

        this.msgContent = str;
    }

    public String getMsgContent(){

        return this.msgContent;
    }
}
