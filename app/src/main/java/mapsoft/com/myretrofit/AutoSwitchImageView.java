package mapsoft.com.myretrofit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.List;

public class AutoSwitchImageView extends ImageView {

    private List<Bitmap> images;

    private AnimationDrawable mAnimationDrawable;

    private static final int AUTO_SWITCH_TIME = 2000;

    public AutoSwitchImageView(Context context) {
        super(context, null);
    }

    public AutoSwitchImageView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public AutoSwitchImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void start(List<Bitmap> bitmaps) {
        images = bitmaps;
        mAnimationDrawable = new AnimationDrawable();
        for (Bitmap pBitmap : images) {
            BitmapDrawable pDrawable = new BitmapDrawable(pBitmap);
            mAnimationDrawable.addFrame(pDrawable,AUTO_SWITCH_TIME);
        }

        mAnimationDrawable.setOneShot(false);
        setBackground(mAnimationDrawable);
        mAnimationDrawable.start();
    }




}