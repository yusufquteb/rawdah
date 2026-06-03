package myfirstwords.mynationdreams;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RewardsActivity extends AppCompatActivity {

    static class BadgeInfo {
        String name;
        String emoji;
        String categoryKey;
        int requiredStars;  // stars needed to unlock (0 = always show progress)

        BadgeInfo(String name, String emoji, String categoryKey, int requiredStars) {
            this.name = name;
            this.emoji = emoji;
            this.categoryKey = categoryKey;
            this.requiredStars = requiredStars;
        }
    }

    private RecyclerView rvBadges;
    private TextView tvStarsCount, tvWordsLearned;
    private ImageView ivMascot;
    private ImageView[] stars;

    private PrefsHelper prefs;
    private ProgressManager progressManager;
    private List<BadgeInfo> badges = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        prefs           = new PrefsHelper(this);
        progressManager = new ProgressManager(this);

        tvStarsCount   = findViewById(R.id.tv_stars_count);
        tvWordsLearned = findViewById(R.id.tv_words_learned);
        ivMascot       = findViewById(R.id.iv_mascot);
        rvBadges       = findViewById(R.id.rv_badges);

        stars = new ImageView[]{
            findViewById(R.id.star1),
            findViewById(R.id.star2),
            findViewById(R.id.star3),
            findViewById(R.id.star4),
            findViewById(R.id.star5)
        };

        ((ImageView) findViewById(R.id.btn_back)).setOnClickListener(v -> finish());
        ivMascot.setImageResource(prefs.isBoy() ? R.drawable.mascot_boy : R.drawable.mascot_girl);

        buildBadges();
        loadStats();

        rvBadges.setLayoutManager(new GridLayoutManager(this, 3));
        rvBadges.setAdapter(new BadgeAdapter());
    }

    private void buildBadges() {
        badges.add(new BadgeInfo(getString(R.string.rewards_badge_animals),     "🦁", "animals",    1));
        badges.add(new BadgeInfo(getString(R.string.rewards_badge_alphabet_ar), "ا", "alphabet",   1));
        badges.add(new BadgeInfo(getString(R.string.rewards_badge_alphabet_en), "A",  "alphabet-e", 1));
        badges.add(new BadgeInfo(getString(R.string.rewards_badge_numbers),     "١",  "numbers",    1));
        badges.add(new BadgeInfo(getString(R.string.rewards_badge_week),        "🔥", "streak",     0));
        badges.add(new BadgeInfo(getString(R.string.rewards_badge_explorer),    "🔍", "all",        0));
    }

    private void loadStats() {
        int totalStars = prefs.getTotalStars();
        int wordsLearned = progressManager.getTotalLearnedWords();

        tvStarsCount.setText(String.valueOf(totalStars));
        tvWordsLearned.setText(String.valueOf(wordsLearned));

        // Animate stars display (up to 5 visual stars)
        int displayStars = Math.min(totalStars, 5);
        for (int i = 0; i < stars.length; i++) {
            final int idx = i;
            final boolean fill = i < displayStars;
            stars[i].postDelayed(() -> {
                stars[idx].setImageResource(fill ? R.drawable.ic_star_filled : R.drawable.ic_star_empty);
                if (fill) {
                    stars[idx].animate().scaleX(1.3f).scaleY(1.3f).setDuration(120)
                        .withEndAction(() -> stars[idx].animate().scaleX(1f).scaleY(1f)
                            .setDuration(150).setInterpolator(new OvershootInterpolator()).start())
                        .start();
                }
            }, idx * 150L);
        }

        // Animate mascot bounce
        ivMascot.postDelayed(() ->
            ivMascot.animate().scaleX(1.15f).scaleY(1.15f).setDuration(200)
                .withEndAction(() -> ivMascot.animate().scaleX(1f).scaleY(1f)
                    .setDuration(250).setInterpolator(new OvershootInterpolator()).start())
                .start(), 400);
    }

    class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.VH> {

        @NonNull @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_badge, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull VH h, int position) {
            BadgeInfo badge = badges.get(position);
            h.tvName.setText(badge.name);

            int stars = getBadgeStars(badge);
            boolean unlocked = stars > 0;

            // Set icon as text emoji in the ImageView background
            // Use a TextView-style approach with the emoji as a colored background circle
            h.tvEmoji.setText(badge.emoji);
            h.lockOverlay.setVisibility(unlocked ? View.GONE : View.VISIBLE);
            h.tvLockIcon.setVisibility(unlocked ? View.GONE : View.VISIBLE);

            int[] starIds = {R.id.bstar1, R.id.bstar2, R.id.bstar3};
            ImageView[] starViews = {
                h.itemView.findViewById(R.id.bstar1),
                h.itemView.findViewById(R.id.bstar2),
                h.itemView.findViewById(R.id.bstar3)
            };
            for (int i = 0; i < 3; i++) {
                starViews[i].setImageResource(i < stars
                    ? R.drawable.ic_star_filled : R.drawable.ic_star_empty);
            }

            if (unlocked) {
                h.itemView.setAlpha(1f);
                h.itemView.animate().scaleX(1f).scaleY(1f)
                    .setDuration(200).setInterpolator(new OvershootInterpolator()).start();
            } else {
                h.itemView.setAlpha(0.65f);
            }
        }

        private int getBadgeStars(BadgeInfo badge) {
            if (badge.categoryKey.equals("streak")) {
                return prefs.getStreakDays() >= 7 ? 3 : (prefs.getStreakDays() >= 3 ? 1 : 0);
            }
            if (badge.categoryKey.equals("all")) {
                int total = progressManager.getTotalLearnedWords();
                return total >= 50 ? 3 : (total >= 20 ? 2 : (total >= 5 ? 1 : 0));
            }
            return progressManager.getCategoryStars(badge.categoryKey);
        }

        @Override public int getItemCount() { return badges.size(); }

        class VH extends RecyclerView.ViewHolder {
            TextView tvName, tvEmoji, tvLockIcon;
            View lockOverlay;

            VH(View v) {
                super(v);
                tvName      = v.findViewById(R.id.tv_badge_name);
                tvEmoji     = v.findViewById(R.id.iv_badge_icon);
                lockOverlay = v.findViewById(R.id.lock_overlay);
                tvLockIcon  = v.findViewById(R.id.tv_lock_icon);
            }
        }
    }
}
