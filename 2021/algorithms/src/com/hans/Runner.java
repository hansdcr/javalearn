package com.hans;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    public static List<String> buildArray(int[] target, int n) {
        List<String> arr = new ArrayList<>();
        for(int i=0, j=1;i<target.length;j++){
            arr.add("push");
            if(j<target[i]){
                arr.add("pop");
            }else {
                ++i;
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        int [] target = new int[]{2,3};
        List<String> arr = buildArray(target, 5);
        System.out.printf(String.valueOf(arr));
    }
}
