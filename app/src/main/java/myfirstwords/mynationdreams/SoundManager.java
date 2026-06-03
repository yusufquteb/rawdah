package myfirstwords.mynationdreams;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundManager {

    private MediaPlayer mediaPlayer;
    private SoundPool soundPool;
    private int soundCorrect = -1;
    private int soundWrong = -1;
    private int soundCelebrate = -1;
    private boolean poolReady = false;

    public SoundManager(Context context) {
        AudioAttributes attrs = new AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build();

        soundPool = new SoundPool.Builder()
            .setMaxStreams(4)
            .setAudioAttributes(attrs)
            .build();

        soundPool.setOnLoadCompleteListener((sp, sampleId, status) -> {
            poolReady = true;
        });

        try {
            soundCorrect = soundPool.load(context.getAssets().openFd("sound effects/jump.mp3"), 1);
            soundCelebrate = soundPool.load(context.getAssets().openFd("sound effects/collect-achievement-prize.mp3"), 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playAsset(Context context, String path) {
        stopMedia();
        try {
            mediaPlayer = new MediaPlayer();
            AssetFileDescriptor fd = context.getAssets().openFd(path);
            mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            fd.close();
            mediaPlayer.prepare();
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playAssetWithDelay(Context context, String path, long delayMs) {
        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() ->
            playAsset(context, path), delayMs);
    }

    public void playCorrectSound() {
        if (poolReady && soundCorrect != -1) {
            soundPool.play(soundCorrect, 1f, 1f, 1, 0, 1f);
        }
    }

    public void playCelebration() {
        if (poolReady && soundCelebrate != -1) {
            soundPool.play(soundCelebrate, 1f, 1f, 1, 0, 1f);
        }
    }

    public void stopMedia() {
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) mediaPlayer.stop();
                mediaPlayer.release();
            } catch (Exception ignored) {}
            mediaPlayer = null;
        }
    }

    public void release() {
        stopMedia();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }
}
