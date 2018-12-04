package com.xuebling.newpoetryspread.common.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class RepoUtils {
    public static ArrayList<String> transformURL(String id){
        String[] list= id.split("/");
        ArrayList<String> result = new ArrayList<>(Arrays.asList(list));
        return result;
    }
}
