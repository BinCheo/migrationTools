package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellType;
//import org.apache.poi.ss.usermodel.Row;


public class Application {
	private static int count = 0;
	final private static String comment = "";
	
	public static void main(String[] args) throws IOException {
		//path folder
		final File folder = new File("");
		
		//loop file in folder
		listFilesForFolder(folder);
		
		System.out.println("Number of pattern:" + count);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	private static void addComment(File f) {

		try {
			File ftemp = new File(f.getAbsolutePath() + "_temp");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(ftemp);
			BufferedWriter bw = new BufferedWriter(fw);
			String line;
			while ((line = br.readLine()) != null) {
				bw.write(line);
				break;
			}
			while ((line = br.readLine()) != null) {
				bw.newLine();
				
//				//Upper to lowercase
//				if ( (line.contains("ＩＤ") || line.contains("ＦＡＸ"))  && (line.contains("\");") || line.contains("\"));")) && !line.contains("//")  ){
//					bw.write(comment);
//					count += 1;
//				}
				
//				//(+) left join
//				if ( line.contains("(+)") && !line.contains("//")  ){
//					bw.write("comment");
//					count += 1;
//				}
				
//				//nvl() -> coalesce 
//				if ( isInsensitiveContains("nvl", line) && !line.contains("//")  ){
//					bw.write(comment);
//					count += 1;
//				}
				
				
				bw.write(line);
			}
			if (isLastLineNull(f)) {
				bw.newLine();
			}
			bw.close();
			br.close();
			fw.close();
			fr.close();
			f.delete();
			boolean successful = ftemp.renameTo(f);
			System.out.println(successful + ": " +ftemp.getName());

		} catch (Exception e) {
			System.out.println("Loi ghi file: " + e);
		}
	}

	private static boolean isInsensitiveContains(String wantedStr, String source) {
		boolean rs = false;
		rs = Pattern.compile(Pattern.quote(wantedStr), Pattern.CASE_INSENSITIVE).matcher(source).find();
		return rs;
	}

	public static void listFilesForFolder(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				if (getFileExtension(fileEntry).equalsIgnoreCase(".java")) {
					addComment(fileEntry);
				}

			}
		}
	}

	private static String getFileExtension(File file) {
		String extension = "";
		try {
			if (file != null && file.exists()) {
				String name = file.getName();
				extension = name.substring(name.lastIndexOf("."));
			}
		} catch (Exception e) {
			extension = "";
		}
		return extension;
	}
	
	private static boolean isLastLineNull (File filename){
		boolean rs = false;
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(filename, "r");
			byte b;
			long length = randomAccessFile.length() ;
			if (length != 0) {
					length -= 1;
			        randomAccessFile.seek(length);
			        b = randomAccessFile.readByte();
			        if(b == 10) {
			        	rs = true;
			        }
			        randomAccessFile.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
//	private static void createExcel() throws IOException {
//		HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet("Employees sheet");
//        
//        int rownum = 0;
//        Cell cell;
//        Row row;
//        
//        row = sheet.createRow(rownum);
//        cell = row.createCell(0, CellType.STRING);
//        cell.setCellValue("PROJECT");
//        
//        cell = row.createCell(1, CellType.STRING);
//        cell.setCellValue("FUNCTION");
//        
//        cell = row.createCell(2, CellType.STRING);
//        cell.setCellValue("FILE");
//        
//        cell = row.createCell(3, CellType.STRING);
//        cell.setCellValue("PIC");
//        
//        cell = row.createCell(4, CellType.STRING);
//        cell.setCellValue("TEST");
//        
//        cell = row.createCell(5, CellType.STRING);
//        cell.setCellValue("REVIEW");
//        
//     // Data
//        for (Employee emp : list) {
//            rownum++;
//            row = sheet.createRow(rownum);
// 
//            // EmpNo (A)
//            cell = row.createCell(0, CellType.STRING);
//            cell.setCellValue(emp.getEmpNo());
//            // EmpName (B)
//            cell = row.createCell(1, CellType.STRING);
//            cell.setCellValue(emp.getEmpName());
//            // Salary (C)
//            cell = row.createCell(2, CellType.STRING);
//            cell.setCellValue(emp.getSalary());
//            // Grade (D)
//            cell = row.createCell(3, CellType.STRING);
//            cell.setCellValue(emp.getGrade());
//            // Bonus (E)
//            String formula = "0.1*C" + (rownum + 1) + "*D" + (rownum + 1);
//            cell = row.createCell(4, CellType.FORMULA);
//            cell.setCellFormula(formula);
//        }
//        
//        
//        File file = new File("C:/Users/thanhbinh truong/Documents/Project/Getti/employee.xls");
//        file.getParentFile().mkdirs();
//        FileOutputStream outFile = new FileOutputStream(file);
//        workbook.write(outFile);
//        System.out.println("Created file: " + file.getAbsolutePath());
//        
//	}
}