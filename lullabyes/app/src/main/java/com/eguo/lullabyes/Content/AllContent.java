package com.eguo.lullabyes.Content;

/**
 * Created by Владимир on 11.05.2018.
 */

public class AllContent {
    private String name;
    private String singer;
    private int description;
    private int song;
    private int image;
    private int type;
public static  final  int ONE_TYPE =1;
public static  final  int TWO_TYPE = 2;


    public AllContent(String name, String singer, int song, int image, int type,int description){
        this.name  = name;
        this.singer = singer;
        this.song = song;
        this.image = image;
        this.type = type;
        this.description=description;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public int getSong() {
        return song;
    }

    public void setSong(int song) {
        this.song = song;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
    public int getType() {
        return type;
    }
    public  void setType(int type){
        this.type = type;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }
}
