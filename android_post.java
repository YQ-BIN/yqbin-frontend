	public static String get_yahoo(String sentence,Integer type) {
		String ret = "";
		String comment = "";
		// URL
		URI url = null;
		try {
			url = new URI("http://yq-bin.sakura.ne.jp/order_insert.php");
		} catch (URISyntaxException e) {
			e.printStackTrace();
			ret = e.toString();
		}

		// POSTパラメータ付きでPOSTリクエストを構築
		HttpPost request = new HttpPost(url);
		List<NameValuePair> post_params = new ArrayList<NameValuePair>();
		value.add(new BasicNameValuePair("word", sentence));
		value.add(new BasicNameValuePair("type", String.valueOf(type)));

		try {
			// 送信パラメータのエンコードを指定
			request.setEntity(new UrlEncodedFormEntity(post_params, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		// POSTリクエストを実行
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			// //Log.d("posttest", "POST開始");
			ret = httpClient.execute(request, new ResponseHandler<String>() {

				@Override
				public String handleResponse(HttpResponse response)
						throws IOException {

					// 正常に受信できた場合は200
					switch (response.getStatusLine().getStatusCode()) {
					case HttpStatus.SC_OK:
						// Log.d("posttest", "レスポンス取得に成功");
						// レスポンスデータをエンコード済みの文字列として取得する
						return EntityUtils.toString(response.getEntity(),
								"UTF-8");

					case HttpStatus.SC_NOT_FOUND:
						// Log.d("posttest", "データが存在しない");
						return null;

					default:
						// Log.d("posttest", "通信エラー");
						// break;
						return null;
					}

				}

			});

		} catch (IOException e) {
			// Log.d("posttest", "通信に失敗：" + e.toString());
		} finally {
			// shutdownすると通信できなくなる
			httpClient.getConnectionManager().shutdown();
		}

		if (ret == null) {
			ret = "";
			// SpeechText("取得できませんでした");
		} else {

		}
		return ret;

	}
