package util.sparkutil

import java.io.{File, OutputStream, FileOutputStream, FileInputStream}

import jxl.Workbook
import jxl.write.{Label, WritableSheet, WritableWorkbook}
import org.apache.poi.hssf.usermodel.{HSSFRow, HSSFWorkbook, HSSFSheet}
import org.apache.poi.poifs.filesystem.POIFSFileSystem

import scala.collection.mutable

/**
  * Created by lenovo on 2018/8/30.
  */
object ExcelInsertUtil {
  def addExcel(excelPath: String, list: Array[String], sheetIndex: Int) {
    val fs: FileInputStream = new FileInputStream(excelPath)
    val ps: POIFSFileSystem = new POIFSFileSystem(fs)
    val wb: HSSFWorkbook = new HSSFWorkbook(ps)
    val sheet: HSSFSheet = wb.getSheetAt(sheetIndex)
    var row: HSSFRow = sheet.getRow(0)
    System.out.println(sheet.getLastRowNum + "空" + row.getLastCellNum)
    val out: FileOutputStream = new FileOutputStream(excelPath)
    row = sheet.createRow(sheet.getLastRowNum + 1)
    for (i <- 0 to list.size) {
      val one: String = list(i)
      row.createCell(i).setCellValue(one)
    }
    out.flush
    wb.write(out)
    wb.close
  }

  def createExcel( mutable:scala.collection.mutable.HashMap[String,Array[String]]): Unit ={
    var i=0
    for (elem <- mutable) {
      val stream: FileOutputStream = new FileOutputStream(new File("e://aaa.xls"))
      //创建工作薄
      val key: String = elem._1
      val array: Array[String] = elem._2
      val workbook: WritableWorkbook = Workbook.createWorkbook(stream)
      val sheet: WritableSheet = workbook.createSheet(key, i)
      i=i+1
      val size: Int = array.size
      for (j <- (0 until size)){
        val dk: Label = new Label(j, 0, array(j))
        sheet.addCell(dk)
      }
      workbook.write
      workbook.close
      stream.close
    }
  }

  def main(args: Array[String]): Unit = {
    val stream: FileOutputStream = new FileOutputStream(new File("e://aaa.xls"))
    val stringToStrings: mutable.HashMap[String, Array[String]] = new mutable.HashMap[String,Array[String]]()
    stringToStrings.put("a",Array[String]("时间","任务","PV","UV"))
    stringToStrings.put("b",Array[String]("时间","任务","PV","UV"))
    ExcelInsertUtil.createExcel(stringToStrings)
  }
}
