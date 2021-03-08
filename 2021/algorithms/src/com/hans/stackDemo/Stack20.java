package com.hans.stackDemo;

import java.util.Stack;

public class Stack20 {
    public static boolean isValid(String s){
        Stack<Character> st = new Stack<>();
        for(char c: s.toCharArray()){
            switch (c){
                case '(': case '[': case '{':
                    st.push(c);
                    break;
                case ')':
                    if(st.empty()|| st.pop() !='('){
                        return false;
                    }
                    break;
                case ']':
                    if(st.empty()|| st.pop() !='['){
                        return false;
                    }
                    break;
                case '}':
                    if(st.empty()|| st.pop() !='{'){
                        return false;
                    }
                    break;
                }
            }
        return st.empty();
        }
    public static void main(String[] args) {
        String s = "([])";
        System.out.printf(String.valueOf(isValid(s)));
    }
}
