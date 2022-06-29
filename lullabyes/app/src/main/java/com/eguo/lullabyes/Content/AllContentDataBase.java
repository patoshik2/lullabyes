package com.eguo.lullabyes.Content;

public class AllContentDataBase extends TailePostId{
  private String image_url;
  private String image_thumb;
  public   String name;
  private String search;
  private String desc;
  public   String user_id;
  public   String type;
  private String audio_url;
  public   String priority;
  private String classautor;
  private int like;


    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getEditlanguges() {
        return editlanguges;
    }

    public void setEditlanguges(String editlanguges) {
        this.editlanguges = editlanguges;
    }

    String editlanguges;

    public AllContentDataBase(String editlanguges) {
        this.editlanguges = editlanguges;
    }

    public String getClassautor() {
         return classautor;

     }


    public void setClassautor(String classautor) {
        this.classautor = classautor;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAudio_url() {
        return audio_url;
    }

    public void setAudio_url(String audio_url) {
        this.audio_url = audio_url;
    }





 public    AllContentDataBase(){}
    public String getImage_url() {
        return image_url;
    }

    public String getImage_thumb() {
        return image_thumb;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setImage_thumb(String image_thumb) {
        this.image_thumb = image_thumb;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String getName() {
        return name;

    }

    public String getDesc() {
        return desc;
    }

    public String getUser_id() {
        return user_id;
    }



    public AllContentDataBase(String user_id, String image_url, String name, String desc, String image_thumb ) {
        this.image_url = image_url;
        this.image_thumb = image_thumb;
        this.name = name;
        this.desc = desc;
        this.user_id = user_id;


    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }



}
