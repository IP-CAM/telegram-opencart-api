package ua.kpi.telegram.opencart.telegramopencart.domain.model;

import ua.kpi.telegram.opencart.telegramopencart.domain.model.taxonomy.Goods;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
public class Cart implements Identified {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany
    private List<BuyItem> buyItems = new ArrayList<>();

    public void addToCart(BuyItem buyItem) {
        buyItems.add(buyItem);
    }

    public void addToCart(List<BuyItem> buyItems) {
        this.buyItems.addAll(buyItems);
    }

    public void remove(BuyItem buyItem) {
        buyItems.remove(buyItem);
    }

    public void remove(Goods goods) {
        buyItems = buyItems.stream().filter(buyItem -> !buyItem.getGoods().equals(goods)).collect(toList());
    }

    public void clear() {
        buyItems.clear();
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<BuyItem> getBuyItems() {
        return buyItems;
    }

    public void setBuyItems(List<BuyItem> buyItems) {
        this.buyItems = buyItems;
    }
}
