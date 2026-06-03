package myfirstwords.mynationdreams;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class SetupActivity extends AppCompatActivity {

    private EditText etName;
    private LinearLayout btnBoy, btnGirl, btnAge1, btnAge2, btnAge3, btnStart;
    private ImageView ivMascot;
    private String selectedGender = "boy";
    private int selectedAge = 1;
    private PrefsHelper prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        prefs = new PrefsHelper(this);

        etName = findViewById(R.id.et_name);
        btnBoy = findViewById(R.id.btn_boy);
        btnGirl = findViewById(R.id.btn_girl);
        btnAge1 = findViewById(R.id.btn_age1);
        btnAge2 = findViewById(R.id.btn_age2);
        btnAge3 = findViewById(R.id.btn_age3);
        btnStart = findViewById(R.id.btn_start);
        ivMascot = findViewById(R.id.iv_mascot);

        ivMascot.animate().scaleX(0f).scaleY(0f).setDuration(0).start();
        ivMascot.animate().scaleX(1f).scaleY(1f)
            .setDuration(600).setInterpolator(new OvershootInterpolator()).start();

        btnBoy.setOnClickListener(v -> selectGender("boy"));
        btnGirl.setOnClickListener(v -> selectGender("girl"));
        btnAge1.setOnClickListener(v -> selectAge(1));
        btnAge2.setOnClickListener(v -> selectAge(2));
        btnAge3.setOnClickListener(v -> selectAge(3));
        btnStart.setOnClickListener(v -> startLearning());

        selectGender("boy");
        selectAge(1);
    }

    private void selectGender(String gender) {
        selectedGender = gender;
        ivMascot.setImageResource(gender.equals("boy")
            ? R.drawable.mascot_boy : R.drawable.mascot_girl);

        ivMascot.animate().scaleX(0.8f).scaleY(0.8f).setDuration(100)
            .withEndAction(() ->
                ivMascot.animate().scaleX(1f).scaleY(1f)
                    .setDuration(200).setInterpolator(new OvershootInterpolator()).start()
            ).start();

        btnBoy.setBackground(ContextCompat.getDrawable(this,
            gender.equals("boy") ? R.drawable.bg_gender_boy_selected : R.drawable.bg_gender_unselected));
        btnGirl.setBackground(ContextCompat.getDrawable(this,
            gender.equals("girl") ? R.drawable.bg_gender_girl_selected : R.drawable.bg_gender_unselected));

        // Update text colors
        ((TextView) btnBoy.getChildAt(1)).setTextColor(
            gender.equals("boy")
                ? ContextCompat.getColor(this, R.color.cat_alphabet_en)
                : ContextCompat.getColor(this, R.color.text_secondary));
        ((TextView) btnGirl.getChildAt(1)).setTextColor(
            gender.equals("girl")
                ? ContextCompat.getColor(this, R.color.cat_colors)
                : ContextCompat.getColor(this, R.color.text_secondary));
    }

    private void selectAge(int age) {
        selectedAge = age;
        btnAge1.setBackground(ContextCompat.getDrawable(this,
            age == 1 ? R.drawable.bg_age_selected : R.drawable.bg_age_unselected));
        btnAge2.setBackground(ContextCompat.getDrawable(this,
            age == 2 ? R.drawable.bg_age_selected : R.drawable.bg_age_unselected));
        btnAge3.setBackground(ContextCompat.getDrawable(this,
            age == 3 ? R.drawable.bg_age_selected : R.drawable.bg_age_unselected));

        updateAgeTextColors();
    }

    private void updateAgeTextColors() {
        int primary = ContextCompat.getColor(this, R.color.colorPrimary);
        int secondary = ContextCompat.getColor(this, R.color.text_secondary);
        ((TextView) btnAge1.getChildAt(0)).setTextColor(selectedAge == 1 ? primary : secondary);
        ((TextView) btnAge2.getChildAt(0)).setTextColor(selectedAge == 2 ? primary : secondary);
        ((TextView) btnAge3.getChildAt(0)).setTextColor(selectedAge == 3 ? primary : secondary);
    }

    private void startLearning() {
        String name = etName.getText() != null ? etName.getText().toString().trim() : "";
        if (TextUtils.isEmpty(name)) {
            etName.setError("من فضلك اكتب اسمك");
            etName.requestFocus();
            return;
        }

        btnStart.animate().scaleX(0.93f).scaleY(0.93f).setDuration(80)
            .withEndAction(() -> btnStart.animate().scaleX(1f).scaleY(1f).setDuration(100)
                .withEndAction(() -> {
                    prefs.saveChildProfile(name, selectedGender, selectedAge, 10);
                    // Save to Room DB
                    ChildProfile profile = new ChildProfile();
                    profile.name = name;
                    profile.gender = selectedGender;
                    profile.ageGroup = selectedAge;
                    profile.createdAt = System.currentTimeMillis();
                    AppDatabase.getInstance(this).childProfileDao().insert(profile);

                    startActivity(new Intent(this, HmeActivity.class));
                    finish();
                }).start()
            ).start();
    }
}
