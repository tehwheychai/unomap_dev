package com.example.wuikhong.unomap.camera;

/**
 * Created by Guest on 11/20/2017.
 */

public class Card {

    private String name, singer, year;
    private int pic, rank;

    public Card(String name, String singer, int rank, int pic){
        this.name=name;
        this.singer=singer;
        this.rank=rank;
        this.pic=pic;
    }
    public int getRank(){
        return rank;
    }
    public void setRank(int rank){
        this.rank=rank;

    }
    public String getName(){

        return name;
    }
    public void setName(){
        this.name=name;
    }
    public int getPic(){
        return pic;

    }
    public void setPic(){
        this.pic=pic;
    }
    public String getSinger(){
        return singer;
    }
    public void setSinger(){
        this.singer=singer;
    }
    public String getYear(){
        return year;

    }
    public void setYear(){
        this.year=year;
    }

}
