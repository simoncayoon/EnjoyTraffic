package com.eteng.entity;

import android.view.View;
import android.widget.ImageButton;

/**
 * Created by apple on 13-9-10.
 */
public class ClickEvent{

    /**
     * 判断是哪个view被点击，转到相应的界面
     */
    private ImageButton focusView;

    public ClickEvent(ImageButton view){
        this.focusView = view;
        clickEvent();
    }

    public void clickEvent(){
        Object id = focusView.getTag();
        focusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
