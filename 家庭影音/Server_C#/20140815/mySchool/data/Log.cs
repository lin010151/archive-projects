using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


namespace CloudServer
{
    /// <summary>
    /// 日志类
    /// </summary>
    class Log
    {
        public const string REEOR = "错误";
        public const string WARN = "警告";
        public const string INFO = "信息";

        private static string m_path = @"C:\\123.log";  //日志文件保存路径
        private static StreamWriter writer = new StreamWriter(m_path, true);


        /// <summary>
        /// 设置日志文件保存路径
        /// </summary>
        /// <param name="path">路径名</param>
        public static void SetPath(string path)
        {
            m_path = path;
        }


        /// <summary>
        /// 向日志文件写入一行日志
        /// </summary>
        /// <param name="tag"></param>
        /// <param name="message"></param>
        public static void Write(string tag, string message)
        {
            try
            {
                writer.WriteLine(string.Format("[{0}] {1}：{2}", System.DateTime.Now, tag, message));
                writer.Flush();
            }
            catch
            {
                // Nothing to do
            }
        }


        public static void WriteInfo(string message)
        {
            WriteInfo("{0}", message);
        }


        public static void WriteInfo(string format, params object[] obj)
        {
            try
            {
                writer.WriteLine( string.Format("[{0}] {1}", System.DateTime.Now, string.Format(format, obj)));
                writer.Flush();
            }
            catch
            {
                // Nothing to do
            }
        }
    }

   
   

}
