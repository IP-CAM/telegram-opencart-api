package ua.kpi.telegram.opencart.telegramopencart.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.telegram.opencart.telegramopencart.domain.model.Goods;
import ua.kpi.telegram.opencart.telegramopencart.domain.service.GoodsService;
import ua.kpi.telegram.opencart.telegramopencart.repository.GoodsRepository;

@Service
public class GoodsServiceImpl implements GoodsService {
    private final GoodsRepository goodsRepository;

    @Autowired
    public GoodsServiceImpl(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @Override
    public void addGoods(Goods goods) {
        goodsRepository.save(goods);
    }
}
