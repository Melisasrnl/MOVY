package com.movies;

public enum ProfilePhoto {

    AVATAR_1("https://res.cloudinary.com/dsvq5k1xr/image/upload/v1775046449/yellowpf_njndsd.jpg"),
    AVATAR_2("https://res.cloudinary.com/dsvq5k1xr/image/upload/v1775046449/redpf_tbknbr.jpg"),
    AVATAR_3("https://res.cloudinary.com/dsvq5k1xr/image/upload/v1775046448/orangepf_hka3dh.jpg"),
    AVATAR_4("https://res.cloudinary.com/dsvq5k1xr/image/upload/v1775046448/purplepf_yhpknq.jpg"),
    AVATAR_5("https://res.cloudinary.com/dsvq5k1xr/image/upload/v1775046448/pinkpf_x9wf48.jpg"),
    AVATAR_6("https://res.cloudinary.com/dsvq5k1xr/image/upload/v1775046448/bluepf_vznwsj.jpg"),
    AVATAR_7("https://res.cloudinary.com/dsvq5k1xr/image/upload/v1775046448/greenpf_luxsqf.jpg"),
    DEFAULT("https://res.cloudinary.com/dsvq5k1xr/image/upload/v1775046448/graypf_msd6wr.jpg");

    private String url;
 
    ProfilePhoto(String url) {
        this.url = url;
    }
 
    public String getUrl() {
        return url;
    }
}
