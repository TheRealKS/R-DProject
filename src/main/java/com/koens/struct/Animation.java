package com.koens.struct;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Paint;

public class Animation {

    private Bitmap[] frames;
    private int frameIndex;
    private float frameTime;

    private boolean isPlaying = false;
    private long lastFrame;

    public boolean isPlaying()
    {
        return isPlaying;
    }

    public void play()
    {
        isPlaying = true;
        frameIndex = 0;
        lastFrame = System.currentTimeMillis();
    }

    public void stop()
    {
        isPlaying = false;
    }

    public Animation(Bitmap[] frames, float animationTime)
    {
        this.frames = frames;
        frameIndex = 0;
        frameTime = animationTime/frames.length;
        lastFrame = System.currentTimeMillis();
    }

    public void Draw(Canvas canvas, Rect destination)
    {
        if(!isPlaying)
        {
            return;
        }

        canvas.drawBitmap(frames[frameIndex], null, destination, new Paint());

    }

    public void Update()
    {
        if(!isPlaying)
        {
            return;
        }

        if(System.currentTimeMillis()-lastFrame > frameTime*1000)
        {
            frameIndex++;
            frameIndex = frameIndex >= frames.length ? 0 : frameIndex;
            lastFrame = System.currentTimeMillis();
        }
    }
}
