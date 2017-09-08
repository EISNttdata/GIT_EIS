package com.dell.dims.Utils;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.GroupActivity;
import com.dell.dims.gop.GopNode;
import com.dell.dims.gop.ProcessDefinition;
import com.dell.dims.service.DimsServiceImpl;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.context.ApplicationContext;

import java.io.*;
import java.util.List;

//import com.dell.dims.endpoint.uri.DefaultEndpoint;

/**
 * Created by Kriti_Kanodia on 1/30/2017.
 */
public class PoiWrite {

    // private static final String FILE_NAME = "/MyFirstExcel.xlsx";

    private static ApplicationContext ctx;
    private static boolean updatexls = false;
    private static InputStream is;
    static int sourceGraphRowNum = 1;
    static int endpopintRowNum = 10;
    static int colNum = 0;
    private static XSSFRow row;
    private static final String FILE_FIRST_PROCESS ="Dims_template.xlsm";
    private static final String FILE_OTHER_PROCESS = "./out.xlsm";
    public static void RendertoXls(ProcessDefinition pd) throws IOException {

      if (updatexls == false) {
            is = DimsServiceImpl.ctx.getResource(FILE_FIRST_PROCESS).getInputStream();

        } else {

            is = new FileInputStream(new File(FILE_OTHER_PROCESS));
        }


        XSSFWorkbook workbook = new XSSFWorkbook(is);

        XSSFSheet graphSheet = workbook.getSheet("SOURCE ROUTE GRAPH");
        row = graphSheet.createRow(sourceGraphRowNum);
        sourceGraphRowNum = sourceGraphRowNum + 2;
        Cell cell = row.createCell(colNum);
        cell.setCellValue(pd.getRoutegraph());

        XSSFSheet endpointSheet = workbook.getSheet("ENDPOINTS");

        for (GopNode node : pd.getNodes()) {

            if (node instanceof GroupActivity) {
                GroupActivity groupActivity = (GroupActivity) node;
                if (groupActivity.getActivities().size() > 0) {
                    List<Activity> listActivity = groupActivity.getActivities();
                    for (Activity activity : listActivity) {
                        row = endpointSheet.createRow(endpopintRowNum);
                        row.setHeightInPoints(20);

                        XSSFCellStyle style = workbook.createCellStyle();
                        style.setBorderBottom(CellStyle.BORDER_THIN);
                        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
                        style.setBorderRight(CellStyle.BORDER_THIN);
                        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
                        style.setBorderTop(CellStyle.BORDER_THIN);
                        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
                        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(197, 217, 241)));
                        style.setFillPattern(CellStyle.SOLID_FOREGROUND);

                        for (int rowCol = 0; rowCol < 4; rowCol++) {

                            Cell currentCell = row.createCell(rowCol);

                            if (rowCol == 0) {
                                currentCell.setCellValue(activity.getName());
                                currentCell.setCellStyle(style);
                            } else if (rowCol == 1) {
                                currentCell.setCellValue("file");
                                currentCell.setCellStyle(style);
                            } else if (rowCol == 2) {
                                currentCell.setCellValue(activity.getType().toString());
                                currentCell.setCellStyle(style);
                            }
                        }
                        endpopintRowNum++;
                    }

                }
            } else if (node instanceof Activity) {

                Activity activity = (Activity) node;

                row = endpointSheet.createRow(endpopintRowNum);
                row.setHeightInPoints(20);

                for (int rowCol = 0; rowCol < 4; rowCol++) {

                    Cell currentCell = row.createCell(rowCol);

                    XSSFCellStyle style = workbook.createCellStyle();
                    style.setBorderBottom(CellStyle.BORDER_THIN);
                    style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
                    style.setBorderRight(CellStyle.BORDER_THIN);
                    style.setRightBorderColor(IndexedColors.BLACK.getIndex());
                    style.setBorderTop(CellStyle.BORDER_THIN);
                    style.setTopBorderColor(IndexedColors.BLACK.getIndex());
                    style.setFillForegroundColor(new XSSFColor(new java.awt.Color(197, 217, 241)));
                    style.setFillPattern(CellStyle.SOLID_FOREGROUND);

                    if (rowCol == 0) {
                        currentCell.setCellValue(activity.getName());
                        currentCell.setCellStyle(style);
                    } else if (rowCol == 1) {
                        currentCell.setCellValue("file");
                        currentCell.setCellStyle(style);
                    } else if (rowCol == 2) {
                        currentCell.setCellValue(activity.getType().toString());
                        currentCell.setCellStyle(style);
                    }
                }
                endpopintRowNum++;
            }
        }
        try {
            is.close();
            FileOutputStream os = new FileOutputStream("out.xlsm");
            workbook.write(os);
            workbook.close();
            updatexls = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
