package myfirstwords.mynationdreams;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConfettiView extends View {

    private static final int PARTICLE_COUNT = 60;
    private final List<Particle> particles = new ArrayList<>();
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Random random = new Random();
    private ValueAnimator animator;

    private final int[] colors = {
        0xFFFF6B6B, 0xFFFFB84C, 0xFF6C63FF,
        0xFF4CC9F0, 0xFF06D6A0, 0xFFF72585,
        0xFFFFD700, 0xFF4CAF50
    };

    private static class Particle {
        float x, y, vx, vy, size, rotation, rotSpeed;
        int color;
        boolean isRect;
    }

    public ConfettiView(Context context) { super(context); init(); }
    public ConfettiView(Context context, AttributeSet attrs) { super(context, attrs); init(); }

    private void init() {
        setVisibility(INVISIBLE);
    }

    public void burst() {
        particles.clear();
        if (getWidth() == 0) {
            post(this::burst);
            return;
        }
        for (int i = 0; i < PARTICLE_COUNT; i++) {
            Particle p = new Particle();
            p.x = random.nextInt(getWidth());
            p.y = -random.nextInt(getHeight() / 3);
            p.vx = (random.nextFloat() - 0.5f) * 8f;
            p.vy = random.nextFloat() * 6f + 3f;
            p.size = random.nextFloat() * 12f + 6f;
            p.color = colors[random.nextInt(colors.length)];
            p.rotation = random.nextFloat() * 360f;
            p.rotSpeed = (random.nextFloat() - 0.5f) * 10f;
            p.isRect = random.nextBoolean();
            particles.add(p);
        }
        setVisibility(VISIBLE);
        if (animator != null) animator.cancel();
        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(2200);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(anim -> {
            for (Particle p : particles) {
                p.x += p.vx;
                p.y += p.vy;
                p.vy += 0.18f;
                p.rotation += p.rotSpeed;
            }
            invalidate();
        });
        animator.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(android.animation.Animator a) {
                setVisibility(INVISIBLE);
                particles.clear();
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Particle p : particles) {
            paint.setColor(p.color);
            canvas.save();
            canvas.rotate(p.rotation, p.x, p.y);
            if (p.isRect) {
                canvas.drawRect(p.x - p.size / 2, p.y - p.size / 4,
                    p.x + p.size / 2, p.y + p.size / 4, paint);
            } else {
                canvas.drawCircle(p.x, p.y, p.size / 2, paint);
            }
            canvas.restore();
        }
    }
}
