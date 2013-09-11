package com.eteng.interactionServer;

import android.util.Log;

import com.eteng.entity.CustomAdapter;
import com.eteng.entity.UsrInfoEntity;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;


public class ClientHandler extends SimpleChannelHandler {

	private Channel channel;
    private String sendClientMessage;
    private UsrInfoEntity entity;
    private CustomAdapter myAdapter;

    public String getClickMessage(){
        return sendClientMessage;
    }

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

    public ClientHandler(CustomAdapter adapter){
        this.myAdapter = adapter;
    }

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		setChannel(e.getChannel());
		System.out.println("Connected server");
        }

        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
        throws Exception {
            // 判断传过的对象是string对象还是messageInfo对象，依据相应对象做处理
            sendClientMessage = "";
            if (e.getMessage() instanceof String) {
                entity.setMsgContent((String) e.getMessage());
                entity.setType(CustomAdapter.VALUE_LEFT_TEXT);
                myAdapter.addItem(entity);
            }
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		e.getCause().printStackTrace();
	}

    /**
     * 发送消息
     */
    public void sendMessage(int usrID, UsrInfoEntity msgEntity){
        Log.d("test", "id = "+ String.valueOf(usrID) + " msgContent = " + msgEntity.getMsgContent());
        msgEntity.setUserID(usrID);
        myAdapter.addItem(msgEntity);
//        this.channel.write(msgEntity);
    }

    /**
     * 释放socket
     */
    public void releaseConn(){

        this.channel.disconnect();
    }
}
