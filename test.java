
public class Ai extends Service  implements OnInitListener{

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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
