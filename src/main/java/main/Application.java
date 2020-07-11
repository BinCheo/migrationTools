package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.util.regex.Pattern;

public class Application {

	public static void main(String[] args) {
		final File folder = new File(
				"C:/Users/thanhbinh truong/Documents/Project/thanhbinhtruong/src/components/WeddingDesk");
		listFilesForFolder(folder);
	}

	private static void addComment(File f) {
		try {

			File ftemp = new File(f.getAbsolutePath() + "temp");

			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(ftemp);
			BufferedWriter bw = new BufferedWriter(fw);
			String line;
			boolean flag = false;

			while ((line = br.readLine()) != null) {
				bw.write(line);
				break;
			}
			while ((line = br.readLine()) != null) {
				flag = false;
				bw.newLine();
				if (line.contains("ENTOURAGE")) {
					bw.write("splus comment todo \n");
				}
				bw.write(line);
				if(line.isEmpty()) {
					flag = true;
				}
			}

			if (flag) {
				bw.newLine();
			}
			bw.close();
			br.close();
			fr.close();
			f.delete();
			boolean successful = ftemp.renameTo(f);
			System.out.println(successful);

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
				if (getFileExtension(fileEntry).equalsIgnoreCase(".vue")) {
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

}