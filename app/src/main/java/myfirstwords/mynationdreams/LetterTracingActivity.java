package myfirstwords.mynationdreams;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.List;

public class LetterTracingActivity extends AppCompatActivity {

    private LetterTracingView tracingView;
    private TextView tvHeaderLetter, tvLetterName, tvFeedback, btnClear, btnCheck, btnPrev, btnNext;
    private ImageView ivMascot;
    private ConfettiView confetti;

    private String letter;
    private String letterName;
    private boolean isArabic;
    private int position;
    private int totalLetters;

    private SoundManager soundManager;
    private MediaPlayer mediaPlayer;
    private PrefsHelper prefs;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_tracing);

        prefs        = new PrefsHelper(this);
        soundManager = new SoundManager(this);

        letter     = getIntent().getStringExtra("letter");
        letterName = getIntent().getStringExtra("letter_name");
        isArabic   = getIntent().getBooleanExtra("is_arabic", true);
        position   = getIntent().getIntExtra("position", 0);

        totalLetters = isArabic
            ? AlphabetData.getArabicAlphabet().size()
            : 26;

        tracingView    = findViewById(R.id.tracing_view);
        tvHeaderLetter = findViewById(R.id.tv_header_letter);
        tvLetterName   = findViewById(R.id.tv_letter_name);
        tvFeedback     = findViewById(R.id.tv_feedback);
        btnClear       = findViewById(R.id.btn_clear);
        btnCheck       = findViewById(R.id.btn_check);
        btnPrev        = findViewById(R.id.btn_prev);
        btnNext        = findViewById(R.id.btn_next);
        ivMascot       = findViewById(R.id.iv_mascot);
        confetti       = findViewById(R.id.confetti);

        ((ImageView) findViewById(R.id.btn_back)).setOnClickListener(v -> finish());
        ivMascot.setImageResource(prefs.isBoy() ? R.drawable.mascot_boy : R.drawable.mascot_girl);

        btnClear.setOnClickListener(v -> {
            bounce(btnClear);
            tracingView.reset();
            tvFeedback.setText("");
        });

        btnCheck.setOnClickListener(v -> {
            bounce(btnCheck);
            checkResult();
        });

        btnPrev.setOnClickListener(v -> navigate(-1));
        btnNext.setOnClickListener(v -> navigate(1));

        tracingView.setTracingListener(new LetterTracingView.TracingListener() {
            @Override
            public void onTracingComplete() {
                showSuccess();
            }
            @Override
            public void onProgressChanged(int percent) {
                // Could show progress bar here
            }
        });

        loadLetter(letter, letterName, isArabic);
    }

    private void loadLetter(String ltr, String name, boolean arabic) {
        this.letter     = ltr;
        this.letterName = name;
        this.isArabic   = arabic;

        tvHeaderLetter.setText(ltr);
        tvLetterName.setText(name);
        tvFeedback.setText("");

        tracingView.setLetter(ltr, arabic);

        // Play letter sound
        handler.postDelayed(() -> playLetterSound(ltr, arabic), 400);

        // Update nav buttons
        btnPrev.setAlpha(position > 0 ? 1f : 0.3f);
        btnNext.setAlpha(position < totalLetters - 1 ? 1f : 0.3f);
    }

    private void navigate(int delta) {
        int newPos = position + delta;
        if (newPos < 0 || newPos >= totalLetters) return;
        position = newPos;

        if (isArabic) {
            AlphabetData.LetterEntry e = AlphabetData.getArabicAlphabet().get(position);
            loadLetter(e.letter, e.letterName, true);
        } else {
            String[] letters = {"A","B","C","D","E","F","G","H","I","J","K","L","M",
                                "N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
            String[] names   = {"Alef","Baa","Cee","Dee","Ee","Ef","Gee","Aitch","Eye","Jay",
                                 "Kay","El","Em","En","Oh","Pee","Cue","Ar","Es","Tee","You",
                                 "Vee","Double-U","Ex","Wye","Zee"};
            loadLetter(letters[position], names[position], false);
        }
    }

    private void checkResult() {
        // The tracing view fires onTracingComplete automatically at 70%,
        // but the button lets the child manually check at any time.
        tvFeedback.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));
        tvFeedback.setText(getString(R.string.writing_try_again));
        animateMascot(false);
    }

    private void showSuccess() {
        tvFeedback.setTextColor(ContextCompat.getColor(this, R.color.success_green));
        tvFeedback.setText(getString(R.string.writing_success));
        confetti.burst();
        soundManager.playCelebration();
        animateMascot(true);
        prefs.addStars(1);

        handler.postDelayed(() -> {
            if (position < totalLetters - 1) {
                navigate(1);
            }
        }, 2000);
    }

    private void playLetterSound(String ltr, boolean arabic) {
        stopMedia();
        try {
            String path;
            if (arabic) {
                int idx = -1;
                List<AlphabetData.LetterEntry> arEntries = AlphabetData.getArabicAlphabet();
                for (int i = 0; i < arEntries.size(); i++) {
                    if (arEntries.get(i).letter.equals(ltr)) { idx = i; break; }
                }
                if (idx < 0) return;
                path = "alphabet/sounds/speech/Arabic/" + String.format("%03d", idx + 1) + ".mp3";
            } else {
                int idx = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(ltr);
                if (idx < 0) return;
                path = "alphabet-e/sounds/speech/English/" + String.format("%03d", idx + 1) + ".mp3";
            }
            mediaPlayer = new MediaPlayer();
            android.content.res.AssetFileDescriptor fd = getAssets().openFd(path);
            mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            fd.close();
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception ignored) {}
    }

    private void stopMedia() {
        if (mediaPlayer != null) {
            try { if (mediaPlayer.isPlaying()) mediaPlayer.stop(); mediaPlayer.release(); }
            catch (Exception ignored) {}
            mediaPlayer = null;
        }
    }

    private void animateMascot(boolean happy) {
        if (happy) {
            ivMascot.animate().scaleX(1.2f).scaleY(1.2f).setDuration(150)
                .withEndAction(() -> ivMascot.animate().scaleX(1f).scaleY(1f)
                    .setDuration(200).setInterpolator(new OvershootInterpolator()).start()).start();
        } else {
            ivMascot.animate().translationX(12f).setDuration(60)
                .withEndAction(() -> ivMascot.animate().translationX(-12f).setDuration(60)
                    .withEndAction(() -> ivMascot.animate().translationX(0f).setDuration(60).start())
                    .start()).start();
        }
    }

    private void bounce(android.view.View v) {
        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(80)
            .withEndAction(() -> v.animate().scaleX(1f).scaleY(1f)
                .setDuration(120).setInterpolator(new OvershootInterpolator()).start()).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        stopMedia();
        soundManager.release();
    }
}
