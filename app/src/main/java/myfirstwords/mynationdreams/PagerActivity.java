package myfirstwords.mynationdreams;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.media.MediaPlayer;
import android.net.*;
import android.os.*;
import android.os.Bundle;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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



public class PagerActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private double n = 0;
	private String dir = "";
	private boolean check = false;
	private double counter = 0;
	private String nam1 = "";
	private String name = "";
	private String names = "";
	private String myjson = "";
	private double height = 0;
	private double width = 0;
	private String pathOrUrlOrAsset = "";
	private double currentDuration = 0;
	private double maxDuration = 0;
	private String namme = "";
	private String filename = "";
	private double count2 = 0;
	private String color = "";
	private String color1 = "";
	private String data = "";
	private HashMap<String, Object> datamap = new HashMap<>();
	private String mainlanguage = "";
	private HashMap<String, Object> language = new HashMap<>();
	private String test = "";
	private String secondlanguage = "";
	private String data1 = "";
	private HashMap<String, Object> datamap1 = new HashMap<>();
	private double duration = 0;
	private double duration1 = 0;
	private String animals_e_a = "";
	private double no = 0;
	private String lottie = "";
	private String path = "";
	private String tv = "";
	private String fontName = "";
	private String typeace = "";
	
	private ArrayList<HashMap<String, Object>> map = new ArrayList<>();
	private ArrayList<String> liststring = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> nameslist = new ArrayList<>();
	private ArrayList<String> randomm = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> datalist = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> list_ = new ArrayList<>();
	private ArrayList<String> lottie_list = new ArrayList<>();
	
	private LinearLayout linear5;
	private LinearLayout linear18;
	private LinearLayout linear20;
	private ImageView imageview1;
	private LinearLayout linear3;
	private ViewPager viewpager2;
	
	private TimerTask TIMER;
	private TimerTask ads;
	private SharedPreferences language1;
	private Intent i = new Intent();
	private MediaPlayer mediaPlayer;
	private SharedPreferences pic;
	private TimerTask t4;
	private TimerTask t;
	private TimerTask t1;
	private TimerTask t3;
	private Intent intent = new Intent();
	private TimerTask media_timer = new TimerTask() { @Override public void run() {} };
	private TimerTask hide;
	private Intent i1 = new Intent();
	private MediaPlayer mediaPlayer1;
	private MediaPlayer mediaPlayer2;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.pager);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear5 = PagerActivity.this.findViewById(R.id.linear5);
		linear18 = PagerActivity.this.findViewById(R.id.linear18);
		linear20 = PagerActivity.this.findViewById(R.id.linear20);
		imageview1 = PagerActivity.this.findViewById(R.id.imageview1);
		linear3 = PagerActivity.this.findViewById(R.id.linear3);
		viewpager2 = PagerActivity.this.findViewById(R.id.viewpager2);
		language1 = this.getSharedPreferences("language1", Activity.MODE_PRIVATE);
		pic = this.getSharedPreferences("pic", Activity.MODE_PRIVATE);
		
		imageview1.setOnClickListener(View _view -> {
			if (mediaPlayer!=null) { 
				mediaPlayer.reset(); }
			media_timer.cancel();
			i.setClass(PagerActivity.this.getApplicationContext(), HmeActivity.class);
			PagerActivity.this.startActivity(i);
			PagerActivity.this.finish();
		});
		
		viewpager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int _position, float _positionOffset, int _positionOffsetPixels) {
				
			}
			
			@Override
			public void onPageSelected(int _position) {
				media_timer.cancel();
				if (map.get((int)_position).containsKey("name")) {
					if (mediaPlayer!=null) { 
						mediaPlayer.reset(); }
					try { mediaPlayer =new MediaPlayer();
						AssetFileDescriptor descriptor = getAssets().openFd(getIntent().getStringExtra("type").concat("/sounds/speech/".concat("Arabic".concat("/"))).concat(map.get((int)_position).get("name").toString().toLowerCase().concat(".mp3")));
						mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
						descriptor.close(); 
						mediaPlayer.prepare(); 
						
						mediaPlayer.setLooping(false); 
						mediaPlayer.start();
					} catch (Exception e) { e.printStackTrace(); }
					media_timer = new TimerTask() {
						@Override
						public void run() {
							PagerActivity.this.runOnUiThread(() -> {
								if (mediaPlayer!=null) { 
									mediaPlayer.reset(); }
								try { mediaPlayer =new MediaPlayer();
									AssetFileDescriptor descriptor = getAssets().openFd(getIntent().getStringExtra("type").concat("/sounds/speech/".concat("English".concat("/"))).concat(map.get((int)_position).get("name").toString().toLowerCase().concat(".mp3")));
									mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
									descriptor.close(); 
									mediaPlayer.prepare(); 
									
									mediaPlayer.setLooping(false); 
									mediaPlayer.start();
								} catch (Exception e) { e.printStackTrace(); }
							});
						}
					};
					_timer.schedule(media_timer, (int)(mediaPlayer.getDuration() + 400));
				}
				no = SketchwareUtil.getRandom((int)(1), (int)(17));
			}
			
			@Override
			public void onPageScrollStateChanged(int _scrollState) {
				
			}
		});
	}
	
	private void initializeLogic() {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
		_languageblock2();
		_pagerlist();
		/*
lottie
*/
		no = SketchwareUtil.getRandom((int)(1), (int)(17));
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		_soundsoncreate();
	}
	
	@Override
	public void onBackPressed() {
		if (mediaPlayer!=null) { 
			mediaPlayer.reset(); }
		media_timer.cancel();
		i.setClass(PagerActivity.this.getApplicationContext(), HmeActivity.class);
		PagerActivity.this.startActivity(i);
		PagerActivity.this.finish();
	}
	public void _asset_md(final String _dir) {
		/*
}
private MediaPlayer mediaPlayer;
{
*/
		if (mediaPlayer!=null) { 
			mediaPlayer.reset(); }
		try { mediaPlayer =new MediaPlayer();
			AssetFileDescriptor descriptor = getAssets().openFd(_dir); 
			mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
			descriptor.close(); 
			mediaPlayer.prepare(); 
			mediaPlayer.setLooping(false); 
			mediaPlayer.start();
		} catch (Exception e) { e.printStackTrace(); }
		
	}
	
	
	public void _asset_list() {
		AssetManager liststringA = getAssets();
		try {
			String[] liststringS = liststringA.list("Sounds");
			liststring = new ArrayList<String>(Arrays.asList(liststringS));
		}catch(Exception e){}
		n = 0;
		for(int _repeat12 = 0; _repeat12 < (int)(liststring.size()); _repeat12++) {
			_get("");
			{
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("name", liststring.get((int)(n)));
				map.add(_item);
			}
			n++;
		}
		viewpager2.setCurrentItem((int)0);
		viewpager2.setAdapter(new Viewpager2Adapter(map));
		((PagerAdapter)viewpager2.getAdapter()).notifyDataSetChanged();
	}
	
	
	public void _get(final String _name) {
		/*
get file name
*/
		counter = (_name != null && "." != null ? _name.lastIndexOf(".") : -1);
		if ((counter > 0) && (counter < ((_name != null ? _name.length() : 0) - 1))) {
			nam1 = (_name != null ? _name.substring(Math.max(0, Math.min((int)(0), _name.length())), Math.max(0, Math.min((int)(counter), _name.length()))) : "");
		}
	}
	
	
	public void _FadeOut(final View _view, final double _duration) {
		_Animator(_view, "scaleX", 0, 200);
		_Animator(_view, "scaleY", 0, 200);
		t = new TimerTask() {
			@Override
			public void run() {
				PagerActivity.this.runOnUiThread(() -> {
					_Animator(_view, "scaleX", 1, 200);
					_Animator(_view, "scaleY", 1, 200);
				});
			}
		};
		_timer.schedule(t, (int)(_duration));
	}
	
	
	public void _Animator(final View _view, final String _propertyName, final double _value, final double _duration) {
		ObjectAnimator anim = new ObjectAnimator();
		anim.setTarget(_view);
		anim.setPropertyName(_propertyName);
		anim.setFloatValues((float)_value);
		anim.setDuration((long)_duration);
		anim.start();
	}
	
	
	public void _asset_view_background(final View _vi, final String _dir) {
		try {
			java.io.InputStream _viInput = getAssets().open(_dir); 
			Drawable _viDraw = Drawable.createFromStream(_viInput, null);
			_vi.setBackgroundDrawable(_viDraw);
			_viInput.close();
		} catch(java.io.IOException ex) {}
		
		
	}
	
	
	public void _ass_li(final String _dir, final String _st) {
		map.clear();
		liststring.clear();
		AssetManager liststringA = getAssets();
		try {
			String[] liststringS = liststringA.list(_dir);
			liststring = new ArrayList<String>(Arrays.asList(liststringS));
		}catch(Exception e){}
		n = 0;
		for(int _repeat12 = 0; _repeat12 < (int)(liststring.size()); _repeat12++) {
			{
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("name", liststring.get((int)(n)));
				map.add(_item);
			}
			n++;
		}
		viewpager2.setCurrentItem((int)0);
		viewpager2.setAdapter(new Viewpager2Adapter(map));
		((PagerAdapter)viewpager2.getAdapter()).notifyDataSetChanged();
	}
	
	
	public void _asset_list2(final String _dir, final ArrayList<HashMap<String, Object>> _li) {
		AssetManager liststringA = getAssets();
		try {
			String[] liststringS = liststringA.list(_dir);
			liststring = new ArrayList<String>(Arrays.asList(liststringS));
		}catch(Exception e){}
		n = 0;
		for(int _repeat12 = 0; _repeat12 < (int)(liststring.size()); _repeat12++) {
			{
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("name", liststring.get((int)(n)));
				_li.add(_item);
			}
			n++;
		}
	}
	
	
	public void _setBackground(final View _view, final String _color1, final String _color2, final double _radius, final double _shadow, final String _shadowColor) {
		int[] colors = { Color.parseColor(_color1), Color.parseColor(_color2) }; android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.RIGHT_LEFT, colors); gd.setCornerRadius((int)_radius); _view.setBackground(gd);
		_view.setElevation((int)_shadow);
		_view.setOutlineSpotShadowColor(Color.parseColor(_shadowColor));
	}
	
	
	public void _CardStyle(final View _view, final double _shadow, final double _radius, final String _color, final boolean _touch) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		gd.setColor(Color.parseColor(_color));
		gd.setCornerRadius((int)_radius);
		_view.setBackground(gd);
		
		if (Build.VERSION.SDK_INT >= 21){
			_view.setElevation((int)_shadow);}
		if (_touch) {
			_view.setOnTouchListener((View v, MotionEvent event) -> {
				switch (event.getAction()){
					case MotionEvent.ACTION_DOWN:{
						ObjectAnimator scaleX = new ObjectAnimator();
						scaleX.setTarget(_view);
						scaleX.setPropertyName("scaleX");
						scaleX.setFloatValues(0.9f);
						scaleX.setDuration(100);
						scaleX.start();
						
						ObjectAnimator scaleY = new ObjectAnimator();
						scaleY.setTarget(_view);
						scaleY.setPropertyName("scaleY");
						scaleY.setFloatValues(0.9f);
						scaleY.setDuration(100);
						scaleY.start();
						break;
					}
					case MotionEvent.ACTION_UP:{
						
						ObjectAnimator scaleX = new ObjectAnimator();
						scaleX.setTarget(_view);
						scaleX.setPropertyName("scaleX");
						scaleX.setFloatValues((float)1);
						scaleX.setDuration(100);
						scaleX.start();
						
						ObjectAnimator scaleY = new ObjectAnimator();
						scaleY.setTarget(_view);
						scaleY.setPropertyName("scaleY");
						scaleY.setFloatValues((float)1);
						scaleY.setDuration(100);
						scaleY.start();
						
						break;
					}
				}
				return false;
			});
		}
	}
	
	
	public void _languageblock2() {
		mainlanguage = "Arabic";
		{
			try{
				java.io.InputStream dataIn = PagerActivity.this.getAssets().open((String.valueOf((String.valueOf(getIntent().getStringExtra("type")) + String.valueOf("/sounds/speech/"))) + String.valueOf((String.valueOf((String.valueOf(mainlanguage) + String.valueOf("/"))) + String.valueOf((String.valueOf(mainlanguage) + String.valueOf(".txt")))))));
				int dataSi = dataIn.available();
				byte[] dataBu = new byte[dataSi];
				dataIn.read(dataBu);
				dataIn.close();
				data = new String(dataBu, "UTF-8");
			}catch(Exception e){
				
			}
		}
		datamap = new Gson().fromJson(data, new TypeToken<HashMap<String, Object>>(){}.getType());
		secondlanguage = "English";
		if (!String.valueOf(getIntent().getStringExtra("type")).equals(String.valueOf("alphabet"))) {
			{
				try{
					java.io.InputStream data1In = PagerActivity.this.getAssets().open((String.valueOf((String.valueOf(getIntent().getStringExtra("type")) + String.valueOf("/sounds/speech/"))) + String.valueOf((String.valueOf((String.valueOf(secondlanguage) + String.valueOf("/"))) + String.valueOf((String.valueOf(secondlanguage) + String.valueOf(".txt")))))));
					int data1Si = data1In.available();
					byte[] data1Bu = new byte[data1Si];
					data1In.read(data1Bu);
					data1In.close();
					data1 = new String(data1Bu, "UTF-8");
				}catch(Exception e){
					
				}
			}
			datamap1 = new Gson().fromJson(data1, new TypeToken<HashMap<String, Object>>(){}.getType());
		} else {
			
		}
	}
	
	
	public void _Gradient_Text(final TextView _view, final String _msg) {
		_view.setText(_msg);
		
		TextPaint paint = _view.getPaint();
		
		float width = paint.measureText(_msg); 
		
		Shader textShader = new LinearGradient(0, 0, width,_view.getTextSize(), new int[]{ Color.parseColor("#7E57C2"), Color.parseColor("#5C6BC0"), Color.parseColor("#29B6F6"), Color.parseColor("#66BB6A"), Color.parseColor("#FF7043"), }, null, Shader.TileMode.CLAMP); 
		
		/* By EPIC Technical Tricks YT */
		
		_view.getPaint().setShader(textShader);
	}
	
	
	public void _Gradient_Text_color(final TextView _view, final String _color1, final String _color2) {
		_view.setText(_view.getText().toString());
		
		TextPaint paint = _view.getPaint();
		
		float width = paint.measureText(_view.getText().toString()); 
		
		Shader textShader = new LinearGradient(0, 0, width,_view.getTextSize(), new int[]{ 
			
			Color.parseColor(_color1), Color.parseColor(_color2), 
			
			/*

 //More Colors 

Color.parseColor("#64B678"), Color.parseColor("#478AEA"), Color.parseColor("#8446CC"), 

*/
			
			
		}, null,Shader.TileMode.CLAMP); 
		
		/* By EPIC Technical Tricks YT */
		
		_view.getPaint().setShader(textShader);
	}
	
	
	public void _soundsoncreate() {
		if (mediaPlayer!=null) { 
			mediaPlayer.reset(); }
		try { mediaPlayer =new MediaPlayer();
			AssetFileDescriptor descriptor = getAssets().openFd(getIntent().getStringExtra("type").concat("/sounds/speech/".concat("Arabic".concat("/"))).concat(map.get(viewpager2.getCurrentItem()).get("name").toString().toLowerCase().concat(".mp3")));
			mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
			descriptor.close(); 
			mediaPlayer.prepare(); 
			mediaPlayer.setLooping(false); 
			mediaPlayer.start();
		} catch (Exception e) { e.printStackTrace(); }
		media_timer = new TimerTask() {
			@Override
			public void run() {
				PagerActivity.this.runOnUiThread(() -> {
					if (mediaPlayer!=null) { 
						mediaPlayer.reset(); }
					try { mediaPlayer =new MediaPlayer();
						AssetFileDescriptor descriptor = getAssets().openFd(getIntent().getStringExtra("type").concat("/sounds/speech/".concat("English".concat("/"))).concat(map.get(viewpager2.getCurrentItem()).get("name").toString().toLowerCase().concat(".mp3")));
						mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
						descriptor.close(); 
						mediaPlayer.prepare(); 
						mediaPlayer.setLooping(false); 
						mediaPlayer.start();
					} catch (Exception e) { e.printStackTrace(); }
				});
			}
		};
		_timer.schedule(media_timer, (int)(mediaPlayer.getDuration() + 500));
	}
	
	
	public void _pagerlist() {
		_asset_list2((String.valueOf(getIntent().getStringExtra("type")) + String.valueOf("/images")), map);
		viewpager2.setAdapter(new Viewpager2Adapter(map));
		if (viewpager2.getCurrentItem() > 0) {
			viewpager2.setCurrentItem((int)0);
			((PagerAdapter)viewpager2.getAdapter()).notifyDataSetChanged();
		} else {
			viewpager2.setCurrentItem((int)viewpager2.getCurrentItem());
			((PagerAdapter)viewpager2.getAdapter()).notifyDataSetChanged();
		}
	}
	
	
	public void _Gradient_round(final View _view, final double _radius, final String _color1, final String _color2) {
		android.graphics.drawable.GradientDrawable s = new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TR_BL, new int[]{Color.parseColor("#"+_color1),Color.parseColor("#"+_color2)});
		s.setCornerRadius(new Float(_radius));
		_view.setBackground(s);
	}
	
	
	public void _addCardView(final View _layoutView, final double _margins, final double _cornerRadius, final double _cardElevation, final double _cardMaxElevation, final boolean _preventCornerOverlap, final String _backgroundColor) {
		androidx.cardview.widget.CardView cv = new androidx.cardview.widget.CardView(PagerActivity.this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		int m = (int)_margins;
		lp.setMargins(m,m,m,m);
		cv.setLayoutParams(lp);
		int c = Color.parseColor(_backgroundColor);
		cv.setCardBackgroundColor(c);
		cv.setRadius((float)_cornerRadius);
		cv.setCardElevation((float)_cardElevation);
		cv.setMaxCardElevation((float)_cardMaxElevation);
		cv.setPreventCornerOverlap(_preventCornerOverlap);
		if(_layoutView.getParent() instanceof LinearLayout){
			ViewGroup vg = ((ViewGroup)_layoutView.getParent());
			vg.removeView(_layoutView);
			vg.removeAllViews();
			vg.addView(cv);
			cv.addView(_layoutView);
		}else{
		}
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
	
	
	public void _UI_GradientLR(final View _view, final String _left, final String _right, final double _lt, final double _rt, final double _lb, final double _rb, final double _str, final String _str_color, final double _ele, final String _ripple) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		int clrs[] = new int[]{
			Color.parseColor(_left), Color.parseColor(_right)
		};
		gd.setColors(clrs);
		gd.setOrientation(android.graphics.drawable.GradientDrawable.Orientation.TL_BR);
		gd.setStroke((int)_str, Color.parseColor(_str_color));
		gd.setCornerRadii(new float[] {(float)_lt, (float)_lt, (float)_rt, (float)_rt, (float)_rb, (float)_rb, (float)_lb, (float)_lb});
		_view.setElevation((int)_ele);
		android.content.res.ColorStateList clrbs = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_ripple)});
		android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrbs , gd, null);
		_view.setBackground(ripdrb);
	}
	
	
	public void _UI_GradientTB(final View _view, final String _top, final String _bot, final double _lt, final double _rt, final double _lb, final double _rb, final double _str, final String _str_color, final double _ele, final String _ripple) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		int clrs[] = new int[]{
			Color.parseColor(_top), Color.parseColor(_bot)
		};
		gd.setColors(clrs);
		gd.setStroke((int)_str, Color.parseColor(_str_color));
		gd.setCornerRadii(new float[] {(float)_lt, (float)_lt, (float)_rt, (float)_rt, (float)_rb, (float)_rb, (float)_lb, (float)_lb});
		_view.setElevation((int)_ele);
		android.content.res.ColorStateList clrbs = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_ripple)});
		android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrbs , gd, null);
		_view.setBackground(ripdrb);
	}
	
	
	public void _round(final View _view, final String _color, final String _stroke_c, final double _radius) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		
		gd.setColor(Color.parseColor(_color));
		gd.setCornerRadius((float)_radius);
		gd.setStroke(2, Color.parseColor(_stroke_c));
		
		_view.setBackground(gd);
		//With the view you select what will get rounded corners. The color is what the background color will look like. The stroke is the outline (put the same as color to "ignore" it) and the radius is how round will it be.
	}
	
	
	public void _rippleRoundStroke(final View _view, final String _focus, final String _pressed, final double _round, final double _stroke, final String _strokeclr) {
		android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
		GG.setColor(Color.parseColor(_focus));
		GG.setCornerRadius((float)_round);
		GG.setStroke((int) _stroke,
		Color.parseColor("#" + _strokeclr.replace("#", "")));
		android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.parseColor(_pressed)}), GG, null);
		_view.setBackground(RE);
	}
	
	
	public void _setBG(final View _view, final String _color1, final String _color2, final double _lt, final double _rt, final double _rb, final double _lb, final double _shadow, final String _shadowColor) {
		int[] colors = { Color.parseColor(_color1), Color.parseColor(_color2) }; android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.RIGHT_LEFT, colors);
		gd.setCornerRadii(new float[]{(int)_lt,(int)_lt,(int)_rt,(int)_rt,(int)_rb,(int)_rb,(int)_lb,(int)_lb});
		_view.setBackground(gd);
		
		_view.setElevation((int)_shadow);
		_view.setOutlineSpotShadowColor(Color.parseColor(_shadowColor));
	}
	
	
	public void _changeActivityFont(final String _fontname) {
		fontName = (String.valueOf("fonts/") + String.valueOf((String.valueOf(_fontname) + String.valueOf(".ttf"))));
		overrideFonts(PagerActivity.this,getWindow().getDecorView()); 
	} 
	private void overrideFonts(final android.content.Context context, final View v) {
		
		try {
			Typeface 
			typeace = Typeface.createFromAsset(getAssets(), fontName);;
			if ((v instanceof ViewGroup)) {
				ViewGroup vg = (ViewGroup) v;
				for (int i = 0;
				i < vg.getChildCount();
				i++) {
					View child = vg.getChildAt(i);
					overrideFonts(context, child);
				}
			} else {
				if ((v instanceof TextView)) {
					((TextView) v).setTypeface(typeace);
				} else {
					if ((v instanceof EditText )) {
						((EditText) v).setTypeface(typeace);
					} else {
						if ((v instanceof Button)) {
							((Button) v).setTypeface(typeace);
						}
					}
				}
			}
		}
		catch(Exception e)
		
		{
			SketchwareUtil.showMessage(PagerActivity.this.getApplicationContext(), "Error Loading Font");
		};
	}
	
	public class Viewpager2Adapter extends PagerAdapter {
		
		Context _context;
		ArrayList<HashMap<String, Object>> _data;
		
		public Viewpager2Adapter(Context _ctx, ArrayList<HashMap<String, Object>> _arr) {
			_context = _ctx;
			_data = _arr;
		}
		
		public Viewpager2Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_context = getLayoutInflater().getContext();
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public boolean isViewFromObject(View _view, Object _object) {
			return _view == _object;
		}
		
		@Override
		public void destroyItem(ViewGroup _container, int _position, Object _object) {
			_container.removeView((View) _object);
		}
		
		@Override
		public int getItemPosition(Object _object) {
			return super.getItemPosition(_object);
		}
		
		@Override
		public CharSequence getPageTitle(int pos) {
			// Use the Activity Event (onTabLayoutNewTabAdded) in order to use this method
			return "page " + String.valueOf(pos);
		}
		
		@Override
		public Object instantiateItem(ViewGroup _container,  final int _position) {
			View _view = LayoutInflater.from(_context).inflate(R.layout.bg3, _container, false);
			
			final LinearLayout bg = _view.findViewById(R.id.bg);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final LinearLayout box = _view.findViewById(R.id.box);
			final LinearLayout linear6 = _view.findViewById(R.id.linear6);
			final LinearLayout lin_pic = _view.findViewById(R.id.lin_pic);
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear8 = _view.findViewById(R.id.linear8);
			final LinearLayout linear7 = _view.findViewById(R.id.linear7);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final TextView textview4 = _view.findViewById(R.id.textview4);
			final ImageView play = _view.findViewById(R.id.play);
			final com.airbnb.lottie.LottieAnimationView lottie2 = _view.findViewById(R.id.lottie2);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			
			imageview1.setOnClickListener(v -> {
				_FadeOut(imageview1, 150);
				if (_data.get((int)_position).containsKey("name")) {
					media_timer.cancel();
					if (mediaPlayer!=null) { 
						mediaPlayer.reset(); }
					try { mediaPlayer =new MediaPlayer();
						AssetFileDescriptor descriptor = getAssets().openFd(getIntent().getStringExtra("type").concat("/sounds/speech/".concat("Arabic".concat("/"))).concat(_data.get((int)_position).get("name").toString().toLowerCase().concat(".mp3")));
						mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
						descriptor.close(); 
						mediaPlayer.prepare(); 
						mediaPlayer.setLooping(false); 
						mediaPlayer.start();
					} catch (Exception e) { e.printStackTrace(); }
					/*
textview1.setText((String.valueOf((String.valueOf(getIntent().getStringExtra("type")) + String.valueOf((String.valueOf("/sounds/speech/") + String.valueOf((String.valueOf("Arabic") + String.valueOf("/"))))))) + String.valueOf((String.valueOf(_data.get((int)_position).get("name").toString().toLowerCase()) + String.valueOf(".mp3")))));
*/
				}
			});
			linear3.setOnClickListener(v -> {
				if (!(String.valueOf(getIntent().getStringExtra("type")).equals(String.valueOf("alphabet")) || String.valueOf(getIntent().getStringExtra("type")).equals(String.valueOf("alphabet-e")))) {
					if (_data.get((int)_position).containsKey("name")) {
						media_timer.cancel();
						if (mediaPlayer!=null) { 
							mediaPlayer.reset(); }
						try { mediaPlayer =new MediaPlayer();
							AssetFileDescriptor descriptor = getAssets().openFd(getIntent().getStringExtra("type").concat("/sounds/speech/".concat("English".concat("/"))).concat(_data.get((int)_position).get("name").toString().toLowerCase().concat(".mp3")));
							mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
							descriptor.close(); 
							mediaPlayer.prepare(); 
							mediaPlayer.setLooping(false); 
							mediaPlayer.start();
						} catch (Exception e) { e.printStackTrace(); }
						/*
textview1.setText((String.valueOf((String.valueOf(getIntent().getStringExtra("type")) + String.valueOf((String.valueOf("/sounds/speech/") + String.valueOf((String.valueOf("English") + String.valueOf("/"))))))) + String.valueOf((String.valueOf(_data.get((int)_position).get("name").toString().toLowerCase()) + String.valueOf(".mp3")))));
*/
					}
					_FadeOut(textview4, 100);
				} else {
					
				}
			});
			linear2.setOnClickListener(v -> {
				if (_data.get((int)_position).containsKey("name")) {
					media_timer.cancel();
					if (mediaPlayer!=null) {
						mediaPlayer.reset(); }
					try { mediaPlayer =new MediaPlayer();
						AssetFileDescriptor descriptor = getAssets().openFd(getIntent().getStringExtra("type").concat("/sounds/speech/".concat("Arabic".concat("/"))).concat(_data.get((int)_position).get("name").toString().toLowerCase().concat(".mp3")));
						mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
						descriptor.close(); 
						mediaPlayer.prepare(); 
						mediaPlayer.setLooping(false); 
						mediaPlayer.start();
					} catch (Exception e) { e.printStackTrace(); }
					/*
textview1.setText((String.valueOf((String.valueOf(getIntent().getStringExtra("type")) + String.valueOf((String.valueOf("/sounds/speech/") + String.valueOf((String.valueOf("Arabic") + String.valueOf("/"))))))) + String.valueOf((String.valueOf(_data.get((int)_position).get("name").toString().toLowerCase()) + String.valueOf(".mp3")))));
*/
				}
				_FadeOut(textview1, 100);
			});
			play.setOnClickListener(v -> {
				if (_data.get((int)_position).containsKey("name")) {
					media_timer.cancel();
					if (mediaPlayer!=null) { 
						mediaPlayer.reset(); }
					try { mediaPlayer =new MediaPlayer();
						AssetFileDescriptor descriptor = getAssets().openFd(getIntent().getStringExtra("type").concat("/sounds/".concat("onomatopoeia".concat("/"))).concat(_data.get((int)_position).get("name").toString().toLowerCase().concat(".mp3")));
						mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
						descriptor.close(); 
						mediaPlayer.prepare(); 
						mediaPlayer.setLooping(false); 
						mediaPlayer.start();
					} catch (Exception e) { e.printStackTrace(); }
				}
				_FadeOut(play, 100);
			});
			if (String.valueOf(getIntent().getStringExtra("type")).equals(String.valueOf("animals")) || (String.valueOf(getIntent().getStringExtra("type")).equals(String.valueOf("vehicles")) || String.valueOf(getIntent().getStringExtra("type")).equals(String.valueOf("alphabet")))) {
				play.setVisibility(View.VISIBLE);
			} else {
				play.setVisibility(View.GONE);
			}
			if (_data.get((int)_position).containsKey("name")) {
				try {
					java.io.InputStream imageview1Input = getAssets().open((String.valueOf(getIntent().getStringExtra("type")) + String.valueOf((String.valueOf((String.valueOf("/images/") + String.valueOf(_data.get((int)_position).get("name").toString()))) + String.valueOf("/Solution.png"))))); 
					Drawable imageview1Draw = Drawable.createFromStream(imageview1Input, null);
					imageview1.setImageDrawable(imageview1Draw);
					imageview1Input.close();
				} catch(java.io.IOException ex) {}
				textview1.setText(datamap.get(_data.get((int)_position).get("name").toString().toLowerCase()).toString());
				if (!(String.valueOf(getIntent().getStringExtra("type")).equals(String.valueOf("alphabet")) || String.valueOf(getIntent().getStringExtra("type")).equals(String.valueOf("alphabet-e")))) {
					textview4.setText(datamap1.get(_data.get((int)_position).get("name").toString().toLowerCase()).toString());
				} else {
					textview4.setVisibility(View.INVISIBLE);
					linear3.setVisibility(View.GONE);
				}
			}
			textview2.setText((String.valueOf(String.valueOf((long)(1 + _position))) + String.valueOf((String.valueOf("/") + String.valueOf(String.valueOf((long)(_data.size())))))));
			textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/semi.ttf"), 1);
			textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/font1190.ttf"), 0);
			textview4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/font1190.ttf"), 0);
			_UI_GradientTB(box, "#00FFFFFF", "#66FFFFFF", SketchwareUtil.getDip(PagerActivity.this.getApplicationContext(), (int)(20)), SketchwareUtil.getDip(PagerActivity.this.getApplicationContext(), (int)(20)), SketchwareUtil.getDip(PagerActivity.this.getApplicationContext(), (int)(20)), SketchwareUtil.getDip(PagerActivity.this.getApplicationContext(), (int)(20)), 2, "#FFFFFF", 0, "#FFFFFF");
			{
				android.graphics.drawable.GradientDrawable ROYALUi = new android.graphics.drawable.GradientDrawable();
				int colors [] = { 0x00FFFFFF, 0x59FFFFFF };
				ROYALUi= new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TL_BR, colors);
				ROYALUi.setCornerRadius(getDip(21));
				ROYALUi.setStroke((int)getDip(2) ,0x59FFFFFF);
				box.setElevation(getDip(5));
				box.setBackground(ROYALUi);
			}
			{
				android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
				int colors [] = { 0x00FFFFFF, 0x59FFFFFF };
				SketchUi= new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TL_BR, colors);
				SketchUi.setCornerRadius(getDip(21));
				SketchUi.setStroke((int)getDip(2) ,0x59FFFFFF);
				box.setElevation(getDip(5));
				box.setBackground(SketchUi);
			}
			_shape(SketchwareUtil.getDip(PagerActivity.this.getApplicationContext(), (int)(100)), SketchwareUtil.getDip(PagerActivity.this.getApplicationContext(), (int)(100)), SketchwareUtil.getDip(PagerActivity.this.getApplicationContext(), (int)(100)), SketchwareUtil.getDip(PagerActivity.this.getApplicationContext(), (int)(100)), "#4DFFFFFF", "#FFFFFF", 0, linear3);
			_shape(SketchwareUtil.getDip(PagerActivity.this.getApplicationContext(), (int)(100)), SketchwareUtil.getDip(PagerActivity.this.getApplicationContext(), (int)(100)), SketchwareUtil.getDip(PagerActivity.this.getApplicationContext(), (int)(100)), SketchwareUtil.getDip(PagerActivity.this.getApplicationContext(), (int)(100)), "#4DFFFFFF", "#FFFFFF", 0, linear2);
			_shape(SketchwareUtil.getDip(PagerActivity.this.getApplicationContext(), (int)(100)), SketchwareUtil.getDip(PagerActivity.this.getApplicationContext(), (int)(100)), SketchwareUtil.getDip(PagerActivity.this.getApplicationContext(), (int)(100)), SketchwareUtil.getDip(PagerActivity.this.getApplicationContext(), (int)(100)), "#4DFFFFFF", "#FFFFFF", 0, linear5);
			_round(imageview1, "#4DFFFFFF", "#FFFFFF", SketchwareUtil.getDip(PagerActivity.this.getApplicationContext(), (int)(125)));
			/*
lottie
*/
			if (String.valueOf(no).equals(String.valueOf(1))) {
				lottie = "lottie/train.json";
			} else {
				if (String.valueOf(no).equals(String.valueOf(2))) {
					lottie = "lottie/tiger.json";
				} else {
					if (String.valueOf(no).equals(String.valueOf(3))) {
						lottie = "lottie/astronaut-read-book-on-planet.json";
					} else {
						if (String.valueOf(no).equals(String.valueOf(4))) {
							lottie = "lottie/astronaut-floating-with-rocket-on-space.json";
						} else {
							if (String.valueOf(no).equals(String.valueOf(5))) {
								lottie = "lottie/crab-animation.json";
							} else {
								if (String.valueOf(no).equals(String.valueOf(6))) {
									lottie = "lottie/happy-fox.json";
								} else {
									if (String.valueOf(no).equals(String.valueOf(7))) {
										lottie = "lottie/octopus.json";
									} else {
										if (String.valueOf(no).equals(String.valueOf(8))) {
											lottie = "lottie/petite-girafe.json";
										} else {
											if (String.valueOf(no).equals(String.valueOf(9))) {
												lottie = "lottie/lovely-cats.json";
											} else {
												if (String.valueOf(no).equals(String.valueOf(10))) {
													lottie = "lottie/just-a-pigeon.json";
												} else {
													if (String.valueOf(no).equals(String.valueOf(11))) {
														lottie = "lottie/woodpecker.json";
													} else {
														if (String.valueOf(no).equals(String.valueOf(12))) {
															lottie = "lottie/robot-says-hi.json";
														} else {
															if (String.valueOf(no).equals(String.valueOf(13))) {
																lottie = "lottie/animacao-kids-patria-escola.json";
															} else {
																if (String.valueOf(no).equals(String.valueOf(14))) {
																	lottie = "lottie/my-kick-us-bear.json";
																} else {
																	if (String.valueOf(no).equals(String.valueOf(15))) {
																		lottie = "lottie/robot-playing-computer.json";
																	} else {
																		if (String.valueOf(no).equals(String.valueOf(16))) {
																			lottie = "lottie/star-in-hand-baby-astronaut.json";
																		} else {
																			if (String.valueOf(no).equals(String.valueOf(17))) {
																				lottie = "lottie/victory-sign-baby-astronaut.json";
																			} else {
																				
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			lottie2.setAnimation(lottie);
			
			_container.addView(_view);
			return _view;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(PagerActivity.this.getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
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