package com.demo.mynote.custom_Calender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import androidx.core.view.ViewCompat;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;
import com.demo.mynote.R;


public class MonthViewCustom extends MonthView {
    Paint backgroundPaint;
    Context context1;
    private float mCirclePadding;
    private float mFixedCircleRadius;
    private int mPadding;
    private Paint mPointPaint;
    private float mPointRadius;
    private Paint mTextPaint;
    Paint strokePaint;
    int theme;

    @Override 
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int i, int i2, boolean z) {
        return false;
    }

    public MonthViewCustom(Context context) {
        super(context);
        this.theme = 0;
        this.mPointPaint = new Paint();
        this.strokePaint = new Paint();
        this.backgroundPaint = new Paint();
        this.context1 = context;
        this.mFixedCircleRadius = 10.0f;
        Paint paint = new Paint();
        this.mTextPaint = paint;
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
    }

    @Override 
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int i, int i2) {
        if (calendar.hasScheme()) {
            themes();
            this.mPadding = 4;
            this.mPointRadius = 6.0f;
        }
        canvas.drawCircle(i + (this.mItemWidth / 2), (i2 + this.mItemHeight) - this.mPadding, this.mPointRadius, this.mPointPaint);
    }

    public void theme(int i) {
        this.mPointPaint.setColor(getResources().getColor(i));
        this.strokePaint.setColor(getResources().getColor(i));
        this.backgroundPaint.setColor(getResources().getColor(i));
    }

    public void themes() {
        int i = this.context1.getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        this.theme = i;
        if (i == 0) {
            this.mPointPaint.setColor(getResources().getColor(R.color.green));
            this.strokePaint.setColor(getResources().getColor(R.color.green));
            this.backgroundPaint.setColor(getResources().getColor(R.color.green));
        }
        int i2 = this.theme;
        if (i2 == 1) {
            this.mPointPaint.setColor(getResources().getColor(R.color.green));
            this.strokePaint.setColor(getResources().getColor(R.color.green));
            this.backgroundPaint.setColor(getResources().getColor(R.color.green));
            return;
        }
        if (i2 == 2) {
            this.mPointPaint.setColor(getResources().getColor(R.color.pink));
            this.strokePaint.setColor(getResources().getColor(R.color.pink));
            this.backgroundPaint.setColor(getResources().getColor(R.color.pink));
            return;
        }
        if (i2 == 3) {
            this.mPointPaint.setColor(getResources().getColor(R.color.blue));
            this.strokePaint.setColor(getResources().getColor(R.color.blue));
            this.backgroundPaint.setColor(getResources().getColor(R.color.blue));
            return;
        }
        if (i2 == 4) {
            this.mPointPaint.setColor(getResources().getColor(R.color.purple));
            this.strokePaint.setColor(getResources().getColor(R.color.purple));
            this.backgroundPaint.setColor(getResources().getColor(R.color.purple));
            return;
        }
        if (i2 == 5) {
            this.mPointPaint.setColor(getResources().getColor(R.color.purple));
            this.strokePaint.setColor(getResources().getColor(R.color.purple));
            this.backgroundPaint.setColor(getResources().getColor(R.color.purple));
            return;
        }
        if (i2 == 6) {
            this.mPointPaint.setColor(getResources().getColor(R.color.parrot));
            this.strokePaint.setColor(getResources().getColor(R.color.parrot));
            this.backgroundPaint.setColor(getResources().getColor(R.color.parrot));
            return;
        }
        if (i2 == 7) {
            theme(R.color.themedark7);
            return;
        }
        if (i2 == 8) {
            theme(R.color.themedark8);
            return;
        }
        if (i2 == 9) {
            theme(R.color.themedark9);
            return;
        }
        if (i2 == 10) {
            theme(R.color.themedark10);
            return;
        }
        if (i2 == 11) {
            theme(R.color.themedark11);
            return;
        }
        if (i2 == 12) {
            theme(R.color.themedark12);
            return;
        }
        if (i2 == 13) {
            theme(R.color.themedark13);
            return;
        }
        if (i2 == 14) {
            theme(R.color.themedark14);
            return;
        }
        if (i2 == 15) {
            theme(R.color.themedark15);
        } else if (i2 == 16) {
            theme(R.color.themedark16);
        } else if (i2 == 17) {
            theme(R.color.themedark17);
        }
    }

    private int dipToPx(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    @Override 
    protected void onDrawText(Canvas canvas, Calendar calendar, int i, int i2, boolean z, boolean z2) {
        float f = ((float) this.mItemWidth / 2) + i;
        float f2 = ((float) this.mItemHeight / 2) + i2;
        Paint paint = new Paint();
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(7.0f);
        RectF rectF = new RectF(((float) this.mItemWidth / 6) + i, ((float) this.mItemHeight / 8) + i2, i + ((this.mItemWidth * 5) / 6), i2 + ((this.mItemHeight * 7) / 8));
        if (calendar.isCurrentDay()) {
            this.strokePaint.setStyle(Paint.Style.STROKE);
            themes();
            this.strokePaint.setStrokeWidth(7.0f);
            float f3 = this.mFixedCircleRadius;
            canvas.drawRoundRect(rectF, f3, f3, this.strokePaint);
        }
        Paint paint2 = new Paint();
        if (z2) {
            themes();
            float f4 = this.mFixedCircleRadius;
            canvas.drawRoundRect(rectF, f4, f4, this.backgroundPaint);
            paint2.setColor(-1);
        } else {
            themes();
            int i3 = this.theme;
            if (i3 == 5 || i3 == 15 || i3 == 16 || i3 == 17) {
                paint2.setColor(-1);
            } else {
                paint2.setColor(ViewCompat.MEASURED_STATE_MASK);
            }
            this.backgroundPaint.setColor(Color.parseColor("#80FFFFFF"));
            float f5 = this.mFixedCircleRadius;
            canvas.drawRoundRect(rectF, f5, f5, this.backgroundPaint);
        }
        paint2.setTypeface(Typeface.DEFAULT_BOLD);
        paint2.setTextSize(4.0f);
        String valueOf = String.valueOf(calendar.getDay());
        Rect rect = new Rect();
        paint2.getTextBounds(valueOf, 0, valueOf.length(), rect);
        rect.width();
        rect.height();
        paint2.setTextSize(paint2.getTextSize() * 6.5f);
        paint2.getTextBounds(valueOf, 0, valueOf.length(), rect);
        canvas.drawText(valueOf, f - (rect.width() / 2.0f), f2 + (rect.height() / 2.0f), paint2);
    }
}
