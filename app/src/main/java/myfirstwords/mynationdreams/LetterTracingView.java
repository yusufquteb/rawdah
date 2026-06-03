package myfirstwords.mynationdreams;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
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
    private static final float HIT_RADIUS_DP = 28f;
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
        float cx = w / 2f;
        float cy = h / 2f;
        float unit = Math.min(w, h) * 0.35f;

        buildPathForLetter(letter, cx, cy, unit);

        PathMeasure pm = new PathMeasure(guidePath, false);
        guideLength = pm.getLength();

        for (int i = 0; i < GUIDE_POINTS; i++) {
            float[] pos = new float[2];
            pm.getPosTan(guideLength * i / (GUIDE_POINTS - 1), pos, null);
            guidePoints.add(new PointF(pos[0], pos[1]));
        }

        java.util.Arrays.fill(coveredPoints, false);
        coveredCount = 0;
        invalidate();
    }

    private void buildPathForLetter(String ch, float cx, float cy, float u) {
        // Normalize: take the first code-point
        if (ch == null || ch.isEmpty()) ch = "أ";
        String c = ch.trim();
        if (c.isEmpty()) c = "أ";

        // Arabic letters
        switch (c) {
            case "أ": case "ا":
                // Vertical stroke down, then small curve at top
                guidePath.moveTo(cx, cy - u);
                guidePath.lineTo(cx, cy + u);
                break;
            case "ب":
                // Horizontal line with dot below
                guidePath.moveTo(cx - u, cy);
                guidePath.cubicTo(cx - u * 0.5f, cy + u * 0.3f,
                    cx + u * 0.5f, cy + u * 0.3f,
                    cx + u, cy);
                guidePath.lineTo(cx + u * 0.3f, cy - u * 0.2f);
                break;
            case "ت": case "ث":
                guidePath.moveTo(cx - u, cy);
                guidePath.cubicTo(cx - u * 0.5f, cy + u * 0.3f,
                    cx + u * 0.5f, cy + u * 0.3f,
                    cx + u, cy);
                guidePath.lineTo(cx + u * 0.3f, cy - u * 0.2f);
                break;
            case "ج": case "ح": case "خ":
                guidePath.moveTo(cx - u * 0.5f, cy - u * 0.3f);
                guidePath.lineTo(cx + u * 0.5f, cy - u * 0.3f);
                guidePath.cubicTo(cx + u * 0.8f, cy - u * 0.3f,
                    cx + u, cy + u * 0.2f,
                    cx + u * 0.3f, cy + u * 0.5f);
                guidePath.lineTo(cx - u * 0.2f, cy + u * 0.8f);
                break;
            case "د": case "ذ":
                guidePath.moveTo(cx, cy - u);
                guidePath.cubicTo(cx + u * 0.6f, cy - u,
                    cx + u, cy - u * 0.3f,
                    cx + u, cy + u * 0.1f);
                guidePath.cubicTo(cx + u, cy + u * 0.5f,
                    cx + u * 0.6f, cy + u * 0.8f,
                    cx, cy + u);
                break;
            case "ر": case "ز":
                guidePath.moveTo(cx + u * 0.3f, cy - u * 0.5f);
                guidePath.cubicTo(cx + u * 0.5f, cy - u * 0.3f,
                    cx + u * 0.5f, cy + u * 0.3f,
                    cx, cy + u);
                break;
            case "س": case "ش":
                // Three humps
                guidePath.moveTo(cx - u, cy);
                guidePath.cubicTo(cx - u * 0.7f, cy - u * 0.4f,
                    cx - u * 0.4f, cy - u * 0.4f,
                    cx - u * 0.3f, cy);
                guidePath.cubicTo(cx - u * 0.1f, cy - u * 0.4f,
                    cx + u * 0.2f, cy - u * 0.4f,
                    cx + u * 0.3f, cy);
                guidePath.cubicTo(cx + u * 0.5f, cy - u * 0.4f,
                    cx + u * 0.8f, cy - u * 0.4f,
                    cx + u, cy);
                guidePath.lineTo(cx + u * 0.5f, cy + u * 0.4f);
                break;
            case "ص": case "ض":
                guidePath.moveTo(cx - u * 0.2f, cy - u * 0.3f);
                guidePath.addOval(new RectF(cx - u * 0.7f, cy - u * 0.5f,
                    cx + u * 0.2f, cy + u * 0.3f), Path.Direction.CW);
                guidePath.moveTo(cx + u * 0.2f, cy - u * 0.1f);
                guidePath.lineTo(cx + u, cy - u * 0.1f);
                guidePath.lineTo(cx + u, cy + u * 0.5f);
                break;
            case "ط": case "ظ":
                guidePath.moveTo(cx - u * 0.3f, cy + u * 0.5f);
                guidePath.lineTo(cx + u * 0.3f, cy + u * 0.5f);
                guidePath.moveTo(cx, cy + u * 0.5f);
                guidePath.lineTo(cx, cy - u * 0.5f);
                guidePath.cubicTo(cx, cy - u * 0.8f, cx - u * 0.3f, cy - u * 0.8f,
                    cx - u * 0.3f, cy - u * 0.5f);
                guidePath.lineTo(cx - u * 0.3f, cy + u * 0.5f);
                break;
            case "ع": case "غ":
                guidePath.moveTo(cx + u * 0.5f, cy - u * 0.5f);
                guidePath.cubicTo(cx + u * 0.2f, cy - u * 0.8f,
                    cx - u * 0.4f, cy - u * 0.8f,
                    cx - u * 0.5f, cy - u * 0.2f);
                guidePath.cubicTo(cx - u * 0.6f, cy + u * 0.3f,
                    cx - u * 0.2f, cy + u * 0.5f,
                    cx + u * 0.2f, cy + u * 0.2f);
                guidePath.lineTo(cx + u * 0.2f, cy + u * 0.8f);
                break;
            case "ف":
                guidePath.addCircle(cx - u * 0.2f, cy, u * 0.45f, Path.Direction.CW);
                guidePath.moveTo(cx + u * 0.25f, cy);
                guidePath.lineTo(cx + u, cy);
                guidePath.lineTo(cx + u, cy + u * 0.4f);
                break;
            case "ق":
                guidePath.addCircle(cx, cy - u * 0.1f, u * 0.45f, Path.Direction.CW);
                guidePath.moveTo(cx - u * 0.45f, cy - u * 0.1f);
                guidePath.lineTo(cx - u * 0.45f, cy + u * 0.7f);
                guidePath.moveTo(cx + u * 0.45f, cy - u * 0.1f);
                guidePath.lineTo(cx + u * 0.45f, cy + u * 0.7f);
                break;
            case "ك":
                guidePath.moveTo(cx + u, cy - u * 0.5f);
                guidePath.lineTo(cx + u, cy + u * 0.7f);
                guidePath.moveTo(cx - u * 0.5f, cy - u * 0.3f);
                guidePath.cubicTo(cx, cy - u * 0.5f, cx + u * 0.7f, cy - u * 0.3f,
                    cx + u, cy + u * 0.1f);
                guidePath.moveTo(cx - u * 0.3f, cy + u * 0.1f);
                guidePath.lineTo(cx + u * 0.5f, cy + u * 0.7f);
                break;
            case "ل":
                guidePath.moveTo(cx + u * 0.2f, cy - u);
                guidePath.cubicTo(cx + u * 0.6f, cy - u,
                    cx + u * 0.8f, cy - u * 0.4f,
                    cx + u * 0.8f, cy);
                guidePath.cubicTo(cx + u * 0.8f, cy + u * 0.5f,
                    cx + u * 0.4f, cy + u * 0.8f,
                    cx, cy + u * 0.8f);
                guidePath.lineTo(cx - u * 0.5f, cy + u * 0.8f);
                break;
            case "م":
                guidePath.moveTo(cx + u * 0.5f, cy - u * 0.2f);
                guidePath.addCircle(cx, cy, u * 0.45f, Path.Direction.CW);
                guidePath.moveTo(cx - u * 0.45f, cy);
                guidePath.lineTo(cx - u * 0.45f, cy + u * 0.7f);
                break;
            case "ن":
                guidePath.moveTo(cx - u * 0.8f, cy - u * 0.2f);
                guidePath.cubicTo(cx - u * 0.8f, cy + u * 0.5f,
                    cx + u * 0.8f, cy + u * 0.5f,
                    cx + u * 0.8f, cy - u * 0.2f);
                break;
            case "ه":
                guidePath.addCircle(cx, cy, u * 0.5f, Path.Direction.CW);
                break;
            case "و":
                guidePath.moveTo(cx + u * 0.3f, cy - u * 0.2f);
                guidePath.addArc(new RectF(cx - u * 0.3f, cy - u * 0.5f,
                    cx + u * 0.7f, cy + u * 0.3f), -90, 270);
                guidePath.lineTo(cx - u * 0.3f, cy + u);
                break;
            case "ي": case "ى":
                guidePath.moveTo(cx - u, cy - u * 0.2f);
                guidePath.cubicTo(cx - u * 0.3f, cy + u * 0.3f,
                    cx + u * 0.3f, cy + u * 0.3f,
                    cx + u, cy - u * 0.2f);
                guidePath.cubicTo(cx + u * 0.7f, cy + u * 0.5f,
                    cx - u * 0.7f, cy + u * 0.5f,
                    cx - u, cy + u * 0.2f);
                break;
            // English uppercase letters
            case "A":
                guidePath.moveTo(cx - u * 0.6f, cy + u);
                guidePath.lineTo(cx, cy - u);
                guidePath.lineTo(cx + u * 0.6f, cy + u);
                guidePath.moveTo(cx - u * 0.3f, cy + u * 0.1f);
                guidePath.lineTo(cx + u * 0.3f, cy + u * 0.1f);
                break;
            case "B":
                guidePath.moveTo(cx - u * 0.4f, cy - u);
                guidePath.lineTo(cx - u * 0.4f, cy + u);
                guidePath.moveTo(cx - u * 0.4f, cy - u);
                guidePath.cubicTo(cx + u * 0.5f, cy - u, cx + u * 0.7f, cy - u * 0.5f,
                    cx - u * 0.4f, cy);
                guidePath.moveTo(cx - u * 0.4f, cy);
                guidePath.cubicTo(cx + u * 0.6f, cy, cx + u * 0.8f, cy + u * 0.5f,
                    cx - u * 0.4f, cy + u);
                break;
            case "C":
                guidePath.addArc(new RectF(cx - u * 0.6f, cy - u, cx + u * 0.6f, cy + u),
                    -30, -300);
                break;
            case "D":
                guidePath.moveTo(cx - u * 0.4f, cy - u);
                guidePath.lineTo(cx - u * 0.4f, cy + u);
                guidePath.moveTo(cx - u * 0.4f, cy - u);
                guidePath.cubicTo(cx + u * 0.8f, cy - u, cx + u * 0.8f, cy + u,
                    cx - u * 0.4f, cy + u);
                break;
            case "E":
                guidePath.moveTo(cx + u * 0.5f, cy - u);
                guidePath.lineTo(cx - u * 0.4f, cy - u);
                guidePath.lineTo(cx - u * 0.4f, cy + u);
                guidePath.lineTo(cx + u * 0.5f, cy + u);
                guidePath.moveTo(cx - u * 0.4f, cy);
                guidePath.lineTo(cx + u * 0.3f, cy);
                break;
            case "F":
                guidePath.moveTo(cx - u * 0.4f, cy - u);
                guidePath.lineTo(cx - u * 0.4f, cy + u);
                guidePath.moveTo(cx - u * 0.4f, cy - u);
                guidePath.lineTo(cx + u * 0.5f, cy - u);
                guidePath.moveTo(cx - u * 0.4f, cy);
                guidePath.lineTo(cx + u * 0.3f, cy);
                break;
            case "G":
                guidePath.addArc(new RectF(cx - u * 0.6f, cy - u, cx + u * 0.6f, cy + u),
                    -30, -300);
                guidePath.moveTo(cx + u * 0.6f, cy);
                guidePath.lineTo(cx + u * 0.1f, cy);
                break;
            case "H":
                guidePath.moveTo(cx - u * 0.5f, cy - u);
                guidePath.lineTo(cx - u * 0.5f, cy + u);
                guidePath.moveTo(cx + u * 0.5f, cy - u);
                guidePath.lineTo(cx + u * 0.5f, cy + u);
                guidePath.moveTo(cx - u * 0.5f, cy);
                guidePath.lineTo(cx + u * 0.5f, cy);
                break;
            case "I":
                guidePath.moveTo(cx, cy - u);
                guidePath.lineTo(cx, cy + u);
                guidePath.moveTo(cx - u * 0.3f, cy - u);
                guidePath.lineTo(cx + u * 0.3f, cy - u);
                guidePath.moveTo(cx - u * 0.3f, cy + u);
                guidePath.lineTo(cx + u * 0.3f, cy + u);
                break;
            case "J":
                guidePath.moveTo(cx + u * 0.3f, cy - u);
                guidePath.lineTo(cx + u * 0.3f, cy + u * 0.5f);
                guidePath.cubicTo(cx + u * 0.3f, cy + u, cx - u * 0.5f, cy + u, cx - u * 0.5f, cy + u * 0.3f);
                break;
            case "K":
                guidePath.moveTo(cx - u * 0.4f, cy - u);
                guidePath.lineTo(cx - u * 0.4f, cy + u);
                guidePath.moveTo(cx + u * 0.5f, cy - u);
                guidePath.lineTo(cx - u * 0.4f, cy);
                guidePath.lineTo(cx + u * 0.5f, cy + u);
                break;
            case "L":
                guidePath.moveTo(cx - u * 0.4f, cy - u);
                guidePath.lineTo(cx - u * 0.4f, cy + u);
                guidePath.lineTo(cx + u * 0.5f, cy + u);
                break;
            case "M":
                guidePath.moveTo(cx - u * 0.6f, cy + u);
                guidePath.lineTo(cx - u * 0.6f, cy - u);
                guidePath.lineTo(cx, cy);
                guidePath.lineTo(cx + u * 0.6f, cy - u);
                guidePath.lineTo(cx + u * 0.6f, cy + u);
                break;
            case "N":
                guidePath.moveTo(cx - u * 0.5f, cy + u);
                guidePath.lineTo(cx - u * 0.5f, cy - u);
                guidePath.lineTo(cx + u * 0.5f, cy + u);
                guidePath.lineTo(cx + u * 0.5f, cy - u);
                break;
            case "O":
                guidePath.addOval(new RectF(cx - u * 0.6f, cy - u, cx + u * 0.6f, cy + u),
                    Path.Direction.CW);
                break;
            case "P":
                guidePath.moveTo(cx - u * 0.4f, cy - u);
                guidePath.lineTo(cx - u * 0.4f, cy + u);
                guidePath.moveTo(cx - u * 0.4f, cy - u);
                guidePath.cubicTo(cx + u * 0.6f, cy - u, cx + u * 0.6f, cy,
                    cx - u * 0.4f, cy);
                break;
            case "Q":
                guidePath.addOval(new RectF(cx - u * 0.6f, cy - u, cx + u * 0.6f, cy + u),
                    Path.Direction.CW);
                guidePath.moveTo(cx + u * 0.2f, cy + u * 0.5f);
                guidePath.lineTo(cx + u * 0.7f, cy + u);
                break;
            case "R":
                guidePath.moveTo(cx - u * 0.4f, cy - u);
                guidePath.lineTo(cx - u * 0.4f, cy + u);
                guidePath.moveTo(cx - u * 0.4f, cy - u);
                guidePath.cubicTo(cx + u * 0.6f, cy - u, cx + u * 0.6f, cy,
                    cx - u * 0.4f, cy);
                guidePath.lineTo(cx + u * 0.5f, cy + u);
                break;
            case "S":
                guidePath.moveTo(cx + u * 0.5f, cy - u * 0.8f);
                guidePath.cubicTo(cx - u * 0.5f, cy - u * 1.1f, cx - u * 0.8f, cy - u * 0.3f,
                    cx, cy);
                guidePath.cubicTo(cx + u * 0.8f, cy + u * 0.3f, cx + u * 0.5f, cy + u * 1.1f,
                    cx - u * 0.5f, cy + u * 0.8f);
                break;
            case "T":
                guidePath.moveTo(cx - u * 0.6f, cy - u);
                guidePath.lineTo(cx + u * 0.6f, cy - u);
                guidePath.moveTo(cx, cy - u);
                guidePath.lineTo(cx, cy + u);
                break;
            case "U":
                guidePath.moveTo(cx - u * 0.5f, cy - u);
                guidePath.lineTo(cx - u * 0.5f, cy + u * 0.4f);
                guidePath.cubicTo(cx - u * 0.5f, cy + u * 0.9f, cx + u * 0.5f, cy + u * 0.9f,
                    cx + u * 0.5f, cy + u * 0.4f);
                guidePath.lineTo(cx + u * 0.5f, cy - u);
                break;
            case "V":
                guidePath.moveTo(cx - u * 0.6f, cy - u);
                guidePath.lineTo(cx, cy + u);
                guidePath.lineTo(cx + u * 0.6f, cy - u);
                break;
            case "W":
                guidePath.moveTo(cx - u * 0.7f, cy - u);
                guidePath.lineTo(cx - u * 0.35f, cy + u);
                guidePath.lineTo(cx, cy);
                guidePath.lineTo(cx + u * 0.35f, cy + u);
                guidePath.lineTo(cx + u * 0.7f, cy - u);
                break;
            case "X":
                guidePath.moveTo(cx - u * 0.5f, cy - u);
                guidePath.lineTo(cx + u * 0.5f, cy + u);
                guidePath.moveTo(cx + u * 0.5f, cy - u);
                guidePath.lineTo(cx - u * 0.5f, cy + u);
                break;
            case "Y":
                guidePath.moveTo(cx - u * 0.5f, cy - u);
                guidePath.lineTo(cx, cy);
                guidePath.moveTo(cx + u * 0.5f, cy - u);
                guidePath.lineTo(cx, cy);
                guidePath.lineTo(cx, cy + u);
                break;
            case "Z":
                guidePath.moveTo(cx - u * 0.5f, cy - u);
                guidePath.lineTo(cx + u * 0.5f, cy - u);
                guidePath.lineTo(cx - u * 0.5f, cy + u);
                guidePath.lineTo(cx + u * 0.5f, cy + u);
                break;
            default:
                // Fallback: simple vertical line
                guidePath.moveTo(cx, cy - u);
                guidePath.lineTo(cx, cy + u);
                break;
        }
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
