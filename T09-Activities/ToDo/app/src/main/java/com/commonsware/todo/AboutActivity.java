package com.commonsware.todo;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class AboutActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about);

    WebView wv=findViewById(R.id.about);

    wv.loadUrl("file:///android_asset/about.html");
  }
}
