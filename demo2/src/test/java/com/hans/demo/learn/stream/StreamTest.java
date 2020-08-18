package com.hans.demo.learn.stream;

import com.alibaba.fastjson.JSON;
import com.hans.demo.learn.lambda.cart.Sku;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StreamTest {
    @Test
    public  void getCartSkus(){
        List<Sku> skuList = StreamVs.getCartSkus();
        System.out.println(JSON.toJSONString(skuList, true));
    }

    @Test
    public void getNotBookSkus(){
        List<Sku> skuList = StreamVs.getNotBookSkus();
        System.out.println(JSON.toJSONString(skuList, true));
    }

    @Test
    public void sortSkus(){
        List<Sku> skuList = StreamVs.sortSkus();
        System.out.println(JSON.toJSONString(skuList, true));
    }

    @Test
    public void top2Skus(){
        List<Sku> skuList = StreamVs.top2Skus();
        System.out.println(JSON.toJSONString(skuList, true));
    }
}
