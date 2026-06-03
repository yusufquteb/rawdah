package myfirstwords.mynationdreams;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.VH> {

    public interface OnCategoryClick {
        void onClick(AlphabetData.CategoryInfo cat);
    }

    private final List<AlphabetData.CategoryInfo> items;
    private final ProgressManager progressManager;
    private final OnCategoryClick listener;

    public CategoryAdapter(List<AlphabetData.CategoryInfo> items,
                           ProgressManager pm, OnCategoryClick listener) {
        this.items = items;
        this.progressManager = pm;
        this.listener = listener;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_category_card, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        AlphabetData.CategoryInfo cat = items.get(pos);
        Context ctx = h.itemView.getContext();

        h.tvEmoji.setText(cat.emoji);
        h.tvName.setText(cat.nameAr);

        int color = ContextCompat.getColor(ctx, cat.colorRes);
        int bgColor = ContextCompat.getColor(ctx, cat.bgColorRes);

        GradientDrawable bg = new GradientDrawable();
        bg.setShape(GradientDrawable.RECTANGLE);
        bg.setCornerRadius(48f);
        bg.setColor(bgColor);
        h.cardRoot.setBackground(bg);

        GradientDrawable emojiCircle = new GradientDrawable();
        emojiCircle.setShape(GradientDrawable.OVAL);
        emojiCircle.setColor(color);
        h.emojiContainer.setBackground(emojiCircle);

        int stars = progressManager.getCategoryStars(cat.id);
        h.star1.setImageResource(stars >= 1 ? R.drawable.ic_star_filled : R.drawable.ic_star_empty);
        h.star2.setImageResource(stars >= 2 ? R.drawable.ic_star_filled : R.drawable.ic_star_empty);
        h.star3.setImageResource(stars >= 3 ? R.drawable.ic_star_filled : R.drawable.ic_star_empty);

        h.itemView.setOnClickListener(v -> {
            h.itemView.animate().scaleX(0.93f).scaleY(0.93f).setDuration(80)
                .withEndAction(() ->
                    h.itemView.animate().scaleX(1f).scaleY(1f).setDuration(100)
                        .withEndAction(() -> listener.onClick(cat)).start()
                ).start();
        });
    }

    @Override public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        LinearLayout cardRoot, emojiContainer;
        TextView tvEmoji, tvName;
        ImageView star1, star2, star3;

        VH(View v) {
            super(v);
            cardRoot = v.findViewById(R.id.card_root);
            emojiContainer = v.findViewById(R.id.emoji_container);
            tvEmoji = v.findViewById(R.id.tv_emoji);
            tvName = v.findViewById(R.id.tv_name);
            star1 = v.findViewById(R.id.star1);
            star2 = v.findViewById(R.id.star2);
            star3 = v.findViewById(R.id.star3);
        }
    }
}
