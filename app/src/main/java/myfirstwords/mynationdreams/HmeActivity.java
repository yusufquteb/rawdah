package myfirstwords.mynationdreams;
import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.Intent;
import android.content.res.*;
import android.graphics.*;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.*;
import java.io.*;
import java.io.InputStream;
import java.security.SecureRandom;
import java.text.*;
import java.util.*;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;


public class HmeActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private String fontName = "";
	private String typeace = "";
	private HashMap<String, Object> map = new HashMap<>();
	private String strok = "";
	private String section = "";
	
	private ImageView imageview13;
	private LottieAnimationView lottie1;
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private TextView textview13;
	private HorizontalScrollView hscroll2;
	private TextView textview14;
	private HorizontalScrollView hscroll100;
	private TextView textview1081;
	private HorizontalScrollView hscroll99;
	private LinearLayout linear14;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private ImageView imageview5;
	private TextView textview5;
	private ImageView imageview6;
	private TextView textview6;
	private ImageView imageview7;
	private TextView textview7;
	private LinearLayout linear402;
	private LinearLayout linear2;
	private LinearLayout linear13;
	private LinearLayout linear412;
	private LinearLayout linear12;
	private LinearLayout linear10;
	private LinearLayout linear11;
	private LinearLayout linear405;
	private LinearLayout linear4;
	private LinearLayout linear9;
	private LinearLayout linear396;
	private LinearLayout linear408;
	private LinearLayout linear401;
	private ImageView imageview1;
	private TextView textview1;
	private ImageView imageview12;
	private TextView textview12;
	private ImageView imageview29;
	private TextView textview1097;
	private ImageView imageview11;
	private TextView textview11;
	private ImageView imageview9;
	private TextView textview9;
	private ImageView imageview10;
	private TextView textview10;
	private ImageView imageview22;
	private TextView textview1090;
	private ImageView imageview4;
	private TextView textview3;
	private ImageView imageview8;
	private TextView textview8;
	private ImageView imageview14;
	private TextView textview1082;
	private ImageView imageview25;
	private TextView textview1093;
	private ImageView imageview19;
	private TextView textview1087;
	private LinearLayout linear395;
	private LinearLayout linear397;
	private LinearLayout linear400;
	private LinearLayout linear398;
	private ImageView imageview15;
	private TextView textview1083;
	private ImageView imageview18;
	private TextView textview1086;
	private ImageView imageview16;
	private TextView textview1084;
	
	private Intent i = new Intent();
	private TimerTask t;
	private MediaPlayer mediaPlayer2;
	private TimerTask dialo_timer;
	private TimerTask hide;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.hme);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		imageview13 = HmeActivity.this.findViewById(R.id.imageview13);
		lottie1 = HmeActivity.this.findViewById(R.id.lottie1);
		vscroll1 = HmeActivity.this.findViewById(R.id.vscroll1);
		linear1 = HmeActivity.this.findViewById(R.id.linear1);
		textview13 = HmeActivity.this.findViewById(R.id.textview13);
		hscroll2 = HmeActivity.this.findViewById(R.id.hscroll2);
		textview14 = HmeActivity.this.findViewById(R.id.textview14);
		hscroll100 = HmeActivity.this.findViewById(R.id.hscroll100);
		textview1081 = HmeActivity.this.findViewById(R.id.textview1081);
		hscroll99 = HmeActivity.this.findViewById(R.id.hscroll99);
		linear14 = HmeActivity.this.findViewById(R.id.linear14);
		linear6 = HmeActivity.this.findViewById(R.id.linear6);
		linear7 = HmeActivity.this.findViewById(R.id.linear7);
		linear8 = HmeActivity.this.findViewById(R.id.linear8);
		imageview5 = HmeActivity.this.findViewById(R.id.imageview5);
		textview5 = HmeActivity.this.findViewById(R.id.textview5);
		imageview6 = HmeActivity.this.findViewById(R.id.imageview6);
		textview6 = HmeActivity.this.findViewById(R.id.textview6);
		imageview7 = HmeActivity.this.findViewById(R.id.imageview7);
		textview7 = HmeActivity.this.findViewById(R.id.textview7);
		linear402 = HmeActivity.this.findViewById(R.id.linear402);
		linear2 = HmeActivity.this.findViewById(R.id.linear2);
		linear13 = HmeActivity.this.findViewById(R.id.linear13);
		linear412 = HmeActivity.this.findViewById(R.id.linear412);
		linear12 = HmeActivity.this.findViewById(R.id.linear12);
		linear10 = HmeActivity.this.findViewById(R.id.linear10);
		linear11 = HmeActivity.this.findViewById(R.id.linear11);
		linear405 = HmeActivity.this.findViewById(R.id.linear405);
		linear4 = HmeActivity.this.findViewById(R.id.linear4);
		linear9 = HmeActivity.this.findViewById(R.id.linear9);
		linear396 = HmeActivity.this.findViewById(R.id.linear396);
		linear408 = HmeActivity.this.findViewById(R.id.linear408);
		linear401 = HmeActivity.this.findViewById(R.id.linear401);
		imageview1 = HmeActivity.this.findViewById(R.id.imageview1);
		textview1 = HmeActivity.this.findViewById(R.id.textview1);
		imageview12 = HmeActivity.this.findViewById(R.id.imageview12);
		textview12 = HmeActivity.this.findViewById(R.id.textview12);
		imageview29 = HmeActivity.this.findViewById(R.id.imageview29);
		textview1097 = HmeActivity.this.findViewById(R.id.textview1097);
		imageview11 = HmeActivity.this.findViewById(R.id.imageview11);
		textview11 = HmeActivity.this.findViewById(R.id.textview11);
		imageview9 = HmeActivity.this.findViewById(R.id.imageview9);
		textview9 = HmeActivity.this.findViewById(R.id.textview9);
		imageview10 = HmeActivity.this.findViewById(R.id.imageview10);
		textview10 = HmeActivity.this.findViewById(R.id.textview10);
		imageview22 = HmeActivity.this.findViewById(R.id.imageview22);
		textview1090 = HmeActivity.this.findViewById(R.id.textview1090);
		imageview4 = HmeActivity.this.findViewById(R.id.imageview4);
		textview3 = HmeActivity.this.findViewById(R.id.textview3);
		imageview8 = HmeActivity.this.findViewById(R.id.imageview8);
		textview8 = HmeActivity.this.findViewById(R.id.textview8);
		imageview14 = HmeActivity.this.findViewById(R.id.imageview14);
		textview1082 = HmeActivity.this.findViewById(R.id.textview1082);
		imageview25 = HmeActivity.this.findViewById(R.id.imageview25);
		textview1093 = HmeActivity.this.findViewById(R.id.textview1093);
		imageview19 = HmeActivity.this.findViewById(R.id.imageview19);
		textview1087 = HmeActivity.this.findViewById(R.id.textview1087);
		linear395 = HmeActivity.this.findViewById(R.id.linear395);
		linear397 = HmeActivity.this.findViewById(R.id.linear397);
		linear400 = HmeActivity.this.findViewById(R.id.linear400);
		linear398 = HmeActivity.this.findViewById(R.id.linear398);
		imageview15 = HmeActivity.this.findViewById(R.id.imageview15);
		textview1083 = HmeActivity.this.findViewById(R.id.textview1083);
		imageview18 = HmeActivity.this.findViewById(R.id.imageview18);
		textview1086 = HmeActivity.this.findViewById(R.id.textview1086);
		imageview16 = HmeActivity.this.findViewById(R.id.imageview16);
		textview1084 = HmeActivity.this.findViewById(R.id.textview1084);
		
		imageview13.setOnLongClickListener(_view -> {
			if (mediaPlayer2!=null) { 
				mediaPlayer2.reset(); }
			finishAffinity();
			return true;
		});
		
		linear6.setOnClickListener(_view -> {
			_un_clickable();
			section = "bath";
			if (mediaPlayer2!=null) { 
				mediaPlayer2.reset(); }
			try { mediaPlayer2 =new MediaPlayer();
				AssetFileDescriptor descriptor = getAssets().openFd(
				"sections/".concat(section.concat(".mp3"))
				);
				mediaPlayer2.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
				descriptor.close(); 
				mediaPlayer2.prepare(); 
				
				mediaPlayer2.setLooping(false); 
				mediaPlayer2.start();
			} catch (Exception e) { e.printStackTrace(); }
			t = new TimerTask() {
				@Override
				public void run() {
					HmeActivity.this.runOnUiThread(() -> {
						i.setClass(HmeActivity.this.getApplicationContext(), PagerActivity.class);
						i.putExtra("type", section);
						HmeActivity.this.startActivity(i);
						HmeActivity.this.finish();
					});
				}
			};
			_timer.schedule(t, (int)(mediaPlayer2.getDuration() + 0));
		});
		
		linear7.setOnClickListener(_view -> {
			_un_clickable();
			section = "kitchen";
			if (mediaPlayer2!=null) { 
				mediaPlayer2.reset(); }
			try { mediaPlayer2 =new MediaPlayer();
				AssetFileDescriptor descriptor = getAssets().openFd(
				"sections/".concat(section.concat(".mp3"))
				);
				mediaPlayer2.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
				descriptor.close(); 
				mediaPlayer2.prepare(); 
				
				mediaPlayer2.setLooping(false); 
				mediaPlayer2.start();
			} catch (Exception e) { e.printStackTrace(); }
			t = new TimerTask() {
				@Override
				public void run() {
					HmeActivity.this.runOnUiThread(() -> {
						i.setClass(HmeActivity.this.getApplicationContext(), PagerActivity.class);
						i.putExtra("type", section);
						HmeActivity.this.startActivity(i);
						HmeActivity.this.finish();
					});
				}
			};
			_timer.schedule(t, (int)(mediaPlayer2.getDuration() + 0));
		});
		
		linear8.setOnClickListener(_view -> {
			_un_clickable();
			section = "furniture";
			if (mediaPlayer2!=null) { 
				mediaPlayer2.reset(); }
			try { mediaPlayer2 =new MediaPlayer();
				AssetFileDescriptor descriptor = getAssets().openFd(
				"sections/".concat(section.concat(".mp3"))
				);
				mediaPlayer2.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
				descriptor.close(); 
				mediaPlayer2.prepare(); 
				
				mediaPlayer2.setLooping(false); 
				mediaPlayer2.start();
			} catch (Exception e) { e.printStackTrace(); }
			t = new TimerTask() {
				@Override
				public void run() {
					HmeActivity.this.runOnUiThread(() -> {
						i.setClass(HmeActivity.this.getApplicationContext(), PagerActivity.class);
						i.putExtra("type", section);
						HmeActivity.this.startActivity(i);
						HmeActivity.this.finish();
					});
				}
			};
			_timer.schedule(t, (int)(mediaPlayer2.getDuration() + 0));
		});
		
		linear2.setOnClickListener(_view -> {
			_un_clickable();
			section = "animals";
			if (mediaPlayer2!=null) { 
				mediaPlayer2.reset(); }
			try { mediaPlayer2 =new MediaPlayer();
				AssetFileDescriptor descriptor = getAssets().openFd("sections/".concat(section.concat(".mp3")));
				mediaPlayer2.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
				descriptor.close(); 
				mediaPlayer2.prepare(); 
				
				mediaPlayer2.setLooping(false); 
				mediaPlayer2.start();
			} catch (Exception e) { e.printStackTrace(); }
			t = new TimerTask() {
				@Override
				public void run() {
					HmeActivity.this.runOnUiThread(() -> {
						i.setClass(HmeActivity.this.getApplicationContext(), PagerActivity.class);
						i.putExtra("type", section);
						HmeActivity.this.startActivity(i);
						HmeActivity.this.finish();
					});
				}
			};
			_timer.schedule(t, (int)(mediaPlayer2.getDuration() + 0));
		});
		
		linear13.setOnClickListener(_view -> {
			_un_clickable();
			section = "alphabet";
			if (mediaPlayer2!=null) { 
				mediaPlayer2.reset(); }
			try { mediaPlayer2 =new MediaPlayer();
				AssetFileDescriptor descriptor = getAssets().openFd("sections/".concat(section.concat(".mp3")));
				mediaPlayer2.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
				descriptor.close(); 
				mediaPlayer2.prepare(); 
				
				mediaPlayer2.setLooping(false); 
				mediaPlayer2.start();
			} catch (Exception e) { e.printStackTrace(); }
			t = new TimerTask() {
				@Override
				public void run() {
					HmeActivity.this.runOnUiThread(() -> {
						i.setClass(HmeActivity.this.getApplicationContext(), PagerActivity.class);
						i.putExtra("type", section);
						HmeActivity.this.startActivity(i);
						HmeActivity.this.finish();
					});
				}
			};
			_timer.schedule(t, (int)(mediaPlayer2.getDuration() + 0));
		});
		
		linear412.setOnClickListener(_view -> {
			_un_clickable();
			section = "alphabet-e";
			if (mediaPlayer2!=null) { 
				mediaPlayer2.reset(); }
			try { mediaPlayer2 =new MediaPlayer();
				AssetFileDescriptor descriptor = getAssets().openFd("sections/".concat(section.concat(".mp3")));
				mediaPlayer2.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
				descriptor.close(); 
				mediaPlayer2.prepare(); 
				
				mediaPlayer2.setLooping(false); 
				mediaPlayer2.start();
			} catch (Exception e) { e.printStackTrace(); }
			t = new TimerTask() {
				@Override
				public void run() {
					HmeActivity.this.runOnUiThread(() -> {
						i.setClass(HmeActivity.this.getApplicationContext(), PagerActivity.class);
						i.putExtra("type", section);
						HmeActivity.this.startActivity(i);
						HmeActivity.this.finish();
					});
				}
			};
			_timer.schedule(t, (int)(mediaPlayer2.getDuration() + 0));
		});
		
		linear12.setOnClickListener(_view -> {
			_un_clickable();
			section = "numbers";
			if (mediaPlayer2!=null) { 
				mediaPlayer2.reset(); }
			try { mediaPlayer2 =new MediaPlayer();
				AssetFileDescriptor descriptor = getAssets().openFd(
				"sections/".concat(section.concat(".mp3"))
				);
				mediaPlayer2.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
				descriptor.close(); 
				mediaPlayer2.prepare(); 
				
				mediaPlayer2.setLooping(false); 
				mediaPlayer2.start();
			} catch (Exception e) { e.printStackTrace(); }
			t = new TimerTask() {
				@Override
				public void run() {
					HmeActivity.this.runOnUiThread(() -> {
						i.setClass(HmeActivity.this.getApplicationContext(), PagerActivity.class);
						i.putExtra("type", section);
						HmeActivity.this.startActivity(i);
						HmeActivity.this.finish();
					});
				}
			};
			_timer.schedule(t, (int)(mediaPlayer2.getDuration() + 0));
		});
		
		linear10.setOnClickListener(_view -> {
			_un_clickable();
			section = "colors";
			if (mediaPlayer2!=null) { 
				mediaPlayer2.reset(); }
			try { mediaPlayer2 =new MediaPlayer();
				AssetFileDescriptor descriptor = getAssets().openFd(
				"sections/".concat(section.concat(".mp3"))
				);
				mediaPlayer2.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
				descriptor.close(); 
				mediaPlayer2.prepare(); 
				
				mediaPlayer2.setLooping(false); 
				mediaPlayer2.start();
			} catch (Exception e) { e.printStackTrace(); }
			t = new TimerTask() {
				@Override
				public void run() {
					HmeActivity.this.runOnUiThread(() -> {
						i.setClass(HmeActivity.this.getApplicationContext(), PagerActivity.class);
						i.putExtra("type", section);
						HmeActivity.this.startActivity(i);
						HmeActivity.this.finish();
					});
				}
			};
			_timer.schedule(t, (int)(mediaPlayer2.getDuration() + 0));
		});
		
		linear11.setOnClickListener(_view -> {
			_un_clickable();
			section = "shapes";
			if (mediaPlayer2!=null) { 
				mediaPlayer2.reset(); }
			try { mediaPlayer2 =new MediaPlayer();
				AssetFileDescriptor descriptor = getAssets().openFd(
				"sections/".concat(section.concat(".mp3"))
				);
				mediaPlayer2.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
				descriptor.close(); 
				mediaPlayer2.prepare(); 
				
				mediaPlayer2.setLooping(false); 
				mediaPlayer2.start();
			} catch (Exception e) { e.printStackTrace(); }
			t = new TimerTask() {
				@Override
				public void run() {
					HmeActivity.this.runOnUiThread(() -> {
						i.setClass(HmeActivity.this.getApplicationContext(), PagerActivity.class);
						i.putExtra("type", section);
						HmeActivity.this.startActivity(i);
						HmeActivity.this.finish();
					});
				}
			};
			_timer.schedule(t, (int)(mediaPlayer2.getDuration() + 0));
		});
		
		linear405.setOnClickListener(_view -> {
			_un_clickable();
			section = "body";
			if (mediaPlayer2!=null) { 
				mediaPlayer2.reset(); }
			try { mediaPlayer2 =new MediaPlayer();
				AssetFileDescriptor descriptor = getAssets().openFd(
				"sections/".concat(section.concat(".mp3"))
				);
				mediaPlayer2.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
				descriptor.close(); 
				mediaPlayer2.prepare(); 
				
				mediaPlayer2.setLooping(false); 
				mediaPlayer2.start();
			} catch (Exception e) { e.printStackTrace(); }
			t = new TimerTask() {
				@Override
				public void run() {
					HmeActivity.this.runOnUiThread(() -> {
						i.setClass(HmeActivity.this.getApplicationContext(), PagerActivity.class);
						i.putExtra("type", section);
						HmeActivity.this.startActivity(i);
						HmeActivity.this.finish();
					});
				}
			};
			_timer.schedule(t, (int)(mediaPlayer2.getDuration() + 0));
		});
		
		linear4.setOnClickListener(_view -> {
			_un_clickable();
			section = "vehicles";
			if (mediaPlayer2!=null) { 
				mediaPlayer2.reset(); }
			try { mediaPlayer2 =new MediaPlayer();
				AssetFileDescriptor descriptor = getAssets().openFd(
				"sections/".concat(section.concat(".mp3"))
				);
				mediaPlayer2.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
				descriptor.close(); 
				mediaPlayer2.prepare(); 
				
				mediaPlayer2.setLooping(false); 
				mediaPlayer2.start();
			} catch (Exception e) { e.printStackTrace(); }
			t = new TimerTask() {
				@Override
				public void run() {
					HmeActivity.this.runOnUiThread(() -> {
						i.setClass(HmeActivity.this.getApplicationContext(), PagerActivity.class);
						i.putExtra("type", section);
						HmeActivity.this.startActivity(i);
						HmeActivity.this.finish();
					});
				}
			};
			_timer.schedule(t, (int)(mediaPlayer2.getDuration() + 0));
		});
		
		linear9.setOnClickListener(_view -> {
			_un_clickable();
			section = "tools";
			if (mediaPlayer2!=null) { 
				mediaPlayer2.reset(); }
			try { mediaPlayer2 =new MediaPlayer();
				AssetFileDescriptor descriptor = getAssets().openFd(
				"sections/".concat(section.concat(".mp3"))
				);
				mediaPlayer2.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
				descriptor.close(); 
				mediaPlayer2.prepare(); 
				
				mediaPlayer2.setLooping(false); 
				mediaPlayer2.start();
			} catch (Exception e) { e.printStackTrace(); }
			t = new TimerTask() {
				@Override
				public void run() {
					HmeActivity.this.runOnUiThread(() -> {
						i.setClass(HmeActivity.this.getApplicationContext(), PagerActivity.class);
						i.putExtra("type", section);
						HmeActivity.this.startActivity(i);
						HmeActivity.this.finish();
					});
				}
			};
			_timer.schedule(t, (int)(mediaPlayer2.getDuration() + 0));
		});
		
		linear396.setOnClickListener(_view -> {
			_un_clickable();
			section = "food";
			if (mediaPlayer2!=null) { 
				mediaPlayer2.reset(); }
			try { mediaPlayer2 =new MediaPlayer();
				AssetFileDescriptor descriptor = getAssets().openFd(
				"sections/".concat(section.concat(".mp3"))
				);
				mediaPlayer2.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
				descriptor.close(); 
				mediaPlayer2.prepare(); 
				
				mediaPlayer2.setLooping(false); 
				mediaPlayer2.start();
			} catch (Exception e) { e.printStackTrace(); }
			t = new TimerTask() {
				@Override
				public void run() {
					HmeActivity.this.runOnUiThread(() -> {
						i.setClass(HmeActivity.this.getApplicationContext(), PagerActivity.class);
						i.putExtra("type", section);
						HmeActivity.this.startActivity(i);
						HmeActivity.this.finish();
					});
				}
			};
			_timer.schedule(t, (int)(mediaPlayer2.getDuration() + 0));
		});
		
		linear408.setOnClickListener(_view -> {
			_un_clickable();
			section = "school";
			if (mediaPlayer2!=null) { 
				mediaPlayer2.reset(); }
			try { mediaPlayer2 =new MediaPlayer();
				AssetFileDescriptor descriptor = getAssets().openFd(
				"sections/".concat(section.concat(".mp3"))
				);
				mediaPlayer2.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
				descriptor.close(); 
				mediaPlayer2.prepare(); 
				
				mediaPlayer2.setLooping(false); 
				mediaPlayer2.start();
			} catch (Exception e) { e.printStackTrace(); }
			t = new TimerTask() {
				@Override
				public void run() {
					HmeActivity.this.runOnUiThread(() -> {
						i.setClass(HmeActivity.this.getApplicationContext(), PagerActivity.class);
						i.putExtra("type", section);
						HmeActivity.this.startActivity(i);
						HmeActivity.this.finish();
					});
				}
			};
			_timer.schedule(t, (int)(mediaPlayer2.getDuration() + 0));
		});
		
		linear401.setOnClickListener(_view -> {
			_un_clickable();
			section = "clothes";
			if (mediaPlayer2!=null) { 
				mediaPlayer2.reset(); }
			try { mediaPlayer2 =new MediaPlayer();
				AssetFileDescriptor descriptor = getAssets().openFd(
				"sections/".concat(section.concat(".mp3"))
				);
				mediaPlayer2.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
				descriptor.close(); 
				mediaPlayer2.prepare(); 
				
				mediaPlayer2.setLooping(false); 
				mediaPlayer2.start();
			} catch (Exception e) { e.printStackTrace(); }
			t = new TimerTask() {
				@Override
				public void run() {
					HmeActivity.this.runOnUiThread(() -> {
						i.setClass(HmeActivity.this.getApplicationContext(), PagerActivity.class);
						i.putExtra("type", section);
						HmeActivity.this.startActivity(i);
						HmeActivity.this.finish();
					});
				}
			};
			_timer.schedule(t, (int)(mediaPlayer2.getDuration() + 0));
		});
		
		linear397.setOnClickListener(_view -> {
			_un_clickable();
			section = "seasons";
			if (mediaPlayer2!=null) { 
				mediaPlayer2.reset(); }
			try { mediaPlayer2 =new MediaPlayer();
				AssetFileDescriptor descriptor = getAssets().openFd(
				"sections/".concat(section.concat(".mp3"))
				);
				mediaPlayer2.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
				descriptor.close(); 
				mediaPlayer2.prepare(); 
				
				mediaPlayer2.setLooping(false); 
				mediaPlayer2.start();
			} catch (Exception e) { e.printStackTrace(); }
			t = new TimerTask() {
				@Override
				public void run() {
					HmeActivity.this.runOnUiThread(() -> {
						i.setClass(HmeActivity.this.getApplicationContext(), PagerActivity.class);
						i.putExtra("type", section);
						HmeActivity.this.startActivity(i);
						HmeActivity.this.finish();
					});
				}
			};
			_timer.schedule(t, (int)(mediaPlayer2.getDuration() + 0));
		});
		
		linear400.setOnClickListener(_view -> {
			_un_clickable();
			section = "months";
			if (mediaPlayer2!=null) { 
				mediaPlayer2.reset(); }
			try { mediaPlayer2 =new MediaPlayer();
				AssetFileDescriptor descriptor = getAssets().openFd(
				"sections/".concat(section.concat(".mp3"))
				);
				mediaPlayer2.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
				descriptor.close(); 
				mediaPlayer2.prepare(); 
				
				mediaPlayer2.setLooping(false); 
				mediaPlayer2.start();
			} catch (Exception e) { e.printStackTrace(); }
			t = new TimerTask() {
				@Override
				public void run() {
					HmeActivity.this.runOnUiThread(() -> {
						i.setClass(HmeActivity.this.getApplicationContext(), PagerActivity.class);
						i.putExtra("type", section);
						HmeActivity.this.startActivity(i);
						HmeActivity.this.finish();
					});
				}
			};
			_timer.schedule(t, (int)(mediaPlayer2.getDuration() + 0));
		});
		
		linear398.setOnClickListener(_view -> {
			_un_clickable();
			section = "week days";
			if (mediaPlayer2!=null) { 
				mediaPlayer2.reset(); }
			try { mediaPlayer2 =new MediaPlayer();
				AssetFileDescriptor descriptor = getAssets().openFd(
				"sections/".concat(section.concat(".mp3"))
				);
				mediaPlayer2.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
				descriptor.close(); 
				mediaPlayer2.prepare(); 
				
				mediaPlayer2.setLooping(false); 
				mediaPlayer2.start();
			} catch (Exception e) { e.printStackTrace(); }
			t = new TimerTask() {
				@Override
				public void run() {
					HmeActivity.this.runOnUiThread(() -> {
						i.setClass(HmeActivity.this.getApplicationContext(), PagerActivity.class);
						i.putExtra("type", section);
						HmeActivity.this.startActivity(i);
						HmeActivity.this.finish();
					});
				}
			};
			_timer.schedule(t, (int)(mediaPlayer2.getDuration() + 0));
		});
	}
	
	private void initializeLogic() {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
		_ui();
		_imges_from_asset();
		/*
remove color of scroll
*/
		_removeScollBar(vscroll1);
		_removeScollBar(hscroll2);
		_removeScollBar(hscroll99);
		_removeScollBar(hscroll100);
		/*
lottie
*/
		lottie1.setAnimation("lottie/hme/learning-english.json");
		/*
fonts
*/
		_changeActivityFont("royal_404");
	}
	
	@Override
	public void onBackPressed() {
		finishAffinity();
	}
	public void _rippleRoundStroke(final View _view, final String _focus, final String _pressed, final double _round, final double _stroke, final String _strokeclr) {
		android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
		GG.setColor(Color.parseColor(_focus));
		GG.setCornerRadius((float)_round);
		GG.setStroke((int) _stroke,
		Color.parseColor("#" + _strokeclr.replace("#", "")));
		android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.parseColor("#FF757575")}), GG, null);
		_view.setBackground(RE);
	}
	
	
	public void _removeScollBar(final View _view) {
		_view.setVerticalScrollBarEnabled(false); _view.setHorizontalScrollBarEnabled(false);
	}
	
	
	public void _clickAnimation(final View _view) {
		ScaleAnimation fade_in = new ScaleAnimation(0.9f, 1f, 0.9f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.7f);
		fade_in.setDuration(300);
		fade_in.setFillAfter(true);
		_view.startAnimation(fade_in);
		//aauraparti YouTube channel//
	}
	
	
	public void _un_clickable() {
		linear2.setClickable(false);
		linear4.setClickable(false);
		linear6.setClickable(false);
		linear7.setClickable(false);
		linear8.setClickable(false);
		linear9.setClickable(false);
		linear10.setClickable(false);
		linear11.setClickable(false);
		linear12.setClickable(false);
		linear13.setClickable(false);
		linear396.setClickable(false);
		linear397.setClickable(false);
		linear398.setClickable(false);
		linear400.setClickable(false);
		linear401.setClickable(false);
		linear405.setClickable(false);
		linear408.setClickable(false);
		linear412.setClickable(false);
	}
	
	
	public void _changeActivityFont(final String _fontname) {
		fontName = (String.valueOf("fonts/") + String.valueOf((String.valueOf(_fontname) + String.valueOf(".ttf"))));
		overrideFonts(HmeActivity.this,getWindow().getDecorView()); 
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
			SketchwareUtil.showMessage(HmeActivity.this.getApplicationContext(), "Error Loading Font");
		};
	}
	
	
	public void _ui() {
		strok = "#f5f5f5";
		_rippleRoundStroke(linear2, "#F07060", "#F07060", 25, 4, strok);
		_rippleRoundStroke(linear4, "#D16FB7", "#D16FB7", 25, 4, strok);
		_rippleRoundStroke(linear6, "#F5f5f5", "#F5f5f5", 25, 4, strok);
		_rippleRoundStroke(linear7, "#F5f5f5", "#f5f5f5", 25, 4, strok);
		_rippleRoundStroke(linear8, "#757575", "#757575", 25, 4, strok);
		_rippleRoundStroke(linear9, "#47A3D8", "#47A3D8", 25, 4, strok);
		_rippleRoundStroke(linear10, "#8E8CD8", "#8E8CD8", 25, 4, strok);
		_rippleRoundStroke(linear11, "#FBEC5D", "#FBEC5D", 25, 4, strok);
		_rippleRoundStroke(linear12, "#50C878", "#50C878", 25, 4, strok);
		_rippleRoundStroke(linear13, "#563C5C", "#563C5C", 25, 4, strok);
		_rippleRoundStroke(linear6, "#909090", "#909090", 25, 4, strok);
		_rippleRoundStroke(linear7, "#585F98", "#585F98", 25, 4, strok);
		_rippleRoundStroke(linear396, "#884078", "#884078", 25, 4, strok);
		_rippleRoundStroke(linear6, "#909090", "#909090", 25, 4, strok);
		_rippleRoundStroke(linear397, "#585F98", "#585F98", 25, 4, strok);
		_rippleRoundStroke(linear398, "#4C9CAA", "#4C9CAA", 25, 4, strok);
		_rippleRoundStroke(linear400, "#34495E", "#34495E", 25, 4, strok);
		_rippleRoundStroke(linear401, "#6D8764", "#6D8764", 25, 4, strok);
		_rippleRoundStroke(linear405, "#8050BE", "#8050BE", 25, 4, strok);
		_rippleRoundStroke(linear408, "#3B7A57", "#3B7A57", 25, 4, strok);
		_rippleRoundStroke(linear412, "#9966CC", "#9966CC", 25, 4, strok);
	}
	
	
	public void _imges_from_asset() {
		/*
images from asset
*/
		try {
			java.io.InputStream imageview10Input = getAssets().open((String.valueOf("shapes/images/circle/") + String.valueOf("Solution.png"))); 
			Drawable imageview10Draw = Drawable.createFromStream(imageview10Input, null);
			imageview10.setImageDrawable(imageview10Draw);
			imageview10Input.close();
		} catch(java.io.IOException ex) {}
		try {
			java.io.InputStream imageview11Input = getAssets().open((String.valueOf("numbers/images/001/") + String.valueOf("Solution.png"))); 
			Drawable imageview11Draw = Drawable.createFromStream(imageview11Input, null);
			imageview11.setImageDrawable(imageview11Draw);
			imageview11Input.close();
		} catch(java.io.IOException ex) {}
		try {
			java.io.InputStream imageview12Input = getAssets().open((String.valueOf("alphabet/images/001/") + String.valueOf("Solution.png"))); 
			Drawable imageview12Draw = Drawable.createFromStream(imageview12Input, null);
			imageview12.setImageDrawable(imageview12Draw);
			imageview12Input.close();
		} catch(java.io.IOException ex) {}
		try {
			java.io.InputStream imageview29Input = getAssets().open((String.valueOf("alphabet-e/images/001/") + String.valueOf("Solution.png"))); 
			Drawable imageview29Draw = Drawable.createFromStream(imageview29Input, null);
			imageview29.setImageDrawable(imageview29Draw);
			imageview29Input.close();
		} catch(java.io.IOException ex) {}
		try {
			java.io.InputStream imageview5Input = getAssets().open((String.valueOf("bath/images/bathrobe/") + String.valueOf("Solution.png"))); 
			Drawable imageview5Draw = Drawable.createFromStream(imageview5Input, null);
			imageview5.setImageDrawable(imageview5Draw);
			imageview5Input.close();
		} catch(java.io.IOException ex) {}
		try {
			java.io.InputStream imageview6Input = getAssets().open((String.valueOf("kitchen/images/glass/") + String.valueOf("Solution.png"))); 
			Drawable imageview6Draw = Drawable.createFromStream(imageview6Input, null);
			imageview6.setImageDrawable(imageview6Draw);
			imageview6Input.close();
		} catch(java.io.IOException ex) {}
		try {
			java.io.InputStream imageview7Input = getAssets().open((String.valueOf("furniture/images/stool/") + String.valueOf("Solution.png"))); 
			Drawable imageview7Draw = Drawable.createFromStream(imageview7Input, null);
			imageview7.setImageDrawable(imageview7Draw);
			imageview7Input.close();
		} catch(java.io.IOException ex) {}
		try {
			java.io.InputStream imageview22Input = getAssets().open((String.valueOf("body/images/body1/") + String.valueOf("Solution.png"))); 
			Drawable imageview22Draw = Drawable.createFromStream(imageview22Input, null);
			imageview22.setImageDrawable(imageview22Draw);
			imageview22Input.close();
		} catch(java.io.IOException ex) {}
		try {
			java.io.InputStream imageview15Input = getAssets().open((String.valueOf("seasons/images/season4/") + String.valueOf("Solution.png"))); 
			Drawable imageview15Draw = Drawable.createFromStream(imageview15Input, null);
			imageview15.setImageDrawable(imageview15Draw);
			imageview15Input.close();
		} catch(java.io.IOException ex) {}
		try {
			java.io.InputStream imageview25Input = getAssets().open((String.valueOf("school/images/school4/") + String.valueOf("Solution.png"))); 
			Drawable imageview25Draw = Drawable.createFromStream(imageview25Input, null);
			imageview25.setImageDrawable(imageview25Draw);
			imageview25Input.close();
		} catch(java.io.IOException ex) {}
		try {
			java.io.InputStream imageview18Input = getAssets().open((String.valueOf("months/images/001/") + String.valueOf("Solution.png"))); 
			Drawable imageview18Draw = Drawable.createFromStream(imageview18Input, null);
			imageview18.setImageDrawable(imageview18Draw);
			imageview18Input.close();
		} catch(java.io.IOException ex) {}
		try {
			java.io.InputStream imageview1Input = getAssets().open("ani_8.png"); 
			Drawable imageview1Draw = Drawable.createFromStream(imageview1Input, null);
			imageview1.setImageDrawable(imageview1Draw);
			imageview1Input.close();
		} catch(java.io.IOException ex) {}
		try {
			java.io.InputStream imageview14Input = getAssets().open("frt_8.png"); 
			Drawable imageview14Draw = Drawable.createFromStream(imageview14Input, null);
			imageview14.setImageDrawable(imageview14Draw);
			imageview14Input.close();
		} catch(java.io.IOException ex) {}
		try {
			java.io.InputStream imageview4Input = getAssets().open("toy_4.png"); 
			Drawable imageview4Draw = Drawable.createFromStream(imageview4Input, null);
			imageview4.setImageDrawable(imageview4Draw);
			imageview4Input.close();
		} catch(java.io.IOException ex) {}
		try {
			java.io.InputStream imageview8Input = getAssets().open("tool_6.png"); 
			Drawable imageview8Draw = Drawable.createFromStream(imageview8Input, null);
			imageview8.setImageDrawable(imageview8Draw);
			imageview8Input.close();
		} catch(java.io.IOException ex) {}
		try {
			java.io.InputStream imageview9Input = getAssets().open("kid_stk_16.png"); 
			Drawable imageview9Draw = Drawable.createFromStream(imageview9Input, null);
			imageview9.setImageDrawable(imageview9Draw);
			imageview9Input.close();
		} catch(java.io.IOException ex) {}
		try {
			java.io.InputStream imageview19Input = getAssets().open("clothes.png"); 
			Drawable imageview19Draw = Drawable.createFromStream(imageview19Input, null);
			imageview19.setImageDrawable(imageview19Draw);
			imageview19Input.close();
		} catch(java.io.IOException ex) {}
		try {
			java.io.InputStream imageview16Input = getAssets().open("calendar.png"); 
			Drawable imageview16Draw = Drawable.createFromStream(imageview16Input, null);
			imageview16.setImageDrawable(imageview16Draw);
			imageview16Input.close();
		} catch(java.io.IOException ex) {}
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(HmeActivity.this.getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
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