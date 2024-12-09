package com.sky.controller.admin;

import com.sky.dto.ReportTimeDTO;
import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.service.WorkspaceService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController("adminReportController")
@RequestMapping("/admin/report")
@Slf4j
public class ReportController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private WorkspaceService workspaceService;

    /**
     * 营业额统计
     *
     * @param reportTimeDTO
     * @return
     */
    @GetMapping("/turnoverStatistics")
    public Result<TurnoverReportVO> turnoverReport(ReportTimeDTO reportTimeDTO) {
        log.info("营业额统计");
        TurnoverReportVO turnoverReportVO = reportService.turnoverReport(reportTimeDTO);
        return Result.success(turnoverReportVO);
    }

    /**
     * 用户统计
     *
     * @param reportTimeDTO
     * @return
     */
    @GetMapping("/userStatistics")
    public Result<UserReportVO> userReport(ReportTimeDTO reportTimeDTO) {
        log.info("用户统计");
        UserReportVO userReportVO = reportService.userReport(reportTimeDTO);
        return Result.success(userReportVO);
    }

    /**
     * 订单统计
     *
     * @param reportTimeDTO
     * @return
     */
    @GetMapping("/ordersStatistics")
    public Result<OrderReportVO> orderReport(ReportTimeDTO reportTimeDTO) {
        log.info("订单统计");
        OrderReportVO orderReportVO = reportService.orderReport(reportTimeDTO);
        return Result.success(orderReportVO);
    }

    /**
     * Top10
     *
     * @param reportTimeDTO
     * @return
     */
    @GetMapping("/top10")
    public Result<SalesTop10ReportVO> top10Report(ReportTimeDTO reportTimeDTO) {
        log.info("Top10");
        SalesTop10ReportVO salesTop10ReportVO = reportService.top10Report(reportTimeDTO);
        return Result.success(salesTop10ReportVO);
    }

    /**
     * 导出运营数据报表
     *
     * @param response
     */
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) {
        reportService.exportExcel(response);
    }
}
