package com.hans.arrayDemo;

import java.util.ArrayList;

public class Arr75 {
    public void ThreeCorlorSort(int[] colorArr){
       // int[] colorArr = {0,1,0,2,1,0,1,2,2};
       int a=0,c=0;
       int b = colorArr.length - 1;

       for(a=0; c<=b;){
           if(colorArr[c]==0) {
               int tmp;
               tmp = colorArr[a];
               colorArr[a] = colorArr[c];
               colorArr[c] = tmp;
               a++;
               c++;
           }else if(colorArr[c]==2){
               int tmp;
               tmp = colorArr[b];
               colorArr[b] = colorArr[c];
               colorArr[c] = tmp;
               b--;
           }else{
                c++;
           }
       }

        System.out.printf("%s , %s ,%s, \n", a, c, b);
       for(int i: colorArr){
           System.out.printf("%s", i);
       }
    }
    public static void main(String[] args) {
        int[] colorArr = {0,1,0,2,1,0,1,2,2};
        new Arr75().ThreeCorlorSort(colorArr);
    }
}
