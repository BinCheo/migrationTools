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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FilenameUtils;


public class Application {
	private static int lowercaseCount = 0;
	private static int leftJoinCount = 0;
	private static int NVLCount = 0;
	private static int sysDateCount = 0;
	private static int dualCount = 0;
	private static int decodeCount = 0;

	//final private static String comment = "'#postgres-cnv(splus)-v5.20 :tool :修正済\n";
	//final private static String comment = "'#postgres-cnv(splus)-v5.20 :tool :確認済\n";
	
	final private static String comment = "'#postgres-cnv(splus)-v5.20 :splus :修正済\n";


	public static void main(String[] args) throws IOException {
		 
		String[] folderStr = new String[] {
				//"D:\\workspace\\Getti\\#Migration\\examplefolder",
				//"D:\\workspace\\Getti\\#Migration\\VB\\examplefolder"	
		};
		
		for (int i = 0; i < folderStr.length; i++) {
			File folder = new File( folderStr[i]);
			listFilesForFolder(folder);
		}
		
		String[] fileStr = new String[] {
				//"D:\\workspace\\Getti\\#Migration\\examplefile.vb",
				//"D:\\workspace\\Getti\\#Migration\\examplefile.vb"
		};
		
		
		for (int i = 0; i < fileStr.length; i++) {
			File file = new File( fileStr[i]);
			extractSQL(file);
		}
		
		
		System.out.println("テーブル名、カラム名が小文字の寄せられる / :" + lowercaseCount);
		System.out.println("外部結合演算子(+) :" + leftJoinCount);
		System.out.println("NVL function:" + NVLCount);
		System.out.println("sysdate function:" + sysDateCount);
		System.out.println("DUAL table:" + dualCount);
		System.out.println("Decode function:" + decodeCount);

		
	}

