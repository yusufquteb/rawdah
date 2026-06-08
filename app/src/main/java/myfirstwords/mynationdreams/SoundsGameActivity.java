package myfirstwords.mynationdreams;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SoundsGameActivity extends AppCompatActivity {

    private ImageView ivMascot;
    private LinearLayout btnPlaySound, option1, option2, option3, option4;
    private ImageView imgOpt1, imgOpt2, imgOpt3, imgOpt4;
    private TextView tvOpt1, tvOpt2, tvOpt3, tvOpt4;
    private TextView tvScore, tvFeedback;
    private ConfettiView confetti;

    private List<AlphabetData.AnimalSoundItem> allAnimals;
    private AlphabetData.AnimalSoundItem currentAnimal;
    private List<AlphabetData.AnimalSoundItem> options = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private SoundManager soundManager;
    private int score = 0;
    private boolean answered = false;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private PrefsHelper prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sounds_game);

        prefs = new PrefsHelper(this);
        soundManager = new SoundManager(this);
        allAnimals = AlphabetData.getAnimalSoundsForGame();

        initViews();
        setupMascot();
        loadNextQuestion();
    }

    private void initViews() {
        ivMascot      = findViewById(R.id.iv_mascot_game);
        btnPlaySound  = findViewById(R.id.btn_play_sound);
        option1       = findViewById(R.id.option1);
        option2       = findViewById(R.id.option2);
        option3       = findViewById(R.id.option3);
        option4       = findViewById(R.id.option4);
        imgOpt1       = findViewById(R.id.img_opt1);
        imgOpt2       = findViewById(R.id.img_opt2);
        imgOpt3       = findViewById(R.id.img_opt3);
        imgOpt4       = findViewById(R.id.img_opt4);
        tvOpt1        = findViewById(R.id.tv_opt1);
        tvOpt2        = findViewById(R.id.tv_opt2);
        tvOpt3        = findViewById(R.id.tv_opt3);
        tvOpt4        = findViewById(R.id.tv_opt4);
        tvScore       = findViewById(R.id.tv_score);
        tvFeedback    = findViewById(R.id.tv_feedback);
        confetti      = findViewById(R.id.confetti);

        ((ImageView) findViewById(R.id.btn_back)).setOnClickListener(v -> finish());
        btnPlaySound.setOnClickListener(v -> playCurrentSound());
    }

    private void setupMascot() {
        ivMascot.setImageResource(prefs.isBoy()
            ? R.drawable.mascot_boy : R.drawable.mascot_girl);
    }

    private void loadNextQuestion() {
        if (allAnimals.size() < 4) { finish(); return; }
        answered = false;
        tvFeedback.setText("");
        resetOptionBackgrounds();

        Collections.shuffle(allAnimals);
        currentAnimal = allAnimals.get(0);

        options.clear();
        options.add(currentAnimal);
        for (int i = 1; options.size() < 4 && i < allAnimals.size(); i++) {
            if (!options.contains(allAnimals.get(i))) options.add(allAnimals.get(i));
        }
        Collections.shuffle(options);

        loadOptionInto(options.get(0), imgOpt1, tvOpt1, option1, 0);
        loadOptionInto(options.get(1), imgOpt2, tvOpt2, option2, 1);
        loadOptionInto(options.get(2), imgOpt3, tvOpt3, option3, 2);
        loadOptionInto(options.get(3), imgOpt4, tvOpt4, option4, 3);

        // Play sound after short delay
        handler.postDelayed(this::playCurrentSound, 600);
    }

    private void loadOptionInto(AlphabetData.AnimalSoundItem animal,
                                  ImageView iv, TextView tv,
                                  LinearLayout card, int idx) {
        tv.setText(animal.animalNameAr);
        loadImg(iv, "animals/images/" + animal.imageFolder + "/Solution.png");

        LinearLayout[] cards = {option1, option2, option3, option4};
        cards[idx].setOnClickListener(v -> checkAnswer(animal, cards[idx]));
    }

    private void checkAnswer(AlphabetData.AnimalSoundItem chosen, LinearLayout card) {
        if (answered) return;
        answered = true;

        boolean correct = chosen == currentAnimal;
        setOptionResult(card, correct);

        if (correct) {
            score++;
            tvScore.setText(String.valueOf(score));
            tvFeedback.setTextColor(ContextCompat.getColor(this, R.color.success_green));
            tvFeedback.setText(EncouragementHelper.getCorrectMessage(this));
            confetti.burst();
            soundManager.playCelebration();
            animateMascot(true);
            handler.postDelayed(this::loadNextQuestion, 1800);
        } else {
            tvFeedback.setTextColor(ContextCompat.getColor(this, R.color.error_red));
            tvFeedback.setText(EncouragementHelper.getWrongMessage(this));
            // Highlight correct answer
            highlightCorrect();
            animateMascot(false);
            handler.postDelayed(this::loadNextQuestion, 2200);
        }
    }

    private void setOptionResult(LinearLayout card, boolean correct) {
        GradientDrawable d = new GradientDrawable();
        d.setShape(GradientDrawable.RECTANGLE);
        d.setCornerRadius(40f);
        d.setColor(ContextCompat.getColor(this,
            correct ? R.color.game_correct : R.color.game_wrong));
        card.setBackground(d);
    }

    private void highlightCorrect() {
        LinearLayout[] cards = {option1, option2, option3, option4};
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i) == currentAnimal) {
                setOptionResult(cards[i], true);
                break;
            }
        }
    }

    private void resetOptionBackgrounds() {
        LinearLayout[] cards = {option1, option2, option3, option4};
        for (LinearLayout c : cards) {
            c.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_game_option));
        }
    }

    private void animateMascot(boolean happy) {
        if (happy) {
            ivMascot.animate().scaleX(1.2f).scaleY(1.2f).setDuration(150)
                .withEndAction(() ->
                    ivMascot.animate().scaleX(1f).scaleY(1f).setDuration(200)
                        .setInterpolator(new OvershootInterpolator()).start()
                ).start();
        } else {
            ivMascot.animate().translationX(20f).setDuration(60)
                .withEndAction(() ->
                    ivMascot.animate().translationX(-20f).setDuration(60)
                        .withEndAction(() ->
                            ivMascot.animate().translationX(0f).setDuration(60).start()
                        ).start()
                ).start();
        }
    }

    private void playCurrentSound() {
        stopMedia();
        try {
            mediaPlayer = new MediaPlayer();
            android.content.res.AssetFileDescriptor fd =
                getAssets().openFd("animals/sounds/onomatopoeia/" + currentAnimal.soundFile + ".mp3");
            mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            fd.close();
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadImg(ImageView iv, String path) {
        try {
            InputStream is = getAssets().open(path);
            Bitmap bm = BitmapFactory.decodeStream(is);
            is.close();
            if (bm != null) iv.setImageBitmap(bm);
            else iv.setImageResource(R.drawable.default_image);
        } catch (Exception e) {
            iv.setImageResource(R.drawable.default_image);
        }
    }

    private void stopMedia() {
        if (mediaPlayer != null) {
            try { if (mediaPlayer.isPlaying()) mediaPlayer.stop(); mediaPlayer.release(); }
            catch (Exception ignored) {}
            mediaPlayer = null;
        }
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        stopMedia();
        soundManager.release();
    }
}
