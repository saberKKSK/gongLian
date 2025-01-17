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
} 