package com.hans.demo.learn.stream;

import com.hans.demo.learn.lambda.cart.Sku;
import com.hans.demo.learn.lambda.cart.SkuCategoryEnum;
import com.hans.demo.learn.lambda.cart.SkuService;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StreamVs {
    /**
     * 1、查看购物车中都有哪些商品
     * 2、图书类商品都可以买
     * 3、其余商品中买两件最贵的
     * 4、只需要两件商品的名称和总价
     */

    /**
     * 查看购物车中都有哪些商品
     * @return
     */
    public static List<Sku> getCartSkus(){
       return SkuService.getCartSkuList();
    }

    /**
     * 图书类商品都可以买
     * @return
     */
    public static List<Sku> getNotBookSkus(){
        List<Sku> skuList = getCartSkus();
        List<Sku> skuBookList = new ArrayList<>();
        for(Sku sku: skuList){
            if(!SkuCategoryEnum.BOOKS.equals(sku.getSkuCategory())){
                skuBookList.add(sku);
            }
        }
        return skuBookList;
    }

    public static List<Sku> sortSkus(){
        List<Sku> skuList = getNotBookSkus();
        skuList.sort(new Comparator<Sku>() {
            @Override
            public int compare(Sku sku1, Sku sku2) {
                if (sku1.getSkuPrice() > sku2.getSkuPrice()){
                    return -1;
                } else if(sku1.getSkuPrice() < sku2.getSkuPrice()){
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return skuList;
    }

    public static List<Sku> top2Skus(){
        List<Sku> skuList = sortSkus();
        List<Sku> top2List = new ArrayList<Sku>();
        for(int i = 0; i<2; i++){
            top2List.add(skuList.get(i));
        }

        return top2List;
    }


}
