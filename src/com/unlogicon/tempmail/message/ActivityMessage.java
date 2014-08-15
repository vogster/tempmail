package com.unlogicon.tempmail.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import com.androidquery.AQuery;
import com.unlogicon.tempmail.R;
import com.unlogicon.tempmail.Utils;
import com.unlogicon.tempmail.model.Message;
import com.unlogicon.tempmail.properties.ActivityProperties;

/**
 * Created by nik on 13.08.14.
 */
public class ActivityMessage extends ActionBarActivity {

    private Message message;
    private AQuery aq;
    private WebView text;
    private Menu menu;

    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.message_activity);

        if (getIntent().getExtras() == null)
            finish();

        message = (Message) getIntent().getExtras().getSerializable("message");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        text = (WebView) findViewById(R.id.text);

        aq = new AQuery(this);


        getSupportActionBar().setTitle(message.getMailSubject());
        getSupportActionBar().setSubtitle(getString(R.string.from) + " " + message.getMailFrom());
        text.loadData(message.getMailHtml(), "text/html; charset=UTF-8", null);

        Utils.addMenuButton(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.message_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.properties:
                Intent intent = new Intent(this, ActivityProperties.class);
                intent.putExtra("id", message.getMailId());
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
