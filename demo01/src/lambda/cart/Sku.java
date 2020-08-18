package lambda.cart;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.util.ArrayList;
import java.util.List;

public class Sku {
    private Integer skuId;
    private String skuName;
    private Double skuPrice;
    private Integer totalNum;
    private Double totalPrice;
    private Enum skuCategory;

    public Sku(Integer skuId, String skuName, Double skuPrice, Integer totalNum, Double totalPrice, Enum skuCategory) {
        this.skuId = skuId;
        this.skuName = skuName;
        this.skuPrice = skuPrice;
        this.totalNum = totalNum;
        this.totalPrice = totalPrice;
        this.skuCategory = skuCategory;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public Double getSkuPrice() {
        return skuPrice;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public Enum getSkuCategory() {
        return skuCategory;
    }

    /**
     * 找出购物车中所有电子产品
     * @param cartSkuList
     * @return
     */
    public List<Sku> filterElectronicsSkus(List<Sku> cartSkuList){
        List<Sku> result = new ArrayList<Sku>();
        for(Sku sku: cartSkuList){
            if(SkuCategoryEnum.ELECTRONICS.equals(sku.getSkuCategory())){
                result.add(sku);
            }
        }
        return result;
    }
}
