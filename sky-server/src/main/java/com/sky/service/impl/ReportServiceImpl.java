package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.ReportTimeDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.service.WorkspaceService;
import com.sky.vo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
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
    @Autowired
    private WorkspaceService workspaceService;

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
        List<Integer> newUserList = new ArrayList<>();
        List<Integer> totalUserList = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Integer newUserNum = userMapper.countByCreateTime(beginTime, endTime);
            Integer totalUserNum = userMapper.countByCreateTime(null, endTime);
            newUserList.add(newUserNum == null ? 0 : newUserNum);
            totalUserList.add(totalUserNum == null ? 0 : totalUserNum);
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
        Double orderCompletionRate = totalOrderCount == 0 ? 0.0 : validOrderCount.doubleValue() / totalOrderCount;
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

    /**
     * 导出运营数据报表
     *
     * @param response
     */
    @Override
    public void exportExcel(HttpServletResponse response) {
        LocalDate begin = LocalDate.now().minusDays(30);
        LocalDate end = LocalDate.now().minusDays(1);
        BusinessDataVO businessDate = workspaceService.getBusinessDate(LocalDateTime.of(begin, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX));
        InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("template/运营数据报表模板.xlsx");
        try {
            XSSFWorkbook excel = new XSSFWorkbook(inputStream);
            XSSFSheet sheet1 = excel.getSheet("sheet1");
            sheet1.getRow(1).getCell(1).setCellValue(begin + "至" + end);
            XSSFRow row = sheet1.getRow(3);
            row.getCell(2).setCellValue(businessDate.getTurnover());
            row.getCell(4).setCellValue(businessDate.getOrderCompletionRate());
            row.getCell(6).setCellValue(businessDate.getNewUsers());
            row = sheet1.getRow(4);
            row.getCell(2).setCellValue(businessDate.getValidOrderCount());
            row.getCell(4).setCellValue(businessDate.getUnitPrice());
            for (int i = 0; i < 30; i++) {
                LocalDate date = begin.plusDays(i);
                BusinessDataVO dateBusinessDate = workspaceService.getBusinessDate(LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));
                row = sheet1.getRow(7 + i);
                row.getCell(1).setCellValue(String.valueOf(date));
                row.getCell(2).setCellValue(dateBusinessDate.getTurnover());
                row.getCell(3).setCellValue(dateBusinessDate.getValidOrderCount());
                row.getCell(4).setCellValue(dateBusinessDate.getOrderCompletionRate());
                row.getCell(5).setCellValue(dateBusinessDate.getUnitPrice());
                row.getCell(6).setCellValue(dateBusinessDate.getNewUsers());
            }
            ServletOutputStream outputStream = response.getOutputStream();
            excel.write(outputStream);
            outputStream.close();
            excel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
