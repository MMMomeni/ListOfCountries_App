package com.example.midtronics_first_technical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.Bundle;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private final List<Country> countryList = new ArrayList<>();
    private RecyclerView recyclerView;
    private final String TITLEBAR = "Countries";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setTitle(TITLEBAR);

        String stringXmlContent;

        try {
            stringXmlContent = getEventsFromAnXML();
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        updateRecycler();
    }


    private String getEventsFromAnXML()
            throws XmlPullParserException, IOException
    {
        StringBuffer stringBuffer = new StringBuffer();
        Resources res = getResources();
        XmlResourceParser xpp = res.getXml(R.xml.countries);
        xpp.next();
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            if(eventType == XmlPullParser.START_DOCUMENT)
            {
                stringBuffer.append("--- Start XML ---");
            }
            else if(eventType == XmlPullParser.START_TAG)
            {
                stringBuffer.append("\nSTART_TAG: "+xpp.getName());
            }
            else if(eventType == XmlPullParser.END_TAG)
            {
                stringBuffer.append("\nEND_TAG: "+xpp.getName());
            }
            else if(eventType == XmlPullParser.TEXT)
            {
                stringBuffer.append("\nTEXT: "+xpp.getText());
                countryList.add(new Country(xpp.getText(),null,null,null,null,null));
            }
            eventType = xpp.next();
        }
        stringBuffer.append("\n--- End XML ---");
        return stringBuffer.toString();
    }

    private void updateTitleNoteCount() {
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

    public void updateRecycler(){
        recyclerView = findViewById(R.id.recycler);
        CountryAdapter vh = new CountryAdapter(countryList, this);
        recyclerView.setAdapter(vh);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateTitleNoteCount();
    }


    public void updateData1(Country countryObject) {
        //countryList.clear();
        //countryList.addAll(cList);
        //Collections.reverse(countryList);
        //location.setText(countryList.get(0).getLocation());
        //updateRecycler();
        Intent intent = new Intent(this, CountryActivity.class);
        intent.putExtra("countryObject", countryObject);
        startActivity(intent);
    }

    public void downloadFailed() {
        //officialList.clear();
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