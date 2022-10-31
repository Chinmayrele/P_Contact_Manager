package com.codingproject.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codingproject.contactmanager.data.DatabaseHandler;
import com.codingproject.contactmanager.models.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> contactArrayList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(MainActivity.this);
        listView = findViewById(R.id.listView);
        contactArrayList = new ArrayList<>();

        // GET COUNT OF CONTACTS
        Log.d("GET COUNT", "onCreate: " + db.getContactCount());

//        // CREATING CONTACT OBJECT FIRST]
//        Contact chinmay = new Contact();
//        chinmay.setName("Chinmay");
//        chinmay.setPhoneNumber("9404372648");
//        db.addContacts(chinmay);
//
//        Contact sanku = new Contact();
//        sanku.setName("Sanskruti");
//        sanku.setPhoneNumber("9422558109");
//        db.addContacts(sanku);
//
//        Contact mummy = new Contact();
//        mummy.setName("Bhairavi");
//        mummy.setPhoneNumber("8983206298");
//        db.addContacts(mummy);
//
//        db.addContacts(new Contact("Rani", "86958879"));
//        db.addContacts(new Contact("Tushar", "86795864"));
//        db.addContacts(new Contact("Amit", "08697579"));
//        db.addContacts(new Contact("Sumit", "086797847"));
//        db.addContacts(new Contact( "Mansoor", "8687475"));
//        db.addContacts(new Contact("Amman", "08687432"));
//        db.addContacts(new Contact("Abhay", "9758624"));
//        db.addContacts(new Contact("Mahi", "9895756215"));
//        db.addContacts(new Contact("Piyush", "9746368"));
//        db.addContacts(new Contact("Kalyani", "45487798"));
//        db.addContacts(new Contact("Anikta", "68975736"));

//        // GET ONE CONTACT
//        Contact cont = db.getContact(1);
//        Log.d("OneMainCont", "onCreate: " + cont.getName() + ",  " + cont.getPhoneNumber());
//
//        // UPDATE A CONTACT
//        Contact upContact = db.getContact(2);
//        upContact.setName("New Bhairavi");
//        upContact.setPhoneNumber("8983241115");
//        int updatedRowIndex =  db.updateContact(upContact);
//        Log.d("UpdatedRow", "onCreate: " + updatedRowIndex);
//
//        // DELETE A CONTACT
//        Contact delContact = db.getContact(2);
//        db.deleteContact(delContact);

        // LIST OF ALL THE CONTACTS STORED
        List<Contact> allContacts = db.getAllContacts();
        for(Contact contact : allContacts) {
            Log.d("MainAllContact", "onCreate: " + contact.getName() + "  "
                    + contact.getPhoneNumber() + ",  " + contact.getId());
            contactArrayList.add(contact.getName());
        }
        // CREATE ARRAY ADAPTER
        arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, contactArrayList);
        // ADD ARRAY ADAPTER TO LISTVIEW
        listView.setAdapter(arrayAdapter);

        // ATTACH EVENT LISTENER TO LISTVIEW
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("LIST", "onItemClick: " + contactArrayList.get(i));
            }
        });
    }
}