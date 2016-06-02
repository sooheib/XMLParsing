package com.vivianaranha.xmlparsing;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public static final String SERVER_URL = "https://query.yahooapis.com/v1/public/" +
            "yql?q=select%20*%20from%20local.search%20where%20zip%3D%2720770%27%20and%20query%3D%27pizza%27";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadXMLData downloadXMLData = new DownloadXMLData();
        downloadXMLData.execute();


    }

    public class DownloadXMLData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {


            try {
                URL theUrl = new URL(SERVER_URL);

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(theUrl.openConnection().getInputStream(), "UTF_8");

                int eventType = xpp.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT){
                    if(eventType == XmlPullParser.START_TAG){
                        if(xpp.getName().equalsIgnoreCase("address")){
                            System.out.println("Address: " + xpp.nextText());
                        }
                    }
                    eventType = xpp.next();
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //DowcumentBuilder
//            try {
//                URL theUrl = new URL(SERVER_URL);
//
//                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//                DocumentBuilder db = dbf.newDocumentBuilder();
//                Document document = db.parse(new InputSource(theUrl.openStream()));
//                document.getDocumentElement().normalize();
//
//                NodeList nodeList = document.getElementsByTagName("Result");
//
//                for(int i = 0; i<nodeList.getLength(); i++ ){
//                    Node node = nodeList.item(i);
//                    Element element = (Element) node;
//                    NodeList nl = element.getElementsByTagName("Title");
//                    Element nameElement = (Element) nl.item(0);
//                    nl = nameElement.getChildNodes();
//                    System.out.println("Title: " + nl.item(0).getNodeValue());
//
//
//                }
//
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (ParserConfigurationException e) {
//                e.printStackTrace();
//            } catch (SAXException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            return null;
        }
    }


}
