package com.codingproject.contactmanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.codingproject.contactmanager.R;
import com.codingproject.contactmanager.models.Contact;
import com.codingproject.contactmanager.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(@Nullable Context context) {
        super(context, Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);
    }
//    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Utils.TABLE_NAME + "(" + Utils.KEY_ID +
                " INTEGER PRIMARY KEY," + Utils.KEY_NAME + " TEXT," + Utils.KEY_PHONE_NUMBER + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACT_TABLE); // CREATING OUR TABLE
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String DROP_TABLE = String.valueOf(R.string.db_drop);
        sqLiteDatabase.execSQL(DROP_TABLE, new String[]{Utils.DATABASE_NAME});

        // CREATE A TABLE AGAIN
        onCreate(sqLiteDatabase);
    }

    // CRUD OPERATIONS

    // ADD CONTACTS
    public void addContacts(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utils.KEY_NAME, contact.getName());
        values.put(Utils.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        // INSERT TO ROW
        db.insert(Utils.TABLE_NAME, null, values);
        Log.d("DBHandler", "addContacts: ITEMS ADDED");
        db.close();   // CLOSE THE DATABASE CONNECTION
    }

    // GET THE CONTACTS
    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Utils.TABLE_NAME, new String[] {Utils.KEY_ID, Utils.KEY_NAME, Utils.KEY_PHONE_NUMBER},
                Utils.KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact();
        contact.setId(Integer.parseInt(cursor.getString(0)));
        contact.setName(cursor.getString(1));
        contact.setPhoneNumber(cursor.getString(2));

        return contact;
    }

    // GET ALL THE CONTACTS
    public List<Contact> getAllContacts() {
        List<Contact> allContacts = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        // SELECT ALL CONTACTS FROM DATABASE TABLE
        String selectAll = "SELECT * FROM " + Utils.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        // LOOP THROUGH OUR DATA
        if(cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));

                // ADD CONTACT OBJECT TO OUR LIST
                allContacts.add(contact);
            } while(cursor.moveToNext());

        }

        return allContacts;
    }

    // UPDATE THE CONTACT
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utils.KEY_NAME, contact.getName());
        values.put(Utils.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        // UPDATE THE CONTACT ROW
        // Update(Table_Name, value, id=1,2,3...)
        return db.update(Utils.TABLE_NAME, values, Utils.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});
    }

    // DELETE SINGLE CONTACT
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Utils.TABLE_NAME, Utils.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    // GET CONTACT COUNT
    public int getContactCount() {
        String countQuery = "SELECT * FROM " + Utils.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }
}
