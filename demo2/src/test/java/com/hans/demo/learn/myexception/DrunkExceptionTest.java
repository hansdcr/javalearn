package com.hans.demo.learn.myexception;

import org.junit.jupiter.api.Test;

public class DrunkExceptionTest {
    public void test1() throws DrunkException {
        throw new DrunkException("喝多了");
    }

    public void  test2(){
        try {
            test1();
        } catch (DrunkException e) {
//            RuntimeException newExc = new RuntimeException("喝多了不要开车");
//            newExc.initCause(e);
//            throw newExc;
                RuntimeException newExc = new RuntimeException(e);
                throw newExc;
        }
    }

    @Test
    public void test3(){
        test2();
    }
}
