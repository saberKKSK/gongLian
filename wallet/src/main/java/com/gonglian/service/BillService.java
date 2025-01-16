package com.gonglian.service;

import com.gonglian.dto.*;
import java.util.List;

public interface BillService {
    IncomeOverviewDTO getIncomeOverview();
    UnsettledOrdersDTO getUnsettledOrders();
    OrderDetailDTO getOrderDetail(Long id);
    PageDTO<OrderDetailDTO> getOrderList(OrderQueryDTO queryDTO);
    List<ShopStatsDTO> getShopStats();
} 