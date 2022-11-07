package javawebdev.petsns.post.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDTO{

    private String imgName;
    private String uuid;
    private String path;

    private Integer postId;

    public String getImageURL() throws UnsupportedEncodingException {
        return URLEncoder.encode(path + "/" + uuid + "_" + imgName, StandardCharsets.UTF_8);
    }

    public String getImagePath() {
        return path + "/" + uuid + "_" + imgName;
    }

    public String getThumbnailURL() {
        return URLEncoder.encode(path + "/" + postId + "/s_" + uuid + "_" + imgName, StandardCharsets.UTF_8);
    }
}
