package com.murat.murat.ydskelimatik;

import java.util.ArrayList;

/**
 * Created by murat on 22.02.2017.
 */

public class WordModel extends ArrayList<String> {
    String key,value;
    public WordModel(){

    }

    public WordModel(String key, String value){
        this.key=key;
        this.value=value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

