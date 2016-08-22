package com.ge.appl.trashcan;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Date;
import java.util.Random;

public class TrashActivity extends AppCompatActivity {

    private static final String TAG = "Trash Activity";

    private Button btnAdd;
    private FrameLayout addZone;
    private ImageView trash;

    private int xMoving;
    private int yMoving;
    private Rect trashRect;
    private Rect addZoneRect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);


        btnAdd = (Button)findViewById(R.id.btnAdd);
        addZone = (FrameLayout)findViewById(R.id.addZone);
        trash = (ImageView)findViewById(R.id.trash);




        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addIconMain();
            }

        });
        //함수이름 직관성,addIcon

    }



    /**
     * 함수에 대한 설명
     * param에 대한 설명
     *
     */
    private void addIconMain(){

        final ImageView tempImageView = new ImageView(getApplicationContext());

        tempImageView.setImageResource(R.drawable.ge);

        if(trashRect==null) {
            trashRect = new Rect();
            trash.getHitRect(trashRect);

        }

        if(addZoneRect==null){
        addZoneRect = new Rect();
        addZone.getHitRect(addZoneRect);
        }

        tempImageView.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View view, MotionEvent event) {

                int eventAction = event.getAction();
                int X = (int)event.getRawX();
                int Y = (int)event.getRawY();

                switch(eventAction)
                {
                    case MotionEvent.ACTION_DOWN :
                        xMoving = (int)(X-tempImageView.getTranslationX());
                        yMoving = (int)(Y-tempImageView.getTranslationY());
                        break;

                    //커멘트 많이 달자 - 클래스 함수 단위별
                    //로직
                    //익명객체-메모리 생각  -- touch에서 빈번하게 사용하는 객체 한번만 생성해서 사용해서 쓰자.
                    //터치이벤트 - 보정  done
                    //레이아웃 마진
                    //애니메이션 학습


                    //++ 화면밖으로 못나가게  threshold   -done
                    //쓰레기통에서 나왔을때 원상태 복구    -done
                    //addZone내 랜덤으로 아이콘 들어가기   -done

                    case MotionEvent.ACTION_MOVE :

                            tempImageView.setTranslationX(X - xMoving);
                            tempImageView.setTranslationY(Y - yMoving);

                        //쓰레기통 밖으로 빠질때 적용하기
                        if(trashRect.contains(X-xMoving,Y-yMoving)){
                            tempImageView.animate().alpha(0.2f).setDuration(500);
                        }else{

                        }
                        locateIconWithinAddzone(tempImageView,X,Y);
                        //함수로 빼기,naming   o

                        if(!trashRect.contains(X-xMoving,Y-yMoving)&&!(tempImageView.getAlpha()==1.0f)){
                            tempImageView.animate().alpha(1.0f);
                        }

                        break;

                    case MotionEvent.ACTION_UP :

                        //remove icon when trash contains icon.
                        if(trashRect.contains(X-xMoving,Y-yMoving)) {
                            addZone.removeView(tempImageView);
                        }

                        break;
                }
                return true;
            }
        });

        //variables for random point - random and seed

        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());

        int ranX = (int)(rnd.nextDouble()*800);
        int ranY = (int)(rnd.nextDouble()*800);

        //locate icon in addZone
        addZone.addView(tempImageView,new FrameLayout.LayoutParams(300,300));
        tempImageView.setTranslationX(ranX);
        tempImageView.setTranslationY(ranY);
    }

    private void locateIconWithinAddzone(ImageView tempImageView,int X, int Y){
        if(addZone.getLeft()>X-xMoving){
            tempImageView.setTranslationX(addZone.getLeft());
        } else {
            //do nothing.
        }
        if(addZone.getRight()<=X+tempImageView.getWidth()){
            tempImageView.setTranslationX(addZone.getRight()-tempImageView.getWidth());
        } else {
            //do nothing.
        }

        if(addZone.getBottom()<tempImageView.getY()+tempImageView.getHeight()){
            tempImageView.setTranslationY(addZone.getHeight()-tempImageView.getHeight());
        } else {
            //do nothing.
        }

        if(addZone.getTop()+btnAdd.getHeight()>Y-yMoving){
            tempImageView.setTranslationY(addZone.getTop()-btnAdd.getHeight());
        } else {
            //do nothing.
        }
    }

}
