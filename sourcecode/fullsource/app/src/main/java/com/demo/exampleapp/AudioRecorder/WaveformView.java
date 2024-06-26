package com.demo.exampleapp.AudioRecorder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class WaveformView extends View {
    private ArrayList<Float> amplitudes;
    private float d;
    private float maxSpikes;
    private Paint paint;
    private float radius;
    private float sh;
    private ArrayList<RectF> spikes;
    private float sw;
    private float w;

    public WaveformView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.amplitudes = new ArrayList<>();
        this.d = 6.0f;
        this.maxSpikes = 0.0f;
        this.paint = new Paint();
        this.radius = 6.0f;
        this.sh = 400.0f;
        this.spikes = new ArrayList<>();
        this.sw = 0.0f;
        this.w = 9.0f;
        this.paint.setColor(Color.rgb(244, 81, 30));
        this.sw = getResources().getDisplayMetrics().widthPixels;


        this.maxSpikes = (int) (0 / (this.w + this.d));
    }

    public void addAmplitude(float f) {
        this.amplitudes.add(Float.valueOf(Math.min(((int) f) / 7, 400)));
        this.spikes.clear();
        ArrayList<Float> arrayList = this.amplitudes;
        List<Float> subList = arrayList.subList(Math.max(arrayList.size() - ((int) this.maxSpikes), 0), this.amplitudes.size());
        for (int i = 0; i < subList.size(); i++) {
            float f2 = this.sw - (i * (this.w + this.d));
            float floatValue = (this.sh / 2.0f) - (subList.get(i).floatValue() / 2.0f);
            this.spikes.add(new RectF(f2, floatValue, this.w + f2, subList.get(i).floatValue() + floatValue));
        }
        invalidate();
    }

    @Override 
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Iterator<RectF> it = this.spikes.iterator();
        while (it.hasNext()) {
            float f = this.radius;
            canvas.drawRoundRect(it.next(), f, f, this.paint);
        }
    }

    public ArrayList<Float> clear() {
        ArrayList<Float> arrayList = (ArrayList) this.amplitudes.clone();
        this.amplitudes.clear();
        this.spikes.clear();
        invalidate();
        return arrayList;
    }
}
