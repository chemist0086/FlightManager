package com.mywork.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.mywork.dao.FlightDao;

public class ExportExcelForFlightAction {
	private InputStream is;
	private String fileName;
	
	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String execute() throws IOException{
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("航班");
		
		// 在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((short)0);  
        //设置首行表头信息
        HSSFCell cell = row.createCell((short) 0);  
        cell.setCellValue("ID");  
        cell = row.createCell((short) 1);  
        cell.setCellValue("出发城市");  
        cell = row.createCell((short) 2);  
        cell.setCellValue("目的城市");
        cell = row.createCell((short) 3);  
        cell.setCellValue("航班日期");
        cell = row.createCell((short) 4);  
        cell.setCellValue("出发时间");
        cell = row.createCell((short) 5);  
        cell.setCellValue("到达时间");
        
        
        FlightDao flightdao = new FlightDao();
        flightdao.openDB();
        String sql = "select flight_id,dep_city,arr_city,"
        		+ " CONVERT(varchar(10),flight_date,120) as flight_date,"
        		+ "CONVERT(varchar(20),dep_time,120) as dep_time,"
        		+ "CONVERT(varchar(20),arr_time,120) as arr_time"
        		+ " from t_flight";
		ResultSet rs = flightdao.executeQuery(sql);
		int i=1,columns=0;
		ResultSetMetaData m = null;
		try {
			m = rs.getMetaData();
			columns = m.getColumnCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while(rs.next()){
				row = sheet.createRow(i);
				for(int j=1;j<=columns;j++){
					row.createCell((short) j-1).setCellValue(rs.getString(j));
					sheet.autoSizeColumn((short)i); //调整列宽为自动
				}
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try{  
			String path = ServletActionContext.getServletContext().getRealPath("/Excel");
			File cacheDir = new File(path);//设置目录参数  
		    cacheDir.mkdirs();
			FileOutputStream fout = new FileOutputStream(path+"\\航班.xls");  
            wb.write(fout);  
            fout.close();
            wb.close();
        }  
        catch (Exception e){  
            e.printStackTrace();  
        } 
		fileName = "航班.xls";
		is = ServletActionContext.getServletContext().getResourceAsStream("/Excel/"+fileName);
		//解决下载名称设置为中文时出现的乱码问题，当前"flight.xls"情况无需这样
		byte[] bytes = fileName.getBytes("utf-8");
		fileName = new String(bytes,"ISO-8859-1");
		return "success";
	}
}
