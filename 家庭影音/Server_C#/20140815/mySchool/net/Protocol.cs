using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace mySchool
{
    /// <summary>
    /// 通讯协议类
    /// </summary>
    class Protocol
    {
        public const int PLAY = 1;
        public const int STOP = 2;
        public const int PAUSE = 3;
        public const int NEXT = 4;
        public const int LAST = 5;
        public const int SHUT_DOWN = 6;
        public const int PROGRESS = 7;
        public const int SEND_LIST = 8;
        public const int PLAY_NAME = 9;
        public const int CLOSE_CLIENT = 10;
        private static String m_content = null;


        public static string GetSendString(int command)
        {
            switch (command)
            {
                case PLAY:
                    return "cmd<play>";
                case PAUSE:
                    return "cmd<pause>";
            }
            return null;
        }


        public static string GetSendString(int command, string content)
        {
            switch (command)
            {
                case SEND_LIST:
                    return "cd<list><" + content + ">";

                case PROGRESS:
                    return "cd<progress><" + content + ">";
            }
            return null;
        }


        //TODO s.h.e 不行

        public static int GetCommand(string recvStr)
        {
            if (recvStr.StartsWith("cmd2"))
            {
         
                Regex regex2 = new Regex(@"cmd2<([\d\D]+?)><([\d\D]+?)>");   //TODO 不能括号
                Match m2 = regex2.Match(recvStr);
                if (m2.Success)
                {
     
                    switch (m2.Groups[1].Value)
                    {
                        case "progress":
                            m_content = m2.Groups[2].Value;
                            //MessageBox.Show("标记" + content);
                            return PROGRESS;

                        case "play":
                            m_content = m2.Groups[2].Value;
                            return PLAY_NAME;
                     
                    }
                }
                else
                {

                    return 0;
                }
            }
            else if (recvStr.StartsWith("cmd"))
            {
                Regex regex2 = new Regex(@"cmd<([\d\D]+?)>");
                Match m2 = regex2.Match(recvStr);
                if (m2.Success)
                {
                    switch (m2.Groups[1].Value)
                    {
                        case "play":
                            return PLAY;
                        case "stop":
                            return STOP;
                        case "pause":
                            return PAUSE;
                        case "next":
                            return NEXT;
                        case "last":
                            return LAST;
                        case "shut_down":
                            return SHUT_DOWN;
                        case "get_list":
                            return SEND_LIST;
                        case "close_client":
                            return CLOSE_CLIENT; 

                    }
                }
                else
                {
                    return 0;
                }
            }
            return 0;
        }

        
        public static String GetContent()
        {
            return m_content;
        }


        //以"|"作为文件名的分隔符，文件名不可能有改字符
        /// <summary>
        /// 获得分隔符
        /// </summary>
        /// <returns></returns>
        public static String getSeparator()
        {
            return "abcd";
        }
       
    }
}
