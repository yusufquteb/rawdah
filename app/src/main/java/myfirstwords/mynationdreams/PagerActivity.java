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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

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
    private int audioSeq = 0;

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
                audioSeq++;
                stopMedia();
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(() -> playWordAudio(position), 300);
            }
        });
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

            // JSON-based names (e.g. shapes/sounds/speech/Arabic/Arabic.txt)
            Map<String, String> arMap = loadNameMap("Arabic");
            Map<String, String> enMap = loadNameMap("English");

            // Fallback: mp3 filenames only (no .txt files)
            String[] arFallback = loadNamesFiltered("Arabic");
            String[] enFallback = loadNamesFiltered("English");

            for (int i = 0; i < folders.length; i++) {
                String folder = folders[i];
                WordItem w = new WordItem();
                w.imagePath   = imgDir + "/" + folder + "/Solution.png";
                w.arabicName  = arMap.containsKey(folder) ? arMap.get(folder)
                              : (arFallback != null && i < arFallback.length ? arFallback[i] : folder);
                w.englishName = enMap.containsKey(folder) ? enMap.get(folder)
                              : (enFallback != null && i < enFallback.length ? enFallback[i] : folder);
                w.soundArPath = section + "/sounds/speech/Arabic/" + folder + ".mp3";
                w.soundEnPath = section + "/sounds/speech/English/" + folder + ".mp3";
                if (section.equals("animals") || section.equals("vehicles")) {
                    w.soundEffectPath = section + "/sounds/onomatopoeia/" + folder + ".mp3";
                }
                wordItems.add(w);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Parse JSON map file: {"key":"Arabic name", ...}
    private Map<String, String> loadNameMap(String lang) {
        Map<String, String> map = new HashMap<>();
        try {
            InputStream is = getAssets().open(
                section + "/sounds/speech/" + lang + "/" + lang + ".txt");
            byte[] b = new byte[is.available()];
            is.read(b);
            is.close();
            JSONObject obj = new JSONObject(new String(b, StandardCharsets.UTF_8));
            java.util.Iterator<String> keys = obj.keys();
            while (keys.hasNext()) {
                String k = keys.next();
                map.put(k, obj.getString(k));
            }
        } catch (Exception ignored) {}
        return map;
    }

    // Load display names from mp3 filenames only (skip .txt and other files)
    private String[] loadNamesFiltered(String lang) {
        try {
            String dir = section + "/sounds/speech/" + lang;
            String[] files = getAssets().list(dir);
            if (files == null) return null;
            List<String> mp3s = new ArrayList<>();
            for (String f : files) {
                if (f.toLowerCase().endsWith(".mp3")) mp3s.add(f);
            }
            Collections.sort(mp3s);
            String[] names = new String[mp3s.size()];
            for (int i = 0; i < mp3s.size(); i++) {
                names[i] = mp3s.get(i).replaceAll("\\.mp3$", "");
            }
            return names;
        } catch (IOException e) {
            return null;
        }
    }

    private void playWordAudio(int pos) {
        if (pos >= wordItems.size()) return;
        final int seq = ++audioSeq;
        WordItem w = wordItems.get(pos);
        progressManager.recordWordViewed(section, String.valueOf(pos));
        playSound(w.soundArPath, () ->
            handler.postDelayed(() -> {
                if (seq == audioSeq) playSound(w.soundEnPath, null);
            }, 1200));
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

                if (isAlphabetAr) {
                    // Arabic: show letter in Arabic-styled big box, animal name below
                    arDisplay = entry.letter;
                    enDisplay = entry.animalNameAr;
                    h.tvWordAr.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                    h.tvWordAr.setTextDirection(View.TEXT_DIRECTION_RTL);
                } else {
                    // English: show letter in LTR direction, English animal name below
                    arDisplay = entry.letter;
                    enDisplay = entry.animalNameEn;
                    h.tvWordAr.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                    h.tvWordAr.setTextDirection(View.TEXT_DIRECTION_LTR);
                }

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
