package myfirstwords.mynationdreams;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.media.MediaPlayer;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import java.io.*;
import java.io.InputStream;
import java.security.SecureRandom;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;


public class MainActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private String test = "";
	private HashMap<String, Object> language = new HashMap<>();
	private HashMap<String, Object> bed1 = new HashMap<>();
	private double n = 0;
	private HashMap<String, Object> bed = new HashMap<>();
	
	private ArrayList<String> color = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> bed11 = new ArrayList<>();
	private ArrayList<String> bedd = new ArrayList<>();
	
	private LinearLayout background;
	private LinearLayout linear3;
	private LinearLayout logo;
	private LinearLayout linear4;
	private TextView textview1;
	private LinearLayout left;
	private LinearLayout right;
	private LinearLayout background_one;
	private LinearLayout background_yellow;
	private LinearLayout green;
	private ImageView imageview1;
	private LinearLayout yellow;
	private ImageView imageview2;
	private LinearLayout background_red;
	private LinearLayout background_blue;
	private LinearLayout red;
	private ImageView imageview3;
	private LinearLayout blue;
	private ImageView imageview4;
	
	private TimerTask timer;
	private TimerTask t;
	private TimerTask timer2;
	private Intent intent = new Intent();
	private SharedPreferences language1;
	private MediaPlayer mediaPlayer2;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		background = MainActivity.this.findViewById(R.id.background);
		linear3 = MainActivity.this.findViewById(R.id.linear3);
		logo = MainActivity.this.findViewById(R.id.logo);
		linear4 = MainActivity.this.findViewById(R.id.linear4);
		textview1 = MainActivity.this.findViewById(R.id.textview1);
		left = MainActivity.this.findViewById(R.id.left);
		right = MainActivity.this.findViewById(R.id.right);
		background_one = MainActivity.this.findViewById(R.id.background_one);
		background_yellow = MainActivity.this.findViewById(R.id.background_yellow);
		green = MainActivity.this.findViewById(R.id.green);
		imageview1 = MainActivity.this.findViewById(R.id.imageview1);
		yellow = MainActivity.this.findViewById(R.id.yellow);
		imageview2 = MainActivity.this.findViewById(R.id.imageview2);
		background_red = MainActivity.this.findViewById(R.id.background_red);
		background_blue = MainActivity.this.findViewById(R.id.background_blue);
		red = MainActivity.this.findViewById(R.id.red);
		imageview3 = MainActivity.this.findViewById(R.id.imageview3);
		blue = MainActivity.this.findViewById(R.id.blue);
		imageview4 = MainActivity.this.findViewById(R.id.imageview4);
		language1 = this.getSharedPreferences("language1", Activity.MODE_PRIVATE);
	}
	
	private void initializeLogic() {
		_ui();
		_lightStatusBar(true);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		_anim();
	}
	public void _shape(final double _top1, final double _top2, final double _bottom2, final double _bottom1, final String _inside_color, final String _side_color, final double _side_size, final View _view) {
		Double tlr = _top1;
		Double trr = _top2;
		Double blr = _bottom2;
		Double brr = _bottom1;
		Double sw = _side_size;
		android.graphics.drawable.GradientDrawable s = new android.graphics.drawable.GradientDrawable();
		s.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
		s.setCornerRadii(new float[] {tlr.floatValue(),tlr.floatValue(), trr.floatValue(),trr.floatValue(), blr.floatValue(),blr.floatValue(), brr.floatValue(),brr.floatValue()}); 
		
		s.setColor(Color.parseColor(_inside_color));
		s.setStroke(sw.intValue(), Color.parseColor(_side_color));
		_view.setBackground(s);
	}
	
	
	public void _ui() {
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/semi.ttf"), 1);
		try {
			java.io.InputStream imageview1Input = getAssets().open("ani_8.png"); 
			Drawable imageview1Draw = Drawable.createFromStream(imageview1Input, null);
			imageview1.setImageDrawable(imageview1Draw);
			imageview1Input.close();
		} catch(java.io.IOException ex) {}
		try {
			java.io.InputStream imageview2Input = getAssets().open("frt_8.png"); 
			Drawable imageview2Draw = Drawable.createFromStream(imageview2Input, null);
			imageview2.setImageDrawable(imageview2Draw);
			imageview2Input.close();
		} catch(java.io.IOException ex) {}
		try {
			java.io.InputStream imageview3Input = getAssets().open("toy_4.png"); 
			Drawable imageview3Draw = Drawable.createFromStream(imageview3Input, null);
			imageview3.setImageDrawable(imageview3Draw);
			imageview3Input.close();
		} catch(java.io.IOException ex) {}
		try {
			java.io.InputStream imageview4Input = getAssets().open("tool_6.png"); 
			Drawable imageview4Draw = Drawable.createFromStream(imageview4Input, null);
			imageview4.setImageDrawable(imageview4Draw);
			imageview4Input.close();
		} catch(java.io.IOException ex) {}
		_shape(30, 30, 30, 30, "#00865E", "#ffffff", 0, green);
		_shape(30, 30, 30, 30, "#e8b315", "#ffffff", 0, yellow);
		_shape(30, 30, 30, 30, "#c81c56", "#ffffff", 0, red);
		_shape(30, 30, 30, 30, "#0079ca", "#ffffff", 0, blue);
		textview1.setTextSize((float)20);
	}
	
	
	public void _anim() {
		imageview1.setScaleX((float)(0));
		imageview1.setScaleY((float)(0));
		imageview2.setScaleX((float)(0));
		imageview2.setScaleY((float)(0));
		imageview3.setScaleX((float)(0));
		imageview3.setScaleY((float)(0));
		imageview4.setScaleX((float)(0));
		imageview4.setScaleY((float)(0));
		_Animator(green, "translationY", 500, 0);
		_Animator(yellow, "translationY", 500, 0);
		_Animator(red, "translationY", 500, 0);
		_Animator(blue, "translationY", 500, 0);
		timer = new TimerTask() {
			@Override
			public void run() {
				MainActivity.this.runOnUiThread(() -> {
					_Animator(green, "translationY", 0, 500);
				});
			}
		};
		_timer.schedule(timer, (int)(100));
		timer = new TimerTask() {
			@Override
			public void run() {
				MainActivity.this.runOnUiThread(() -> {
					imageview1.setScaleX((float)(1));
					imageview1.setScaleY((float)(1));
					_FadeOut(imageview1, 100);
					if (mediaPlayer2!=null) { 
						mediaPlayer2.reset(); }
					try { mediaPlayer2 =new MediaPlayer();
						AssetFileDescriptor descriptor = getAssets().openFd("sound effects/".concat("jump.mp3"));
						mediaPlayer2.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
						descriptor.close(); 
						mediaPlayer2.prepare(); 
						
						mediaPlayer2.setLooping(false); 
						mediaPlayer2.start();
					} catch (Exception e) { e.printStackTrace(); }
				});
			}
		};
		_timer.schedule(timer, (int)(600));
		timer = new TimerTask() {
			@Override
			public void run() {
				MainActivity.this.runOnUiThread(() -> {
					_Animator(red, "translationY", 0, 500);
				});
			}
		};
		_timer.schedule(timer, (int)(700));
		timer = new TimerTask() {
			@Override
			public void run() {
				MainActivity.this.runOnUiThread(() -> {
					imageview3.setScaleX((float)(1));
					imageview3.setScaleY((float)(1));
					_FadeOut(imageview3, 100);
					if (mediaPlayer2!=null) { 
						mediaPlayer2.reset(); }
					try { mediaPlayer2 =new MediaPlayer();
						AssetFileDescriptor descriptor = getAssets().openFd("sound effects/".concat("jump.mp3"));
						mediaPlayer2.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
						descriptor.close(); 
						mediaPlayer2.prepare(); 
						
						mediaPlayer2.setLooping(false); 
						mediaPlayer2.start();
					} catch (Exception e) { e.printStackTrace(); }
				});
			}
		};
		_timer.schedule(timer, (int)(1200));
		timer = new TimerTask() {
			@Override
			public void run() {
				MainActivity.this.runOnUiThread(() -> {
					_Animator(yellow, "translationY", 0, 500);
				});
			}
		};
		_timer.schedule(timer, (int)(1300));
		timer = new TimerTask() {
			@Override
			public void run() {
				MainActivity.this.runOnUiThread(() -> {
					imageview4.setScaleX((float)(1));
					imageview4.setScaleY((float)(1));
					_FadeOut(imageview2, 100);
					if (mediaPlayer2!=null) { 
						mediaPlayer2.reset(); }
					try { mediaPlayer2 =new MediaPlayer();
						AssetFileDescriptor descriptor = getAssets().openFd("sound effects/".concat("jump.mp3"));
						mediaPlayer2.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
						descriptor.close(); 
						mediaPlayer2.prepare(); 
						
						mediaPlayer2.setLooping(false); 
						mediaPlayer2.start();
					} catch (Exception e) { e.printStackTrace(); }
				});
			}
		};
		_timer.schedule(timer, (int)(1800));
		timer = new TimerTask() {
			@Override
			public void run() {
				MainActivity.this.runOnUiThread(() -> {
					_Animator(blue, "translationY", 0, 500);
				});
			}
		};
		_timer.schedule(timer, (int)(1900));
		timer = new TimerTask() {
			@Override
			public void run() {
				MainActivity.this.runOnUiThread(() -> {
					imageview2.setScaleX((float)(1));
					imageview2.setScaleY((float)(1));
					_FadeOut(imageview4, 100);
					if (mediaPlayer2!=null) { 
						mediaPlayer2.reset(); }
					try { mediaPlayer2 =new MediaPlayer();
						AssetFileDescriptor descriptor = getAssets().openFd("sound effects/".concat("jump.mp3"));
						mediaPlayer2.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
						descriptor.close(); 
						mediaPlayer2.prepare(); 
						
						mediaPlayer2.setLooping(false); 
						mediaPlayer2.start();
					} catch (Exception e) { e.printStackTrace(); }
				});
			}
		};
		_timer.schedule(timer, (int)(2400));
		if (String.valueOf(MainActivity.this.getApplicationContext().getPackageName()).equals(String.valueOf("myfirstwords.mynationdreams"))) {
			timer = new TimerTask() {
				@Override
				public void run() {
					MainActivity.this.runOnUiThread(() -> {
						intent.setClass(MainActivity.this.getApplicationContext(), HmeActivity.class);
						MainActivity.this.startActivity(intent);
						MainActivity.this.finish();
					});
				}
			};
			_timer.schedule(timer, (int)(3000));
		} else {
			SketchwareUtil.showMessage(MainActivity.this.getApplicationContext(), "Download It From Google Play");
			intent.setAction(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=myfirstwords.mynationdreams"));
			MainActivity.this.startActivity(intent);
			finishAffinity();
		}
	}
	
	
	public void _Animator(final View _view, final String _propertyName, final double _value, final double _duration) {
		ObjectAnimator anim = new ObjectAnimator();
		anim.setTarget(_view);
		anim.setPropertyName(_propertyName);
		anim.setFloatValues((float)_value);
		anim.setDuration((long)_duration);
		anim.setInterpolator(new android.view.animation.AccelerateDecelerateInterpolator());
		anim.start();
	}
	
	
	public void _FadeOut(final View _view, final double _duration) {
		_Animator(_view, "scaleX", 0, 200);
		_Animator(_view, "scaleY", 0, 200);
		t = new TimerTask() {
			@Override
			public void run() {
				MainActivity.this.runOnUiThread(() -> {
					_Animator(_view, "scaleX", 1, 200);
					_Animator(_view, "scaleY", 1, 200);
				});
			}
		};
		_timer.schedule(t, (int)(_duration));
	}
	
	
	public void _lightStatusBar(final boolean _shouldchange) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { View decor = getWindow().getDecorView();
			if (_shouldchange) { decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR); } else { decor.setSystemUiVisibility(0); } }
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(MainActivity.this.getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		SecureRandom random = new SecureRandom();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}