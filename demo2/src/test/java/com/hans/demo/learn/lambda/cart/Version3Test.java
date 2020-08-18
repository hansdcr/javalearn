package com.hans.demo.learn.lambda.cart;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class Version3Test {
    @Test
    public void filterSkusByCategory(){
        List<Sku> skuList = SkuService.getCartSkuList();
        List<Sku> skus = new ArrayList<Sku>();

        // false 安装总价过滤
        skus = Sku.filterSkus(skuList, SkuCategoryEnum.ELECTRONICS, 2000.00, false);

        System.out.println(JSON.toJSONString(skus, true));
    }
}
