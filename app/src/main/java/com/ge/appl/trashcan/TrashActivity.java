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

    private int iconStartingPoint;

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

        iconStartingPoint =  (int)((Math.random()*100));


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
                    //addZone내 랜덤으로 아이콘 들어가기

                    case MotionEvent.ACTION_MOVE :

                            tempImageView.setTranslationX(X - xMoving);
                            tempImageView.setTranslationY(Y - yMoving);

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

                            if(addZone.getLeft()>X-xMoving){
                                tempImageView.setTranslationX(addZone.getLeft());
                            }
                            if(addZone.getRight()<=X+tempImageView.getWidth()){
                                tempImageView.setTranslationX(addZone.getRight()-tempImageView.getWidth());
                            }
<<<<<<< Updated upstream
                            if(addZone.getBottom()<Y+tempImageView.getHeight()){
=======

                        //Bottom

                            if(addZone.getBottom()<tempImageView.getY()+tempImageView.getHeight()){
>>>>>>> Stashed changes
                                tempImageView.setTranslationY(addZone.getHeight()-tempImageView.getHeight());
                            }
                            if(addZone.getTop()+btnAdd.getHeight()>Y-yMoving){
                                tempImageView.setTranslationY(addZone.getTop()-btnAdd.getHeight());
                            }

                        break;
                }
                return true;
            }
        });

        //variables for random point
        int ranX = (int)(Math.random()*800);
        int ranY = (int)(Math.random()*800);

        //locate icon in addZone
        addZone.addView(tempImageView,new FrameLayout.LayoutParams(300,300));
        tempImageView.setTranslationX(ranX);
        tempImageView.setTranslationY(ranY);
    }

}
