package deprecated_classes;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Motoharu on 28.10.2014.
 */
public class DomXmlParser {

    String urlLink;
    InputStream is;

    public Document getDom(String xml) {
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(xml);

        } catch (Exception e) {
            e.printStackTrace();
        }
            return doc;
    }

    public String getXML(String urlCon){
        String xml = null;
                try{
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(urlCon);

                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    xml = EntityUtils.toString(httpEntity);
                }catch(Exception e){
                    e.printStackTrace();
                }
            return xml;
            }

    }

