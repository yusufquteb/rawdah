package myfirstwords.mynationdreams;

import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PagerActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ImageView btnBack;
    private TextView tvTitle;

    private String section = "";
    private String categoryName = "";
    private List<WordItem> wordItems = new ArrayList<>();
    private ProgressManager progressManager;
    private SoundManager soundManager;
    private MediaPlayer mediaPlayer;
    private final Handler handler = new Handler(Looper.getMainLooper());

    // Alphabet mode
    private boolean isAlphabetAr = false;
    private boolean isAlphabetEn = false;
    private List<AlphabetData.LetterEntry> letterEntries;

    static class WordItem {
        String arabicName;
        String englishName;
        String imagePath;
        String soundArPath;
        String soundEnPath;
        String soundEffectPath;
        // Alphabet extras
        AlphabetData.LetterEntry letterEntry;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager);

        section      = getIntent().getStringExtra("section");
        categoryName = getIntent().getStringExtra("category_name");
        if (section == null) section = "animals";
        if (categoryName == null) categoryName = "الحيوانات";

        progressManager = new ProgressManager(this);
        soundManager    = new SoundManager(this);

        viewPager = findViewById(R.id.viewpager2);
        btnBack   = findViewById(R.id.imageview1);
        tvTitle   = findViewById(R.id.tv_category_title);

        tvTitle.setText(categoryName);
        btnBack.setOnClickListener(v -> onBackPressed());

        isAlphabetAr = section.equals("alphabet");
        isAlphabetEn = section.equals("alphabet-e");

        loadContent();

        PagerAdapter adapter = new PagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                stopMedia();
                handler.postDelayed(() -> playWordAudio(position), 300);
            }
        });

        // Play first word
        handler.postDelayed(() -> playWordAudio(0), 500);
    }

    private void loadContent() {
        if (isAlphabetAr) {
            letterEntries = AlphabetData.getArabicAlphabet();
            for (AlphabetData.LetterEntry e : letterEntries) {
                WordItem w = new WordItem();
                w.arabicName   = e.letter + " — " + e.animalNameAr;
                w.englishName  = e.letterName + " — " + e.animalNameEn;
                w.imagePath    = e.letterImageFolder + "/Solution.png";
                w.soundArPath  = "alphabet/sounds/speech/Arabic/" + padNum(letterEntries.indexOf(e) + 1) + ".mp3";
                w.soundEnPath  = "alphabet/sounds/speech/English/" + padNum(letterEntries.indexOf(e) + 1) + ".mp3";
                w.soundEffectPath = "animals/sounds/onomatopoeia/" + e.soundFile + ".mp3";
                w.letterEntry  = e;
                wordItems.add(w);
            }
        } else if (isAlphabetEn) {
            letterEntries = AlphabetData.getEnglishAlphabet();
            for (AlphabetData.LetterEntry e : letterEntries) {
                WordItem w = new WordItem();
                w.arabicName   = e.letter + " — " + e.animalNameAr;
                w.englishName  = e.letterName + " is for " + e.animalNameEn;
                w.imagePath    = e.letterImageFolder + "/Solution.png";
                w.soundArPath  = "alphabet-e/sounds/speech/Arabic/" + padNum(letterEntries.indexOf(e) + 1) + ".mp3";
                w.soundEnPath  = "alphabet-e/sounds/speech/English/" + padNum(letterEntries.indexOf(e) + 1) + ".mp3";
                w.soundEffectPath = "animals/sounds/onomatopoeia/" + e.soundFile + ".mp3";
                w.letterEntry  = e;
                wordItems.add(w);
            }
        } else {
            loadRegularCategory();
        }
    }

    private void loadRegularCategory() {
        try {
            String imgDir    = section + "/images";
            String[] folders = getAssets().list(imgDir);
            if (folders == null) return;
            java.util.Arrays.sort(folders);

            String[] arNames = loadNames("Arabic");
            String[] enNames = loadNames("English");

            for (int i = 0; i < folders.length; i++) {
                WordItem w = new WordItem();
                w.imagePath   = imgDir + "/" + folders[i] + "/Solution.png";
                w.arabicName  = (arNames != null && i < arNames.length) ? arNames[i] : folders[i];
                w.englishName = (enNames != null && i < enNames.length) ? enNames[i] : folders[i];
                w.soundArPath = section + "/sounds/speech/Arabic/" + folders[i] + ".mp3";
                w.soundEnPath = section + "/sounds/speech/English/" + folders[i] + ".mp3";

                if (section.equals("animals") || section.equals("vehicles")) {
                    w.soundEffectPath = section + "/sounds/onomatopoeia/" + folders[i] + ".mp3";
                }
                wordItems.add(w);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] loadNames(String lang) {
        try {
            String dir = section + "/sounds/speech/" + lang;
            String[] files = getAssets().list(dir);
            if (files == null) return null;
            java.util.Arrays.sort(files);
            String[] names = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                names[i] = files[i].replace(".mp3", "");
            }
            return names;
        } catch (IOException e) {
            return null;
        }
    }

    private void playWordAudio(int pos) {
        if (pos >= wordItems.size()) return;
        WordItem w = wordItems.get(pos);
        progressManager.recordWordViewed(section, String.valueOf(pos));
        playSound(w.soundArPath, () ->
            handler.postDelayed(() -> playSound(w.soundEnPath, null), 1200));
    }

    private void playSound(String path, Runnable onComplete) {
        stopMedia();
        try {
            mediaPlayer = new MediaPlayer();
            AssetFileDescriptor fd = getAssets().openFd(path);
            mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            fd.close();
            mediaPlayer.prepare();
            mediaPlayer.setLooping(false);
            if (onComplete != null) {
                mediaPlayer.setOnCompletionListener(mp -> onComplete.run());
            }
            mediaPlayer.start();
        } catch (Exception e) {
            if (onComplete != null) onComplete.run();
        }
    }

    private void stopMedia() {
        if (mediaPlayer != null) {
            try { if (mediaPlayer.isPlaying()) mediaPlayer.stop(); mediaPlayer.release(); }
            catch (Exception ignored) {}
            mediaPlayer = null;
        }
    }

    private String padNum(int n) {
        return String.format("%03d", n);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        stopMedia();
        soundManager.release();
    }

    // ══════════════════════════════════════
    //  ViewPager2 Adapter
    // ══════════════════════════════════════
    class PagerAdapter extends RecyclerView.Adapter<PagerAdapter.VH> {

        @NonNull @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bg3, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull VH h, int position) {
            WordItem w = wordItems.get(position);
            int total  = wordItems.size();

            // Counter
            h.tvCounter.setText((position + 1) + " / " + total);

            // Arabic / English words
            String arDisplay = w.arabicName;
            String enDisplay = w.englishName;

            if (isAlphabetAr || isAlphabetEn) {
                AlphabetData.LetterEntry entry = w.letterEntry;
                // Show the letter big, animal name small
                arDisplay = entry.letter;
                enDisplay = isAlphabetAr ? entry.animalNameAr : entry.animalNameEn;

                // Show song lyric
                h.layoutSongLyric.setVisibility(View.VISIBLE);
                h.tvSongLyric.setText(entry.songLyric);

                // Show animal image
                h.layoutAnimal.setVisibility(View.VISIBLE);
                h.tvAnimalName.setText(entry.animalNameAr + " / " + entry.animalNameEn);
                loadBitmapInto(h.ivAnimal, "animals/images/" + entry.animalFolder + "/Solution.png");

                // Show animal sound button
                h.btnPlayAnimal.setVisibility(View.VISIBLE);
            } else {
                h.layoutSongLyric.setVisibility(View.GONE);
                h.layoutAnimal.setVisibility(View.GONE);
                if (section.equals("animals") || section.equals("vehicles")) {
                    h.btnPlayAnimal.setVisibility(w.soundEffectPath != null ? View.VISIBLE : View.GONE);
                } else {
                    h.btnPlayAnimal.setVisibility(View.GONE);
                }
            }

            h.tvWordAr.setText(arDisplay);
            h.tvWordEn.setText(enDisplay);

            // Word image
            loadBitmapInto(h.ivWord, w.imagePath);

            // Animate in
            h.cardBox.setAlpha(0f);
            h.cardBox.setTranslationY(40f);
            h.cardBox.animate().alpha(1f).translationY(0f)
                .setDuration(350).setInterpolator(new OvershootInterpolator(0.7f)).start();

            // Arabic sound button
            h.btnPlayAr.setOnClickListener(v -> {
                bounce(h.btnPlayAr);
                playSound(w.soundArPath, null);
            });

            // English sound button
            h.btnPlayEn.setOnClickListener(v -> {
                bounce(h.btnPlayEn);
                playSound(w.soundEnPath, null);
            });

            // Animal sound button
            h.btnPlayAnimal.setOnClickListener(v -> {
                bounce(h.btnPlayAnimal);
                if (w.soundEffectPath != null) playSound(w.soundEffectPath, null);
            });

            // Image tap
            h.ivWord.setOnClickListener(v -> {
                bounce(h.ivWord);
                playWordAudio(position);
                showEncouragement(h, true);
            });
        }

        private void bounce(View v) {
            v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(80)
                .withEndAction(() ->
                    v.animate().scaleX(1f).scaleY(1f).setDuration(120)
                        .setInterpolator(new OvershootInterpolator()).start()
                ).start();
        }

        private void showEncouragement(VH h, boolean correct) {
            h.tvEncouragement.setVisibility(View.VISIBLE);
            h.tvEncouragement.setText(correct
                ? EncouragementHelper.getCorrectMessage(PagerActivity.this)
                : EncouragementHelper.getWrongMessage(PagerActivity.this));
            h.tvEncouragement.setAlpha(0f);
            h.tvEncouragement.animate().alpha(1f).setDuration(300).start();

            if (correct) {
                h.confetti.burst();
                soundManager.playCorrectSound();
            }

            handler.postDelayed(() -> {
                h.tvEncouragement.animate().alpha(0f).setDuration(400)
                    .withEndAction(() ->
                        h.tvEncouragement.setVisibility(View.GONE)).start();
            }, 2000);
        }

        private void loadBitmapInto(ImageView iv, String assetPath) {
            try {
                InputStream is = getAssets().open(assetPath);
                Drawable d = Drawable.createFromStream(is, null);
                iv.setImageDrawable(d);
                is.close();
            } catch (Exception e) {
                iv.setImageResource(R.drawable.default_image);
            }
        }

        @Override public int getItemCount() { return wordItems.size(); }

        class VH extends RecyclerView.ViewHolder {
            LinearLayout cardBox, btnPlayAr, btnPlayEn, btnPlayAnimal;
            LinearLayout layoutSongLyric, layoutAnimal;
            ImageView ivWord, ivAnimal;
            TextView tvCounter, tvWordAr, tvWordEn, tvSongLyric, tvAnimalName, tvEncouragement;
            ConfettiView confetti;

            VH(View v) {
                super(v);
                cardBox         = v.findViewById(R.id.box);
                btnPlayAr       = v.findViewById(R.id.btn_play_ar);
                btnPlayEn       = v.findViewById(R.id.btn_play_en);
                btnPlayAnimal   = v.findViewById(R.id.play);
                layoutSongLyric = v.findViewById(R.id.layout_song_lyric);
                layoutAnimal    = v.findViewById(R.id.layout_animal);
                ivWord          = v.findViewById(R.id.imageview1);
                ivAnimal        = v.findViewById(R.id.iv_animal);
                tvCounter       = v.findViewById(R.id.textview2);
                tvWordAr        = v.findViewById(R.id.textview1);
                tvWordEn        = v.findViewById(R.id.textview4);
                tvSongLyric     = v.findViewById(R.id.tv_song_lyric);
                tvAnimalName    = v.findViewById(R.id.tv_animal_name);
                tvEncouragement = v.findViewById(R.id.tv_encouragement);
                confetti        = v.findViewById(R.id.confetti_view);
            }
        }
    }
}
