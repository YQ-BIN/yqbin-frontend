
    public class PostMessageTask extends AsyncTask<String, Integer, Integer> {
    	 
        @Override
        protected Integer doInBackground(String... contents) {
        	
        	String url="http://yq-bin.sakura.ne.jp/order_insert.php";
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
     
            ArrayList <NameValuePair> params = new ArrayList <NameValuePair>();
    		params.add(new BasicNameValuePair("user", "test"));
    		params.add(new BasicNameValuePair("start", spinner1.getSelectedItem().toString()));
    		params.add(new BasicNameValuePair("goal",  spinner2.getSelectedItem().toString()));
    		params.add(new BasicNameValuePair("temp", "temp"));

            HttpResponse res = null;
            
            try {
                post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
                res = httpClient.execute(post);
                Log.d("post", "送信完了");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("post", e.toString());
            }
     	    return Integer.valueOf(res.getStatusLine().getStatusCode());
        }
     
    }
