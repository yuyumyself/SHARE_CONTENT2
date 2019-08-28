package com.hecaigui.EAS.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStream;  
import java.util.ArrayList;  
import java.util.List;

//import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
//import org.springframework.web.multipart.MultipartFile;  
/** 
 * excel读写工具类 
 * 该工具类主要用来处理从客户端上传到服务器的Excel文件。
 * 若需要处理本地的文件，只要把方法getWorkbook做一定修改即可，readExcel方法的形参的类型也需要修改。 
 */  
public class POIUtils {  
    //private static Logger logger  = Logger.getLogger(POIUtil.class);  
    private final static String xls = "xls";  
    private final static String xlsx = "xlsx";  
      
    /** 
     * 读入excel文件，解析后返回 
     * @param file 
     * @throws IOException  
     */  
    public static List<String[]> readExcel(File file,String fileName) throws IOException{  
        //检查文件  
        checkFile(file,fileName);  
        //获得Workbook工作薄对象  
        Workbook workbook = getWorkBook(file,fileName);  
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回  
        List<String[]> list = new ArrayList<String[]>();  
        if(workbook != null){  
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){  
                //获得当前sheet工作表  
                Sheet sheet = workbook.getSheetAt(sheetNum);  
                if(sheet == null){  
                    continue;  
                }  
                //获得当前sheet的开始行  
                int firstRowNum  = sheet.getFirstRowNum();  
                //获得当前sheet的结束行  
                int lastRowNum = sheet.getLastRowNum();  
                //从第一行开始遍历  
                for(int rowNum = firstRowNum;rowNum <= lastRowNum;rowNum++){  
                    //获得当前行  
                    Row row = sheet.getRow(rowNum);  
                    if(row == null){  
                        continue;  
                    }  
                    //获得当前行的开始列  
                    int firstCellNum = row.getFirstCellNum();  
                    //获得当前行的列数  
                    int lastCellNum = row.getPhysicalNumberOfCells();  
                    String[] cells = new String[row.getPhysicalNumberOfCells()];  
                    //循环当前行  
                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){  
                        Cell cell = row.getCell(cellNum);  
                        cells[cellNum] = getCellValue(cell);  
                    }  
                    list.add(cells);  
                }  
            }  
            workbook.close();  
        }  
        return list;  
    }
    public static void checkFile(File file,String fileName) throws IOException{  
        //判断文件是否存在  
        if(null == file){  
            //logger.error("文件不存在！");
        	
            throw new FileNotFoundException("文件不存在！");  
        }  
       
        //判断文件是否是excel文件  
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){  
            //logger.error(fileName + "不是excel文件");  
            throw new IOException(fileName + "不是excel文件");  
        }  
    }  
	
    public static Workbook getWorkBook(File file,String fileName) {  
       
        //创建Workbook工作薄对象，表示整个excel  
        Workbook workbook = null;  
        try {  
            //获取excel文件的io流  
            InputStream is = new FileInputStream(file);
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象  
            if(fileName.endsWith(xls)){  
                //2003  
                workbook = new HSSFWorkbook(is);  
            }else if(fileName.endsWith(xlsx)){  
                //2007  
                workbook = new XSSFWorkbook(is);  
            }  
        } catch (IOException e) {  
            //logger.info(e.getMessage());  
        }  
        return workbook;  
    }  
    public static String getCellValue(Cell cell){  
        String cellValue = "";  
        if(cell == null){  
            return cellValue;  
        }  
        
        //把数字当成String来读，避免出现1读成1.0的情况  
        if(cell.getCellType() == CellType.NUMERIC){
            cell.setCellType(CellType.STRING);  
        }  
        //判断数据的类型  
        switch (cell.getCellType()){  
            case NUMERIC: //数字  
                cellValue = String.valueOf(cell.getNumericCellValue());  
                break; 
            case STRING: //字符串  
                cellValue = String.valueOf(cell.getStringCellValue());  
                break;  
            case BOOLEAN: //Boolean  
                cellValue = String.valueOf(cell.getBooleanCellValue());  
                break;  
            case FORMULA: //公式  
                cellValue = String.valueOf(cell.getCellFormula());  
                break;  
            case BLANK: //空值   
                cellValue = "";  
                break;  
            case ERROR: //故障  
                cellValue = "非法字符";  
                break;  
            default:  
                cellValue = "未知类型";  
                break;  
        }  
        return cellValue;  
    }  
}