package myfirstwords.mynationdreams;

import android.content.Intent;
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

public class WritingActivity extends AppCompatActivity {

    static class LetterTile {
        String letter;
        String name;
        boolean isArabic;
    }

    private RecyclerView rvLetters;
    private TextView tabArabic, tabEnglish;
    private ImageView ivMascot;
    private PrefsHelper prefs;

    private List<LetterTile> currentList = new ArrayList<>();
    private boolean showingArabic = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        prefs = new PrefsHelper(this);

        rvLetters  = findViewById(R.id.rv_letters);
        tabArabic  = findViewById(R.id.tab_arabic);
        tabEnglish = findViewById(R.id.tab_english);
        ivMascot   = findViewById(R.id.iv_mascot);

        ((ImageView) findViewById(R.id.btn_back)).setOnClickListener(v -> finish());
        ivMascot.setImageResource(prefs.isBoy() ? R.drawable.mascot_boy : R.drawable.mascot_girl);

        tabArabic.setOnClickListener(v  -> switchTab(true));
        tabEnglish.setOnClickListener(v -> switchTab(false));

        rvLetters.setLayoutManager(new GridLayoutManager(this, 5));
        switchTab(true);
    }

    private void switchTab(boolean arabic) {
        showingArabic = arabic;
        currentList.clear();

        if (arabic) {
            List<AlphabetData.LetterEntry> entries = AlphabetData.getArabicAlphabet();
            for (AlphabetData.LetterEntry e : entries) {
                LetterTile t = new LetterTile();
                t.letter   = e.letter;
                t.name     = e.letterName;
                t.isArabic = true;
                currentList.add(t);
            }
        } else {
            String[] letters = {"A","B","C","D","E","F","G","H","I","J","K","L","M",
                                "N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
            String[] names   = {"Alef","Baa","Cee","Dee","Ee","Ef","Gee","Aitch","Eye","Jay",
                                 "Kay","El","Em","En","Oh","Pee","Cue","Ar","Es","Tee","You",
                                 "Vee","Double-U","Ex","Wye","Zee"};
            for (int i = 0; i < letters.length; i++) {
                LetterTile t = new LetterTile();
                t.letter   = letters[i];
                t.name     = names[i];
                t.isArabic = false;
                currentList.add(t);
            }
        }

        if (rvLetters.getAdapter() == null) {
            rvLetters.setAdapter(new LetterAdapter());
        } else {
            rvLetters.getAdapter().notifyDataSetChanged();
        }
    }

    class LetterAdapter extends RecyclerView.Adapter<LetterAdapter.VH> {

        @NonNull @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_letter_tile, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull VH h, int position) {
            LetterTile tile = currentList.get(position);
            h.tvLetter.setText(tile.letter);
            h.tvName.setText(tile.name);

            h.itemView.setOnClickListener(v -> {
                v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(80)
                    .withEndAction(() -> v.animate().scaleX(1f).scaleY(1f)
                        .setDuration(120).setInterpolator(new OvershootInterpolator()).start())
                    .start();
                Intent intent = new Intent(WritingActivity.this, LetterTracingActivity.class);
                intent.putExtra("letter", tile.letter);
                intent.putExtra("letter_name", tile.name);
                intent.putExtra("is_arabic", tile.isArabic);
                intent.putExtra("position", position);
                startActivity(intent);
            });
        }

        @Override public int getItemCount() { return currentList.size(); }

        class VH extends RecyclerView.ViewHolder {
            TextView tvLetter, tvName;
            VH(View v) {
                super(v);
                tvLetter = v.findViewById(R.id.tv_letter);
                tvName   = v.findViewById(R.id.tv_letter_name);
            }
        }
    }
}
