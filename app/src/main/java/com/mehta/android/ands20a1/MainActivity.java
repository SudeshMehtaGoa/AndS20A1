package com.mehta.android.ands20a1;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.RawContacts;
import android.widget.Toast;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_ACCESS_FINE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED)
        {

            ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.READ_CONTACTS},REQUEST_ACCESS_FINE);

        }



        // Creating a button click listener for the "Add Contact" button
        View.OnClickListener addClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                // Getting reference to Name EditText
                EditText etName = (EditText) findViewById(R.id.et_name);

                // Getting reference to Mobile EditText
                EditText etMobile = (EditText) findViewById(R.id.et_mobile_phone);

                // Getting reference to HomePhone EditText
                EditText etHomePhone = (EditText) findViewById(R.id.et_home_phone);

                // Getting reference to HomeEmail EditText
                EditText etHomeEmail = (EditText) findViewById(R.id.et_home_email);

                // Getting reference to WorkEmail EditText
                EditText etWorkEmail = (EditText) findViewById(R.id.et_work_email);

                ArrayList<ContentProviderOperation> ops =
                        new ArrayList<ContentProviderOperation>();

                int rawContactID = ops.size();

                // Adding insert operation to operations list
                // to insert a new raw contact in the table ContactsContract.RawContacts
                ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                        .build());



                // Adding insert operation to operations list
                // to insert display name in the table ContactsContract.Data
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, etName.getText().toString())
                        .build());


                // Adding insert operation to operations list
                // to insert Mobile Number in the table ContactsContract.Data
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, etMobile.getText().toString())
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, CommonDataKinds.Phone.TYPE_MOBILE)
                        .build());

                // Adding insert operation to operations list
                // to  insert Home Phone Number in the table ContactsContract.Data
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                        .withValue(ContactsContract.Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
                        .withValue(Phone.NUMBER, etHomePhone.getText().toString())
                        .withValue(Phone.TYPE, Phone.TYPE_HOME)
                        .build());


                // Adding insert operation to operations list
                // to insert Home Email in the table ContactsContract.Data
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                        .withValue(ContactsContract.Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
                        .withValue(Email.ADDRESS, etHomeEmail.getText().toString())
                        .withValue(Email.TYPE, Email.TYPE_HOME)
                        .build());

                // Adding insert operation to operations list
                // to insert Work Email in the table ContactsContract.Data
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                        .withValue(ContactsContract.Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
                        .withValue(Email.ADDRESS, etWorkEmail.getText().toString())
                        .withValue(Email.TYPE, Email.TYPE_WORK)
                        .build());



                try{
                    // Executing all the insert operations as a single database transaction
                    getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                    Toast.makeText(getBaseContext(), "Contact is successfully added", Toast.LENGTH_LONG).show();

                }catch (Exception e) {
                    Log.e("XXX",e.getMessage());
                    Toast.makeText(getBaseContext(), "Contact is not added, check permissions", Toast.LENGTH_LONG).show();
                }
            }
        };

        // Creating a button click listener for the "Add Contact" button
        View.OnClickListener contactsClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Creating an intent to open Android's Contacts List
                Intent contacts = new Intent(Intent.ACTION_VIEW,ContactsContract.Contacts.CONTENT_URI);

                // Starting the activity
                startActivity(contacts);
            }
        };

        // Getting reference to "Add Contact" button
        Button btnAdd = (Button) findViewById(R.id.btn_add);

        // Getting reference to "Contacts List" button
        Button btnContacts = (Button) findViewById(R.id.btn_contacts);

        // Setting click listener for the "Add Contact" button
        btnAdd.setOnClickListener(addClickListener);

        // Setting click listener for the "List Contacts" button
        btnContacts.setOnClickListener(contactsClickListener);

    }
}
