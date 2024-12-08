package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.ReportTimeDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取日期列表
     *
     * @param reportTimeDTO
     * @return
     */
    private static List<LocalDate> getDateList(ReportTimeDTO reportTimeDTO) {
        LocalDate begin = reportTimeDTO.getBegin();
        LocalDate end = reportTimeDTO.getEnd();
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        return dateList;
    }

    /**
     * 营业额统计
     *
     * @param reportTimeDTO
     * @return
     */
    @Override
    public TurnoverReportVO turnoverReport(ReportTimeDTO reportTimeDTO) {
        List<LocalDate> dateList = getDateList(reportTimeDTO);
        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Double turnover = orderMapper.sumByStatusAndOrderTime(Orders.COMPLETED, beginTime, endTime);
            turnoverList.add(turnover == null ? 0.0 : turnover);
        }
        return TurnoverReportVO.builder().dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnoverList, ",")).build();
    }

    /**
     * 用户统计
     *
     * @param reportTimeDTO
     * @return
     */
    @Override
    public UserReportVO userReport(ReportTimeDTO reportTimeDTO) {
        List<LocalDate> dateList = getDateList(reportTimeDTO);
        List<Long> newUserList = new ArrayList<>();
        List<Long> totalUserList = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Long newUserNum = userMapper.countByCreateTime(beginTime, endTime);
            Long totalUserNum = userMapper.countByCreateTime(null, endTime);
            newUserList.add(newUserNum == null ? 0L : newUserNum);
            totalUserList.add(totalUserNum == null ? 0L : totalUserNum);
        }
        return UserReportVO.builder().dateList(StringUtils.join(dateList, ","))
                .newUserList(StringUtils.join(newUserList, ",")).totalUserList(StringUtils.join(totalUserList, ","))
                .build();
    }

    /**
     * 订单统计
     *
     * @param reportTimeDTO
     * @return
     */
    @Override
    public OrderReportVO orderReport(ReportTimeDTO reportTimeDTO) {
        List<LocalDate> dateList = getDateList(reportTimeDTO);
        List<Integer> orderCountList = new ArrayList<>();
        List<Integer> validOrderCountList = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Integer orderCount = orderMapper.countByOrderTime(beginTime, endTime);
            Integer validOrderCount = orderMapper.countByStatusAndOrderTime(Orders.COMPLETED, beginTime, endTime);
            orderCountList.add(orderCount == null ? 0 : orderCount);
            validOrderCountList.add(validOrderCount == null ? 0 : validOrderCount);
        }
        Integer totalOrderCount = orderCountList.stream().reduce(Integer::sum).get();
        Integer validOrderCount = validOrderCountList.stream().reduce(Integer::sum).get();
        Double orderCompletionRate = totalOrderCount == 0 ? 0.0 : validOrderCount / totalOrderCount;
        return OrderReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .orderCountList(StringUtils.join(orderCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .totalOrderCount(totalOrderCount).validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate).build();
    }

    /**
     * Top10
     *
     * @param reportTimeDTO
     * @return
     */
    @Override
    public SalesTop10ReportVO top10Report(ReportTimeDTO reportTimeDTO) {
        LocalDateTime beginTime = LocalDateTime.of(reportTimeDTO.getBegin(), LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(reportTimeDTO.getEnd(), LocalTime.MAX);
        List<GoodsSalesDTO> goodsSalesDTOList = orderMapper.getSalesTop10(beginTime, endTime);
        return SalesTop10ReportVO.builder()
                .nameList(StringUtils.join(goodsSalesDTOList.stream().map(GoodsSalesDTO::getName)
                        .collect(Collectors.toList()), ","))
                .numberList(StringUtils.join(goodsSalesDTOList.stream().map(GoodsSalesDTO::getNumber)
                        .collect(Collectors.toList()), ","))
                .build();
    }
}
