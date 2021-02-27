package com.galleryx.model;

public class Image {
    String imagePath;
    String album;
    String dateTaken;

    public Image(String imagePath, String album, String dateTaken) {
        this.imagePath = imagePath;
        this.album = album;
        this.dateTaken = dateTaken;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }
}
