package myfirstwords.mynationdreams;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout green, yellow, red, blue;
    private ImageView imageview1, imageview2, imageview3, imageview4;
    private ImageView ivMascot;
    private MediaPlayer mediaPlayer;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private PrefsHelper prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        prefs = new PrefsHelper(this);
        prefs.startSession();

        ivMascot = findViewById(R.id.iv_mascot);
        green = findViewById(R.id.green);
        yellow = findViewById(R.id.yellow);
        red = findViewById(R.id.red);
        blue = findViewById(R.id.blue);
        imageview1 = findViewById(R.id.imageview1);
        imageview2 = findViewById(R.id.imageview2);
        imageview3 = findViewById(R.id.imageview3);
        imageview4 = findViewById(R.id.imageview4);

        setupMascot();
        setupColoredSquares();
        loadImages();
        animateSplash();
    }

    private void setupMascot() {
        if (prefs.isSetupDone()) {
            ivMascot.setImageResource(prefs.isBoy()
                ? R.drawable.mascot_boy : R.drawable.mascot_girl);
        } else {
            ivMascot.setImageResource(R.drawable.mascot_boy);
        }
        ivMascot.setAlpha(0f);
        ivMascot.animate().alpha(1f).setDuration(600).start();
        startMascotWave();
    }

    private void startMascotWave() {
        ObjectAnimator wave = ObjectAnimator.ofFloat(ivMascot, "rotation", 0f, 15f, -15f, 10f, -10f, 0f);
        wave.setDuration(1200);
        wave.setStartDelay(400);
        wave.setInterpolator(new AccelerateDecelerateInterpolator());
        wave.start();
    }

    private void setupColoredSquares() {
        roundAndColor(green, "#4CAF50");
        roundAndColor(yellow, "#FFB84C");
        roundAndColor(red, "#F72585");
        roundAndColor(blue, "#4895EF");
    }

    private void roundAndColor(View v, String color) {
        GradientDrawable d = new GradientDrawable();
        d.setShape(GradientDrawable.RECTANGLE);
        d.setCornerRadius(20f);
        d.setColor(android.graphics.Color.parseColor(color));
        v.setBackground(d);
    }

    private void loadImages() {
        loadImg(imageview1, "ani_8.png");
        loadImg(imageview2, "frt_8.png");
        loadImg(imageview3, "toy_4.png");
        loadImg(imageview4, "tool_6.png");
    }

    private void loadImg(ImageView iv, String assetPath) {
        try {
            java.io.InputStream is = getAssets().open(assetPath);
            Drawable d = Drawable.createFromStream(is, null);
            iv.setImageDrawable(d);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void animateSplash() {
        // Start off-screen
        green.setTranslationY(-500f);
        yellow.setTranslationY(-500f);
        red.setTranslationY(-500f);
        blue.setTranslationY(-500f);
        imageview1.setScaleX(0f);
        imageview2.setScaleX(0f);
        imageview3.setScaleX(0f);
        imageview4.setScaleX(0f);
        imageview1.setScaleY(0f);
        imageview2.setScaleY(0f);
        imageview3.setScaleY(0f);
        imageview4.setScaleY(0f);

        playJump();
        animateDrop(green, 0);
        animatePopIn(imageview1, 300);

        handler.postDelayed(() -> { playJump(); animateDrop(red, 0); }, 400);
        handler.postDelayed(() -> animatePopIn(imageview3, 0), 700);

        handler.postDelayed(() -> { playJump(); animateDrop(yellow, 0); }, 800);
        handler.postDelayed(() -> animatePopIn(imageview2, 0), 1100);

        handler.postDelayed(() -> { playJump(); animateDrop(blue, 0); }, 1200);
        handler.postDelayed(() -> animatePopIn(imageview4, 0), 1500);

        // Navigate after animation
        handler.postDelayed(this::navigateNext, 2800);
    }

    private void animateDrop(View v, long delay) {
        ObjectAnimator a = ObjectAnimator.ofFloat(v, "translationY", -500f, 0f);
        a.setDuration(500);
        a.setStartDelay(delay);
        a.setInterpolator(new OvershootInterpolator(0.8f));
        a.start();
    }

    private void animatePopIn(View v, long delay) {
        ObjectAnimator sx = ObjectAnimator.ofFloat(v, "scaleX", 0f, 1f);
        ObjectAnimator sy = ObjectAnimator.ofFloat(v, "scaleY", 0f, 1f);
        sx.setDuration(350);
        sy.setDuration(350);
        sx.setStartDelay(delay);
        sy.setStartDelay(delay);
        sx.setInterpolator(new OvershootInterpolator());
        sy.setInterpolator(new OvershootInterpolator());
        sx.start();
        sy.start();
    }

    private void playJump() {
        try {
            stopMedia();
            mediaPlayer = new MediaPlayer();
            android.content.res.AssetFileDescriptor fd =
                getAssets().openFd("sound effects/jump.mp3");
            mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            fd.close();
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateNext() {
        Intent intent;
        if (!prefs.isSetupDone()) {
            intent = new Intent(this, SetupActivity.class);
        } else {
            intent = new Intent(this, HmeActivity.class);
        }
        startActivity(intent);
        finish();
    }

    private void stopMedia() {
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) mediaPlayer.stop();
                mediaPlayer.release();
            } catch (Exception ignored) {}
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        stopMedia();
    }
}
