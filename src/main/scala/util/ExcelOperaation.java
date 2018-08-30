package util;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
/*
* 向 xls表中写入数据
*
* */
public class ExcelOperaation {
    public void writeExcel(String path, List<String[]> list, String sheet, String[] title) {
        try {
            // 创建Excel工作薄
            WritableWorkbook wwb = null;
            // 新建立一个jxl文件
            OutputStream os = new FileOutputStream(path,true);
            wwb = Workbook.createWorkbook(os);
            // 添加第一个工作表并设置第一个Sheet的名字
            WritableSheet sheets = wwb.createSheet(sheet, 1);
         /*   sheets.mergeCells(0, 0, 1, 0);
            Label label;
            for (int i = 0; i < title.length; i++) {
                // Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
                // 在Label对象的子对象中指明单元格的位置和内容
                // label = new Label(i, 0, title[i]);
                label = new Label(i, 0, title[i], getHeader());
                // 设置列宽
                sheets.setColumnView(i, 20);
                // sheets.setColumnView(4, 100);
                // 将定义好的单元格添加到工作表中
                sheets.addCell(label);
            }*/

          /*  // 设置单元格属性
            WritableCellFormat wc = new WritableCellFormat();

            // 设置居中
            wc.setAlignment(Alignment.CENTRE);
            // 设置边框线
            wc.setBorder(Border.ALL, BorderLineStyle.THIN);

            for (int i = 0; i < list.size(); i++) {
                String[] arrData = list.get(i);
                for (int j = 0; j < arrData.length; j++) {
                    // 向特定单元格写入数据
                    // sheets.setColumnView(j, 20);
                    label = new Label(j, 1 + i, arrData[j], wc);
                    sheets.addCell(label);
                }
            }*/
            // 写入数据
            wwb.write();
            // 关闭文件
            wwb.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
    public static WritableCellFormat getHeader() {
        // 定义字体
        WritableFont font = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD);
        try {
            // 黑色字体
            font.setColour(jxl.format.Colour.BLACK);
        }
        catch (WriteException e1) {
            e1.printStackTrace();
        }
        WritableCellFormat format = new WritableCellFormat(font);
        try {
            // 左右居中
            format.setAlignment(jxl.format.Alignment.CENTRE);
            // 上下居中
            format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
            // 黑色边框
            format.setBorder(Border.ALL, BorderLineStyle.THIN, jxl.format.Colour.BLACK);
            // 黄色背景
            format.setBackground(jxl.format.Colour.YELLOW);
        }
        catch (WriteException e) {
            e.printStackTrace();
        }
        return format;
    }


    public static void createExcel(Map<String, Object> map, OutputStream os,int i,String sheetName ) throws WriteException,IOException {



        int n = 0;

        int m = 0;

        //创建工作薄

        WritableWorkbook workbook = Workbook.createWorkbook(os);



        //创建新的一页

        WritableSheet sheet = workbook.createSheet(sheetName,0);





        //创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容



        for (String key : map.keySet()) {

            Label dk = new Label(n,0,key);

            sheet.addCell(dk);

            n++;



        }



        for (Object value : map.values()) {

            Label dk = new Label(m,1,value.toString());

            sheet.addCell(dk);

            m++;

        }





        //把创建的内容写入到输出流中，并关闭输出流

        workbook.write();

        workbook.close();

        os.close();

    }


    //向excel中追加数据.@excelPath:excel所在路径,list:数据集合.(第2,3,4,5,6.....次写入

    public synchronized static void addExcel(String excelPath,List<Object> list ,int sheetIndex) throws IOException{
        FileInputStream fs = new FileInputStream(excelPath);//获取excel
        POIFSFileSystem ps = new POIFSFileSystem(fs);//获取excel信息
        HSSFWorkbook wb = new HSSFWorkbook(ps);
        HSSFSheet sheet = wb.getSheetAt(sheetIndex);//获取工作表
        HSSFRow row = sheet.getRow(0);//获取第一行(即:字段列头,便于赋值)
        System.out.println(sheet.getLastRowNum()+"空"+row.getLastCellNum());//分别得到最后一行行号,和一条记录的最后一个单元格
        FileOutputStream out = new FileOutputStream(excelPath);//向excel中添加数据
        row = sheet.createRow(sheet.getLastRowNum()+1);//在现有行号后追加数据
        for (int i = 0; i < list.size(); i++) {
            String str = String.valueOf(list.get(i));
            row.createCell(i).setCellValue(str);//设置单元格的数据
        }
        out.flush();
        wb.write(out);
        wb.close();
    }

    public static void main(String args[]) throws IOException, WriteException {
        HashMap<String, Object> map = new HashMap<>();
        OutputStream os = new FileOutputStream("e://test.xls",true);
        map.put("1","");
        map.put("2","");
       // ExcelOperaation.createExcel(map,os);
        ArrayList<Object> objects = new ArrayList<>();
        objects.add("a");
        objects.add("b");
        ExcelOperaation.addExcel("e://test.xls",objects,0);
    }
}