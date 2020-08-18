package com.hans.demo.learn.lambda.cart;

public class SkuBooksCategoryPredicate implements SkuPredicate {
    @Override
    public Boolean test(Sku sku) {
        return SkuCategoryEnum.BOOKS.equals(sku.getSkuCategory());
    }
}
