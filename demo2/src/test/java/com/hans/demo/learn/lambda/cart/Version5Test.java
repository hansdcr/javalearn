package com.hans.demo.learn.lambda.cart;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class Version5Test {
    @Test
    public void filterSkusByCategory(){
        List<Sku> skuList = SkuService.getCartSkuList();
        List<Sku> skus = new ArrayList<Sku>();

        // 通过匿名类的方式，传递参数
        skus = Sku.filterSkus(skuList, new SkuPredicate() {
            @Override
            public Boolean test(Sku sku) {
                return sku.getTotalPrice() > 4000;
            }
        });

        System.out.println(JSON.toJSONString(skus, true));
    }
}
