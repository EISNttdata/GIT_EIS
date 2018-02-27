package com.dell.dims.Builder.Utils;

import com.dell.dims.service.DimsServiceImpl;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Kriti_Kanodia on 6/8/2017.
 */
public class ReadMappingXls {

   // private InputStream is;
   // private static final String INPUT_FILE = "./TibcoOracleSOAMappings.xlsx";
   private final String INPUT_FILE = "TibcoOracleSOAMappings.xlsx";
    //multivalue map key : TibcoAdapterType => "activityType Type[classType]", value : TibcoAdapterProperties
    MultiValueMap multiValueMapTibcoAdapter = new MultiValueMap();
    Map<TibcoAdapterProperties,SoaAdapterProperties> attributesMap = new LinkedHashMap<>();

    public Map<TibcoAdapterProperties, SoaAdapterProperties> getAttributesMap() {
        return attributesMap;
    }

    public void setAttributesMap(Map<TibcoAdapterProperties, SoaAdapterProperties> attributesMap) {
        this.attributesMap = attributesMap;
    }

    public MultiValueMap getMultiValueMapTibcoAdapter() {
        return multiValueMapTibcoAdapter;
    }

    public void setMultiValueMapTibcoAdapter(MultiValueMap multiValueMapTibcoAdapter) {
        this.multiValueMapTibcoAdapter = multiValueMapTibcoAdapter;
    }

    //public static Map<List<TibcoAdapterProperties>,List<SoaAdapterProperties>> readExcelSheet(String excelFilePath) throws IOException {
    public ReadMappingXls readExcelSheet() throws IOException {

        InputStream  is = DimsServiceImpl.ctx.getResource(INPUT_FILE).getInputStream();
       // is = new FileInputStream(new File(INPUT_FILE));

        XSSFWorkbook workbook = new XSSFWorkbook(is);
        XSSFSheet sheet = workbook.getSheet("Properties Mapping  Tibco2SOA");

        Iterator<Row> iterator = sheet.iterator();

        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            TibcoAdapterType tibcoAdapterType=new TibcoAdapterType();
            TibcoAdapterProperties tibcoAdapterProperties = new TibcoAdapterProperties();
            SoaAdapterProperties soaAdapterProperties = new SoaAdapterProperties();

            if (nextRow.getRowNum() >= 1 && nextRow.getRowNum()<= sheet.getLastRowNum()) {

                Iterator<Cell> cellIterator = nextRow.cellIterator();


                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();
                    int columnIndex = nextCell.getColumnIndex();

                    switch (columnIndex) {
                        case 0:
                            tibcoAdapterProperties.setActivityType((String) getCellValue(nextCell));
                            break;
                        case 1:
                            tibcoAdapterProperties.setClassType((String) getCellValue(nextCell));
                            break;
                        case 2:
                            tibcoAdapterProperties.setRootName((String) getCellValue(nextCell));
                            break;
                        case 3:
                            tibcoAdapterProperties.setProperty((String)getCellValue(nextCell));
                            break;
                        case 4:
                            tibcoAdapterProperties.setType((String) getCellValue(nextCell));
                            break;
                        case 5:
                            soaAdapterProperties.setInteractionSpec((String) getCellValue(nextCell));
                            break;
                        case 6:
                            soaAdapterProperties.setOperation((String) getCellValue(nextCell));
                            break;
                        case 7:
                            soaAdapterProperties.setProperty((String) getCellValue(nextCell));
                            break;
                        case 8:
                            soaAdapterProperties.setRootName((String) getCellValue(nextCell));
                            break;
                    }
                }

                //set tibcoAdapterType properties
                tibcoAdapterType.setActivityType(tibcoAdapterProperties.getActivityType());
                tibcoAdapterType.setClassType(tibcoAdapterProperties.getType());

                //set multivalue map using key as "activityType Type[classType]"  and value as TibcoAdapterProperties
                multiValueMapTibcoAdapter.put(tibcoAdapterProperties.getActivityType()+" TYPE["+tibcoAdapterProperties.getClassType()+"]",tibcoAdapterProperties);

                //set Tibco and Soa attributes mapper map
                attributesMap.put(tibcoAdapterProperties,soaAdapterProperties);

            }
        }

        setAttributesMap(attributesMap);
        setMultiValueMapTibcoAdapter(multiValueMapTibcoAdapter);

        is.close();
        workbook.close();
        return this;
    }


    private static Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
        }
        return null;
    }

    /**@MM 3/10/2017
     * getSoa properties corresponding to TibcoAdapterType
     * @param tibcoAdapter //e.g. //com.tibco.plugin.file.FileRenameActivity TYPE[Config]
     * @param mapperMap
     * @return
     *
     */
   public List<SoaAdapterProperties> getSOAObject(Object tibcoAdapter ,Map<TibcoAdapterProperties,SoaAdapterProperties> mapperMap)
    {
        List<SoaAdapterProperties> listSoaAdapterProperties=new ArrayList<>();
        List tibcoAdapterPropList = (ArrayList) multiValueMapTibcoAdapter.get(tibcoAdapter);

        System.out.println("Activity type *********:::"+tibcoAdapter.toString());
        for(int i=0;i<tibcoAdapterPropList.size();i++)
        {
            TibcoAdapterProperties tibocAdapterProp= (TibcoAdapterProperties) tibcoAdapterPropList.get(i);

            System.out.println("-------Tibco------Properties---------"
                    +"\nRootName::: "+tibocAdapterProp.getRootName()
                    +"\nProperty:::" +tibocAdapterProp.getProperty()
                    +"\nPropertyType :::"+tibocAdapterProp.getType());

            // GET the corresponding Oracle SOA object
            SoaAdapterProperties soaObject = mapperMap.get(tibocAdapterProp);
            listSoaAdapterProperties.add(soaObject);

            System.out.println("\n:::::Corresponding SOA attributes:::");
            System.out.println("InteractionSpec:::"+soaObject.getInteractionSpec()
                    +"\nOPERATION:::"+soaObject.getOperation()
                    +"\nPROPERTY :::"+soaObject.getProperty()
                    +"\nROOTNAME :::"+ soaObject.getRootName());
        }
        return listSoaAdapterProperties;
    }
  }
