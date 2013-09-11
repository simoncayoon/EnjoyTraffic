package com.eteng.interactionServer;

import android.util.Log;

import com.eteng.entity.CustomAdapter;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by apple on 13-9-9.
 */
public class ConnServer {

    private static final String IP = "localhost";
    private static final int PORT_ID = 9999;
    public ClientHandler handler;
    private CustomAdapter myAdapter;

    public ConnServer(CustomAdapter adapter){
        this.myAdapter = adapter;
    }
    /**
     * 连接服务器、设置保持连接、即时通信
     */
    public void createConnection(){

        ChannelFactory cf = new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool());
        ClientBootstrap boot = new ClientBootstrap(cf);
        handler = new ClientHandler(myAdapter);

        boot.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(
                        // 设置对象编码和解码
                        new ObjectEncoder(),
                        new ObjectDecoder(ClassResolvers
                                .cacheDisabled(getClass().getClassLoader())),
                        handler);
            }
        });
        boot.setOption("tcpNoDelay", true);
        boot.setOption("keepAlive", true);
        try{
            boot.connect(new InetSocketAddress(IP, PORT_ID));
            Log.d("handle_flow", "建立服务器连接");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            Log.d("test", "oops, connect failed");
        }
    }

}
