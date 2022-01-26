package com.nguyenluongcuong.bidatool;

import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class DrawFrame extends View {

    private Bitmap moveButton, scaleButton;

    private float initialX;
    private float initialY;
    private float initialTouchX;
    private float initialTouchY;

    private boolean movingMoveButton = false;
    private boolean movingScaleButton = false;
    private boolean movingCueBallButton = false;
    private boolean movingSelectedBallButton = false;

    private DrawFeature drawFeature;

    public DrawFrame(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        Properties.setPaint();

        drawFeature = new DrawFeature();
        Bitmap moveBitmap= BitmapFactory.decodeResource(getResources(), R.drawable.move_icon);
        moveButton = Bitmap.createScaledBitmap(moveBitmap,Properties.sizeButton,Properties.sizeButton,false);
        Bitmap scaleBitmap= BitmapFactory.decodeResource(getResources(), R.drawable.scale_icon);
        scaleButton = Bitmap.createScaledBitmap(scaleBitmap,Properties.sizeButton,Properties.sizeButton,false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(this.getMeasuredWidth(), this.getMeasuredHeight());

        if(ManagerAbleTouch.readDataSuccessfully || ListPoint.pBorderStart.x != 0){
            drawFeature.setValueOfListTwoLine();
            invalidate();
            return;
        }
        Log.d("aaa","Chưa lưu");

        if(this.getHeight() < this.getWidth()){
            Properties.widthFrame = 650;
            Properties.heightFrame = 350;
            ListPoint.pBorderStart.set((this.getMeasuredWidth() - Properties.widthFrame) / 2,(this.getMeasuredHeight() - Properties.heightFrame) / 2);
            ListPoint.pBorderEnd.set(ListPoint.pBorderStart.x + Properties.widthFrame, ListPoint.pBorderStart.y + Properties.heightFrame);
        }else {
            ListPoint.pBorderStart.set(100,300);
            ListPoint.pBorderEnd.set(700,600);
        }
        ListPoint.pMoveButton.set(ListPoint.pBorderEnd.x + Properties.sizeButton/2 + 7, ListPoint.pBorderEnd.y - Properties.sizeButton/2 + 2);
        ListPoint.pScaleButton.set(ListPoint.pBorderStart.x - Properties.sizeButton/2 - 5, ListPoint.pBorderStart.y - Properties.sizeButton/2 - 5);
        drawFeature.setValueOfListTwoLine();
        invalidate();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if((Math.abs(ListPoint.pMoveButton.x - x) <= Properties.sizeButton/2 && Math.abs(ListPoint.pMoveButton.y - y) <= Properties.sizeButton/2) || movingMoveButton){ // nút di chuyển
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:{
                    initialX = ListPoint.pMoveButton.x;
                    initialY = ListPoint.pMoveButton.y;
                    initialTouchX = event.getX();
                    initialTouchY = event.getY();
                    return true;
                }
                case MotionEvent.ACTION_UP:{
                    movingMoveButton = false;
                    break;
                }
                case MotionEvent.ACTION_MOVE:{
                    movingMoveButton = true;
                    int width = ListPoint.pBorderEnd.x - ListPoint.pBorderStart.x;
                    int height = ListPoint.pBorderEnd.y - ListPoint.pBorderStart.y;
                    float moveButtonX_Unchecked = (int) (initialX + (event.getX() - initialTouchX));
                    float moveButtonY_Unchecked = (int) (initialY + (event.getY() - initialTouchY));
                    float scaleButtonX_Unchecked = ( moveButtonX_Unchecked - 7 - Properties.sizeButton/2 ) - width - 5 - Properties.sizeButton/2;
                    float scaleButtonY_Unchecked = ( moveButtonY_Unchecked - 2 + Properties.sizeButton/2 ) - height - 5 - Properties.sizeButton/2;
                    if(scaleButtonX_Unchecked <= Properties.sizeButton/2){
                        return false;
                    }
                    if(scaleButtonY_Unchecked <= Properties.sizeButton/2){
                        return false;
                    }
                    if(moveButtonX_Unchecked >= this.getWidth() - Properties.sizeButton/2){
                        return false;
                    }
                    if(moveButtonY_Unchecked >= this.getHeight() - Properties.sizeButton/2){
                        return false;
                    }
                    ListPoint.pMoveButton.set( (int) moveButtonX_Unchecked, (int) moveButtonY_Unchecked);
                    ListPoint.pBorderEnd.set(
                            ListPoint.pMoveButton.x - 7 - Properties.sizeButton/2,
                            ListPoint.pMoveButton.y - 2 + Properties.sizeButton/2);
                    ListPoint.pBorderStart.set(
                            ListPoint.pBorderEnd.x - width,
                            ListPoint.pBorderEnd.y - height);
                    ListPoint.pScaleButton.set( (int) scaleButtonX_Unchecked, (int) scaleButtonY_Unchecked);
                    drawFeature.setValueOfListTwoLine();
                    break;
                }
            }
            invalidate();
            return true;
        }
        if((Math.abs(ListPoint.pScaleButton.x - x) <= Properties.sizeButton/2 && Math.abs(ListPoint.pScaleButton.y - y) <= Properties.sizeButton/2) || movingScaleButton){  // nút scale
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:{
                    initialX = ListPoint.pScaleButton.x;
                    initialY = ListPoint.pScaleButton.y;
                    initialTouchX = (int) event.getX();
                    initialTouchY = (int) event.getY();
                    return true;
                }
                case MotionEvent.ACTION_UP:{
                    movingScaleButton = false;
                    break;
                }
                case MotionEvent.ACTION_MOVE:{
                    movingScaleButton = true;
                    int scaleButtonX_Unchecked = (int) (initialX + (event.getX() - initialTouchX));
                    int scaleButtonY_Unchecked = (int) (initialY + (event.getY() - initialTouchY));
                    int borderStartX_Unchecked = scaleButtonX_Unchecked + 5 + Properties.sizeButton/2;
                    int borderStartY_Unchecked = scaleButtonY_Unchecked + 5 + Properties.sizeButton/2;
                    if(scaleButtonX_Unchecked >= (this.getWidth() - Properties.sizeButton/2)){
                        return false;
                    }
                    if(scaleButtonY_Unchecked >= (this.getHeight() - Properties.sizeButton/2)){
                        return false;
                    }
                    if(scaleButtonX_Unchecked <= Properties.sizeButton/2){
                        return false;
                    }
                    if(scaleButtonY_Unchecked <= Properties.sizeButton/2){
                        return false;
                    }
                    if(ListPoint.pBorderEnd.x - borderStartX_Unchecked <= Properties.radius_pSelected){
                        return false;
                    }
                    if(ListPoint.pBorderEnd.y - borderStartY_Unchecked <= Properties.radius_pSelected){
                        return false;
                    }

                    ListPoint.pScaleButton.set(scaleButtonX_Unchecked, scaleButtonY_Unchecked);
                    ListPoint.pBorderStart.set(borderStartX_Unchecked, borderStartY_Unchecked);
                    Properties.widthFrame = ListPoint.pBorderEnd.x - ListPoint.pBorderStart.x;
                    Properties.heightFrame = ListPoint.pBorderEnd.y - ListPoint.pBorderStart.y;
                    Properties.ball_Radius = Properties.heightFrame / 35;
                    drawFeature.setValueOfListTwoLine();

                    if(ListPoint.pSelected.x > Properties.widthFrame - Properties.ball_Radius){
                        ListPoint.pSelected.set(Properties.widthFrame - Properties.ball_Radius,ListPoint.pSelected.y);
                    }
                    if(ListPoint.pSelected.y > Properties.heightFrame - Properties.ball_Radius){
                        ListPoint.pSelected.set(ListPoint.pSelected.x, Properties.heightFrame - Properties.ball_Radius);
                    }
                    if(ListPoint.pCueBall.x > Properties.widthFrame - Properties.ball_Radius){
                        ListPoint.pCueBall.set(Properties.widthFrame - Properties.ball_Radius,ListPoint.pCueBall.y);
                    }
                    if(ListPoint.pCueBall.y > Properties.heightFrame - Properties.ball_Radius){
                        ListPoint.pCueBall.set(ListPoint.pCueBall.x, Properties.heightFrame - Properties.ball_Radius);
                    }
                    break;
                }
            }
            invalidate();
            return true;
        }
        if((Math.abs(ListPoint.pSelected.x + ListPoint.pBorderStart.x - x) <= Properties.radius_pSelected && Math.abs(ListPoint.pSelected.y + ListPoint.pBorderStart.y - y) <= Properties.radius_pSelected) || movingSelectedBallButton){ // nút bi mục tiêu
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:{
                    initialX = ListPoint.pSelected.x + ListPoint.pBorderStart.x;
                    initialY = ListPoint.pSelected.y + ListPoint.pBorderStart.y;
                    initialTouchX = (int) event.getX();
                    initialTouchY = (int) event.getY();
                    return true;
                }
                case MotionEvent.ACTION_UP:{
                    movingSelectedBallButton = false;
                    break;
                }
                case MotionEvent.ACTION_MOVE:{
                    movingSelectedBallButton = true;
                    int pSelecedX_Unchecked = (int) (initialX + (event.getX() - initialTouchX));
                    int pSelecedY_Unchecked = (int) (initialY + (event.getY() - initialTouchY));
                    if(pSelecedX_Unchecked > ListPoint.pBorderEnd.x - Properties.ball_Radius / 2){
                        return false;
                    }
                    if(pSelecedX_Unchecked < ListPoint.pBorderStart.x + Properties.ball_Radius / 2){
                        return false;
                    }
                    if(pSelecedY_Unchecked > ListPoint.pBorderEnd.y - Properties.ball_Radius / 2){
                        return false;
                    }
                    if(pSelecedY_Unchecked < ListPoint.pBorderStart.y + Properties.ball_Radius / 2){
                        return false;
                    }
                    ListPoint.pSelected.set(pSelecedX_Unchecked - ListPoint.pBorderStart.x,pSelecedY_Unchecked - ListPoint.pBorderStart.y);
                    drawFeature.setValueOfListTwoLine();
                    break;
                }
            }
            invalidate();
            return true;
        }

        if((Math.abs(ListPoint.pCueBall.x + ListPoint.pBorderStart.x - x) <= Properties.radius_pSelected && Math.abs(ListPoint.pCueBall.y + ListPoint.pBorderStart.y - y) <= Properties.radius_pSelected && Properties.option == 3) || movingCueBallButton){ // nút bi mục tiêu
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:{
                    initialX = ListPoint.pCueBall.x + ListPoint.pBorderStart.x;
                    initialY = ListPoint.pCueBall.y + ListPoint.pBorderStart.y;
                    initialTouchX = (int) event.getX();
                    initialTouchY = (int) event.getY();
                    return true;
                }
                case MotionEvent.ACTION_UP:{
                    movingCueBallButton = false;
                    break;
                }
                case MotionEvent.ACTION_MOVE:{
                    movingCueBallButton = true;
                    int pCueBallX_Unchecked = (int) (initialX + (event.getX() - initialTouchX));
                    int pCueBallY_Unchecked = (int) (initialY + (event.getY() - initialTouchY));
                    if(pCueBallX_Unchecked > ListPoint.pBorderEnd.x - Properties.ball_Radius / 2){
                        return false;
                    }
                    if(pCueBallX_Unchecked < ListPoint.pBorderStart.x  + Properties.ball_Radius / 2){
                        return false;
                    }
                    if(pCueBallY_Unchecked > ListPoint.pBorderEnd.y - Properties.ball_Radius / 2){
                        return false;
                    }
                    if(pCueBallY_Unchecked < ListPoint.pBorderStart.y + Properties.ball_Radius / 2){
                        return false;
                    }
                    ListPoint.pCueBall.set(pCueBallX_Unchecked - ListPoint.pBorderStart.x,pCueBallY_Unchecked - ListPoint.pBorderStart.y);
                    drawFeature.setValueOfListTwoLine();
                    break;
                }
            }
            invalidate();
            return true;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBorder(canvas);
        drawMoveButton(canvas);
        drawScaleButton(canvas);

        switch (Properties.option){
            case 1:{
                drawFeature.findOneLine(canvas);
                break;
            }
            case 2:{
                drawFeature.findAllLine(canvas);
                break;
            }
            case 3:{
                drawFeature.drawTwoShootLine(canvas);
                break;
            }
            case 4:{

                break;
            }
            case 5:{

                break;
            }
        }
    }

    private void drawBorder(Canvas canvas){
        canvas.drawLine(ListPoint.pBorderStart.x,ListPoint.pBorderStart.y,ListPoint.pBorderEnd.x,ListPoint.pBorderStart.y,Properties.paintBorder);
        canvas.drawLine(ListPoint.pBorderStart.x,ListPoint.pBorderStart.y,ListPoint.pBorderStart.x,ListPoint.pBorderEnd.y,Properties.paintBorder);
        canvas.drawLine(ListPoint.pBorderEnd.x,ListPoint.pBorderEnd.y,ListPoint.pBorderEnd.x,ListPoint.pBorderStart.y,Properties.paintBorder);
        canvas.drawLine(ListPoint.pBorderEnd.x,ListPoint.pBorderEnd.y,ListPoint.pBorderStart.x,ListPoint.pBorderEnd.y,Properties.paintBorder);
    }

    private void drawMoveButton(Canvas canvas){
        canvas.drawBitmap(moveButton, ListPoint.pMoveButton.x - Properties.sizeButton/2, ListPoint.pMoveButton.y - Properties.sizeButton/2, Properties.paintBorder);
    }

    private void drawScaleButton(Canvas canvas){
        canvas.drawBitmap(scaleButton, ListPoint.pScaleButton.x - Properties.sizeButton/2, ListPoint.pScaleButton.y - Properties.sizeButton/2, Properties.paintBorder);
    }
}
