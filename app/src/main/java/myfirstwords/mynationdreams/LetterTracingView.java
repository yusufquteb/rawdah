package myfirstwords.mynationdreams;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom view that shows a letter as a dashed guide path and lets the child trace it
 * with their finger. Arrows indicate direction. When the child covers ≥70% of the
 * guide path, onTracingComplete() is called.
 */
public class LetterTracingView extends View {

    public interface TracingListener {
        void onTracingComplete();
        void onProgressChanged(int percent);
    }

    // ── Paint objects ──────────────────────────────────────────────────────────

    private final Paint guidePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint tracePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint letterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint arrowPaint  = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint dotPaint    = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint startDotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // ── State ──────────────────────────────────────────────────────────────────

    private String letter = "أ";
    private boolean isArabic = true;
    private final Path userPath  = new Path();
    private final Path guidePath = new Path();
    private float guideLength = 0f;
    private final List<PointF> guidePoints = new ArrayList<>();
    private final boolean[] coveredPoints;
    private int coveredCount = 0;
    private boolean isComplete = false;
    private TracingListener listener;

    private static final int GUIDE_POINTS = 200;
    private static final float HIT_RADIUS_DP = 22f;
    private float hitRadius;

    public LetterTracingView(Context context) {
        this(context, null);
    }

