package com.example.midtronics_first_technical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private final List<Country> countryList = new ArrayList<>();
    private RecyclerView recyclerView;
    private final String TITLEBAR = "Countries";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);

        setTitle(TITLEBAR);

        try {
            getEventsFromAnXML();
        } catch (XmlPullParserException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        refreshRecycler();
    }


    private void getEventsFromAnXML() throws XmlPullParserException, IOException
    {
        Resources res = getResources();
        XmlResourceParser xpp = res.getXml(R.xml.countries);
        xpp.next();
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            if(eventType == XmlPullParser.TEXT)
            {
                countryList.add(new Country(xpp.getText(),null,null,null,null,null));
            }
            eventType = xpp.next();
        }
    }

    private void CountriesCount() {
        setTitle(" (" + countryList.size() + ") " + TITLEBAR );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(this, ProfileActivity.class); // this is explicit intent
        startActivity(intent);
        return true;
    }

    public void refreshRecycler(){
        CountryAdapter vh = new CountryAdapter(countryList, this);
        recyclerView.setAdapter(vh);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CountriesCount();
    }


    public void startCountryActivity(Country countryObject) {
        Intent intent = new Intent(this, CountryActivity.class);
        intent.putExtra("countryObject", countryObject);
        startActivity(intent);
    }

    public void downloadFailed() {
        Toast.makeText(this, "Download failed!!", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View view) {
        int postion = recyclerView.getChildLayoutPosition(view);
        String country = countryList.get(postion).getCountry();
        CountryLoaderRunnable countryLoader = new CountryLoaderRunnable(this, country);
        new Thread(countryLoader).start();

    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}