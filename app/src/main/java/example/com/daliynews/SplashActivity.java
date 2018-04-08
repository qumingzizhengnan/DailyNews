package example.com.daliynews;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


/**
 * use for splash UI
 */
public class SplashActivity extends AppCompatActivity {
    //延迟3秒
    private static final long SPLASH_DELAY_MILLIS = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        showSplash();
    }

    /**
     * start MainActivity
     */

    public class Splashhandler implements Runnable{

        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    Splashhandler splashhandler;
    Handler x;
    public void showSplash(){
        x = new Handler();
        splashhandler = new Splashhandler();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(x!=null&&splashhandler!=null){
            x.removeCallbacks(splashhandler);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(x!=null&&splashhandler!=null){
            x.postDelayed(splashhandler, SPLASH_DELAY_MILLIS);
        }
    }

}
