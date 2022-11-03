package javawebdev.petsns.post.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDTO{

    private String imgName;
    private String uuid;
    private String path;

    private int postId;

    public String getImageURL() throws UnsupportedEncodingException {
        try{
            return URLEncoder.
                    encode(path +"/"+uuid+"_"+imgName,"UTF-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }
    public String getThumbnailURL() {
        try {
            return URLEncoder.encode(path + "/s_" + uuid + "_" + imgName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
