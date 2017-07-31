package cn.zttek.thesis.modules.excel;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @描述: 自定义导出Excel文件的视图
 * @作者: Pengo.Wen
 * @日期: 2016-10-15 18:00
 * @版本: v1.0
 */
public class MyExcelView extends AbstractExcelView {

    private CellStyle headerStyle = null;
    private Font headerFont = null;
    private CellStyle cellStyle = null;
    private Font cellFont = null;

    private String name;
    private String[] titles;
    private List<Object[]> datas;

    public MyExcelView(String name, String[] titles, List<Object[]> datas) {
        this.name = name;
        this.titles = titles;
        this.datas = datas;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        HSSFSheet sheet = workbook.createSheet(name);

        prepareStyle(workbook);

        buildTitles(sheet);

        buildDatas(sheet);

        //对单元格进行自适应宽度
        for (int i = 0; i < titles.length; i++) {
            sheet.autoSizeColumn(i);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        name = name + sdf.format(new Date()) + ".xls";
        //response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + encodeFilename(name, request));
        /*OutputStream ouputStream = response.getOutputStream();
        workbook.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
        workbook.close();*/
    }

    /**
     * 准备Excel表格的标题和单元格样式
     * @param workbook
     */
    private void prepareStyle(HSSFWorkbook workbook) {
        headerFont = workbook.createFont();
        headerFont.setFontName("宋体");
        headerFont.setBold(true);
        headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFont(headerFont);

        cellStyle = workbook.createCellStyle();
        cellFont = workbook.createFont();
        cellFont.setFontName("宋体");
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setFont(cellFont);
    }

    /**
     * 设置标题单元格
     * @param sheet
     */
    private void buildTitles(HSSFSheet sheet) {
        Row row = sheet.createRow(0);
        for (int i = 0; i < titles.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(titles[i]);
        }
    }


    /**
     * 设置数据单元格
     * @param sheet
     */
    private void buildDatas(HSSFSheet sheet) {
        for (int i = 0; i < datas.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Object[] objs = datas.get(i);
            for (int j = 0; j < objs.length; j++) {
                Cell cell = row.createCell(j);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(objs[j] != null ? String.valueOf(objs[j]) : "");
            }
        }
    }

    /**
     * 设置下载文件中文件的名称
     *
     * @param filename
     * @param request
     * @return
     */
    public static String encodeFilename(String filename, HttpServletRequest request) {
        /**
         * 获取客户端浏览器和操作系统信息
         * 在IE浏览器中得到的是：User-Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Maxthon; Alexa Toolbar)
         * 在Firefox中得到的是：User-Agent=Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.7.10) Gecko/20050717 Firefox/1.0.6
         */
        String agent = request.getHeader("USER-AGENT");
        try {
            if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {
                String newFileName = URLEncoder.encode(filename, "UTF-8");
                newFileName = StringUtils.replace(newFileName, "+", "%20");
                if (newFileName.length() > 150) {
                    newFileName = new String(filename.getBytes("GB2312"), "ISO8859-1");
                    newFileName = StringUtils.replace(newFileName, " ", "%20");
                }
                return newFileName;
            }
            if ((agent != null) && (-1 != agent.indexOf("Mozilla")))
                return MimeUtility.encodeText(filename, "UTF-8", "B");

            return filename;
        } catch (Exception ex) {
            return filename;
        }
    }
}
