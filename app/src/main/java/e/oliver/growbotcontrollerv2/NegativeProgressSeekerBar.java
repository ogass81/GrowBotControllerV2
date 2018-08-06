package e.oliver.growbotcontrollerv2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

@SuppressLint("AppCompatCustomView")
public class NegativeProgressSeekerBar extends android.widget.SeekBar {
    public NegativeProgressSeekerBar(Context context) {
        super(context);
    }

    public NegativeProgressSeekerBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public NegativeProgressSeekerBar(Context context, AttributeSet attrs) {
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
        c.drawText("" + this.getActualProgress(), thumb_x, middle, paint);
    }

    private String getActualProgress() {
        String label;
        if (this.getProgress() < 25) {
            label = "-" + 25 - this.getProgress() + "%";
        } else
            label = "+" + this.getProgress() - 25 + "%";
        return label;
    }

}
