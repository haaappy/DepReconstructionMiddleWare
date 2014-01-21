package nju.moon.lhc.reconstruction.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.swing.JOptionPane;

public class FileOperator {

	public static long copyFile(File srcFile, File destDir, String newFileName){
		long copySize = 0;
		if (!srcFile.exists()){
			System.out.println("srcFile does not exist in copyFile");
			copySize = -1;
		}
		else if (!destDir.exists()){
			System.out.println("destDir does not exist in copyFile");
			copySize = -1;
		}
		else if (newFileName == null || newFileName.length() == 0){
			System.out.println("newFileName is null or length is 0 in copyFile");
			copySize = -1;
		}
		else{
			try{
				@SuppressWarnings("resource")
				FileChannel fcin = new FileInputStream(srcFile).getChannel();
				@SuppressWarnings("resource")
				FileChannel fcout = new FileOutputStream(new File(destDir, newFileName)).getChannel();
				long size = fcin.size();
				fcin.transferTo(0, fcin.size(), fcout);
				fcin.close();
				fcout.close();
				copySize = size;
			}
			catch (FileNotFoundException e){
				e.printStackTrace();
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
		return copySize;
	}
	
	public static boolean removeFile(String filePath){
		if (filePath.length()!=0){
			File file = new File(filePath);
			if (file.exists()){
				int saveChoose = JOptionPane.showConfirmDialog(null, "Are you sure to remove "+filePath+"?", "Reminder", JOptionPane.YES_NO_OPTION);
				if (saveChoose == JOptionPane.YES_OPTION){
					return file.delete();
				}				
			}	
		}
		return false;
	}	
}
