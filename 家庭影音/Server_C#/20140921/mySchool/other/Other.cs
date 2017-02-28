using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Text;


namespace mySchool
{
    class Other
    {
        /// <summary>
        /// 关机
        /// </summary>
        /// <returns></returns>
        public static bool CloseComputer()
        {
      
            Process myProcess = new Process(); 
            myProcess.StartInfo.FileName = "cmd.exe"; 
            myProcess.StartInfo.UseShellExecute = false; 
            myProcess.StartInfo.RedirectStandardInput = true; 
            myProcess.StartInfo.RedirectStandardOutput = true; 
            myProcess.StartInfo.RedirectStandardError = true; 
            myProcess.StartInfo.CreateNoWindow = true; 
            myProcess.Start(); 
            myProcess.StandardInput.WriteLine("shutdown -s -t 10");
            //myProcess.StandardInput.WriteLine("shutdown -a");

        
            return true;
        }

    }
}
