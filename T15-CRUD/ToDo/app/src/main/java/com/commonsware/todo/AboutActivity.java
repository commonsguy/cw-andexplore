package com.commonsware.todo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebView;

public class AboutActivity extends FragmentActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about);

    WebView wv=findViewById(R.id.about);

    wv.loadUrl("file:///android_asset/about.html");
  }
}
