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
//            rect.left = trash.getLeft();
//            rect.right = trash.getRight();
//            rect.top = trash.getTop();
//            //rect.bottom = trash.getBottom();
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
                //getRawX, getX

                switch(eventAction)
                {
                    case MotionEvent.ACTION_DOWN :
                        xMoving = (int)(X-tempImageView.getTranslationX());
                        yMoving = (int)(Y-tempImageView.getTranslationY());
                        break;

                    //커멘트 많이 달자 - 클래스 함수 단위별
                    //로직
                    //익명객체-메모리 생각  -- touch에서 빈번하게 사용하는 객체 한번만 생성해서 사용해서 쓰자.
                    //터치이벤트 - 보정
                    //레이아웃 마진
                    //애니메이션 학습


                    //++ 화면밖으로 못나가게  threshold
                    //쓰레기통에서 나왔을때 원상태 복구

                    case MotionEvent.ACTION_MOVE :

                        if(addZoneRect.contains(X,Y)) {
                            //if(addZone.getLeft()<X-xMoving&&X-xMoving<addZone.getRight()
                            // &&addZone.getTop()<Y-yMoving&&Y-yMoving<addZone.getBottom()) {
                            tempImageView.setTranslationX(X - xMoving);
                            tempImageView.setTranslationY(Y - yMoving);
                            // }
                        }
//                        if (addZone.getLeft()>X-xMoving){
//                            Log.i(TAG,addZone.getLeft()+"!!!!!!!!!!!!"+addZone.getRight());
//                            tempImageView.setTranslationX(addZone.getLeft());
//
//                        }
//                        if (X+xMoving>addZone.getRight()){
//                            Log.i(TAG,X-xMoving+"@@@@@@@@");
//                            tempImageView.setTranslationX(addZone.getRight()-xMoving);
//                        }

//                     //   if(!addZoneRect.contains(X-xMoving,Y-yMoving)){
////                            tempImageView.setTranslationX(addZoneRect.centerX());
////
////                            tempImageView.setTranslationY(addZoneRect.centerY());
//                            Log.i(TAG,X+"@@@@@@@");
//                            Log.i(TAG,Y+"@@@@@@@");
//                        }

                        if(trashRect.contains(X-xMoving,Y-yMoving)){
                            tempImageView.animate().alpha(0.2f).setDuration(500);

                        }else{

                        }

                        break;
                    case MotionEvent.ACTION_UP :

                        if(trashRect.contains(X-xMoving,Y-yMoving)){
                            addZone.removeView(tempImageView);
                        }else{
                            tempImageView.animate().alpha(1.0f);

                        }
                        break;
                }
                return true;
            }
        });
        addZone.addView(tempImageView,new FrameLayout.LayoutParams(300,300));

    }

}
