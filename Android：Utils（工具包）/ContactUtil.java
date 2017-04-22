package com.gomejr.myf.core.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Alex
 * 时间：2017/2/27 10:48
 * 简述：
 */
public class ContactUtil {
    /*
    * context.getContentResolver();
    * */
    public static  List<ContactBean> getPhoneContactList(ContentResolver contentResolver){
        List<ContactBean> list = new ArrayList<>();
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while(cursor.moveToNext())
        {
            ContactBean contactBean = new ContactBean();
            //String _ID = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String DISPLAY_NAME = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String NUMBER = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactBean.setName(DISPLAY_NAME);
            contactBean.setPhone(NUMBER);
            list.add(contactBean);
            LogTrack.i(contactBean);
        }
        cursor.close();
        return list;
    }
    public static final class ContactBean{
        private String name;
        private String phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Override
        public String toString() {
            return "ContactBean{" +
                    "name='" + name + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }
}
