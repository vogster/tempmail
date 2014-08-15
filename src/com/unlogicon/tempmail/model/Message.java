package com.unlogicon.tempmail.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by nik on 12.08.14.
 */
public class Message implements Serializable, Parcelable {

    private String mail_unique_id;  // Что то, на сайте этого нет
    private String mail_id;         // Уникальный идентификатор письма, присвоенный системой
    private String mail_address_id; // md5 хеш почтового адрес
    private String mail_from;       // Отправитель
    private String mail_subject;    // Тема
    private String mail_preview;    // Предпросмотр сообщения
    private String mail_text_only;  // Cообщение в текстовом или в html формате (основной)
    private String mail_text;       // Cообщение только в текстовом формате
    private String mail_html;       // Cообщение только в html формате
    private String mail_timestamp;  // Время

    public String getMailUniqueId() {
        return mail_unique_id;
    }

    public void setMailUniqueId(String mailUniqueId) {
        this.mail_id = mailUniqueId;
    }

    public String getMailId() {
        return mail_id;
    }

    public void setMailId(String mailId) {
        this.mail_id = mailId;
    }

    public String getMailAdressId() {
        return mail_address_id;
    }

    public void setMailAddressId(String mailAddressId) {
        this.mail_address_id = mailAddressId;
    }

    public String getMailFrom() {
        return mail_from;
    }

    public void setMailFrom(String mailFrom) {
        this.mail_from = mailFrom;
    }

    public String getMailSubject() {
        return mail_subject;
    }

    public void setMailSubject(String mailSubject) {
        this.mail_subject = mailSubject;
    }

    public String getMailPreview() {
        return mail_preview;
    }

    public void setMailPreview(String mailPreview) {
        this.mail_preview = mailPreview;
    }

    public String getMailTextOnly() {
        return mail_text_only;
    }

    public void setMailTextOnly(String mailTextOnly) {
        this.mail_text_only = mailTextOnly;
    }

    public String getMailText() {
        return mail_text;
    }

    public void setMailText(String mailText) {
        this.mail_text = mailText;
    }

    public String getMailHtml() {
        return mail_html;
    }

    public void setMailHtml(String mailHtml) {
        this.mail_html = mailHtml;
    }

    public String getMailTimestamp() {
        return mail_timestamp;
    }

    public void setMailTimestamp(String mailTimestamp) {
        this.mail_timestamp = mailTimestamp;
    }

    @Override
    public int describeContents() {
        return ((Object)this).hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mail_unique_id);
        dest.writeString(mail_id);
        dest.writeString(mail_address_id);
        dest.writeString(mail_from);
        dest.writeString(mail_subject);
        dest.writeString(mail_preview);
        dest.writeString(mail_text_only);
        dest.writeString(mail_text);
        dest.writeString(mail_html);
        dest.writeString(mail_timestamp);
    }

    public Message(){
    }

    public Message(Parcel in) {
        mail_unique_id = in.readString();
        mail_id = in.readString();
        mail_address_id = in.readString();
        mail_from = in.readString();
        mail_subject = in.readString();
        mail_preview = in.readString();
        mail_text_only = in.readString();
        mail_text = in.readString();
        mail_html = in.readString();
        mail_timestamp = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
