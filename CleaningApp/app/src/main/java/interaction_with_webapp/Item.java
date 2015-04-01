package interaction_with_webapp;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Motoharu on 09.11.2014.
 */
@Root
public class Item {
    @Element
    private String title;
    @Element
    private String description;

    public Item(){
        super();
    }
    public Item(String title, String description){
        this.title = title;
        this.description = description;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
}
