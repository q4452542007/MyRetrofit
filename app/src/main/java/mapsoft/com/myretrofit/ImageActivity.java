package mapsoft.com.myretrofit;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author djl
 * @function
 */

public class ImageActivity extends Activity {

    private AutoSwitchImageView mImageView1;
    private AutoSwitchImageView mImageView2;
    private AutoSwitchImageView mImageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mImageView1 = (AutoSwitchImageView) findViewById(R.id.image_view1);
        mImageView2 = (AutoSwitchImageView) findViewById(R.id.image_view2);
        mImageView3 = (AutoSwitchImageView) findViewById(R.id.image_view3);
        mImageView1.start(Tool.getImages(3,0));
        mImageView2.start(Tool.getImages(3,1));
        mImageView3.start(Tool.getImages(3,2));
    }
}
