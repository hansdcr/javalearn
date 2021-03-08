package com.hans.stackDemo;

import java.util.ArrayList;
import java.util.List;

public class Stack1441 {
    public static  List<String> buildArray(int[] target){
        List<String> newArr = new ArrayList<>();
        for(int i=0,j=1;i<target.length;j++){
            newArr.add("Push");
            if(j < target[i]){
                newArr.add("Pop");
            }else{
                i++;
            }
        }
        return newArr;
    }
    public static void main(String[] args) {
        int[] target = new int[]{1,3};
        List<String> list =buildArray(target);
        System.out.printf(String.valueOf(list)
        );
    }
}
