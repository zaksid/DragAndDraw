package com.bignerdranch.android.draganddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class BoxDrawingView extends View {

    public static final String TAG = "BoxDrawingView";


    private ArrayList<Box> boxes = new ArrayList<>();
    private Box currentBox;
    private Paint boxPaint;
    private Paint backgroundPaint;

    // Used when creating the view in code
    public BoxDrawingView(Context context) {
        this(context, null);
    }

    // Used when inflating the view from XML
    public BoxDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        boxPaint = new Paint();
        boxPaint.setColor(0x22ff0000);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(0xfff8efe0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(backgroundPaint);

        for (Box box : boxes) {
            float left = Math.min(box.getOrigin().x, box.getCurrent().x);
            float right = Math.max(box.getOrigin().x, box.getCurrent().x);
            float top = Math.min(box.getOrigin().y, box.getCurrent().y);
            float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);

            canvas.drawRect(left, top, right, bottom, boxPaint);
        }
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        PointF current = new PointF(event.getX(), event.getY());

        Log.i(TAG, "Received event at x=" + current.x + ", y=" + current.y + " : ");

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "ACTION_DOWN");

                // Reset drawing state
                currentBox = new Box(current);
                boxes.add(currentBox);
                break;

            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "ACTION_MOVE");

                if (currentBox != null) {
                    currentBox.setCurrent(current);
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
                Log.i(TAG, "ACTION_UP");

                currentBox = null;
                break;

            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "ACTION_CANCEL");

                currentBox = null;
                break;
        }

        return true;
    }
}
