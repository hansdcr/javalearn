package com.hans.demo.learn.lambda.cart;

public class SkuTotalPricePredicate implements SkuPredicate{
    @Override
    public Boolean test(Sku sku) {
        return sku.getTotalPrice() > 2000;
    }
}
