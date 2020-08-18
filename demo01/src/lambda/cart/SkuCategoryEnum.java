package lambda.cart;

public enum SkuCategoryEnum {
    CLOTHING(10,"服装类"),
    ELECTRONICS(20,"数码类"),
    SPORTS(30,"运动类"),
    BOOKS(40,"书籍类");

    // 商品类型编号
    private Integer code;
    // 商品类型名称
    private String name;

    SkuCategoryEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
