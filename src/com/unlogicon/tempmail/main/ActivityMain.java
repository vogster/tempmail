package com.unlogicon.tempmail.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.unlogicon.tempmail.R;
import com.unlogicon.tempmail.Settings;
import com.unlogicon.tempmail.Utils;
import com.unlogicon.tempmail.UtilsApi;
import com.unlogicon.tempmail.adapter.MessageAdapter;
import com.unlogicon.tempmail.model.Message;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.unlogicon.tempmail.UtilsApi.getCallBack;

public class ActivityMain extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {
    /**
     * Called when the activity is first created.
     */

    private Activity activity;

    private ListView messageList;
    private MessageAdapter messageAdapter;
    public ArrayList<Message> rows;
    private AQuery aq;
    private Menu menu;
    private Settings settings;

    private SwipeRefreshLayout swipeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        activity = this;

        settings = new Settings(this);

        aq = new AQuery(this);
        rows = new ArrayList<Message>();

        messageList = (ListView) findViewById(R.id.messageList);
        messageAdapter = new MessageAdapter(this, rows);

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(R.color.cool_green);


        Utils.addMenuButton(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main_menu, menu);

        if (settings.getHash() == null) {
            settings.setEmail(Utils.generateRandomEmail(this));
            settings.setHash(Utils.computeMD5(settings.getEmail()));
            getSupportActionBar().setSubtitle(settings.getEmail());
        } else {
            getSupportActionBar().setSubtitle(settings.getEmail());
        }

        reloadList();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_copy:
                Utils.copyToClipboard(this, settings.getEmail());
                return true;
            case R.id.action_about:
                Utils.showAboutDialog(this);
                break;
            case R.id.action_change:
                showChangeEmailDialog();
                break;
            case R.id.action_share:
                Utils.shareEmail(this, settings.getEmail());
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void reloadList() {
        aq.progress(this).ajax(getCallBack(this, settings.getHash()));
    }


    public void onRefresh(String url, String s, AjaxStatus status) {
        rows.clear();
        if (s != null) {
            messageAdapter.notifyDataSetInvalidated();
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);
                    rows.add(UtilsApi.parseMessage(c));
                }
            } catch (Exception e) {
                AQUtility.debug("error List parser", e.toString());
                e.printStackTrace();
            }
            messageAdapter.notifyDataSetChanged();
            messageList.setAdapter(messageAdapter);

        } else {
            AQUtility.debug("error List parser", s);
            aq.ajaxCancel();
        }
        swipeLayout.setRefreshing(false);
    }

    private void showChangeEmailDialog() {

        final String login = Utils.generateRandomString(this);

        LayoutInflater li = LayoutInflater.from(activity);
        View promptsView = li.inflate(R.layout.change_email_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setTitle(getString(R.string.title_dialog));

        final EditText et_login = (EditText) promptsView.findViewById(R.id.login);
        final Spinner domine = (Spinner) promptsView.findViewById(R.id.domine);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.emailDomains)); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        domine.setAdapter(spinnerArrayAdapter);

        et_login.setHint(login);

        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton(getString(R.string.create),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                rows.clear();
                                messageAdapter.notifyDataSetChanged();
                                if (!et_login.getText().toString().equals(""))
                                    settings.setEmail(et_login.getText().toString() + "@" + domine.getSelectedItem().toString());
                                else
                                    settings.setEmail(login + "@" + domine.getSelectedItem().toString());
                                settings.setHash(Utils.computeMD5(settings.getEmail()));
                                getSupportActionBar().setSubtitle(settings.getEmail());
                                reloadList();
                                dialog.cancel();
                            }
                        })
                .setNegativeButton(getString(R.string.close),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    @Override
    public void onRefresh() {
        reloadList();
    }
}



