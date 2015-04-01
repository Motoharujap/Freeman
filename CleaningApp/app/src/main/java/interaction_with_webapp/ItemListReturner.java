package interaction_with_webapp;

import android.util.Log;

import com.android.volley.toolbox.StringRequest;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Motoharu on 31.10.2014.
 */
public class ItemListReturner {
    public static ArrayList<SAXGettersSetters> itemList = new ArrayList<SAXGettersSetters>();
    public static String xmlUrl = "";
    //we don't actually need this constructor, I just left it here for mo reason
    public ItemListReturner(String xmlUrl){
        this.xmlUrl = xmlUrl;
    }
    public ArrayList<SAXGettersSetters> getItemList(String s){
        Log.d("LIST 2", itemList.toString());
        makeList(s);
        return itemList;
    }
    public void handleHandler(XMLReader reader){
        SaxHandler handler = new SaxHandler();
    }
    public void makeList(final String s){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser sp = factory.newSAXParser();
                    XMLReader xmlReader = sp.getXMLReader();
                    SaxHandler handler = new SaxHandler();
                    xmlReader.setContentHandler(handler);
                    itemList = handler.getList();
                    URL url = new URL(xmlUrl);
                    InputSource is = new InputSource(new StringReader(s));
                    is.setEncoding("UTF-8");
                    xmlReader.parse(is);
                    Log.d("LIST 1", itemList.toString());
                } catch (SAXException e) {
                    System.out.println(e);
                } catch (ParserConfigurationException p) {
                    System.out.println(p);
                } catch (IOException i) {
                    System.out.print(i);
                }
            }
        });
        thread.start();
        try{
            thread.join();
        }catch(InterruptedException i){
            Log.d("JOIN THREAD", i.toString());
        }
    }
}
