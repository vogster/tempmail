package com.unlogicon.tempmail;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.util.AQUtility;
import com.unlogicon.tempmail.model.Message;
import org.json.JSONObject;

/**
 * Created by nik on 13.08.14.
 */
public class UtilsApi {

    public static AjaxCallback getCallBack(Object o,String mailHash) {
        String url = Constants.REQUEST_MAIL + mailHash + Constants.FORMAT_JSON;
        AQUtility.debug("geturl", url);
        AjaxCallback<String> cb = new AjaxCallback<String>();
        cb.url(url).type(String.class).weakHandler(o, "onRefresh").fileCache(false).expire(-1);
        return cb;
    }

    public static Message parseMessage(final JSONObject siteObject) {
        Message message = new Message();
        message.setMailUniqueId(siteObject.optString("mail_unique_id"));
        message.setMailId(siteObject.optString("mail_id"));
        message.setMailAddressId(siteObject.optString("mail_address"));
        message.setMailFrom(siteObject.optString("mail_from"));
        message.setMailSubject(siteObject.optString("mail_subject"));
        message.setMailPreview(siteObject.optString("mail_preview"));
        message.setMailTextOnly(siteObject.optString("mail_text_only"));
        message.setMailText(siteObject.optString("mail_text"));
        message.setMailHtml(siteObject.optString("mail_html"));
        message.setMailTimestamp(siteObject.optString("mail_timestamp"));
        return message;
    }

}
