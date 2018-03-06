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

    public VideoData() {
    }

    public VideoData(String type, String link) {
        this.type = type;
        this.link = link;
    }
}
