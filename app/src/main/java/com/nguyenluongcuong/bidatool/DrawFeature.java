package com.nguyenluongcuong.bidatool;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class DrawFeature {

    private float listTwoShotLine[][] = new float[6][2];
    private float x,y,spaceRadius,length,cX,cY;

    public static ArrayList<Point> listPointTwoShootLine = new ArrayList<>();
    public static int direction = 1;

    public static int saiSoCanBang = 0 /*Properties.ball_Radius*/;
    public static int customSaiSo = 70;

    public void setValueOfListTwoLine(){

        listPointTwoShootLine.clear();

        x = ListPoint.pSelected.x;
        y = ListPoint.pSelected.y;
        spaceRadius = 2 * (Properties.ball_Radius + 1);

        // ô 1
        length = (float) Math.sqrt(x * x + y * y);
        cX = ((x) * (length + spaceRadius)) / length;
        cY = ((y) * (length + spaceRadius)) / length;
        listTwoShotLine[0][0] = cX;
        listTwoShotLine[0][1] = cY;

        // ô 2
        length = (int) (Math.sqrt(Math.pow(Math.abs(x - Properties.widthFrame / 2), 2) + y * y));
        cX = ((Math.abs(x - Properties.widthFrame / 2)) * (length + spaceRadius)) / length;
        cY = ((y) * (length + spaceRadius)) / length;
        if (x < Properties.widthFrame / 2) {
            cX = Properties.widthFrame / 2 - cX;
        } else {
            cX = Properties.widthFrame / 2 + cX;
        }
        listTwoShotLine[1][0] = cX;
        listTwoShotLine[1][1] = cY;

        // ô 3
        length = (int) (Math.sqrt(Math.pow(Properties.widthFrame - x, 2) + y * y));
        cX = ((Properties.widthFrame - x) * (length + spaceRadius)) / length;
        cY = ((y) * (length + spaceRadius)) / length;
        listTwoShotLine[2][0] = Properties.widthFrame - cX;
        listTwoShotLine[2][1] = cY;

        // ô 4
        length = (int) (Math.sqrt(x * x + Math.pow(Properties.heightFrame - y, 2)));
        cX = ((x) * (length + spaceRadius)) / length;
        cY = ((Properties.heightFrame - y) * (length + spaceRadius)) / length;
        listTwoShotLine[3][0] = cX;
        listTwoShotLine[3][1] = Properties.heightFrame - cY;

        // ô 5
        length = (int) (Math.sqrt(Math.pow((Math.abs(x - Properties.widthFrame / 2)), 2) + Math.pow(Properties.heightFrame - y, 2)));
        cX = ((Math.abs(x - Properties.widthFrame / 2)) * (length + spaceRadius)) / length;
        cY = ((Properties.heightFrame - y) * (length + spaceRadius)) / length;
        if (x < Properties.widthFrame / 2) {
            cX = Properties.widthFrame / 2 - cX;
        } else {
            cX = Properties.widthFrame / 2 + cX;
        }
        listTwoShotLine[4][0] = cX;
        listTwoShotLine[4][1] = Properties.heightFrame - cY;

        // ô 6
        length = (int) (Math.sqrt(Math.pow(Properties.widthFrame - x, 2) + Math.pow(Properties.heightFrame - y, 2)));
        cX = ((Properties.widthFrame - x) * (length + spaceRadius)) / length;
        cY = ((Properties.heightFrame - y) * (length + spaceRadius)) / length;
        listTwoShotLine[5][0] = Properties.widthFrame - cX ;
        listTwoShotLine[5][1] = Properties.heightFrame - cY;
    }

    public void findOneLine(Canvas canvas) {
        float x = ListPoint.pSelected.x + ListPoint.pBorderStart.x;
        float y = ListPoint.pSelected.y + ListPoint.pBorderStart.y;

        canvas.drawCircle(x,y,Properties.ball_Radius,Properties.paintTarget);
        canvas.drawCircle(x,y,Properties.radius_pSelected,Properties.paintSelectedBall);
        //pointAddY.set((int) x, (int) y);

        if(Properties.listHole[0]){ // vẽ ô thứ 1
            canvas.drawCircle(Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.ball_Radius + ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall);
            canvas.drawLine(Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.ball_Radius + ListPoint.pBorderStart.y, x, y, Properties.paintLine);
        }

        if(Properties.listHole[1]){ // vẽ ô thứ 2
            canvas.drawCircle(Properties.widthFrame / 2 + ListPoint.pBorderStart.x, ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall);
            canvas.drawLine(Properties.widthFrame / 2 + ListPoint.pBorderStart.x, ListPoint.pBorderStart.y, x, y,Properties.paintLine);
        }

        if(Properties.listHole[2]){ // vẽ ô thứ 3
            canvas.drawCircle(Properties.widthFrame - Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.ball_Radius + ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall);
            canvas.drawLine(Properties.widthFrame - Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.ball_Radius + ListPoint.pBorderStart.y, x, y, Properties.paintLine);
        }

        if(Properties.listHole[3]){ // vẽ ô thứ 4
            canvas.drawCircle(Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.heightFrame - Properties.ball_Radius + ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall);
            canvas.drawLine(Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.heightFrame - Properties.ball_Radius + ListPoint.pBorderStart.y, x, y, Properties.paintLine);
        }

        if(Properties.listHole[4]){ // vẽ ô thứ 5
            canvas.drawCircle(Properties.widthFrame / 2 + ListPoint.pBorderStart.x, Properties.heightFrame + ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall);
            canvas.drawLine(Properties.widthFrame / 2 + ListPoint.pBorderStart.x, Properties.heightFrame + ListPoint.pBorderStart.y, x, y, Properties.paintLine);
        }

        if(Properties.listHole[5]){ // vẽ ô thứ 6
            canvas.drawCircle(Properties.widthFrame - Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.heightFrame - Properties.ball_Radius - 5 + ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall);
            canvas.drawLine(Properties.widthFrame - Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.heightFrame - Properties.ball_Radius - 5 + ListPoint.pBorderStart.y, x, y,Properties.paintLine);
        }
    }

    public void findAllLine(Canvas canvas) {
        float x = ListPoint.pSelected.x + ListPoint.pBorderStart.x;
        float y = ListPoint.pSelected.y + ListPoint.pBorderStart.y;

        canvas.drawCircle(x,y,Properties.ball_Radius,Properties.paintTarget);
        canvas.drawCircle(x,y,Properties.radius_pSelected,Properties.paintSelectedBall);
        //pointAddY.set( (int) x, (int) y);
        //float spaceRadius = 2 * (Properties.ball_Radius + 2);

        if(Properties.listHole[0]){  // vẽ ô thứ 1
            if (Properties.reverseDirection) {
                float b = (Properties.heightFrame * (x - ListPoint.pBorderStart.x)) / (Properties.heightFrame + Properties.heightFrame - (y - ListPoint.pBorderStart.y));
                float pX = b;
//                    float length = (int) (Math.sqrt((x - pX) * (x - pX) + Math.pow(Properties.heightFrame - y, 2)));
//                    float cX = ((x - pX) * (length + spaceRadius)) / length;
//                    float cY = ((Properties.heightFrame - y) * (length + spaceRadius)) / length;
                //canvas.drawCircle(cX + pX - Properties.ball_Radius, Properties.heightFrame - cY - Properties.ball_Radius, 2 * Properties.ball_Radius, 2 * Properties.ball_Radius);
                //pointAddY.set(cX + pX, Properties.heightFrame - cY);
                if (Math.abs(Properties.heightFrame - (y - ListPoint.pBorderStart.y)) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                    canvas.drawLine(pX + ListPoint.pBorderStart.x, Properties.heightFrame - saiSoCanBang + ListPoint.pBorderStart.y, x, y, Properties.paintLine);
                    canvas.drawLine(pX + ListPoint.pBorderStart.x, Properties.heightFrame - saiSoCanBang + ListPoint.pBorderStart.y, Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.ball_Radius + ListPoint.pBorderStart.y,Properties.paintLine);
                    canvas.drawCircle(pX + ListPoint.pBorderStart.x, Properties.heightFrame - saiSoCanBang + ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall); // điểm đập
                } else {
                    canvas.drawLine(pX + ListPoint.pBorderStart.x, Properties.heightFrame + ListPoint.pBorderStart.y, x, y, Properties.paintLine);
                    canvas.drawLine(pX + ListPoint.pBorderStart.x, Properties.heightFrame + ListPoint.pBorderStart.y, Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.ball_Radius + ListPoint.pBorderStart.y,Properties.paintLine);
                }

            } else {
                float b = (Properties.widthFrame * (y - ListPoint.pBorderStart.y)) / (Properties.widthFrame + Properties.widthFrame - (x - ListPoint.pBorderStart.x));
                float pY = b;
//                    float length = (int) (Math.sqrt((y - pY) * (y - pY) + Math.pow(Properties.widthFrame - x, 2)));
//                    float cX = ((Properties.widthFrame - x) * (length + spaceRadius)) / length;
//                    float cY = ((y - pY) * (length + spaceRadius)) / length;
                //canvas.drawCircle(Properties.widthFrame - cX - Properties.ball_Radius, pY + cY - Properties.ball_Radius, 2 * Properties.ball_Radius, 2 * Properties.ball_Radius);
                //pointAddY.set(Properties.widthFrame - cX, pY + cY);
                if (Math.abs(Properties.widthFrame - (x - ListPoint.pBorderStart.x)) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                    canvas.drawLine(Properties.widthFrame - saiSoCanBang + ListPoint.pBorderStart.x, pY + ListPoint.pBorderStart.y, x, y,Properties.paintLine);
                    canvas.drawLine(Properties.widthFrame - saiSoCanBang + ListPoint.pBorderStart.x, pY + ListPoint.pBorderStart.y, Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.ball_Radius + ListPoint.pBorderStart.y,Properties.paintLine);
                    canvas.drawCircle(Properties.widthFrame - saiSoCanBang + ListPoint.pBorderStart.x, pY + ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall); // điểm đập
                } else {
                    canvas.drawLine(Properties.widthFrame + ListPoint.pBorderStart.x, pY + ListPoint.pBorderStart.y, x, y,Properties.paintLine);
                    canvas.drawLine(Properties.widthFrame + ListPoint.pBorderStart.x, pY + ListPoint.pBorderStart.y, Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.ball_Radius + ListPoint.pBorderStart.y,Properties.paintLine);
                }
            }
            canvas.drawCircle(Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.ball_Radius + ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall);
        }

        if(Properties.listHole[1]){ // vẽ ô thứ 2
            float c = Math.abs(Properties.widthFrame / 2 - (x - ListPoint.pBorderStart.x)); // ta có a + b = c
            float b = (Properties.heightFrame * c) / (Properties.heightFrame + Properties.heightFrame - (y - ListPoint.pBorderStart.y));
            float pX = 0;
            if ((x - ListPoint.pBorderStart.x) > Properties.widthFrame / 2) {
                pX = Properties.widthFrame / 2 + b;
            } else {
                pX = Properties.widthFrame / 2 - b;
            }
//                float length = (int) (Math.sqrt(Math.pow(Math.abs(x - pX), 2) + Math.pow(Properties.heightFrame - y, 2)));
//                float cX = ((Math.abs(x - pX)) * (length + spaceRadius)) / length;
//                float cY = ((Properties.heightFrame - y) * (length + spaceRadius)) / length;
//                if (x < pX) {
//                    cX = pX - cX;
//                } else {
//                    cX = pX + cX;
//                }
//                canvas.drawCircle(cX - Properties.ball_Radius, Properties.heightFrame - cY - Properties.ball_Radius, 2 * Properties.ball_Radius, 2 * Properties.ball_Radius);
//                pointAddY.set(cX, Properties.heightFrame - cY);
            canvas.drawCircle(Properties.widthFrame / 2 + ListPoint.pBorderStart.x, ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall);
            if (Math.abs(Properties.heightFrame - (y - ListPoint.pBorderStart.y)) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                canvas.drawLine(pX + ListPoint.pBorderStart.x, Properties.heightFrame - saiSoCanBang + ListPoint.pBorderStart.y, x, y,Properties.paintLine);
                canvas.drawLine(pX + ListPoint.pBorderStart.x, Properties.heightFrame - saiSoCanBang + ListPoint.pBorderStart.y, Properties.widthFrame / 2 + ListPoint.pBorderStart.x, ListPoint.pBorderStart.y, Properties.paintLine);

                canvas.drawCircle(pX + ListPoint.pBorderStart.x, Properties.heightFrame - saiSoCanBang + ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall);
            } else {
                canvas.drawLine(pX + ListPoint.pBorderStart.x, Properties.heightFrame + ListPoint.pBorderStart.y, x, y,Properties.paintLine);
                canvas.drawLine(pX + ListPoint.pBorderStart.x, Properties.heightFrame + ListPoint.pBorderStart.y, Properties.widthFrame / 2 + ListPoint.pBorderStart.x, ListPoint.pBorderStart.y, Properties.paintLine);
            }
        }

        if(Properties.listHole[2]){ // vẽ ô thứ 3
            if (Properties.reverseDirection) {
                float b = (Properties.heightFrame * (Properties.widthFrame - (x - ListPoint.pBorderStart.x))) / (Properties.heightFrame + Properties.heightFrame - (y - ListPoint.pBorderStart.y));
                float pX = Properties.widthFrame - b;
//                    float length = (int) (Math.sqrt((pX - x) * (pX - x) + Math.pow(Properties.heightFrame - y, 2)));
//                    float cX = ((pX - x) * (length + spaceRadius)) / length;
//                    float cY = ((Properties.heightFrame - y) * (length + spaceRadius)) / length;
//                    canvas.drawCircle(pX - cX - Properties.ball_Radius, Properties.heightFrame - cY - Properties.ball_Radius, 2 * Properties.ball_Radius, 2 * Properties.ball_Radius);
//                    pointAddY.set(pX - cX, Properties.heightFrame - cY);
                if (Math.abs(Properties.heightFrame - (y - ListPoint.pBorderStart.y)) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                    canvas.drawLine(pX + ListPoint.pBorderStart.x, Properties.heightFrame - saiSoCanBang + ListPoint.pBorderStart.y, x, y,Properties.paintLine);
                    canvas.drawLine(pX + ListPoint.pBorderStart.x, Properties.heightFrame - saiSoCanBang + ListPoint.pBorderStart.y, Properties.widthFrame - Properties.ball_Radius - 6 + ListPoint.pBorderStart.x, Properties.ball_Radius + 3 + ListPoint.pBorderStart.y,Properties.paintLine);

                    canvas.drawCircle(pX + ListPoint.pBorderStart.x, Properties.heightFrame - saiSoCanBang + ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall);
                } else {
                    canvas.drawLine(pX + ListPoint.pBorderStart.x, Properties.heightFrame + ListPoint.pBorderStart.y, x, y,Properties.paintLine);
                    canvas.drawLine(pX + ListPoint.pBorderStart.x, Properties.heightFrame + ListPoint.pBorderStart.y, Properties.widthFrame - Properties.ball_Radius - 6 + ListPoint.pBorderStart.x, Properties.ball_Radius + 3 + ListPoint.pBorderStart.y,Properties.paintLine);
                }
            } else {
                float b = (Properties.widthFrame * (y - ListPoint.pBorderStart.y)) / (Properties.widthFrame + (x - ListPoint.pBorderStart.x));
                float pY = b;
//                    float length = (int) (Math.sqrt((y - pY) * (y - pY) + Math.pow(x, 2)));
//                    float cX = ((x) * (length + spaceRadius)) / length;
//                    float cY = ((y - pY) * (length + spaceRadius)) / length;
//                    canvas.drawCircle(cX - Properties.ball_Radius, pY + cY - Properties.ball_Radius, 2 * Properties.ball_Radius, 2 * Properties.ball_Radius);
//                    pointAddY.set(cX, pY + cY);
                if (Math.abs((x - ListPoint.pBorderStart.x)) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                    canvas.drawLine(saiSoCanBang + ListPoint.pBorderStart.x, pY + ListPoint.pBorderStart.y, x, y,Properties.paintLine);
                    canvas.drawLine(saiSoCanBang + ListPoint.pBorderStart.x, pY + ListPoint.pBorderStart.y, Properties.widthFrame - Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.ball_Radius + ListPoint.pBorderStart.y,Properties.paintLine);

                    canvas.drawCircle(saiSoCanBang + ListPoint.pBorderStart.x, pY + ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall);
                } else {
                    canvas.drawLine(ListPoint.pBorderStart.x, pY + ListPoint.pBorderStart.y, x, y,Properties.paintLine);
                    canvas.drawLine(ListPoint.pBorderStart.x, pY + ListPoint.pBorderStart.y, Properties.widthFrame - Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.ball_Radius + ListPoint.pBorderStart.y,Properties.paintLine);
                }
            }
            canvas.drawCircle(Properties.widthFrame - Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.ball_Radius + ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall);
        }

        if(Properties.listHole[3]){ // vẽ ô thứ 4
            if (Properties.reverseDirection) {
                float b = (Properties.heightFrame * (x - ListPoint.pBorderStart.x)) / (Properties.heightFrame + (y - ListPoint.pBorderStart.y));
                float pX = b;
//                    float length = (int) (Math.sqrt((x - pX) * (x - pX) + Math.pow(y, 2)));
//                    float cX = ((x - pX) * (length + spaceRadius)) / length;
//                    float cY = ((y) * (length + spaceRadius)) / length;
//                    canvas.drawCircle(cX + pX - Properties.ball_Radius, cY - Properties.ball_Radius, 2 * Properties.ball_Radius, 2 * Properties.ball_Radius);
//                    pointAddY.set(cX + pX, cY);
                if (Math.abs(y - ListPoint.pBorderStart.y) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                    canvas.drawLine(pX + ListPoint.pBorderStart.x, saiSoCanBang + ListPoint.pBorderStart.y, x, y,Properties.paintLine);
                    canvas.drawLine(pX + ListPoint.pBorderStart.x, saiSoCanBang + ListPoint.pBorderStart.y, Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.heightFrame - Properties.ball_Radius + ListPoint.pBorderStart.y,Properties.paintLine);

                    canvas.drawCircle(pX + ListPoint.pBorderStart.x, saiSoCanBang + ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall);
                } else {
                    canvas.drawLine(pX + ListPoint.pBorderStart.x, ListPoint.pBorderStart.y, x, y,Properties.paintLine);
                    canvas.drawLine(pX + ListPoint.pBorderStart.x, ListPoint.pBorderStart.y, Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.heightFrame - Properties.ball_Radius + ListPoint.pBorderStart.y,Properties.paintLine);
                }
            } else {
                float b = (Properties.widthFrame * (Properties.heightFrame - (y - ListPoint.pBorderStart.y))) / (Properties.widthFrame + Properties.widthFrame - (x - ListPoint.pBorderStart.x));
                float pY = Properties.heightFrame - b;
//                    float length = (int) (Math.sqrt((pY - y) * (pY - y) + Math.pow(Properties.widthFrame - x, 2)));
//                    float cX = ((Properties.widthFrame - x) * (length + spaceRadius)) / length;
//                    float cY = ((pY - y) * (length + spaceRadius)) / length;
//                    canvas.drawCircle(Properties.widthFrame - cX - Properties.ball_Radius, pY - cY - Properties.ball_Radius, 2 * Properties.ball_Radius, 2 * Properties.ball_Radius);
//                    pointAddY.set(Properties.widthFrame - cX, pY - cY);
                if (Math.abs(Properties.widthFrame - (x - ListPoint.pBorderStart.x)) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                    canvas.drawLine(Properties.widthFrame - saiSoCanBang + ListPoint.pBorderStart.x, pY + ListPoint.pBorderStart.y, x, y,Properties.paintLine);
                    canvas.drawLine(Properties.widthFrame - saiSoCanBang + ListPoint.pBorderStart.x, pY + ListPoint.pBorderStart.y, Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.heightFrame - Properties.ball_Radius + ListPoint.pBorderStart.y,Properties.paintLine);

                    canvas.drawCircle(Properties.widthFrame - saiSoCanBang + ListPoint.pBorderStart.x, pY + ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall);
                } else {
                    canvas.drawLine(Properties.widthFrame + ListPoint.pBorderStart.x, pY + ListPoint.pBorderStart.y, x, y,Properties.paintLine);
                    canvas.drawLine(Properties.widthFrame + ListPoint.pBorderStart.x, pY + ListPoint.pBorderStart.y, Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.heightFrame - Properties.ball_Radius + ListPoint.pBorderStart.y,Properties.paintLine);
                }
            }
            canvas.drawCircle(Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.heightFrame - Properties.ball_Radius + ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall);
        }

        if(Properties.listHole[4]){ // vẽ ô thứ 5
            float c = Math.abs(Properties.widthFrame / 2 - (x - ListPoint.pBorderStart.x)); // ta có a + b = c
            float b = (Properties.heightFrame * c) / (Properties.heightFrame + (y - ListPoint.pBorderStart.y));
            float pX = 0;
            if ((x - ListPoint.pBorderStart.x) > Properties.widthFrame / 2) {
                pX = Properties.widthFrame / 2 + b;
            } else {
                pX = Properties.widthFrame / 2 - b;
            }
//                float length = (int) (Math.sqrt(Math.pow((Math.abs(x - pX)), 2) + Math.pow(y, 2)));
//                float cX = ((Math.abs(x - pX)) * (length + spaceRadius)) / length;
//                float cY = ((y) * (length + spaceRadius)) / length;
//                if (x < pX) {
//                    cX = pX - cX;
//                } else {
//                    cX = pX + cX;
//                }
//                canvas.drawCircle(cX - Properties.ball_Radius, cY - Properties.ball_Radius, 2 * Properties.ball_Radius, 2 * Properties.ball_Radius);
//                pointAddY.set(cX, cY);
            if (Math.abs((y - ListPoint.pBorderStart.y)) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                canvas.drawLine(pX + ListPoint.pBorderStart.x, saiSoCanBang + ListPoint.pBorderStart.y, x, y, Properties.paintLine);
                canvas.drawLine(pX + ListPoint.pBorderStart.x, saiSoCanBang + ListPoint.pBorderStart.y, Properties.widthFrame / 2 + ListPoint.pBorderStart.x, Properties.heightFrame - Properties.ball_Radius / 2 + ListPoint.pBorderStart.y, Properties.paintLine);

                canvas.drawCircle(pX + ListPoint.pBorderStart.x, saiSoCanBang + ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall);
            } else {
                canvas.drawLine(pX + ListPoint.pBorderStart.x,ListPoint.pBorderStart.y, x, y, Properties.paintLine);
                canvas.drawLine(pX + ListPoint.pBorderStart.x, ListPoint.pBorderStart.y, Properties.widthFrame / 2 + ListPoint.pBorderStart.x, Properties.heightFrame - Properties.ball_Radius / 2 + ListPoint.pBorderStart.y, Properties.paintLine);
            }
            canvas.drawCircle(Properties.widthFrame / 2 + ListPoint.pBorderStart.x, Properties.heightFrame + ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall);
        }

        if(Properties.listHole[5]){ // vẽ ô thứ 6
            if (Properties.reverseDirection) {
                float b = (Properties.heightFrame * (Properties.widthFrame - (x - ListPoint.pBorderStart.x))) / (Properties.heightFrame + (y - ListPoint.pBorderStart.y));
                float pX = Properties.widthFrame - b;
//                    float length = (int) (Math.sqrt((pX - x) * (pX - x) + Math.pow(y, 2)));
//                    float cX = ((pX - x) * (length + spaceRadius)) / length;
//                    float cY = ((y) * (length + spaceRadius)) / length;
//                    canvas.drawCircle(pX - cX - Properties.ball_Radius, cY - Properties.ball_Radius, 2 * Properties.ball_Radius, 2 * Properties.ball_Radius);
//                    pointAddY.set(pX - cX, cY);
                if (Math.abs(y - ListPoint.pBorderStart.y) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                    canvas.drawLine(pX + ListPoint.pBorderStart.x, saiSoCanBang + ListPoint.pBorderStart.y, x, y, Properties.paintLine);
                    canvas.drawLine(pX + ListPoint.pBorderStart.x, saiSoCanBang + ListPoint.pBorderStart.y, Properties.widthFrame - Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.heightFrame - Properties.ball_Radius + ListPoint.pBorderStart.y, Properties.paintLine);

                    canvas.drawCircle(pX + ListPoint.pBorderStart.x, saiSoCanBang + ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall);
                } else {
                    canvas.drawLine(pX + ListPoint.pBorderStart.x, ListPoint.pBorderStart.y, x, y, Properties.paintLine);
                    canvas.drawLine(pX + ListPoint.pBorderStart.x, ListPoint.pBorderStart.y, Properties.widthFrame - Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.heightFrame - Properties.ball_Radius + ListPoint.pBorderStart.y, Properties.paintLine);
                }
            } else {
                float b = (Properties.widthFrame * (Properties.heightFrame - (y - ListPoint.pBorderStart.y))) / (Properties.widthFrame + (x - ListPoint.pBorderStart.x));
                float pY = Properties.heightFrame - b;
//                    float length = (int) (Math.sqrt((pY - y) * (pY - y) + Math.pow(x, 2)));
//                    float cX = ((x) * (length + spaceRadius)) / length;
//                    float cY = ((pY - y) * (length + spaceRadius)) / length;
//                    canvas.drawCircle(cX - Properties.ball_Radius, pY - cY - Properties.ball_Radius, 2 * Properties.ball_Radius, 2 * Properties.ball_Radius);
//                    pointAddY.set(cX, pY - cY);
                if (Math.abs(x - ListPoint.pBorderStart.x) > customSaiSo) {  // khoảng cách đủ lớn mới có điểm đập
                    canvas.drawLine(saiSoCanBang + ListPoint.pBorderStart.x, pY + ListPoint.pBorderStart.y, x, y, Properties.paintLine);
                    canvas.drawLine(saiSoCanBang + ListPoint.pBorderStart.x, pY + ListPoint.pBorderStart.y, Properties.widthFrame - Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.heightFrame - Properties.ball_Radius + ListPoint.pBorderStart.y, Properties.paintLine);

                    canvas.drawCircle(saiSoCanBang + ListPoint.pBorderStart.x, pY + ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall);
                } else {
                    canvas.drawLine(ListPoint.pBorderStart.x, pY + ListPoint.pBorderStart.y, x, y, Properties.paintLine);
                    canvas.drawLine(ListPoint.pBorderStart.x, pY + ListPoint.pBorderStart.y, Properties.widthFrame - Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.heightFrame - Properties.ball_Radius + ListPoint.pBorderStart.y, Properties.paintLine);
                }
            }
            canvas.drawCircle(Properties.widthFrame - Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.heightFrame - Properties.ball_Radius + ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall);
        }
    }

    public void drawTwoShootLine(Canvas canvas){
        if(Properties.listHole[0]){
            try {
                twoShootLine(canvas, listTwoShotLine[0][0], listTwoShotLine[0][1] ,1);
            }catch (Exception e){}
        }
        if(Properties.listHole[1]){
            try {

            }catch (Exception e){}
            twoShootLine(canvas, listTwoShotLine[1][0], listTwoShotLine[1][1] ,2);
        }
        if(Properties.listHole[2]){
            try {

            }catch (Exception e){}
            twoShootLine(canvas, listTwoShotLine[2][0], listTwoShotLine[2][1] ,3);
        }
        if(Properties.listHole[3]){
            try {

            }catch (Exception e){}
            twoShootLine(canvas, listTwoShotLine[3][0], listTwoShotLine[3][1] ,4);
        }
        if(Properties.listHole[4]){
            try {

            }catch (Exception e){}
            twoShootLine(canvas, listTwoShotLine[4][0], listTwoShotLine[4][1] ,5);
        }
        if(Properties.listHole[5]){
            try{
                twoShootLine(canvas, listTwoShotLine[5][0], listTwoShotLine[5][1] ,6);
            }catch (Exception e){}
        }
    }

    private void twoShootLine(Canvas canvas, float cXTwoShootLine, float cYTwoShootLine, int hole) {
        canvas.drawCircle(ListPoint.pSelected.x + ListPoint.pBorderStart.x,ListPoint.pSelected.y + ListPoint.pBorderStart.y,Properties.ball_Radius,Properties.paintTarget);
        canvas.drawCircle(ListPoint.pSelected.x + ListPoint.pBorderStart.x,ListPoint.pSelected.y + ListPoint.pBorderStart.y,Properties.radius_pSelected,Properties.paintSelectedBall);

        canvas.drawCircle(ListPoint.pCueBall.x + ListPoint.pBorderStart.x, ListPoint.pCueBall.y + ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintTarget);
        canvas.drawCircle(ListPoint.pCueBall.x + ListPoint.pBorderStart.x, ListPoint.pCueBall.y + ListPoint.pBorderStart.y, Properties.radius_pSelected, Properties.paintCueBall);

        canvas.drawCircle(cXTwoShootLine + ListPoint.pBorderStart.x, cYTwoShootLine + ListPoint.pBorderStart.y, Properties.ball_Radius, Properties.paintBall);

        Point pointTwoLineStart = ListPoint.pCueBall;
        Point pointTwoLineEnd = new Point( (int) cXTwoShootLine, (int) cYTwoShootLine);

        // cạnh trái
        if (pointTwoLineStart.y > pointTwoLineEnd.y) {
            double y = (pointTwoLineEnd.x * Math.abs(pointTwoLineEnd.y - pointTwoLineStart.y)) / (pointTwoLineEnd.x + pointTwoLineStart.x) + pointTwoLineEnd.y;
            if(pointTwoLineEnd.x > customSaiSo){
                listPointTwoShootLine.add(new Point(0 + saiSoCanBang, (int) y));
            }else{
                listPointTwoShootLine.add(new Point(0, (int) y));
            }
        } else {
            double y = (pointTwoLineStart.x * Math.abs(pointTwoLineEnd.y - pointTwoLineStart.y)) / (pointTwoLineStart.x + pointTwoLineEnd.x) + pointTwoLineStart.y;
            if(pointTwoLineEnd.x > customSaiSo){
                listPointTwoShootLine.add(new Point(0 + saiSoCanBang, (int) y));
            }else{
                listPointTwoShootLine.add(new Point(0, (int) y));
            }
        }
        // cạnh phải
        if (pointTwoLineStart.y > pointTwoLineEnd.y) {
            double y = ((Properties.widthFrame - pointTwoLineEnd.x) * Math.abs(pointTwoLineEnd.y - pointTwoLineStart.y)) / ((Properties.widthFrame - pointTwoLineEnd.x) + (Properties.widthFrame - pointTwoLineStart.x)) + pointTwoLineEnd.y;
            if((Properties.widthFrame - pointTwoLineEnd.x) > customSaiSo){
                listPointTwoShootLine.add(new Point(Properties.widthFrame - saiSoCanBang, (int) y));
            }else{
                listPointTwoShootLine.add(new Point(Properties.widthFrame, (int) y));
            }
        } else {
            double y = ((Properties.widthFrame - pointTwoLineStart.x) * Math.abs(pointTwoLineEnd.y - pointTwoLineStart.y)) / (2 * Properties.widthFrame - pointTwoLineStart.x - pointTwoLineEnd.x) + pointTwoLineStart.y;
            if((Properties.widthFrame - pointTwoLineEnd.x) > customSaiSo){
                listPointTwoShootLine.add(new Point(Properties.widthFrame - saiSoCanBang, (int) y));
            }else{
                listPointTwoShootLine.add(new Point(Properties.widthFrame, (int) y));
            }
        }
        // cạnh trên
        if (pointTwoLineStart.x > pointTwoLineEnd.x) {
            double x = (pointTwoLineEnd.y * Math.abs(pointTwoLineEnd.x - pointTwoLineStart.x)) / (pointTwoLineEnd.y + pointTwoLineStart.y) + pointTwoLineEnd.x;
            if(pointTwoLineEnd.y > customSaiSo){
                listPointTwoShootLine.add(new Point((int) x, 0 + saiSoCanBang));
            }else{
                listPointTwoShootLine.add(new Point((int) x, 0));
            }
        } else {
            double x = (pointTwoLineStart.y * Math.abs(pointTwoLineEnd.x - pointTwoLineStart.x)) / (pointTwoLineStart.y + pointTwoLineEnd.y) + pointTwoLineStart.x;
            if(pointTwoLineEnd.y > customSaiSo){
                listPointTwoShootLine.add(new Point((int) x, 0 + saiSoCanBang));
            }else{
                listPointTwoShootLine.add(new Point((int) x, 0));
            }
        }
        // cạnh dưới
        if (pointTwoLineStart.x > pointTwoLineEnd.x) {
            double x = ((Properties.heightFrame - pointTwoLineEnd.y) * Math.abs(pointTwoLineEnd.x - pointTwoLineStart.x)) / (2 * Properties.heightFrame - pointTwoLineEnd.y - pointTwoLineStart.y) + pointTwoLineEnd.x;
            if((Properties.heightFrame - pointTwoLineEnd.y) > customSaiSo){
                listPointTwoShootLine.add(new Point((int) x, Properties.heightFrame - saiSoCanBang));
            }else{
                listPointTwoShootLine.add(new Point((int) x, Properties.heightFrame));
            }
        } else {
            double x = ((Properties.heightFrame - pointTwoLineStart.y) * Math.abs(pointTwoLineEnd.x - pointTwoLineStart.x)) / (2 * Properties.heightFrame - pointTwoLineStart.y - pointTwoLineEnd.y) + pointTwoLineStart.x;
            if((Properties.heightFrame - pointTwoLineEnd.y) > customSaiSo){
                listPointTwoShootLine.add(new Point((int) x, Properties.heightFrame - saiSoCanBang));
            }else{
                listPointTwoShootLine.add(new Point((int) x, Properties.heightFrame));
            }
        }

        pointTwoLineEnd.set((int) cXTwoShootLine + ListPoint.pBorderStart.x, (int) cYTwoShootLine + ListPoint.pBorderStart.y); // set để vẽ

        if (direction < listPointTwoShootLine.size()) {
            canvas.drawLine(pointTwoLineStart.x + ListPoint.pBorderStart.x, pointTwoLineStart.y + ListPoint.pBorderStart.y, listPointTwoShootLine.get(direction).x + ListPoint.pBorderStart.x, listPointTwoShootLine.get(direction).y + ListPoint.pBorderStart.y, Properties.paintLine);
            canvas.drawLine(pointTwoLineEnd.x, pointTwoLineEnd.y, listPointTwoShootLine.get(direction).x + ListPoint.pBorderStart.x, listPointTwoShootLine.get(direction).y + ListPoint.pBorderStart.y, Properties.paintLine);
        } else {
            direction = 0;
        }

        switch (hole) {
            case 1: {
                canvas.drawLine(Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.ball_Radius + ListPoint.pBorderStart.y, pointTwoLineEnd.x, pointTwoLineEnd.y, Properties.paintLine);
                break;
            }
            case 2: {
                canvas.drawLine(Properties.widthFrame / 2 + ListPoint.pBorderStart.x, Properties.ball_Radius / 2 + ListPoint.pBorderStart.y, pointTwoLineEnd.x, pointTwoLineEnd.y, Properties.paintLine);
                break;
            }
            case 3: {
                canvas.drawLine(Properties.widthFrame - Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.ball_Radius + ListPoint.pBorderStart.y, pointTwoLineEnd.x, pointTwoLineEnd.y, Properties.paintLine);
                break;
            }
            case 4: {
                canvas.drawLine(Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.heightFrame - Properties.ball_Radius + ListPoint.pBorderStart.y, pointTwoLineEnd.x, pointTwoLineEnd.y, Properties.paintLine);
                break;
            }
            case 5: {
                canvas.drawLine(Properties.widthFrame / 2 + ListPoint.pBorderStart.x, Properties.heightFrame - Properties.ball_Radius / 2 + ListPoint.pBorderStart.y, pointTwoLineEnd.x, pointTwoLineEnd.y, Properties.paintLine);
                break;
            }
            case 6: {
                canvas.drawLine(Properties.widthFrame - Properties.ball_Radius + ListPoint.pBorderStart.x, Properties.heightFrame - Properties.ball_Radius + ListPoint.pBorderStart.y, pointTwoLineEnd.x, pointTwoLineEnd.y, Properties.paintLine);
                break;
            }
        }
    }
}
