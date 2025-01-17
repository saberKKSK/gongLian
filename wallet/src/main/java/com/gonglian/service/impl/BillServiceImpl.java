package com.gonglian.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gonglian.dto.*;
import com.gonglian.exception.BusinessException;
import com.gonglian.mapper.BillMapper;
import com.gonglian.model.Bills;
import com.gonglian.service.BillService;
import com.gonglian.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillServiceImpl implements BillService {

    private static final CopyOptions BILL_COPY_OPTIONS = CopyOptions.create()
            .setIgnoreNullValue(true)
            .setFieldMapping(Collections.singletonMap("rank", "ranked"));

    private static final CopyOptions DTO_COPY_OPTIONS = CopyOptions.create()
            .setFieldMapping(Collections.singletonMap("ranked", "rank"));

    private final BillMapper billMapper;
    private final JwtUtil jwtUtil;
    private final HttpServletRequest request;

    @Override
    public IncomeOverviewDTO getIncomeOverview() {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);

        LambdaQueryWrapper<Bills> baseWrapper = new LambdaQueryWrapper<Bills>()
                .eq(Bills::getUserId, userId);

        List<Bills> bills = billMapper.selectList(baseWrapper);

        // 计算总收入
        BigDecimal totalIncome = bills.stream()
                .map(Bills::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 统计订单数
        long totalOrders = bills.size();
        long settledOrders = bills.stream().filter(Bills::getSettled).count();
        long unsettledOrders = totalOrders - settledOrders;

        return IncomeOverviewDTO.builder()
                .totalIncome(totalIncome)
                .totalOrders((int) totalOrders)
                .settledOrders((int) settledOrders)
                .unsettledOrders((int) unsettledOrders)
                .build();
    }

    @Override
    public UnsettledOrdersDTO getUnsettledOrders() {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);

        LambdaQueryWrapper<Bills> wrapper = new LambdaQueryWrapper<Bills>()
                .eq(Bills::getUserId, userId)
                .eq(Bills::getSettled, false)
                .orderByDesc(Bills::getCreatedAt);

        List<Bills> bills = billMapper.selectList(wrapper);

        BigDecimal totalPendingAmount = bills.stream()
                .map(Bills::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<OrderDetailDTO> orderDetails = bills.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return UnsettledOrdersDTO.builder()
                .totalPendingAmount(totalPendingAmount)
                .orders(orderDetails)
                .build();
    }

    @Override
    public OrderDetailDTO getOrderDetail(Long id) {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);

        LambdaQueryWrapper<Bills> wrapper = new LambdaQueryWrapper<Bills>()
                .eq(Bills::getId, id)
                .eq(Bills::getUserId, userId);

        Bills bill = billMapper.selectOne(wrapper);
        if (bill == null) {
            throw new BusinessException("订单不存在或无权访问");
        }

        return convertToDTO(bill);
    }

    @Override
    public PageDTO<OrderDetailDTO> getOrderList(OrderQueryDTO queryDTO) {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);

        LambdaQueryWrapper<Bills> wrapper = new LambdaQueryWrapper<Bills>()
                .eq(Bills::getUserId, userId)
                .eq(queryDTO.getSettled() != null, Bills::getSettled, queryDTO.getSettled())
                .eq(StringUtils.hasText(queryDTO.getRank()), Bills::getRanked, queryDTO.getRank())
                .eq(queryDTO.getGameId() != null, Bills::getGameId, queryDTO.getGameId())
                .eq(queryDTO.getPlatform() != null, Bills::getPlatform, queryDTO.getPlatform())
                .orderByDesc(Bills::getCreatedAt);

        Page<Bills> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        Page<Bills> result = billMapper.selectPage(page, wrapper);

        List<OrderDetailDTO> records = result.getRecords().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        PageDTO<OrderDetailDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotal(result.getTotal());
        pageDTO.setRecords(records);
        pageDTO.setPageNum(queryDTO.getPageNum());
        pageDTO.setPageSize(queryDTO.getPageSize());
        pageDTO.setPages((int) result.getPages());

        return pageDTO;
    }

    @Override
    public List<ShopStatsDTO> getShopStats() {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);

        // 查询该用户的所有订单
        LambdaQueryWrapper<Bills> wrapper = new LambdaQueryWrapper<Bills>()
                .eq(Bills::getUserId, userId)
                .orderByDesc(Bills::getCreatedAt);

        List<Bills> bills = billMapper.selectList(wrapper);

        // 按平台分组统计
        return bills.stream()
                .collect(Collectors.groupingBy(Bills::getPlatform))
                .entrySet()
                .stream()
                .map(entry -> {
                    List<Bills> platformBills = entry.getValue();
                    return ShopStatsDTO.builder()
                            .platform(entry.getKey())
                            .orderCount((long) platformBills.size())
                            .totalAmount(platformBills.stream()
                                    .map(Bills::getPrice)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add))
                            .totalDuration(platformBills.stream()
                                    .mapToDouble(Bills::getDuration)
                                    .sum())
                            .build();
                })
                .sorted((a, b) -> b.getOrderCount().compareTo(a.getOrderCount())) // 按订单数量降序
                .collect(Collectors.toList());
    }

    @Override
    public BillDTO createBill(CreateBillDTO createBillDTO) {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);

        Bills bill = BeanUtil.toBean(createBillDTO, Bills.class, BILL_COPY_OPTIONS);
        
        // 设置额外字段
        bill.setUserId(userId);
        bill.setSettled(false);
        LocalDateTime now = LocalDateTime.now();
        bill.setCreatedAt(now);
        bill.setUpdatedAt(now);

        billMapper.insert(bill);
        return BeanUtil.toBean(bill, BillDTO.class, DTO_COPY_OPTIONS);
    }

    @Override
    public BillDTO updateBill(Long id, CreateBillDTO updateBillDTO) {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);

        Bills existingBill = billMapper.selectOne(
            new LambdaQueryWrapper<Bills>()
                .eq(Bills::getId, id)
                .eq(Bills::getUserId, userId)
        );

        if (existingBill == null) {
            throw new BusinessException("订单不存在或无权修改");
        }

        if (existingBill.getSettled()) {
            throw new BusinessException("已结算订单不能修改");
        }

        BeanUtil.copyProperties(updateBillDTO, existingBill, BILL_COPY_OPTIONS);
        existingBill.setUpdatedAt(LocalDateTime.now());

        billMapper.updateById(existingBill);
        return BeanUtil.toBean(existingBill, BillDTO.class, DTO_COPY_OPTIONS);
    }

    @Override
    public void deleteBill(Long id) {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);

        Bills bill = billMapper.selectOne(
            new LambdaQueryWrapper<Bills>()
                .eq(Bills::getId, id)
                .eq(Bills::getUserId, userId)
        );

        if (bill == null) {
            throw new BusinessException("订单不存在或无权删除");
        }

        if (bill.getSettled()) {
            throw new BusinessException("已结算订单不能删除");
        }

        billMapper.deleteById(id);
    }

    @Override
    public void settleBill(Long id) {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);

        Bills bill = billMapper.selectOne(
            new LambdaQueryWrapper<Bills>()
                .eq(Bills::getId, id)
                .eq(Bills::getUserId, userId)
        );

        if (bill == null) {
            throw new BusinessException("订单不存在或无权操作");
        }

        if (bill.getSettled()) {
            throw new BusinessException("订单已结算");
        }

        bill.setSettled(true);
        bill.setUpdatedAt(LocalDateTime.now());
        billMapper.updateById(bill);
    }

    @Override
    public List<RankGroupDTO> getUnsettledOrdersByRank(Integer platformId) {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);

        // 查询未结算订单
        List<Bills> bills = billMapper.selectList(
            new LambdaQueryWrapper<Bills>()
                .eq(Bills::getUserId, userId)
                .eq(Bills::getPlatform, platformId)
                .eq(Bills::getSettled, false)
                .orderByDesc(Bills::getCreatedAt)
        );

        // 按段位分组
        return bills.stream()
                .collect(Collectors.groupingBy(Bills::getRanked))
                .entrySet()
                .stream()
                .map(entry -> {
                    List<Bills> rankBills = entry.getValue();
                    return RankGroupDTO.builder()
                            .rank(entry.getKey())
                            .totalAmount(rankBills.stream()
                                    .map(Bills::getPrice)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add))
                            .totalDuration(rankBills.stream()
                                    .mapToDouble(Bills::getDuration)
                                    .sum())
                            .build();
                })
                .sorted((a, b) -> {
                    // 空段位排在最后
                    if (a.getRank() == null) return 1;
                    if (b.getRank() == null) return -1;
                    return a.getRank().compareTo(b.getRank());
                })
                .collect(Collectors.toList());
    }

    private OrderDetailDTO convertToDTO(Bills bill) {
        return BeanUtil.toBean(bill, OrderDetailDTO.class, DTO_COPY_OPTIONS);
    }

    private BillDTO convertToBillDTO(Bills bill) {
        return BeanUtil.toBean(bill, BillDTO.class, DTO_COPY_OPTIONS);
    }
} 