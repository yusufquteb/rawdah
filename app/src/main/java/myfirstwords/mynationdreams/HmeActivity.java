package myfirstwords.mynationdreams;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import java.util.List;

public class HmeActivity extends AppCompatActivity {

    private ImageView ivMascot, btnClose, btnParent;
    private TextView tvGreeting, tvEncouragement, tvWordsCount, tvStreak, tvGoalProgress;
    private LinearProgressIndicator progressDaily;
    private RecyclerView rvCategories;
    private LinearLayout btnRewards, btnWriting;
    private LinearLayout btnSoundsGame, btnMatchGame, btnMemoryGame;

    private PrefsHelper prefs;
    private ProgressManager progressManager;
    private MediaPlayer mediaPlayer;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hme);
        prefs = new PrefsHelper(this);
        progressManager = new ProgressManager(this);
        initViews();
        setupMascot();
        setupGreeting();
        setupStats();
        setupCategories();
        setupButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupStats();
        if (rvCategories.getAdapter() != null)
            rvCategories.getAdapter().notifyDataSetChanged();
    }

    private void initViews() {
        ivMascot       = findViewById(R.id.iv_mascot);
        tvGreeting     = findViewById(R.id.tv_greeting);
        tvEncouragement= findViewById(R.id.tv_encouragement);
        tvWordsCount   = findViewById(R.id.tv_words_count);
        tvStreak       = findViewById(R.id.tv_streak);
        tvGoalProgress = findViewById(R.id.tv_goal_progress);
        progressDaily  = findViewById(R.id.progress_daily);
        rvCategories   = findViewById(R.id.rv_categories);
        btnRewards     = findViewById(R.id.btn_rewards);
        btnWriting     = findViewById(R.id.btn_writing);
        btnSoundsGame  = findViewById(R.id.btn_sounds_game);
        btnMatchGame   = findViewById(R.id.btn_match_game);
        btnMemoryGame  = findViewById(R.id.btn_memory_game);
        btnClose       = findViewById(R.id.imageview13);
        btnParent      = findViewById(R.id.btn_parent);
    }

    private void setupMascot() {
        ivMascot.setImageResource(prefs.isBoy()
            ? R.drawable.mascot_boy : R.drawable.mascot_girl);
        ivMascot.setAlpha(0f);
        ivMascot.setScaleX(0.5f);
        ivMascot.setScaleY(0.5f);
        ivMascot.animate().alpha(1f).scaleX(1f).scaleY(1f)
            .setDuration(600).setInterpolator(new OvershootInterpolator()).start();
        handler.postDelayed(() -> {
            ObjectAnimator wave = ObjectAnimator.ofFloat(
                ivMascot, "rotation", 0f, 12f, -12f, 8f, -8f, 0f);
            wave.setDuration(900);
            wave.start();
        }, 700);
    }

    private void setupGreeting() {
        String name = prefs.getChildName();
        tvGreeting.setText(EncouragementHelper.getGreeting(this, name, prefs.isBoy()));
        tvEncouragement.setText(EncouragementHelper.getStartMessage(this));
    }

    private void setupStats() {
        int totalWords = progressManager.getTotalLearnedWords();
        int streak     = progressManager.getStreakDays();
        int todayWords = progressManager.getTodayWords();
        int dailyGoal  = prefs.getDailyGoal();

        tvWordsCount.setText(String.valueOf(totalWords));
        tvStreak.setText(String.valueOf(streak));
        tvGoalProgress.setText(todayWords + " / " + dailyGoal);

        int pct = dailyGoal > 0 ? Math.min(100, (todayWords * 100) / dailyGoal) : 0;
        ObjectAnimator.ofInt(progressDaily, "progress", 0, pct).setDuration(800).start();
    }

    private void setupCategories() {
        List<AlphabetData.CategoryInfo> cats = AlphabetData.getAllCategories();
        rvCategories.setLayoutManager(new GridLayoutManager(this, 3));
        rvCategories.setAdapter(new CategoryAdapter(cats, progressManager, cat -> {
            playSectionIntro(cat.id);
            handler.postDelayed(() -> openCategory(cat), 500);
        }));
    }

    private void setupButtons() {
        btnClose.setOnClickListener(v -> finishAffinity());
        btnParent.setOnClickListener(v -> showParentDashboard());

        btnRewards.setOnClickListener(v -> {
            bounce(btnRewards);
            handler.postDelayed(() ->
                startActivity(new Intent(this, RewardsActivity.class)), 150);
        });
        btnWriting.setOnClickListener(v -> {
            bounce(btnWriting);
            handler.postDelayed(() ->
                startActivity(new Intent(this, WritingActivity.class)), 150);
        });
        btnSoundsGame.setOnClickListener(v -> {
            bounce(btnSoundsGame);
            handler.postDelayed(() ->
                startActivity(new Intent(this, SoundsGameActivity.class)), 150);
        });
        btnMatchGame.setOnClickListener(v -> {
            bounce(btnMatchGame);
            handler.postDelayed(() ->
                startActivity(new Intent(this, MatchGameActivity.class)), 150);
        });
        btnMemoryGame.setOnClickListener(v -> {
            bounce(btnMemoryGame);
            handler.postDelayed(() ->
                startActivity(new Intent(this, MemoryGameActivity.class)), 150);
        });
    }

    private void bounce(android.view.View v) {
        v.animate().scaleX(0.92f).scaleY(0.92f).setDuration(80)
            .withEndAction(() ->
                v.animate().scaleX(1f).scaleY(1f).setDuration(120)
                    .setInterpolator(new OvershootInterpolator()).start()
            ).start();
    }

    private void playSectionIntro(String sectionId) {
        stopMedia();
        try {
            mediaPlayer = new MediaPlayer();
            android.content.res.AssetFileDescriptor fd =
                getAssets().openFd("sections/" + sectionId + ".mp3");
            mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            fd.close();
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openCategory(AlphabetData.CategoryInfo cat) {
        Intent intent = new Intent(this, PagerActivity.class);
        intent.putExtra("section", cat.id);
        intent.putExtra("category_name", cat.nameAr);
        startActivity(intent);
    }

    private void showParentDashboard() {
        String name      = prefs.getChildName();
        int totalWords   = progressManager.getTotalLearnedWords();
        int todayWords   = progressManager.getTodayWords();
        int minutes      = progressManager.getTodayMinutes();
        int streak       = progressManager.getStreakDays();

        String msg = "👦 اسم الطفل: " + name + "\n\n"
            + "📚 إجمالي الكلمات: " + totalWords + "\n"
            + "📅 كلمات اليوم: " + todayWords + "\n"
            + "⏱ وقت اليوم: " + minutes + " دقيقة\n"
            + "🔥 الأيام المتتالية: " + streak + " يوم";

        new android.app.AlertDialog.Builder(this)
            .setTitle(getString(R.string.parent_title))
            .setMessage(msg)
            .setPositiveButton(getString(R.string.parent_close), null)
            .show();
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
    }
}
