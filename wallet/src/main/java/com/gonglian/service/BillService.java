package com.gonglian.service;

import com.gonglian.dto.*;

import java.util.List;

public interface BillService {
    IncomeOverviewDTO getIncomeOverview();
    UnsettledOrdersDTO getUnsettledOrders();
    OrderDetailDTO getOrderDetail(Long id);
    PageDTO<OrderDetailDTO> getOrderList(OrderQueryDTO queryDTO);
    List<ShopStatsDTO> getShopStats();
    BillDTO createBill(CreateBillDTO createBillDTO);
    BillDTO updateBill(Long id, CreateBillDTO updateBillDTO);
    void deleteBill(Long id);
    void settleBill(Long id);
    /**
     * 获取指定平台下未结算订单的段位分组统计
     *
     * @param platformId 平台ID
     * @return 段位分组统计列表
     */
    List<RankGroupDTO> getUnsettledOrdersByRank(Integer platformId);
} 