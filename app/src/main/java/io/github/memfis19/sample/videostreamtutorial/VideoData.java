package io.github.memfis19.sample.videostreamtutorial;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Ravi Tamada on 07/10/16.
 * www.androidhive.info
 */

@IgnoreExtraProperties
public class VideoData {

    public String type;
    public String link;
    public String thumnail;

    public VideoData(){}

    public VideoData(String type, String link,String thumnail) {
        this.type = type;
        this.link = link;
        this.thumnail = thumnail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getThumnail() {
        return thumnail;
    }

    public void setThumnail(String thumnail) {
        this.thumnail = thumnail;
    }
}
