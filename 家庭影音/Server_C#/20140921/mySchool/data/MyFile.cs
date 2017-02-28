using CloudServer;
using System;
using System.Collections;
using System.IO;
using System.Windows.Forms; 


namespace mySchool
{
    /// <summary>
    /// 文件操作类，用来管理文件系统
    /// </summary>
    class MyFile
    {
        public const int SUCCEED = 0;
 
        private string m_savePath;
 
        MyFile()
        {
            m_savePath = "";

        }

        public bool SetSavePath(string path)
        {
            m_savePath = path;
            return true;
        }


        public string GetSavePath()
        {
            return m_savePath;
        }


        public static bool CreateFile(string path)
        {
            try
            {
                //创建文件，若存在则覆盖
                File.Create(path);
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }


        /// <summary>
        /// 新建文件夹
        /// </summary>
        /// <param name="path">文件的完整路径</param>
        /// <returns>删除成功返回true，删除失败返回false</returns>
        public static bool CreateFold(string path)
        {
            try
            {
                //创建文件夹，若存在则不创建
                Directory.CreateDirectory(path);
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }


        /// <summary>
        /// 删除文件
        /// </summary>
        /// <param name="path">文件的完整路径</param>
        /// <returns>删除成功返回true，删除失败返回false</returns>
        public static bool DeleteFile(string path)
        {
            try
            {
                //删除文件，若不存在则忽略此操作
                File.Delete(path); 
                return true;
            }
            catch (Exception)
            {
                return false;
            }
             
            
        }


        /// <summary>
        /// 删除文件夹及其子文件（夹）
        /// </summary>
        /// <param name="path">文件的完整路径</param>
        /// <returns>删除成功返回true，删除失败返回false</returns>
        public static bool DeleteFold(string path)
        {
            try
            {
                //删除文件夹，文件夹可不为空，但必须存在
                Directory.Delete(path, true); 
                return true;
            }
            catch (Exception err)
            {
                MessageBox.Show(err.ToString());
                return false;
            }
            
        }


        /// <summary>
        /// 移动文件
        /// </summary>
        /// <returns>成功返回true，失败返回false</returns>
        public static bool MoveFile()
        {
            return true;
        }

   
        /// <summary>
        /// 移动文件夹
        /// </summary>
        /// <param name="srcPath">源文件夹的完整路径</param>
        /// <param name="distPath">目标位置的完整路径</param>
        /// <returns>复制成功返回true，复制失败返回false</returns>
        public static bool MoveFold(string srcPath, string distPath)
        {   
   
            try
            {
                CopyFold(srcPath, distPath);             //先剪切
                Directory.Delete(srcPath, true);        //再删除
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }


        /// <summary>
        /// 复制文件，不覆盖
        /// </summary>
        /// <param name="srcPath"></param>
        /// <param name="distPath"></param>
        /// <returns>成功返回true，失败返回false</returns>
        public static bool CopyFile(string srcPath, string distPath)
        {
            try
            {
                File.Copy(srcPath, distPath);
                //File.Copy(@"C:\Cloud\3.txt", @"C:\Cloud\f\3.txt", true);//覆盖
                return true;
            }
            catch (IOException)
            {
                return false;
                //MessageBox.Show("文件已存在");
            }
            catch (Exception)
            {
                return false;
            }
        }


        /// <summary>
        /// 复制文件夹
        /// </summary>
        /// <param name="srcPath">源文件夹的完整路径</param>
        /// <param name="distPath">目标位置的完整路径</param>
        /// <returns>成功返回true，失败返回false</returns>
        public static bool CopyFold(string srcPath, string distPath)
        {
            try
            {
                CopyDirectory(srcPath, distPath, true);
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }


        /// <summary>
        /// 文件重命名
        /// </summary>
        /// <returns></returns>
        public static bool RenameFile()
        {
            try
            {
                File.Move("C:/Cloud/3.txt", @"C:\Cloud\2.txt");
                return true;
            }
            catch (IOException)
            {
                return false;
                //MessageBox.Show("文件名不能重复");
            }
            catch (Exception)
            {
                return false;
                //MessageBox.Show("出错");
            }
        }


        public static bool RenameFold()
        {
            try
            {
                Directory.Move("C:/Cloud/相册", @"C:\Cloud\f");
                return true;
            }
            catch (IOException)
            {
                return false;
                //MessageBox.Show("文件夹名不能重复");
            }
            catch (Exception)
            {
                return false;
                //MessageBox.Show("出错");
            }
        }


        /// <summary>
        /// 文件剪切函数
        /// </summary>
        /// <param name="directoryPath"></param>
        /// <param name="dirAddress"></param>
        public static void CutDirectory(string directoryPath, string dirAddress)
        {
            
        }


        /// <summary>
        /// 复制文件夹中的所有文件到指定文件夹
        /// </summary>
        /// <param name="directoryPath">源文件夹路径</param>
        /// <param name="dirAddress">保存路径</param>
        /// <param name="dirFirst">true保留第一个文件夹目录，false不保留第一个文件夹目录</param>
        public static void CopyDirectory(string directoryPath, string dirAddress, bool dirFirst)//复制文件夹，
        {
            string s = directoryPath.Substring(DirectoryName(directoryPath));   //获取文件夹名
            DirectoryInfo directoryArray = new DirectoryInfo(directoryPath);
            FileInfo[] files = directoryArray.GetFiles();   //获取该文件夹下的文件列表     
            DirectoryInfo[] directorys = directoryArray.GetDirectories();   //获取该文件夹下的文件夹列表
            if (!dirFirst)
            {
                if (Directory.Exists(dirAddress))
                {
                    Directory.Delete(dirAddress, true);     //若文件夹存在，不管目录是否为空，删除 
                    Directory.CreateDirectory(dirAddress);  //删除后，重新创建文件夹    
                }
                else
                {
                    Directory.CreateDirectory(dirAddress);  //文件夹不存在，创建     
                }
                foreach (FileInfo inf in files)//逐个复制文件     
                {
                    File.Copy(directoryPath + "\\" + inf.Name, dirAddress + "\\" + inf.Name);
                }
                foreach (DirectoryInfo Dir in directorys)//逐个获取文件夹名称，并递归调用方法本身     
                {
                    CopyDirectory(directoryPath + "\\" + Dir.Name, dirAddress, true);
                }
            }
            else
            {
                if (Directory.Exists(dirAddress + "\\" + s))
                {
                    Directory.Delete(dirAddress + "\\" + s, true);//若文件夹存在，不管目录是否为空，删除 
                    Directory.CreateDirectory(dirAddress + "\\" + s);//删除后，重新创建文件夹    
                }
                else
                {
                    Directory.CreateDirectory(dirAddress + "\\" + s);//文件夹不存在，创建     
                }
                foreach (FileInfo inf in files)//逐个复制文件     
                {
                    File.Copy(directoryPath + "\\" + inf.Name, dirAddress + "\\" + s + "\\" + inf.Name);
                }
                foreach (DirectoryInfo Dir in directorys)//逐个获取文件夹名称，并递归调用方法本身     
                {
                    CopyDirectory(directoryPath + "\\" + Dir.Name, dirAddress + "\\" + s, true);
                }
            }
        }


        /// <summary>
        /// 获取文件夹名，截取“\”
        /// </summary>
        /// <param name="DirectoryPath"></param>
        /// <returns></returns>
        public static int DirectoryName(string DirectoryPath) 
        {
            //string path = Globals.GetFilePath(DirectoryPath);
            string path = DirectoryPath;
            int j = 0;
            j = DirectoryPath.LastIndexOf("\\");
            return j + 1;
        }


        /// <summary>
        /// 找出路径下的所有能播放的音乐、视频文件
        /// </summary>
        /// <param name="path">文件夹路径</param>
        /// <returns>返回所有音乐文件的完整路径，找不到返回null</returns>
        public static string[] GetAllMusic(string path)
        {
            ArrayList alst = new ArrayList();
            alst.Clear();
            try
            {
                string[] files = Directory.GetFiles(path);//得到文件
                
                foreach (string file in files)  //循环文件
                {
                    string exname = file.Substring(file.LastIndexOf(".") + 1);//得到后缀名

                    if ("mp3|m4a|wmv|avi|mpeg|3gp".IndexOf(file.Substring(file.LastIndexOf(".") + 1)) > -1)
                    {
                        FileInfo fi = new FileInfo(file);
                        alst.Add(fi.FullName);//把文件全名加人到FileInfo对象
                    }
                }

                if (alst.Count == 0)
                {
                    return null;
                }
               
                return (string[])alst.ToArray(typeof(string));//把ArrayList转化为string[]
            }
            catch (Exception err)
            {
                Log.Write("exception", err.ToString());
                MessageBox.Show(err.ToString());
                return null;
            }
        }

       
        //获取文件夹 Path.GetDirectoryName(path) Path.GetFileNameWithoutExtension(path)
        // 获取GetFileName(path) Path.GetExtension(path)
    }
}

