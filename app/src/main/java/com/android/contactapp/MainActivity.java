package com.android.contactapp;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,LoaderManager.LoaderCallbacks<Cursor> {
    private Button mybutton;
    private TextView myText;
    private ContentResolver contentResolver;
    private boolean firstTimeLoaded=false;
    private String mOrderBy = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;



    private String[] mColumnProjection = new String[]{
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.CONTACT_STATUS,
            ContactsContract.Contacts.HAS_PHONE_NUMBER};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myText = (TextView) findViewById(R.id.mytext);
        mybutton = (Button) findViewById(R.id.loadbutton);
        mybutton.setOnClickListener(this);
        contentResolver=getContentResolver();

    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        if(i==1){
           return new CursorLoader(this,ContactsContract.Contacts.CONTENT_URI,mColumnProjection, null,null,mOrderBy);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor!=null && cursor.getCount()>0){
            StringBuilder stringBuilderQueryResult=new StringBuilder("");
            while (cursor.moveToNext()){
                stringBuilderQueryResult.append(cursor.getString(0)+" , "+cursor.getString(1)+" , "+cursor.getString(2)+" , "+cursor.getString(3)+"\n");
            }
            myText.setText(stringBuilderQueryResult.toString());
        }else{
            myText.setText("No Contacts in device");
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View view) {
        if(firstTimeLoaded==false){
            getLoaderManager().initLoader(1, null,this);
            firstTimeLoaded=true;
        }else{
            getLoaderManager().restartLoader(1,null,this);
        }
    }
}
