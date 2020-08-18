package com.hans.demo.learn.lambda.cart;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

import java.util.List;


public class Version1Test {
    @Test
    public void filterElectronicsSkus(){
        List<Sku> cartSkuList = SkuService.getCartSkuList();
        List<Sku> result = Sku.filterElectronicsSkus(cartSkuList);
        System.out.println(JSON.toJSONString(result, true));
    }
}
