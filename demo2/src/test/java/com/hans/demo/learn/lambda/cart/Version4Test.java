package com.hans.demo.learn.lambda.cart;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class Version4Test {
    @Test
    public void filterSkusByCategory(){
        List<Sku> skuList = SkuService.getCartSkuList();
        List<Sku> skus = new ArrayList<Sku>();


        skus = Sku.filterSkus(skuList, new SkuTotalPricePredicate());

        System.out.println(JSON.toJSONString(skus, true));
    }
}
