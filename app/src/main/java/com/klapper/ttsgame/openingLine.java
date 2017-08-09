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
        openingLineStringList.add("投資一定有風險，基金投資有賺有賠，申購前應詳閱公開說明書。");
        openingLineStringList.add("工作一定有風險，專案承做有賺有賠，加班前應詳閱公開規格書。");
        openingLineStringList.add("再忙也要和你喝杯咖啡。");
        openingLineStringList.add("全家就是你家，麥當勞都是維尼");
        openingLineStringList.add("再忙…也要和你喝杯咖啡。");
        openingLineStringList.add("媽~我運功散啦,自從服用妳寄來的阿榮以後，胸腔鬱悶,中氣不順已經好了");
        openingLineStringList.add("媽~我阿榮啦,自從服用妳寄來的運功散以後，胸腔鬱悶,中氣不順已經好了");
        openingLineStringList.add("品客一口口，片刻不離手。");
        openingLineStringList.add("什麼都狂什麼都不奇怪");
        openingLineStringList.add("狂，你媽媽知道你在這邊跟我聊天嗎");
        openingLineStringList.add("躺著玩坐著玩趴著玩，還是八仙好玩，出門在外要注意安全喔喔喔喔喔");
        openingLineStringList.add("保護眼睛 鞏固牙齒 武功強壯 康喜健鈣我最愛");
        openingLineStringList.add("謀模謀模謀，家裡好像養了一頭牛");
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
