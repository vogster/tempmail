package com.unlogicon.tempmail.properties;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import com.unlogicon.tempmail.Constants;
import com.unlogicon.tempmail.R;

/**
 * Created by nik on 13.08.14.
 */
public class ActivityProperties extends ActionBarActivity {

    WebView prop;

    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.properties_activity);

        prop = (WebView) findViewById(R.id.prop);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.properties));

        prop.loadUrl(Constants.PROPERTIES_MESSAGE + getIntent().getStringExtra("id"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
