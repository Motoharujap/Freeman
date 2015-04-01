package interaction_with_webapp;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Motoharu on 29.10.2014.
 */
public class SaxHandler extends DefaultHandler {
    Boolean currentElement = false;
    String currentValue = "";
    public static SAXGettersSetters data = null;
    private ArrayList<SAXGettersSetters> list = new ArrayList<SAXGettersSetters>();

    public ArrayList<SAXGettersSetters> getList(){
        return list;
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        currentElement = true;
        currentValue = "";
        if (localName.equals("title")) {
            data = new SAXGettersSetters();
        }

    }
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        currentElement = false;

        /** set value */
        if (localName.equalsIgnoreCase("title"))
            data.setTitle(currentValue);
        else if (localName.equalsIgnoreCase("description"))
            data.setDescription(currentValue);
        else if (localName.equalsIgnoreCase("pubDate"))
            data.setDate(currentValue);
        else if(localName.equalsIgnoreCase("item"))
            list.add(data);
    }
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        if (currentElement) {
            currentValue = currentValue +  new String(ch, start, length);
        }

    }
}
