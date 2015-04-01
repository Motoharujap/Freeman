package interaction_with_webapp;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Motoharu on 29.10.2014.
 */


public class SAXGettersSetters{
    String title = null;
    String description = null;
    String date = null;

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }
    public String getDate(){
        return date;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }
    public void setDate(String date){
        this.date = date;
    }
}
