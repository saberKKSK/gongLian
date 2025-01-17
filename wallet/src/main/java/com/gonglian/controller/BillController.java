package com.gonglian.controller;

import com.gonglian.dto.*;
import com.gonglian.service.BillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    @Operation(summary = "创建订单", description = "创建新的订单")
    @PostMapping("/add")
    public ResponseResult<BillDTO> createBill(@Valid @RequestBody CreateBillDTO createBillDTO) {
        return ResponseResult.success(billService.createBill(createBillDTO));
    }

    @Operation(summary = "更新订单", description = "更新订单信息")
    @PutMapping("/update/{id}")
    public ResponseResult<BillDTO> updateBill(
            @Parameter(description = "订单ID") @PathVariable Long id,
            @Valid @RequestBody CreateBillDTO updateBillDTO) {
        return ResponseResult.success(billService.updateBill(id, updateBillDTO));
    }

    @Operation(summary = "删除订单", description = "删除指定的订单")
    @DeleteMapping("/delete/{id}")
    public ResponseResult<Void> deleteBill(
            @Parameter(description = "订单ID") @PathVariable Long id) {
        billService.deleteBill(id);
        return ResponseResult.success(null);
    }

    @Operation(summary = "结算订单", description = "将订单标记为已结算")
    @PutMapping("/settle/{id}")
    public ResponseResult<Void> settleBill(
            @Parameter(description = "订单ID") @PathVariable Long id) {
        billService.settleBill(id);
        return ResponseResult.success(null);
    }

    @Operation(summary = "段位统计", description = "获取指定平台下未结算订单的段位分组统计")
    @GetMapping("/rank-stats/{platformId}")
    public ResponseResult<List<RankGroupDTO>> getUnsettledOrdersByRank(
            @Parameter(description = "平台ID") @PathVariable Integer platformId) {
        return ResponseResult.success(billService.getUnsettledOrdersByRank(platformId));
    }

} 