package e.oliver.growbotcontrollerv2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by oliver on 27.02.2018.
 */

@SuppressLint("AppCompatCustomView")
public class ProgressSeekerBar extends android.widget.SeekBar {
    public ProgressSeekerBar(Context context) {
        super(context);
    }

    public ProgressSeekerBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ProgressSeekerBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);
        int thumb_x = (int) (((double) this.getProgress() / this.getMax()) * (double) this.getWidth());
        float middle = (float) (this.getHeight());

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(25);
        c.drawText("" + this.getProgress(), thumb_x, middle, paint);
    }

}

