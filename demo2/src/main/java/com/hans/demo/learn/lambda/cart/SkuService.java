package com.hans.demo.learn.lambda.cart;

import java.util.ArrayList;
import java.util.List;

public class SkuService {
    private static List<Sku> cartSkuList = new ArrayList<Sku>(){
        {
            add(new Sku(654032,
                    "无人机",
                    4999.00,
                    1,
                    4999.00,
                    SkuCategoryEnum.ELECTRONICS
            ));
            add(new Sku(654034,
                    "VR一体机",
                    2999.00,
                    1,
                    2999.00,
                    SkuCategoryEnum.ELECTRONICS
            ));

            add(new Sku(654321,
                    "纯色衬衫",
                    409.00,
                    3,
                    1227.00,
                    SkuCategoryEnum.CLOTHING
            ));

            add(new Sku(654327,
                    "牛仔裤",
                    528.00,
                    1,
                    528.00,
                    SkuCategoryEnum.CLOTHING
            ));

            add(new Sku(675489,
                    "跑步机",
                    2699.00,
                    1,
                    2699.00,
                    SkuCategoryEnum.SPORTS
            ));
            add(new Sku(678678,
                    "Java编程思想",
                    149.00,
                    1,
                    149.00,
                    SkuCategoryEnum.BOOKS
            ));
        }
    };

    public static List<Sku> getCartSkuList(){
        return cartSkuList;
    }
}
