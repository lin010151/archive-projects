using System;
using System.Collections.Generic;
using System.Text;
using System.Text.RegularExpressions;
using System.Windows.Forms;


namespace mySchool
{
    /// <summary>
    /// 通讯协议类
    /// </summary>
    class Protocol
    {
        public const int PLAY = 1;          //播放
        public const int PAUSE = 3;         //暂停
        public const int NEXT = 4;          //下一首
        public const int LAST = 5;          //上一首
        public const int SHUT_DOWN = 6;     //关机
        public const int PROGRESS = 7;
        public const int SEND_LIST = 8;     //发送歌曲列表给客户端TODO
        public const int PLAY_NAME = 9;
        public const int CLOSE_CLIENT = 10;
        public const int NAME = 11;
        public const int INIT = 12;
        public const int TURN_UP = 13;      //增大音量
        public const int TURN_DOWN = 14;    //减小音量
        public const int SOUND = 15;
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

                case NAME:
                    return "cd<name><" + content + ">";

                case SOUND:
                    return "cd<sound><" + content + ">";
            }
            return null;
        }


        public static int GetCommand(string recvStr)
        {
            if (recvStr.StartsWith("cmd2"))
            {
         
                Regex regex2 = new Regex(@"cmd2<([\d\D]+?)><([\d\D]+?)>");   //不能括号，文件名可以包含括号
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
                        case "init":
                            return INIT;
                        case "turn_up":
                            return TURN_UP;
                        case "turn_down":
                            return TURN_DOWN;

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
