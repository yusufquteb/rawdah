package myfirstwords.mynationdreams;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

public class MatchGameActivity extends AppCompatActivity {

    private ImageView ivTarget, ivMascot;
    private TextView wordOpt1, wordOpt2, wordOpt3, wordOpt4;
    private TextView tvScore, tvFeedback;
    private ConfettiView confetti;

    private List<AlphabetData.AnimalSoundItem> allAnimals;
    private AlphabetData.AnimalSoundItem currentAnimal;
    private List<AlphabetData.AnimalSoundItem> options = new ArrayList<>();
    private SoundManager soundManager;
    private MediaPlayer mediaPlayer;
    private int score = 0;
    private boolean answered = false;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private PrefsHelper prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_game);

        prefs = new PrefsHelper(this);
        soundManager = new SoundManager(this);
        allAnimals = AlphabetData.getAnimalSoundsForGame();

        ivTarget   = findViewById(R.id.iv_target);
        ivMascot   = findViewById(R.id.iv_mascot);
        wordOpt1   = findViewById(R.id.word_opt1);
        wordOpt2   = findViewById(R.id.word_opt2);
        wordOpt3   = findViewById(R.id.word_opt3);
        wordOpt4   = findViewById(R.id.word_opt4);
        tvScore    = findViewById(R.id.tv_score);
        tvFeedback = findViewById(R.id.tv_feedback);
        confetti   = findViewById(R.id.confetti);

        ((ImageView) findViewById(R.id.btn_back)).setOnClickListener(v -> finish());
        ivMascot.setImageResource(prefs.isBoy() ? R.drawable.mascot_boy : R.drawable.mascot_girl);

        loadQuestion();
    }

    private void loadQuestion() {
        answered = false;
        tvFeedback.setText("");
        resetButtons();

        Collections.shuffle(allAnimals);
        currentAnimal = allAnimals.get(0);

        options.clear();
        options.add(currentAnimal);
        for (int i = 1; options.size() < 4; i++) {
            options.add(allAnimals.get(i));
        }
        Collections.shuffle(options);

        loadImg(ivTarget, "animals/images/" + currentAnimal.imageFolder + "/Solution.png");

        // Play name of animal
        playAnimalName(currentAnimal);

        setWordOpt(wordOpt1, options.get(0), 0);
        setWordOpt(wordOpt2, options.get(1), 1);
        setWordOpt(wordOpt3, options.get(2), 2);
        setWordOpt(wordOpt4, options.get(3), 3);
    }

    private void setWordOpt(TextView tv, AlphabetData.AnimalSoundItem animal, int idx) {
        tv.setText(animal.animalNameAr);
        tv.setOnClickListener(v -> checkAnswer(animal, tv));
    }

    private void checkAnswer(AlphabetData.AnimalSoundItem chosen, TextView tv) {
        if (answered) return;
        answered = true;

        boolean correct = chosen == currentAnimal;
        setTvResult(tv, correct);

        if (correct) {
            score++;
            tvScore.setText(String.valueOf(score));
            tvFeedback.setTextColor(ContextCompat.getColor(this, R.color.success_green));
            tvFeedback.setText(EncouragementHelper.getCorrectMessage(this));
            confetti.burst();
            soundManager.playCelebration();
            animateMascot(true);
            handler.postDelayed(this::loadQuestion, 1800);
        } else {
            tvFeedback.setTextColor(ContextCompat.getColor(this, R.color.error_red));
            tvFeedback.setText(EncouragementHelper.getWrongMessage(this));
            highlightCorrect();
            animateMascot(false);
            handler.postDelayed(this::loadQuestion, 2200);
        }
    }

    private void setTvResult(TextView tv, boolean correct) {
        GradientDrawable d = new GradientDrawable();
        d.setShape(GradientDrawable.RECTANGLE);
        d.setCornerRadius(40f);
        d.setColor(ContextCompat.getColor(this,
            correct ? R.color.game_correct : R.color.game_wrong));
        tv.setBackground(d);
        tv.setTextColor(0xFFFFFFFF);
    }

    private void highlightCorrect() {
        TextView[] tvs = {wordOpt1, wordOpt2, wordOpt3, wordOpt4};
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i) == currentAnimal) { setTvResult(tvs[i], true); break; }
        }
    }

    private void resetButtons() {
        TextView[] tvs = {wordOpt1, wordOpt2, wordOpt3, wordOpt4};
        for (TextView tv : tvs) {
            tv.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_game_option));
            tv.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        }
    }

    private void animateMascot(boolean happy) {
        if (happy) {
            ivMascot.animate().scaleX(1.2f).scaleY(1.2f).setDuration(150)
                .withEndAction(() -> ivMascot.animate().scaleX(1f).scaleY(1f)
                    .setDuration(200).setInterpolator(new OvershootInterpolator()).start()).start();
        } else {
            ivMascot.animate().translationX(15f).setDuration(60)
                .withEndAction(() -> ivMascot.animate().translationX(-15f).setDuration(60)
                    .withEndAction(() -> ivMascot.animate().translationX(0f).setDuration(60).start())
                    .start()).start();
        }
    }

    private void playAnimalName(AlphabetData.AnimalSoundItem animal) {
        stopMedia();
        try {
            mediaPlayer = new MediaPlayer();
            android.content.res.AssetFileDescriptor fd =
                getAssets().openFd("animals/sounds/speech/Arabic/" + animal.imageFolder + ".mp3");
            mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            fd.close();
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void loadImg(ImageView iv, String path) {
        try {
            InputStream is = getAssets().open(path);
            iv.setImageDrawable(Drawable.createFromStream(is, null));
            is.close();
        } catch (Exception e) { iv.setImageResource(R.drawable.default_image); }
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
