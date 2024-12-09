package com.sky.service;

import com.sky.dto.ReportTimeDTO;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import javax.servlet.http.HttpServletResponse;

public interface ReportService {
    /**
     * 营业额统计
     *
     * @param reportTimeDTO
     * @return
     */
    TurnoverReportVO turnoverReport(ReportTimeDTO reportTimeDTO);

    /**
     * 用户统计
     *
     * @param reportTimeDTO
     * @return
     */
    UserReportVO userReport(ReportTimeDTO reportTimeDTO);

    /**
     * 订单统计
     *
     * @param reportTimeDTO
     * @return
     */
    OrderReportVO orderReport(ReportTimeDTO reportTimeDTO);

    /**
     * Top10
     *
     * @param reportTimeDTO
     * @return
     */
    SalesTop10ReportVO top10Report(ReportTimeDTO reportTimeDTO);

    /**
     * 导出运营数据报表
     *
     * @param response
     */
    void exportExcel(HttpServletResponse response);
}
