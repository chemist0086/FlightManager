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

import com.mywork.dao.DelivererDao;

public class ExportExcelForDelivererlAction {
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
		HSSFSheet sheet = wb.createSheet("送票员");
		
		// 在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((short)0);  
        //设置首行表头信息
        HSSFCell cell = row.createCell((short) 0);  
        cell.setCellValue("ID");  
        cell = row.createCell((short) 1);  
        cell.setCellValue("姓名");  
        cell = row.createCell((short) 2);  
        cell.setCellValue("年龄");
        cell = row.createCell((short) 3);  
        cell.setCellValue("性别");
        cell = row.createCell((short) 4);  
        cell.setCellValue("手机号");
        cell = row.createCell((short) 5);  
        cell.setCellValue("邮箱");
        
        
        DelivererDao delivererdao = new DelivererDao();
        delivererdao.openDB();
        String sql = "select * from t_deliverer";
		ResultSet rs = delivererdao.executeQuery(sql);
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
					if(j==4){//对性别进行进行判断
						if(rs.getString(j).equals("1")){
							row.createCell((short) j-1).setCellValue("男");
						}
						else row.createCell((short) j-1).setCellValue("女");
					}else row.createCell((short) j-1).setCellValue(rs.getString(j));
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
			FileOutputStream fout = new FileOutputStream(path+"\\送票员.xls");  
            wb.write(fout);  
            fout.close();
            wb.close();
        }  
        catch (Exception e){  
            e.printStackTrace();  
        } 
		fileName = "送票员.xls";
		is = ServletActionContext.getServletContext().getResourceAsStream("/Excel/"+fileName);
		//解决下载名称设置为中文时出现的乱码问题，当前"deliverer.xls"情况无需这样
		byte[] bytes = fileName.getBytes("utf-8");
		fileName = new String(bytes,"ISO-8859-1");
		return "success";
	}
}