    public LetterTracingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        coveredPoints = new boolean[GUIDE_POINTS];
        float density = context.getResources().getDisplayMetrics().density;
        hitRadius = HIT_RADIUS_DP * density;
        setupPaints(density);
    }

    private void setupPaints(float dp) {
        guidePaint.setStyle(Paint.Style.STROKE);
        guidePaint.setStrokeWidth(8 * dp);
        guidePaint.setColor(0xFFCCCCCC);
        guidePaint.setPathEffect(new DashPathEffect(new float[]{14 * dp, 8 * dp}, 0));
        guidePaint.setStrokeCap(Paint.Cap.ROUND);

        tracePaint.setStyle(Paint.Style.STROKE);
        tracePaint.setStrokeWidth(10 * dp);
        tracePaint.setColor(0xFF6C63FF);
        tracePaint.setStrokeCap(Paint.Cap.ROUND);
        tracePaint.setStrokeJoin(Paint.Join.ROUND);

        letterPaint.setTextAlign(Paint.Align.CENTER);
        letterPaint.setColor(0xFFE0E0E0);
        letterPaint.setAntiAlias(true);

        arrowPaint.setStyle(Paint.Style.FILL);
        arrowPaint.setColor(0xFFFF9800);
        arrowPaint.setAntiAlias(true);

        dotPaint.setStyle(Paint.Style.FILL);
        dotPaint.setColor(0xFFFFB84C);

        startDotPaint.setStyle(Paint.Style.FILL);
        startDotPaint.setColor(0xFF4CAF50);
    }

    public void setLetter(String letter, boolean isArabic) {
        this.letter  = letter;
        this.isArabic = isArabic;
        reset();
        requestLayout();
        invalidate();
    }

    public void setTracingListener(TracingListener l) {
        this.listener = l;
    }

    public void reset() {
        userPath.reset();
        java.util.Arrays.fill(coveredPoints, false);
        coveredCount = 0;
        isComplete = false;
        buildGuidePath();
        invalidate();
    }

    // ── Guide path construction ────────────────────────────────────────────────

    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh) {
        super.onSizeChanged(w, h, ow, oh);
        buildGuidePath();
    }

    private void buildGuidePath() {
        if (getWidth() == 0 || getHeight() == 0) return;
        guidePath.reset();
        guidePoints.clear();

        float w = getWidth();
        float h = getHeight();
        float textSize = Math.min(w, h) * 0.55f;

        // Extract the exact glyph outline from the font — matches the ghost letter in onDraw
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.getTextPath(letter, 0, letter.length(), w / 2f, h / 2f + textSize * 0.35f, guidePath);

        // First pass: collect all sub-path lengths
        List<Float> segLengths = new ArrayList<>();
        PathMeasure pm = new PathMeasure(guidePath, false);
        do {
            float len = pm.getLength();
            if (len > 0f) segLengths.add(len);
        } while (pm.nextContour());

        if (segLengths.isEmpty()) {
            java.util.Arrays.fill(coveredPoints, false);
            coveredCount = 0;
            invalidate();
            return;
        }

        float total = 0f;
        for (float l : segLengths) total += l;
        guideLength = total;

        // Distribute GUIDE_POINTS proportionally across sub-paths; last absorbs rounding remainder
        int[] ptCount = new int[segLengths.size()];
        int assigned = 0;
        for (int i = 0; i < segLengths.size() - 1; i++) {
            ptCount[i] = Math.max(1, (int)(GUIDE_POINTS * segLengths.get(i) / total));
            assigned += ptCount[i];
        }
        ptCount[segLengths.size() - 1] = Math.max(1, GUIDE_POINTS - assigned);

        // Second pass: sample points from each sub-path
        pm = new PathMeasure(guidePath, false);
        for (int ci = 0; ci < segLengths.size(); ci++) {
            float segLen = segLengths.get(ci);
            int n = ptCount[ci];
            for (int i = 0; i < n; i++) {
                float[] pos = new float[2];
                float dist = (n == 1) ? 0f : segLen * i / (n - 1);
                pm.getPosTan(dist, pos, null);
                guidePoints.add(new PointF(pos[0], pos[1]));
            }
            if (ci < segLengths.size() - 1) pm.nextContour();
        }

        java.util.Arrays.fill(coveredPoints, false);
        coveredCount = 0;
        invalidate();
    }

    // ── Draw ───────────────────────────────────────────────────────────────────

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float w = getWidth();
        float h = getHeight();

        // Ghost letter in background
        letterPaint.setTextSize(Math.min(w, h) * 0.55f);
        canvas.drawText(letter, w / 2f, h / 2f + letterPaint.getTextSize() * 0.35f, letterPaint);

        // Guide dashed path
        canvas.drawPath(guidePath, guidePaint);

        // Start dot (green)
        if (!guidePoints.isEmpty()) {
            PointF start = guidePoints.get(0);
            canvas.drawCircle(start.x, start.y, hitRadius * 0.55f, startDotPaint);
            // Arrow at start
            drawArrow(canvas, start.x, start.y, guidePoints.size() > 5 ? guidePoints.get(5) : start);
        }

        // Guide waypoint dots every ~15 points
        for (int i = 15; i < guidePoints.size(); i += 15) {
            PointF p = guidePoints.get(i);
            dotPaint.setAlpha(coveredPoints[i] ? 80 : 180);
            canvas.drawCircle(p.x, p.y, 6f, dotPaint);
        }

        // User trace
        canvas.drawPath(userPath, tracePaint);
    }

    private void drawArrow(Canvas canvas, float px, float py, PointF towards) {
        float dx = towards.x - px;
        float dy = towards.y - py;
        float len = (float) Math.sqrt(dx * dx + dy * dy);
        if (len == 0) return;
        dx /= len; dy /= len;

        float arrowLen = hitRadius * 0.8f;
        float ax = px + dx * arrowLen;
        float ay = py + dy * arrowLen;

        float wx = -dy * arrowLen * 0.35f;
        float wy =  dx * arrowLen * 0.35f;

        Path arrow = new Path();
        arrow.moveTo(ax, ay);
        arrow.lineTo(px + wx, py + wy);
        arrow.lineTo(px - wx, py - wy);
        arrow.close();
        canvas.drawPath(arrow, arrowPaint);
    }

    // ── Touch ──────────────────────────────────────────────────────────────────

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isComplete) return true;
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                userPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                userPath.lineTo(x, y);
                checkCoverage(x, y);
                break;
            case MotionEvent.ACTION_UP:
                userPath.lineTo(x, y);
                checkCoverage(x, y);
                break;
        }
        invalidate();
        return true;
    }

    private void checkCoverage(float x, float y) {
        for (int i = 0; i < guidePoints.size(); i++) {
            if (coveredPoints[i]) continue;
            PointF p = guidePoints.get(i);
            float dx = p.x - x;
            float dy = p.y - y;
            if (dx * dx + dy * dy <= hitRadius * hitRadius) {
                coveredPoints[i] = true;
                coveredCount++;
            }
        }

        int percent = (int) (coveredCount * 100f / GUIDE_POINTS);
        if (listener != null) listener.onProgressChanged(percent);

        if (!isComplete && percent >= 70) {
            isComplete = true;
            if (listener != null) listener.onTracingComplete();
        }
    }
}
