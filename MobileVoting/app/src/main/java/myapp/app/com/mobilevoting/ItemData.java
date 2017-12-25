package myapp.app.com.mobilevoting;

/**
 * Created by sunny on 9/11/17.
 */
public class ItemData {


    String text;
    Integer imageId;
    public ItemData(String text, Integer imageId){
        this.text=text;
        this.imageId=imageId;
    }

    public String getText(){
        return text;
    }

    public Integer getImageId(){
        return imageId;
    }
}

