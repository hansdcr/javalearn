package com.hans.demo.learn.lambda.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Sku {
    private Integer skuId;
    private String skuName;
    private Double skuPrice;
    private Integer totalNum;
    private Double totalPrice;
    private Enum skuCategory;

    /**
     * 找出购物车中所有电子产品
     * @param cartSkuList
     * @return
     */
    public static List<Sku> filterElectronicsSkus(List<Sku> cartSkuList){
        List<Sku> result = new ArrayList<Sku>();
        for(Sku sku: cartSkuList){
            if(SkuCategoryEnum.ELECTRONICS.equals(sku.getSkuCategory())){
                result.add(sku);
            }
        }
        return result;
    }

    public static List<Sku> filterSkusByCategory(List<Sku> cartSkuList, SkuCategoryEnum skuCategory){
        List<Sku> skuList = new ArrayList<Sku>();
        for(Sku sku: cartSkuList){
            if(skuCategory.equals(sku.getSkuCategory())){
                skuList.add(sku);
            }
        }
        return skuList;
    }

    public static List<Sku> filterSkus(List<Sku> skuList,
                                       SkuCategoryEnum skuCategory,
                                       Double totalPrice,
                                       Boolean categoryOrPrice){
        List<Sku> skus = new ArrayList<Sku>();
        for(Sku sku: skuList){
            if(
                    (skuCategory.equals(sku.getSkuCategory()) && categoryOrPrice)
                    ||(!categoryOrPrice && sku.getTotalPrice() > totalPrice)
            ){
                skus.add(sku);
            }
        }
        return skus;
    }

    public static List<Sku> filterSkus(List<Sku> skuList, SkuPredicate skuPredicate){
        List<Sku> skus = new ArrayList<Sku>();
        for(Sku sku: skuList){
            if(skuPredicate.test(sku)){
                skus.add(sku);
            }
        }
        return skus;
    }
}
