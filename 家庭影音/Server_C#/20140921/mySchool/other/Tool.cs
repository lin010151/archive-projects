using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Text;


namespace mySchool.other
{
    /// <summary>
    /// 
    /// </summary>
    class Tool
    {
        public static void Log(string tag, string msg)
        {
   
            Debug.WriteLine(tag + "：  " + msg);
        }


        public static void Log(string msg)
        {
            Debug.WriteLine(msg);
        }
    }
}
