    private void post_word(){
    	   HttpClient client = new DefaultHttpClient();
         HttpPost post = new HttpPost("http://realfind.jp/p_insert.php");
         // BODYに登録、設定
         ArrayList<NameValuePair> value = new ArrayList<NameValuePair>();
         value.add( new BasicNameValuePair("msg", hatugen));
         value.add( new BasicNameValuePair("userkey", userkey));

         String body = null;
         try {
              post.setEntity(new UrlEncodedFormEntity(value, "UTF-8"));
              // リクエスト送信
              HttpResponse response = client.execute(post);
              // 取得
              HttpEntity entity = response.getEntity();
              body = EntityUtils.toString(entity, "UTF-8");
         } catch(IOException e) {
              e.printStackTrace();
         }
         client.getConnectionManager().shutdown();
    }
