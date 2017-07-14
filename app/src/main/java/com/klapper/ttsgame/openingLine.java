package com.klapper.ttsgame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c1103304 on 2017/7/14.
 */

public class openingLine {
    static openingLine op;
    int listSize=0;
    List<String> openingLineStringList;
    public static openingLine getInstance(){
        if(op == null){
            op = new openingLine();
        }
        return op;
    }
    public void init(){
        openingLineStringList = new ArrayList<>();
        openingLineStringList.add("你好，我是煞氣a妹仔，歡迎使用來啊互相傷害啊舒壓軟體體體體體體體體體體體體體體體體體體體體體體體你該不會以為我當機了吧，哈哈哈騙到你了");
        /**
         *   建立開頭語詞庫
         *
         */
        listSize = openingLineStringList.size();
    }

    public String getRandomOpeningLine(){
        int num = (int)(Math.random()*listSize);
        return openingLineStringList.get(num);
    }

    public void addOpeningLine(String str){
        if(openingLineStringList!=null) {
            openingLineStringList.add(str);
            openingLineSize();
        }
    }
    public void deleteOpeningLine(int Index){
        if(openingLineStringList!=null) {
            openingLineStringList.remove(Index);
            openingLineSize();
        }
    }
    public int openingLineSize(){
        listSize = openingLineStringList!=null?openingLineStringList.size():0;
        return listSize;
    }
}
