package com.dell.dims.Model.InterfaceInventoryDetails;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.*;

public class WriteInventory {

    private InputStream is;
    private static final String INPUT_FILE = "InterfaceInventory_Template.xlsm";
    private String UPDATED_FILE = "./Interface_Inventory_Template.xlsm";
    int inventoryRowNum = 1;
    private XSSFRow row;

    public void RendertoXls(Map<String, InterfaceInventory> interfaceInventoryMap, String processName) throws IOException {

       /* is = DimsServiceImpl.ctx.getResource(INPUT_FILE).getInputStream();*/

         is = new FileInputStream(new File(UPDATED_FILE));

         XSSFWorkbook workbook = new XSSFWorkbook(is);

         XSSFSheet sheet = workbook.getSheet("Template");

       /*  Map<String, InterfaceInventory> nameandtypemap = new HashMap();
         for (String key1 : interfaceInventoryMap.keySet()){
            String[] separateNameType = key1.split("#");
            nameandtypemap.put(separateNameType[0],interfaceInventoryMap.get(key1));
         }*/


          for (String key : interfaceInventoryMap.keySet()) {

              Map configMap = interfaceInventoryMap.get(key).getConfigforInventory();
              if(configMap!=null) {
                  for (Object configKey : configMap.keySet())

                  // for(int i=0 ; i < configMap.size() ; i++)
                  {

                      row = sheet.createRow(inventoryRowNum);
                      for (int rowCol = 0; rowCol < 7; rowCol++) {

                          Cell currentCell = row.createCell(rowCol);

                          if (rowCol == 0) {
                              currentCell.setCellValue(interfaceInventoryMap.get(key).getActivityNameforInventory());
                          } else if (rowCol == 1) {
                              currentCell.setCellValue(interfaceInventoryMap.get(key).getActivityTypeforInventory());
                          } else if (rowCol == 2) {

                              if (interfaceInventoryMap.get(key).getConfigforInventory() == null) {

                                  currentCell.setCellValue("");

                              } else {
                                  currentCell.setCellValue(configKey.toString());
                              }


                          } else if (rowCol == 3) {


                              if (interfaceInventoryMap.get(key).getConfigforInventory() == null) {

                                  currentCell.setCellValue("");

                              } else {

                                  currentCell.setCellValue(configMap.get(configKey).toString());
                              }

                          } else if (rowCol == 4) {
                              currentCell.setCellValue(interfaceInventoryMap.get(key).getInputBindingsforInventory());
                          }else if (rowCol == 5) {
                              currentCell.setCellValue(interfaceInventoryMap.get(key).getInputSchema());
                          }else if (rowCol == 6) {
                              currentCell.setCellValue(interfaceInventoryMap.get(key).getOutputSchema());
                          }
                      }
                      inventoryRowNum++;
                  }
              }
              inventoryRowNum++;
          }
        try {
            is.close();
            FileOutputStream os = new FileOutputStream("Interface_Inventory_Template" + processName + ".xlsm");
            workbook.write(os);
            os.close();
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

           /* row = sheet.createRow(inventoryRowNum);
            for (int rowCol = 0; rowCol < 5; rowCol++) {

                Cell currentCell = row.createCell(rowCol);

                if (rowCol == 0) {
                    currentCell.setCellValue(interfaceInventoryMap.get(key).getActivityNameforInventory());
                } else if (rowCol == 1) {
                    currentCell.setCellValue(interfaceInventoryMap.get(key).getActivityTypeforInventory());
                } else if (rowCol == 2) {

                    if (interfaceInventoryMap.get(key).getConfigforInventory() == null) {
                        currentCell.setCellValue(interfaceInventoryMap.get(key).getConfigProperty());
                    } else {
                        CellStyle cs = workbook.createCellStyle();
                        cs.setWrapText(true);
                        currentCell.setCellStyle(cs);

                        //increase row height to accomodate text
                        row.setHeightInPoints((interfaceInventoryMap.get(key).getConfigforInventory().size() * sheet.getDefaultRowHeightInPoints()));

                        //adjust column width to fit the content
                        sheet.autoSizeColumn((short) interfaceInventoryMap.get(key).getConfigforInventory().size());
                        currentCell.setCellValue(interfaceInventoryMap.get(key).getConfigProperty());
                    }
                    // currentCell.setCellValue(interfaceInventoryMap.get(key).getConfigProperty());

                } else if (rowCol == 3) {


                    if (interfaceInventoryMap.get(key).getConfigforInventory() == null) {
                        currentCell.setCellValue(interfaceInventoryMap.get(key).getConfigProperty());
                    } else {
                        CellStyle cs = workbook.createCellStyle();
                        cs.setWrapText(true);
                        currentCell.setCellStyle(cs);

                        //increase row height to accomodate text
                        row.setHeightInPoints((interfaceInventoryMap.get(key).getConfigforInventory().size() * sheet.getDefaultRowHeightInPoints()));

                        //adjust column width to fit the content
                        sheet.autoSizeColumn((short) interfaceInventoryMap.get(key).getConfigforInventory().size());
                        currentCell.setCellValue(interfaceInventoryMap.get(key).getConfigValue());
                    }

                } else if (rowCol == 4) {
                    currentCell.setCellValue(interfaceInventoryMap.get(key).getInputBindingsforInventory());
                }
            }*/

                /*    for (int rowCol = 0; rowCol < 5; rowCol++) {

                        Cell currentCell = row.createCell(rowCol);

                        if (rowCol == 0) {
                            currentCell.setCellValue(inventory.getActivityNameforInventory());
                        } else if (rowCol == 1) {
                            currentCell.setCellValue(inventory.getActivityTypeforInventory());
                        } else if (rowCol == 2) {

                            //to enable newlines you need set a cell styles with wrap=true
                            CellStyle cs = workbook.createCellStyle();
                            cs.setWrapText(true);
                            currentCell.setCellStyle(cs);

                            //increase row height to accomodate text
                            row.setHeightInPoints((inventory.getConfigforInventory().size() * sheet.getDefaultRowHeightInPoints()));

                            //adjust column width to fit the content
                            sheet.autoSizeColumn((short) inventory.getConfigforInventory().size());
                            currentCell.setCellValue(inventory.getConfigProperty());


                        } else if (rowCol == 3) {

                            CellStyle cs = workbook.createCellStyle();
                            cs.setWrapText(true);
                            currentCell.setCellStyle(cs);
                            row.setHeightInPoints((inventory.getConfigforInventory().size() * sheet.getDefaultRowHeightInPoints()));
                            sheet.autoSizeColumn((short) inventory.getConfigforInventory().size());
                            currentCell.setCellValue(inventory.getConfigProperty());
                            currentCell.setCellValue(inventory.getConfigValue());
                        }

            *//*else if (rowCol == 2) {

                for (String key : inventory.getConfigforInventory().keySet()) {

                    currentCell.setCellValue(key);

                    break;
                }

            } else if (rowCol == 3) {

                for (String key : inventory.getConfigforInventory().keySet()) {

                    currentCell.setCellValue(inventory.getConfigforInventory().get(key));

                    break;
                }

            }*//*
                        else if (rowCol == 4) {

                            currentCell.setCellValue(inventory.getInputBindingsforInventory());
              *//*  //wrap text
                CellStyle cs = workbook.createCellStyle();
                cs.setWrapText(true);
                currentCell.setCellStyle(cs);*//*
                        }
                    }
*/
        /*inventoryRowNum++;*/
/*
        if(inventory.getConfigforInventory().size()>1){

            for (int rowCol = 2; rowCol < 4; rowCol++) {

                Cell currentCell = row.createCell(rowCol);

                if (rowCol == 2) {

                    for (String key : inventory.getConfigforInventory().keySet()) {

                        currentCell.setCellValue(key);

                        break;

                    }

                } else if (rowCol == 3) {

                    for (String key : inventory.getConfigforInventory().keySet()) {

                        currentCell.setCellValue(inventory.getConfigforInventory().get(key));

                    }
                }
            }
            inventoryRowNum++;
        }*/


       /* try {
            is.close();
            FileOutputStream os = new FileOutputStream("Interface_Inventory_Template.xlsm");
            workbook.write(os);
            os.close();
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

