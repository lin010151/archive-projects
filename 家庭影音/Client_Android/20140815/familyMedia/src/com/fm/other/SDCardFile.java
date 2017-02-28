package com.fm.other;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import android.os.Environment;
import android.util.Log;


/**文件类，对文件操作的封装，处理所有与存储卡文件有关的操作*/
public final class SDCardFile 
{
	/**
	 * 新建文件或文件夹
	 * @param path 文件的完整路径
	 * @return 成功返回true，失败返回false
	 * @注意 文件路径与windows的区别	文件夹必须存在
	 */
	public static boolean createFile(String path)
	{
        try 
        {
			File file = new File(path);
			return file.createNewFile();
		} 
        catch (IOException e1) 
        {
			Log.d("gdei", "创建文件失败" + e1.toString()); 
			e1.printStackTrace();
			return false;
		}

	}
	
	
	/**创建文件夹*/
	public static boolean createFolder(String path)
	{
		File destDir = new File(path);
		if (!destDir.exists()) 
		{
			destDir.mkdirs();
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	
	/**
	 * 判断是否插入了内存卡
	 * @return 有则返回true，没有返回false
	 */
	public static boolean haveSDCard()
	{
		return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
	
    //获得sd卡根目录�?
    //File skRoot = Environment.getExternalStorageDirectory(); 
    //获得私有根目�?
    ///Context.getFilesDir() + "\\";
	
	/**删除文件或文件夹*/
	public static boolean deleteFile(String path)
	{
		File file = new File(path);
		
		if (file.isFile())
		{
			return file.delete();
		}
		else
		{
			//删除文件�?
			return deleteFolder(file);
		}
	}
	
	
	/**删除文件夹及其子文件 */
	public static boolean deleteFolder(File folder)
	{
		boolean result = false;
		try 
		{
			String[] children = folder.list();
			if (children == null || children.length <= 0)
			{
				if (folder.delete())
				{
					result = true;
				}
			}
			else
			{
				for (int i = 0; i < children.length; i++)
				{
					String childName = children[i];
					String childPath = folder.getPath() + File.separator + childName;
					File filePath = new File(childPath);
					if (filePath.exists() && filePath.isFile()) 
					{
						if (filePath.delete())
						{
							result = true;
						}
						else
						{
							result = false;
							break;
						}
					}
					else if (filePath.exists() && filePath.isDirectory())
					{
						if (deleteFolder(filePath))
						{
							result = true;
						}
						else
						{
							result = false;
							break;
						}
					}
				}
				folder.delete();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	
	/**复制文件或文件夹*/
	public static boolean copyFile(String oldPath, String newPath)
	{
		File file = new File(oldPath);
		if (file.isFile())
		{
			return copySingleFile(oldPath, newPath);
		}
		else
		{
			return copyFolder(oldPath, newPath);
		}
	}

	
	/**剪切文件或文件夹*/
	public static boolean cutFile(String oldPath, String newPath)
	{
		File file = new File(oldPath);
		if (file.isFile())
		{
			return cutSingleFile(oldPath, newPath);
		}
		else
		{
			return cutFolder(oldPath, newPath);
		}
	}
	
	
	/**删除文件夹*/
	public static boolean deleteFolder(String path)
	{
		File file = new File(path);
		return deleteFolder(file);
	}
	
	
	/**移动文件*/ 
	public void moveFile(String from, String to)
	{
		new File(from).renameTo(new File(to));
	}
	
	
	/**
	 * 通过文件后缀名判断是否为给定类型的文件
	 * @param fileName 文件后
	 * @param fileEnds 文件格式的数组
	 * @return
	 */
	public static boolean checkEndName(String fileName, String[] fileEnds) 
	{
		for (String endName : fileEnds) 
		{
			if (fileName.endsWith(endName)) 
			{
				return true;
			}
		}
		return false;
	}
	
	
	/**判断文件是否为给定类型的文件*/
	public static boolean checkEndName(File file, String type) 
	{	
		String fileName = file.getAbsolutePath();
		
		if (fileName.endsWith(type)) 
		{
			return true;
		}
		
		return false;
	}
	
	
	/**转换文件大小*/
	public static String FormetFileSize(long fileSize)
	{
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileSize == 0)
		{
			return "0B";
		}
		else if (fileSize < 1024)
		{
			fileSizeString = df.format((double) fileSize) + "B";
		}
		else if (fileSize < 1048576)
		{
			fileSizeString = df.format((double) fileSize / 1024) + "K";
		}
		else if (fileSize < 1073741824)
		{
			fileSizeString = df.format((double) fileSize / 1048576) + "M";
		}
		else
		{
			fileSizeString = df.format((double) fileSize / 1073741824) + "G";
		}
		return fileSizeString;
	}
	
	
	/**获得文件的格式*/
	// TODO 
	public static String getType(File file)
	{
		String fileName = file.getAbsolutePath();
		
		return null;
	}
		
	
	/**获取文件或文件夹的大小*/
	public static long getFilesSize(File file) //throws Exception
	{
		try
		{		
			if (file.isFile())
			{
				return file.length();
			}
			else
			{
				long size = 0;			
				File flist[] = file.listFiles();
				for (int i = 0; i < flist.length; i++)
				{
					if (flist[i].isDirectory())
					{
						size += getFilesSize(flist[i]);
					}
					else
					{
						size += flist[i].length();
					}
				}
				return size;				
			}

		}
		/*
		try
		{
			long size = 0;
			File flist[] = file.listFiles();
			for (int i = 0; i < flist.length; i++)
			{
				if (flist[i].isDirectory())
				{
					size = size + getFileSize(flist[i]);
				}
				else
				{
					size = size + flist[i].length();
				}
			}
			return size;
		}
		*/
		catch (Exception err)
		{
			return 0;
		}
	}
	
	
	/**
	 * 从文件完整路径中获取文件名
	 * @param path 文件完整路径
	 * @return 文件名
	 * @注意 文件路径必须是"\"类型而不是"/"类型
	 */
	public static String getName(String path)
	{
		if (path.equals(null))
		{
			return null;
		}
		
		int index = path.lastIndexOf("\\");
		if (index == -1)
		{
			return path;
		}
		String fileName = path.substring(index + 1);  
		Log.d("_TAG", path + "的文件名是：" + fileName);
		return fileName;
	}
	
	
	/**
	 * 从文件完整路径中获取不带格式的文件名
	 * @param path 文件完整路径
	 * @return 不带格式的文件名
	 * @注意 文件路径必须是"\"类型而不是"/"类型
	 */
	public static String getFileNameWithoutExtension(String path)
	{
		if (path.equals(null))
		{
			return null;
		}
		
		String fileName = getName(path);
		
		int index = fileName.lastIndexOf(".");
		if (index == -1)
		{
			return fileName;
		}
		
		return fileName.substring(0, index);
			
	}
	
	
	/**判断所给的路径是不是文件*/
	public static boolean isFile(String path)
	{
		File file = new File(path);
		return file.isFile();
	}
	
	
	/**剪切一个文件*/
	private static boolean cutSingleFile(String oldPath, String newPath)
	{
		if (copySingleFile(oldPath, newPath) 
				&& deleteFile(oldPath))
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	
	/**复制一个文件*/
	private static boolean copySingleFile(String oldPath, String newPath) 
	{
		File file = new File(oldPath);
		return copySingleFile2(oldPath, newPath + "/" + file.getName());
	}
	
	
	/**
	 * 复制单个文件
	 * @param oldPath 原文件路径 如：c:/fqf.txt 
	 * @param newPath 复制后的路径 如：f:/fqf.txt  
	 * @return
	 * @注意 应该会覆盖
	 */
	private static boolean copySingleFile2(String oldPath, String newPath) 
	{   
       try 
       {   
           int bytesum = 0;   
           int byteread = 0;   
           File oldfile = new File(oldPath);   
           if (oldfile.exists()) 		//文件存在�?   
           { 
               InputStream inStream = new FileInputStream(oldPath); //读入原文�?   
               FileOutputStream fs = new FileOutputStream(newPath);   
               byte[] buffer = new byte[1444];   
           
               while ( (byteread = inStream.read(buffer)) != -1) {   
                   bytesum += byteread; //字节�? 文件大小   
                   System.out.println(bytesum);   
                   fs.write(buffer, 0, byteread);   
               }   
               inStream.close();  
               fs.close();
           }  
           
           return true;
       }   
       catch (Exception e) 
       {   
           //System.out.println("复制单个文件操作出错");   
           e.printStackTrace();   
           return false;
       }   
  
   }   
  
  
	/**
	 * 剪切文件夹
	 * @param oldPath
	 * @param newPath
	 * @return
	 * @注意 一半的情况
	 */
	private static boolean cutFolder(String oldPath, String newPath)
	{
		return copyFolder(oldPath, newPath) && deleteFolder(oldPath);
	}
	
	
	/**复制文件夹*/
	private static boolean copyFolder(String oldPath, String newPath)
	{
		File file = new File(oldPath);
		return copyFolder2(oldPath, newPath + "/" + file.getName());
	}
	
	
	/**
	 * 复制整个文件夹内容
	 * @param oldPath 原文件路径,如：c:/fqf
	 * @param newPath 复制后路径,如：f:/fqf/ff
	 * @return
	 */
	private static boolean copyFolder2(String oldPath, String newPath) 
	{   
       try
       {   
           (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件�?   
           File a=new File(oldPath);   
           String[] file=a.list();   
           File temp=null;   
           for (int i = 0; i < file.length; i++) 
           {   
               if(oldPath.endsWith(File.separator)){   
                   temp=new File(oldPath+file[i]);   
               }   
               else{   
                   temp=new File(oldPath+File.separator+file[i]);   
               }   
  
               if(temp.isFile()){   
                   FileInputStream input = new FileInputStream(temp);   
                   FileOutputStream output = new FileOutputStream(newPath + "/" +   
                           (temp.getName()).toString());   
                   byte[] b = new byte[1024 * 5];   
                   int len;   
                   while ( (len = input.read(b)) != -1) {   
                       output.write(b, 0, len);   
                   }   
                   output.flush();   
                   output.close();   
                   input.close();   
               }   
               if(temp.isDirectory()){//如果是子文件�?   
                   copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);   
               }   
           } 
           
           return true;
       }   
       catch (Exception e) 
       {   
           //System.out.println("复制整个文件夹内容操作出�?");   
           e.printStackTrace(); 
           return false;
  
       }   
  
   }  
	

	/**重命名*/
	public static boolean rename(String path)
	{
		return true;
	}
}

