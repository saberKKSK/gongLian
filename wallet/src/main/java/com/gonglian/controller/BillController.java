package com.gonglian.controller;

import com.gonglian.dto.*;
import com.gonglian.service.BillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "账单管理", description = "账单相关接口")
@RestController
@RequestMapping("/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @Operation(summary = "收入概览", description = "获取收入和订单统计数据")
    @GetMapping("/overview")
    public ResponseResult<IncomeOverviewDTO> getIncomeOverview() {
        return ResponseResult.success(billService.getIncomeOverview());
    }

    @Operation(summary = "未结算订单", description = "获取未结算订单列表和待结算总金额")
    @GetMapping("/unsettled")
    public ResponseResult<UnsettledOrdersDTO> getUnsettledOrders() {
        return ResponseResult.success(billService.getUnsettledOrders());
    }

    @Operation(summary = "订单详情", description = "获取特定订单的详细信息")
    @GetMapping("/{id}")
    public ResponseResult<OrderDetailDTO> getOrderDetail(
            @Parameter(description = "订单ID") @PathVariable Long id) {
        return ResponseResult.success(billService.getOrderDetail(id));
    }

    @Operation(summary = "订单列表", description = "获取订单列表，支持分页和条件查询")
    @GetMapping("/list")
    public ResponseResult<PageDTO<OrderDetailDTO>> getOrderList(OrderQueryDTO queryDTO) {
        return ResponseResult.success(billService.getOrderList(queryDTO));
    }

    @Operation(summary = "店铺统计", description = "统计各店铺的订单数量、金额等数据")
    @GetMapping("/shop-stats")
    public ResponseResult<List<ShopStatsDTO>> getShopStats() {
        return ResponseResult.success(billService.getShopStats());
    }

} 