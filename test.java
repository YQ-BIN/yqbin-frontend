package com.droidvoice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.app.SearchManager.OnCancelListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.AlarmClock;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Ai extends Service  implements OnInitListener{
	public boolean frg_study = false;
	// 何もしない回数＝自発発言のための変数
	public Integer free_count = 0;
	public Integer study_mode = 0;
	public String study_word = "";
	public String study_ans = "";
	public static boolean frg_kanshi = false;
	public String study_ans_save = "";
	private SpeechRecognizer mSpeechRecognizer;
	private String timeline = "";
	private String reply = "";
	private boolean RecEnable = true;
	private String hatugen;
	private String userkey;
	private boolean frg_y_n;
	public String make_answer_thread = "";
	public static String mode;
	private String mode_word;
	private Intent intent_browser;
	private Integer cook_index = 0;
	public static String mainkey = "";
	private String[] cook_h;
	private boolean ok_read = true;
	private boolean frg_start = true;
	private boolean frg_voice = true;
	private Integer action_point = 0;
	private String memo_content = "";
	private String load_plan;
	private String load_memo;
	private String load_voc;
	public static String hozi_hiduke;
	public static String hozi_content;
	public static String keep_noun = "";
	private String read_news;
	private String[] h_read_news;
	public boolean study_go = true;
	public boolean frg_edit = false;
	public String mail_content = "";
	public String thread_word = "";

	Handler handler = new Handler();
	Handler handler2 = new Handler();

	public void sendNotification2(String key, String msg, Object icon) {
		Notification n = new Notification(); // Notificationの生成
		n.icon = R.drawable.droid_icon2; // アイコンの設定
		n.tickerText = msg; // メッセージの設定

		Intent i = new Intent(getApplicationContext(), MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
		n.setLatestEventInfo(getApplicationContext(), "Droid Chan", msg, pi);
		long[] vibrate_ptn = { 0, 100, 300, 8000 }; // 独自バイブレーションパターン
		n.vibrate = vibrate_ptn; // 独自バイブレーションパターンを設定
		n.defaults |= Notification.DEFAULT_LIGHTS; // デフォルトLED点滅パターンを設定
		// NotificationManagerのインスタンス取得
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		try {
			nm.notify(1, n); // 設定したNotificationを通知する
		} catch (Exception e) {
			e.printStackTrace();
			// Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
		}
	}

	public void sendNotification(String key, String msg, Object icon) {
		if (msg.indexOf("dic\">") >= 0) {
			msg = msg.replace("dic\">", "");
		}

		if (key.indexOf("url") > 0) {
			msg = msg + "\n詳しくはこちらをタップ";
		}
		try {
			MainActivity.msg_out(key, msg, icon);
		} catch (Exception e) {

		}

		// }

		Notification n = new Notification(); // Notificationの生成
		n.icon = R.drawable.droid_icon2;// アイコンの設定
		n.tickerText = msg; // メッセージの設定

		Intent i = new Intent(getApplicationContext(), MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
		n.setLatestEventInfo(getApplicationContext(), "Droid Chan", msg, pi);
		long[] vibrate_ptn = { 0, 100, 300, 1000 }; // 独自バイブレーションパターン
		n.vibrate = vibrate_ptn; // 独自バイブレーションパターンを設定
		n.defaults |= Notification.DEFAULT_LIGHTS; // デフォルトLED点滅パターンを設定
		// NotificationManagerのインスタンス取得
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		try {
			nm.notify(1, n); // 設定したNotificationを通知する
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
