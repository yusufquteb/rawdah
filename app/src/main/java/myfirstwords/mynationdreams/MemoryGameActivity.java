package myfirstwords.mynationdreams;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryGameActivity extends AppCompatActivity {

    private RecyclerView rvCards;
    private TextView tvScore, tvFeedback, tvPairs;
    private ImageView ivMascot;
    private ConfettiView confetti;

    private List<MemoryCard> cards = new ArrayList<>();
    private MemoryCard firstFlipped = null;
    private boolean canFlip = true;
    private int pairsFound = 0;
    private int score = 0;
    private static final int TOTAL_PAIRS = 8;

    private SoundManager soundManager;
    private PrefsHelper prefs;
    private final Handler handler = new Handler(Looper.getMainLooper());

    static class MemoryCard {
        String animalFolder;
        String animalName;
        int pairId;
        boolean isFaceUp = false;
        boolean isMatched = false;
        RecyclerView.ViewHolder holder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game);

        prefs = new PrefsHelper(this);
        soundManager = new SoundManager(this);

        rvCards    = findViewById(R.id.rv_cards);
        tvScore    = findViewById(R.id.tv_score);
        tvFeedback = findViewById(R.id.tv_feedback);
        tvPairs    = findViewById(R.id.tv_pairs);
        ivMascot   = findViewById(R.id.iv_mascot);
        confetti   = findViewById(R.id.confetti);

        ((ImageView) findViewById(R.id.btn_back)).setOnClickListener(v -> finish());
        ivMascot.setImageResource(prefs.isBoy() ? R.drawable.mascot_boy : R.drawable.mascot_girl);

        setupCards();
    }

    private void setupCards() {
        cards.clear();
        pairsFound = 0;
        tvPairs.setText("0/" + TOTAL_PAIRS);

        List<AlphabetData.AnimalSoundItem> animals = AlphabetData.getAnimalSoundsForGame();
        Collections.shuffle(animals);

        List<AlphabetData.AnimalSoundItem> selected = animals.subList(0, TOTAL_PAIRS);

        for (int i = 0; i < TOTAL_PAIRS; i++) {
            AlphabetData.AnimalSoundItem a = selected.get(i);
            // Create two cards per pair
            MemoryCard c1 = new MemoryCard();
            c1.animalFolder = a.imageFolder;
            c1.animalName = a.animalNameAr;
            c1.pairId = i;

            MemoryCard c2 = new MemoryCard();
            c2.animalFolder = a.imageFolder;
            c2.animalName = a.animalNameAr;
            c2.pairId = i;

            cards.add(c1);
            cards.add(c2);
        }
        Collections.shuffle(cards);

        rvCards.setLayoutManager(new GridLayoutManager(this, 4));
        rvCards.setAdapter(new CardAdapter());
    }

    private void onCardTapped(MemoryCard card, int position) {
        if (!canFlip || card.isFaceUp || card.isMatched) return;

        card.isFaceUp = true;
        flipCardToFront(card, position);

        if (firstFlipped == null) {
            firstFlipped = card;
        } else {
            canFlip = false;
            if (firstFlipped.pairId == card.pairId) {
                // Match!
                handler.postDelayed(() -> {
                    firstFlipped.isMatched = true;
                    card.isMatched = true;
                    highlightMatched(firstFlipped);
                    highlightMatched(card);
                    firstFlipped = null;
                    canFlip = true;
                    pairsFound++;
                    score++;
                    tvScore.setText(String.valueOf(score));
                    tvPairs.setText(pairsFound + "/" + TOTAL_PAIRS);
                    tvFeedback.setTextColor(ContextCompat.getColor(this, R.color.success_green));
                    tvFeedback.setText(EncouragementHelper.getCorrectMessage(this));
                    confetti.burst();
                    soundManager.playCelebration();
                    animateMascot(true);

                    if (pairsFound == TOTAL_PAIRS) {
                        handler.postDelayed(this::onGameComplete, 800);
                    }
                }, 600);
            } else {
                // No match
                handler.postDelayed(() -> {
                    flipCardToBack(firstFlipped);
                    flipCardToBack(card);
                    firstFlipped.isFaceUp = false;
                    card.isFaceUp = false;
                    firstFlipped = null;
                    canFlip = true;
                    tvFeedback.setTextColor(ContextCompat.getColor(this, R.color.error_red));
                    tvFeedback.setText(EncouragementHelper.getWrongMessage(this));
                    animateMascot(false);
                }, 1000);
            }
        }
    }

    private void onGameComplete() {
        confetti.burst();
        soundManager.playCelebration();
        tvFeedback.setTextColor(ContextCompat.getColor(this, R.color.primary_purple));
        tvFeedback.setText(EncouragementHelper.getCompletionMessage("بطاقات الذاكرة"));
        animateMascot(true);
        handler.postDelayed(() -> {
            setupCards();
            tvFeedback.setText("");
            score = 0;
            tvScore.setText("0");
        }, 3000);
    }

    private void flipCardToFront(MemoryCard card, int position) {
        View view = getCardView(position);
        if (view == null) return;
        LinearLayout front = view.findViewById(R.id.card_front);
        LinearLayout back  = view.findViewById(R.id.card_back);

        view.animate().scaleX(0f).setDuration(150).setInterpolator(new AccelerateInterpolator())
            .withEndAction(() -> {
                back.setVisibility(View.GONE);
                front.setVisibility(View.VISIBLE);
                view.animate().scaleX(1f).setDuration(150)
                    .setInterpolator(new DecelerateInterpolator()).start();
            }).start();
    }

    private void flipCardToBack(MemoryCard card) {
        View view = getCardViewByCard(card);
        if (view == null) return;
        LinearLayout front = view.findViewById(R.id.card_front);
        LinearLayout back  = view.findViewById(R.id.card_back);

        view.animate().scaleX(0f).setDuration(150).setInterpolator(new AccelerateInterpolator())
            .withEndAction(() -> {
                front.setVisibility(View.GONE);
                back.setVisibility(View.VISIBLE);
                view.animate().scaleX(1f).setDuration(150)
                    .setInterpolator(new DecelerateInterpolator()).start();
            }).start();
    }

    private void highlightMatched(MemoryCard card) {
        View view = getCardViewByCard(card);
        if (view == null) return;
        LinearLayout front = view.findViewById(R.id.card_front);
        GradientDrawable d = new GradientDrawable();
        d.setShape(GradientDrawable.RECTANGLE);
        d.setCornerRadius(20f);
        d.setColor(ContextCompat.getColor(this, R.color.game_correct));
        front.setBackground(d);
        view.animate().scaleX(1.1f).scaleY(1.1f).setDuration(100)
            .withEndAction(() -> view.animate().scaleX(1f).scaleY(1f).setDuration(150)
                .setInterpolator(new OvershootInterpolator()).start()).start();
    }

    private View getCardView(int position) {
        RecyclerView.ViewHolder vh = rvCards.findViewHolderForAdapterPosition(position);
        return vh != null ? vh.itemView : null;
    }

    private View getCardViewByCard(MemoryCard card) {
        int idx = cards.indexOf(card);
        return getCardView(idx);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        soundManager.release();
    }

    // ─── Adapter ───────────────────────────────────────────────────────────────

    class CardAdapter extends RecyclerView.Adapter<CardAdapter.VH> {

        @NonNull @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_memory_card, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull VH h, int position) {
            MemoryCard card = cards.get(position);
            card.holder = h;

            if (card.isFaceUp || card.isMatched) {
                h.cardBack.setVisibility(View.GONE);
                h.cardFront.setVisibility(View.VISIBLE);
                loadImg(h.ivImage, "animals/images/" + card.animalFolder + "/Solution.png");
                h.tvName.setText(card.animalName);

                if (card.isMatched) {
                    GradientDrawable d = new GradientDrawable();
                    d.setShape(GradientDrawable.RECTANGLE);
                    d.setCornerRadius(20f);
                    d.setColor(ContextCompat.getColor(MemoryGameActivity.this, R.color.game_correct));
                    h.cardFront.setBackground(d);
                }
            } else {
                h.cardBack.setVisibility(View.VISIBLE);
                h.cardFront.setVisibility(View.GONE);
            }

            h.itemView.setOnClickListener(v -> {
                int pos = h.getAdapterPosition();
                if (pos != RecyclerView.NO_ID) onCardTapped(cards.get(pos), pos);
            });
        }

        @Override public int getItemCount() { return cards.size(); }

        class VH extends RecyclerView.ViewHolder {
            LinearLayout cardBack, cardFront;
            ImageView ivImage;
            TextView tvName;

            VH(View v) {
                super(v);
                cardBack  = v.findViewById(R.id.card_back);
                cardFront = v.findViewById(R.id.card_front);
                ivImage   = v.findViewById(R.id.iv_card_image);
                tvName    = v.findViewById(R.id.tv_card_name);
            }
        }
    }

    private void loadImg(ImageView iv, String path) {
        try {
            InputStream is = getAssets().open(path);
            iv.setImageDrawable(Drawable.createFromStream(is, null));
            is.close();
        } catch (Exception e) { iv.setImageResource(R.drawable.default_image); }
    }
}