	private static void replacePattern(File f) {

		try {
			boolean flag = false;
			boolean flag2 = false; // check line next to ORA2SYMFO [TODO]
			File ftemp = new File(f.getAbsolutePath() + "_temp");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(ftemp);
			BufferedWriter bw = new BufferedWriter(fw);
			String line;
			String lineBK = "";
			while ((line = br.readLine()) != null) {
				bw.write(line);
				break;
			}
			while ((line = br.readLine()) != null) {

				if (!flag2)
					bw.newLine();
			
//================================TO_NCHAR => TO_CHAR============================================================
//				if (isInsensitiveContains("to_nchar", line) && line.trim().charAt(0) != '\'') {
//					String tab = getTab(line);
//					bw.write(tab+comment);
//					bw.write(tab + "'" + line.trim() + "\n");
//					line = line.replace("to_nchar", "TO_CHAR");
//					line = line.replace("TO_NCHAR", "TO_CHAR");
//					bw.write(line);
//					flag = true;
//				}
				
				
//==============================================REGEXP_SUBSTR ===================================================
//				if (isInsensitiveContains("CASE WHEN REGEXP_SUBSTR(", line) && line.trim().charAt(0) != '\'') {
//					String tab = getTab(line);
//					// bw.write(tab+comment);
//					bw.write(tab + "'" + line.trim() + "\n");
//
//					Pattern pattern = Pattern.compile("(?<=CASE WHEN REGEXP_SUBSTR\\()[^)]*(?=\\))");
//					Matcher matcher = pattern.matcher(line);
//					String value[];
//					if (matcher.find()) {
//						value = matcher.group(0).split(",");
//						String rep = value[0] + " SIMILAR TO " + value[1];
//						line = line.replaceAll("(REGEXP_SUBSTR\\([^)]*\\))", rep);
//					}
//
//					bw.write(line);
//
//					flag = true;
//				}

//===================================2 BYTE TO 1 BYTE ===================================================
				if (line.contains("ＩＤ") && (line.contains("ppendFormat") || line.contains("ppend") || line.contains("=") ) && line.trim().charAt(0) != '\'') {
					String tab = getTab(line);
					bw.write(tab+comment);
					bw.write(tab + "'" + line.trim() + "\n");
					 
					line = line.replace("ＩＤ", "ｉｄ");
					bw.write(line);
					flag = true;
				}else
				if (line.contains("ＦＡＸ") && (line.contains("ppendFormat") || line.contains("ppend") || line.contains("=") ) && line.trim().charAt(0) != '\'') {
					String tab = getTab(line);
					bw.write(tab+comment);
					bw.write(tab + "'" + line.trim() + "\n");
					 
					line = line.replace("ＦＡＸ", "ｆａｘ");
					bw.write(line);
					flag = true;
				}else
				if (line.contains("ＩＰ") && (line.contains("ppendFormat") || line.contains("ppend") || line.contains("=") ) && line.trim().charAt(0) != '\'') {
					String tab = getTab(line);
					bw.write(tab+comment);
					bw.write(tab + "'" + line.trim() + "\n");
					 
					line = line.replace("ＩＰ", "ｉｐ");
					bw.write(line);
					flag = true;
				}else
				if (line.contains("ＥＤＹ") && (line.contains("ppendFormat") || line.contains("ppend") || line.contains("=") ) && line.trim().charAt(0) != '\'') {
					String tab = getTab(line);
					bw.write(tab+comment);
					bw.write(tab + "'" + line.trim() + "\n");
					 
					line = line.replace("ＥＤＹ", "ｅｄｙ");
					bw.write(line);
					flag = true;
				}else
				if (line.contains("ＳＵＩＣＡ") && (line.contains("ppendFormat") || line.contains("ppend") || line.contains("=") ) && line.trim().charAt(0) != '\'') {
					String tab = getTab(line);
					bw.write(tab+comment);
					bw.write(tab + "'" + line.trim() + "\n");
					 
					line = line.replace("ＳＵＩＣＡ", "ｓｕｉｃａ");
					bw.write(line);
					flag = true;
				}else
					if (line.contains("ＰＡＳＵＭＯ") && (line.contains("ppendFormat") || line.contains("ppend") || line.contains("=") ) && line.trim().charAt(0) != '\'') {
						String tab = getTab(line);
						bw.write(tab+comment);
						bw.write(tab + "'" + line.trim() + "\n");
						 
						line = line.replace("ＰＡＳＵＭＯ", "ｐａｓｕｍｏ");
						bw.write(line);
						flag = true;
					}
				
//=================================================================================================================

//=================================REPLACE CUSTOMER'S COMMENT TO CONFIRM===========================================

//				if(line.contains("' ##ORA2SYMFO [TODO]") && line.contains("\"LPAD\"")){   
//					String tab = getTab(lineBK);
//					bw.write(tab+comment);
//					flag2 = true;
//				}else if(line.contains("' ##ORA2SYMFO ヒント") && flag2){
//					 
//				}else 
//				if(!line.contains("' ##ORA2SYMFO ヒント")){
//					flag2 = false;
//					bw.write(line);
//				}
//=================================REPLACE CUSTOMER'S COMMENT TO CONFIRM===========================================

				else {
					bw.write(line);
				}
				lineBK = line;
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
			if (flag) {
				System.out.println(successful + ": " + ftemp.getAbsolutePath());
			}

		} catch (Exception e) {
			System.out.println("Loi ghi file: " + e);
		}
	}

	private static void countPattern(File f) {

		try {
			boolean flag = false;
			boolean flag2 = false; // check line next to ORA2SYMFO [TODO]
			File ftemp = new File(f.getAbsolutePath() + "_temp");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(ftemp);
			BufferedWriter bw = new BufferedWriter(fw);
			String line;
			String lineBK = "";
			while ((line = br.readLine()) != null) {
				bw.write(line);
				break;
			}
			while ((line = br.readLine()) != null) {

				if (!flag2)
					bw.newLine();

//============================================COUNT PATTERNT=================================================================
				if ( (line.contains("ＩＤ") || line.contains("ＦＡＸ"))  && (line.contains("\");") || line.contains("\"));")) && !line.contains("//")  ){
					lowercaseCount += 1;
				}
				
				//(+) left join
				if ( line.contains("(+)") && !line.contains("//")  ){
					leftJoinCount += 1;
				}
				
				//nvl() -> coalesce 
				if ( isInsensitiveContains("nvl", line) && !line.contains("//")  ){
					NVLCount += 1;
				}
				//DECODE() -> CASE 
				if ( isInsensitiveContains("DECODE", line) && !line.contains("//")  ){
					decodeCount += 1;
				}
//============================================COUNT PATTERNT=================================================================				
				else {
					bw.write(line);
				}
				lineBK = line;
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
			if (flag) {
				System.out.println(successful + ": " + ftemp.getAbsolutePath());
			}

		} catch (Exception e) {
			System.out.println("Loi ghi file: " + e);
		}
	}
	
	private static void extractSQL(File f) {
		int lineNum = 0;
		try {
			boolean flag = false;
			boolean flag2 = false; // check line next to ORA2SYMFO [TODO]
			 
			String fileNameWithOutExt = FilenameUtils.removeExtension(f.getName());
			File ftemp = new File("D:\\workspace\\Getti\\#Migration\\VB\\sql\\"+fileNameWithOutExt+".sql");
			
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(ftemp);
			BufferedWriter bw = new BufferedWriter(fw);
			String line;
			lineNum =0;
			 
			while ((line = br.readLine()) != null) {
				lineNum+=1;
				if (!flag2)
					bw.newLine();

 
				if (  (isInsensitiveContains("if", line) || isInsensitiveContains("else", line))  
						&& line.trim().charAt(0) != '\''  ){
					bw.write("--"+line);
				}
				
				if (  (isInsensitiveContains("Public", line) || isInsensitiveContains("Private", line)) 
						&& isInsensitiveContains("function", line) && line.trim().charAt(0) != '\''  ){
					bw.write(";--"+line);
				}else
				if (  (isInsensitiveContains("SQL", line) )  
						&& ( line.contains("=") || isInsensitiveContains("append", line) )
						&& line.trim().charAt(0) != '\'' 
						&& !isInsensitiveContains("dim", line) 
						&& !isInsensitiveContains("new", line) 
						&& !isInsensitiveContains(".Length", line)
						&& !isInsensitiveContains("CommandText", line)){
					int start = 0;
					int end = 0;
					line = line.trim();
					
					
					for (int i = 0; i < line.length(); i++) {
						start += 1;
						if (line.charAt(i) == '\"') {
							break;
						}
					}
					for (int i = line.length() - 1; i >= 0; i--) {
						end += 1;
						if (	line.charAt(i) == '\"' 
								|| ( line.charAt(i) >= 'A' && line.charAt(i) <= 'Z') 
								|| ( line.charAt(i) >= 'a' && line.charAt(i) <= 'z') ) {
							break;
						}
					}
					if(!line.trim().isEmpty() && (end < line.length() )) {
						line = line.substring(start, line.length() - end );
					}
					
					line = line.replaceAll("\"\"", "\"");  						// "" -> "
					line = line.replaceAll(".[\" ][&].+[&][ \"].", "?"); 		// & parameter &  => ?
					line = line.replaceAll(".[\" ][&].+", "?");					// & parameter  => ?
					
					bw.write(line);
				}
				
			}
			if (isLastLineNull(f)) {
				bw.newLine();
			}
			bw.close();
			br.close();
			fw.close();
			fr.close();
			//boolean successful = ftemp.renameTo(f);
			if (flag) {
				System.out.println(ftemp.getAbsolutePath());
			}

		} catch (Exception e) {
			System.out.println("File: " + f.getAbsolutePath());
			System.out.println("Line :" + lineNum );
			System.out.println("Loi ghi file: " + e);
		}
	}
	
	
	private static String getTab(String line) {
		String tab = new String();
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == '\t' || line.charAt(i) == ' ') {
				tab += line.charAt(i);
			} else {
				break;
			}
		}
		return tab;
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
				if (getFileExtension(fileEntry).equalsIgnoreCase(".vb")) {
					extractSQL(fileEntry);
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

	private static boolean isLastLineNull(File filename) {
		boolean rs = false;
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(filename, "r");
			byte b;
			long length = randomAccessFile.length();
			if (length != 0) {
				length -= 1;
				randomAccessFile.seek(length);
				b = randomAccessFile.readByte();
				if (b == 10) {
					rs = true;
				}
				randomAccessFile.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

}