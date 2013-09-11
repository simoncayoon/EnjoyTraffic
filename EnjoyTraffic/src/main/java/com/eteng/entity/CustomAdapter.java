package com.eteng.entity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.eteng.enjoytraffic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 13-9-5.
 */
public class CustomAdapter extends BaseAdapter
{


    public static final String KEY = "key";
    public static final String VALUE = "value";

    public static final int VALUE_TIME_TIP = 0;//7种不同的布局
    public static final int VALUE_LEFT_TEXT = 1;
    public static final int VALUE_LEFT_IMAGE = 2;
    public static final int VALUE_LEFT_AUDIO = 3;
    public static final int VALUE_RIGHT_TEXT = 4;
    public static final int VALUE_RIGHT_IMAGE = 5;
    public static final int VALUE_RIGHT_AUDIO = 6;
    private LayoutInflater mInflater;

    private Context context;
    private List<UsrInfoEntity> myList, newList;

    public CustomAdapter(Context ctx, List<UsrInfoEntity> myList){


        this.context = ctx;
        this.myList = myList;
        this.newList = new ArrayList<UsrInfoEntity>();

        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final UsrInfoEntity item) {
        myList.add(item);
        newList.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        UsrInfoEntity msg = myList.get(position);
        if(convertView != null)
            Log.d("test__", "the msg is : " + msg.getMsgContent() + "\n" + "the position is: " + String.valueOf(position));
        int type = getItemViewType(position);
        ViewHolder holder = null;
        if(convertView == null){

            Log.d("test__", "稍后执行");
            holder = new ViewHolder();
            switch (type) {
                case VALUE_LEFT_TEXT:

                    convertView = mInflater.inflate(R.layout.left_other_text, null);
                    holder.ivLeftIcon = (ImageView)convertView.findViewById(R.id.iv_icon);
                    holder.btnLeftText = (Button)convertView.findViewById(R.id.btn_left_text);
                    holder.btnLeftText.setText(msg.getMsgContent());
                    break;

                case VALUE_RIGHT_TEXT:

                    convertView = mInflater.inflate(R.layout.right_self_text, null);
                    holder.ivRightIcon = (ImageView)convertView.findViewById(R.id.iv_icon);
                    holder.btnRightText = (Button)convertView.findViewById(R.id.btn_right_text);
                    holder.btnRightText.setText(msg.getMsgContent());
                    break;

                default:
                    break;
            }
            convertView.setTag(holder);
        }else{
            Log.d("test__", "首先执行");
            holder = (ViewHolder)convertView.getTag();
        }
        return convertView;
    }

    /**
     * 根据数据源的position返回需要显示的的layout的type
     *
     * */
    @Override
    public int getItemViewType(int position) {

        UsrInfoEntity msg = myList.get(position);
        int type = msg.getType();
        return type;
    }

    /**
     * 返回所有的layout的数量
     *
     * */
    @Override
    public int getViewTypeCount() {
        return 7;
    }

    class ViewHolder{

        private TextView tvTimeTip;//时间

        private ImageView ivLeftIcon;//左边的头像
        private Button btnLeftText;//左边的文本

        private ImageView ivRightIcon;//右边的头像
        private Button btnRightText;//右边的文本

    }

}

