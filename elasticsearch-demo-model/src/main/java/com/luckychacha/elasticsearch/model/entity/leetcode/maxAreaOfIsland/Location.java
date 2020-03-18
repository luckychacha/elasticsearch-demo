package com.luckychacha.elasticsearch.model.entity.leetcode.maxAreaOfIsland;

import lombok.Data;

@Data
public class Location {
    public Location(int x,int y){
        this.x=x;
        this.y=y;
    }
    public int x;
    public int y;
}
