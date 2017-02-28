using mySchool.form;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;


namespace mySchool
{
    public partial class MainForm : Form
    {
        const int MSG_ACCEPT = 1;    //创建服务器失败
        const int MSG_RECEIVE = 2;    //收到文本消息
        const int MSG_SEND_SUCCEED = 4;    //发送成功
        const int MSG_SEND_FAIL = 5;    //发送失败
        const int MSG_RESPONSE_SUCCEED = 6;    //服务器响应成功
        const int MSG_RESPONSE_FAIL = 7;    //服务器响应失败
        const int MSG_EXCEPTION = 8;    //连接异常
        const int MSG_RECEIVE_SUCCEED = 9;
        const int MSG_RECEIVE_FAIL = 10;

        List<Socket> clientSocketList = new List<Socket>();     //
        List<Thread> threadList = new List<Thread>();           //receive线程列表

        volatile int whatRecv = 0;  //0为接收文字，1为接收文件


        private string[] m_list = null;
        private int m_curMusic;     //当前播放的音乐
        private bool haveConnect = false;

        public MainForm()
        {
            InitializeComponent();
        }

        private void btnTest_Click(object sender, EventArgs e)
        {
            Directory.CreateDirectory("C:/123");
            /*
            bool ok = DataBase.Check("13551101047", "12345");
            if (ok)
            {
                MessageBox.Show("登陆成功");
            }
            else
            {
                MessageBox.Show("登陆失败");
            }
            */

            /*
            string st = "";
            string[] s = DataBase.GetAllSchool();

            for (int i = 0; i < s.Length; i++)
            {
                st += s[i];
            }
            MessageBox.Show(st);
            */ 
            //DataBase.CreateDataBase("123");
            //DataBase.CreateTable("123");
            //DataBase.Test();
            //DataBase.DeleteTable("表1");
            //DataBase.ClearTable("表1");
        }


        private void btnCreateServer_Click(object sender, EventArgs e)
        {
            string ip = comboBox1.Text.Trim();
            String strPort = txtPort.Text.Trim();
            int port;
            try
            {
                port = Int32.Parse(strPort);
            }
            catch
            {
                MessageBox.Show("请输入正确的端口号");
                return;
            }



            if (Server.haveCreate())
            {
                MessageBox.Show("已创建服务器，不能再创建");
                return;
            }

            if (!Server.CreateServer(ip, 1234))
            {
                lblServerState.Text = "创建失败";
            }
            else
            {
                lblServerState.Text = "创建成功";

                //显示本机IP和服务器占用的端口
                //lblIP.Text = Server.GetServerIP();
                //lblPort.Text = Server.GetPort();
                

                //创建新线程来监听客户端的请求
                Thread thread = new Thread(new ThreadStart(ServerListen));
                thread.Start();            
            }


        }


        /// <summary>
        /// 监听客户端的连接
        /// </summary>
        private void ServerListen()
        {
            Socket serverSocket = Server.GetServerSocket();
            while (true)
            {
                serverSocket.Listen(10);    //开始监听，限制同时在线的最大客户端数量为10

                Socket acceptSock = serverSocket.Accept();


                //接收消息
                string clientName = Server.ReceiveText(acceptSock);    //改，规定客户端连接后先发送文字过来
                
                Delegate d = new Delegate(MsgProc);
                object[] arg = new object[] { MSG_ACCEPT, clientName };                    
                this.Invoke(d, arg);

                //获得客户端的IP地址
                IPAddress clientIp = ((System.Net.IPEndPoint)acceptSock.RemoteEndPoint).Address;
                string strip = clientIp.ToString();

                //为每一个客户端分配一个接收线程
                //创建线程开始接受消息
                ParameterizedThreadStart parstart = new ParameterizedThreadStart(ReceiveThread);
                Thread th = new Thread(parstart);
                th.Start((object)acceptSock);


                //多个客户端
                //clientSocketList.Add(acceptSock);
                //threadList.Add(th);

                //TODO 暂时只支持一个客户端 即一对一
                clientSocketList.Clear();
                threadList.Clear();
                clientSocketList.Add(acceptSock);
                threadList.Add(th);
            }

        }


        /// <summary>
        /// 接收文字线程
        /// </summary>
        /// <param name="socket"></param>
        public void ReceiveThread(object socket)
        {
            if (whatRecv == 0)  //接收文字
            {
                while (true)
                {
                    string recvStr = Server.ReceiveText((Socket)socket);         
                    Delegate d = new Delegate(MsgProc);
                    object[] arg = new object[] { MSG_RECEIVE, recvStr };//要传入的参数值     
                    this.Invoke(d, arg);

                }
    
               
            }
            else if (whatRecv == 1)     //接收文件
            { 
                int ret = Server.ReceiveFile((Socket)socket);    //接收文件
                MessageBox.Show("接收返回");    //不能返回         
                if (ret == Return.SUCCEED)
                {

                    Delegate d = new Delegate(MsgProc);
                    object[] arg = new object[] { MSG_RECEIVE_SUCCEED, "接收成功" };//要传入的参数值     
                    this.Invoke(d, arg);
                }
                else
                {
                    Delegate d = new Delegate(MsgProc);
                    object[] arg = new object[] { MSG_RECEIVE_FAIL, "接收失败" };//要传入的参数值     
                    this.Invoke(d, arg);
                }
            }
        }

        private delegate void Delegate(int msg, object obj1);

        #region 负责处理线程消息
        //负责处理线程消息
        /// <summary>
        /// 负责处理线程消息
        /// </summary>
        /// <param name="msg"></param>
        /// <param name="obj1"></param>
        void MsgProc(int msg, object obj1)
        {
            switch (msg)
            {
                case MSG_ACCEPT:
                    //MessageBox.Show((string)obj1);
                    //lblServerState.Text = "收到消息";
                    lblClientState.Text = "客户端(" + (string)obj1 + ")已连接";
                    
                    haveConnect = true;

                    //SendInitData();
                    break;

                case MSG_SEND_SUCCEED:
                    lblMsg.Text = "发送成功（" + (string)obj1 + "）";
                    break;

                case MSG_SEND_FAIL:
                    lblMsg.Text = "发送失败";
                    break;

                case MSG_RESPONSE_SUCCEED:
                    //lblSendState.Text = "服务器响应成功";
                    break;

                case MSG_RESPONSE_FAIL:
                    //lblSendState.Text = "服务器响应失败";
                    break;

                case MSG_EXCEPTION:
                    //lblSendState.Text = "连接异常";
                    break;

                case MSG_RECEIVE_SUCCEED:
                    lblMsg.Text = "接收文件成功（" + (string)obj1 + "）";
                    break;

                case MSG_RECEIVE_FAIL:  
                    lblMsg.Text = "接收文件失败（" + (string)obj1 + "）";
                    break;
                    
                // 收到文字
                // TODO 协议处理
                case MSG_RECEIVE:
                    lblMsg.Text = "接收文字（" + (string)obj1 + "）";  
                    switch (Protocol.GetCommand((string)obj1))
                    {

                        case Protocol.STOP:
                            player.close();
                            break;

                        case Protocol.PAUSE:
                            player.Ctlcontrols.pause();
                            break;

                        case Protocol.PLAY:
                            player.Ctlcontrols.play();
                            break;

                        case Protocol.SHUT_DOWN:
                            Other.CloseComputer();
                            break;

                        case Protocol.PROGRESS:
                            string strProgress = Protocol.GetContent();
                            int progress;
                            try
                            {
                                progress = Int32.Parse(strProgress);
                            }
                            catch
                            {
                                MessageBox.Show("数字出错");
                                return;
                            }
                            int total = (int) player.currentMedia.duration;
                            
                            player.Ctlcontrols.currentPosition = progress / 100.0 * total;
                          
                            break;

                        case Protocol.LAST:
                            PreMusic();
                            break;

                        case Protocol.NEXT:
                            NextMusic();
                            break;

                        case Protocol.SEND_LIST:
                            SendList();
                            break;

                        case Protocol.PLAY_NAME:
                            string name = Protocol.GetContent();
                            player.URL = name;
                            player.Ctlcontrols.play();
                            break;

                        case Protocol.CLOSE_CLIENT:
                            lblClientState.Text = "客户端已断开连接";
                            haveConnect = false;
                            break;
                    }
                    break;
            }

        }
        #endregion


        /// <summary>
        /// 给客户端发送歌曲列表
        /// </summary>
        private void SendList()
        {
            ParameterizedThreadStart parstart = new ParameterizedThreadStart(SendTextThread);
            Thread thread = new Thread(parstart);
            string sendStr = null;
            foreach (string s in m_list)
            {
                sendStr += s + Protocol.getSeparator();
            }

            sendStr = Protocol.GetSendString(Protocol.SEND_LIST, sendStr);
            thread.Start((object)sendStr);
        }


        /// <summary>
        /// 给客户端发送初始化数据
        /// </summary>
        private void SendInitData()
        {
            //发送当前进度
            SendCurProgress();
            
            //发送播放按钮状态
            ParameterizedThreadStart parstart = new ParameterizedThreadStart(SendTextThread);
            Thread thread = new Thread(parstart);

            string sendStr;
            if (player.playState == WMPLib.WMPPlayState.wmppsPlaying)
            {
                sendStr = Protocol.GetSendString(Protocol.PLAY);
                sendStr = Protocol.GetSendString(Protocol.SEND_LIST);
                thread.Start((object)sendStr);
            }
            else if (player.playState == WMPLib.WMPPlayState.wmppsPaused
                || player.playState == WMPLib.WMPPlayState.wmppsStopped)
            {
                sendStr = Protocol.GetSendString(Protocol.PAUSE);
                sendStr = Protocol.GetSendString(Protocol.SEND_LIST);
                thread.Start((object)sendStr);
            }
            

            //发送
        }


        /// <summary>
        /// 接收线程
        /// </summary>
        private void ReceiveThread()
        {
            while (true)
            {
                string recvStr = Server.ReceiveText(clientSocketList[0]);   //改

                if (recvStr != "异常")
                {
                    Delegate d = new Delegate(MsgProc);
                    object[] arg = new object[] { MSG_RECEIVE_SUCCEED, recvStr };//要传入的参数值     
                    this.Invoke(d, arg);
                }
                else
                {
                    Delegate d = new Delegate(MsgProc);
                    object[] arg = new object[] { MSG_RECEIVE_FAIL, recvStr };//要传入的参数值     
                    this.Invoke(d, arg);
                }
            }

        }


        private void SendThread()
        {
            bool ret = Server.SendFile(clientSocketList[0], "C:/123.bmp");   //clientSocketList[0]改以及路径没错
           
            if (ret)
            {
                Delegate d = new Delegate(MsgProc);
                object[] arg = new object[] { MSG_SEND_SUCCEED, "发送文件成功" };//要传入的参数值     
                this.Invoke(d, arg);
            }
            else
            {
                Delegate d = new Delegate(MsgProc);
                object[] arg = new object[] { MSG_SEND_FAIL, "发送文件失败" };//要传入的参数值     
                this.Invoke(d, arg);
            }
          

        }


        private void Form1_Load(object sender, EventArgs e)
        {
            //填充IP
            
            string[] s = Server.GetLocalIPs();
            foreach (string st in s)
            {
                comboBox1.Items.Add(st);
                if (st.StartsWith("210") || st.StartsWith("10"))
                {
                    comboBox1.Text = st;
                }
            }
          

            //player.URL = "D:/亲爱的那不是爱情.mp3";
            //player.close();
            player.enableContextMenu = false;
            player.Ctlcontrols.currentPosition = 0.4;
            //DataBase.Open();

            
           
        }

        
        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            // 退出，可以强制关闭所有线程
            Environment.Exit(0);

            //关闭线程
            for (int i = 0; i < threadList.Count; i++)
            {
                if (threadList[i].IsAlive)
                {
                    threadList[i].Abort();
                }
            }

            //关闭数据库
            DataBase.Close();
        }


        //发送文字
        //Thread thread = new Thread(new ThreadStart(SendText));
        //thread.Start();  
        /*发送文件
         Thread thread = new Thread(new ThreadStart(SendThread));
            thread.Start(); 
         */

        // TODO: clientSocketList[0]要更改
        private void SendTextThread(object sendStr)
        {
            Server.SendText(clientSocketList[0], (string)sendStr);
        }


        private void button1_Click(object sender, EventArgs e)
        {
            //NextMusic();
            //PreMusic();
           
            
            //player.URL = "C:/share/第一次爱的人.mp3";
            //player.Ctlcontrols.play();
            //Directory.Delete("C:\\fmMusic", true);
          
            //
          
           
        }


        /// <summary>
        /// 下一首
        /// </summary>
        private void NextMusic()
        {
            m_curMusic += 1;
            if (m_curMusic >= m_list.Length)
            {
                m_curMusic = 0;
            }

            player.Ctlcontrols.stop();
            player.URL = m_list[m_curMusic];
            player.Ctlcontrols.play();

            lbMusic.SelectedIndex = m_curMusic;
        }


        /// <summary>
        /// 上一首
        /// </summary>
        private void PreMusic()
        {
            m_curMusic -= 1;
            if (m_curMusic < 0)
            {
                m_curMusic = m_list.Length - 1;
            }

            player.Ctlcontrols.stop();
            player.URL = m_list[m_curMusic];
            player.Ctlcontrols.play();

            lbMusic.SelectedIndex = m_curMusic;
        }
        private void button2_Click(object sender, EventArgs e)
        {
            //axWindowsMediaPlayer1.close();  //关闭
            player.Ctlcontrols.pause();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            player.Ctlcontrols.play();
        }

  
        private void TSMIAbout_Click(object sender, EventArgs e)
        {
            AboutForm form = new AboutForm();
            form.ShowDialog();
        }

        private void axWindowsMediaPlayer1_Enter(object sender, EventArgs e)
        {

        }

        private void axWindowsMediaPlayer1_PlayStateChange(object sender, AxWMPLib._WMPOCXEvents_PlayStateChangeEvent e)
        {
          
            //播放状态，1=停止，2=暂停，3=播放，6=正在缓冲，9=正在连接，10=准备就绪
            switch (e.newState)
            {
                case 3:
                    if (haveConnect)
                    {
                        timer.Start();
                    }

                    break;

                case 1:
                    if (haveConnect)
                    {
                        timer.Stop();
                    }
                    

                    break;

                case 2:
                    if (haveConnect)
                    {
                        timer.Stop();
                    }

                    break;
   
            }
        }

        private void lbMusic_SelectedIndexChanged(object sender, EventArgs e)
        {
            //MessageBox.Show("选择" + lbMusic.SelectedIndex);
            //MessageBox.Show("选择" + lbMusic.Text);
            //player.URL = list[lbMusic.SelectedIndex];
            m_curMusic = lbMusic.SelectedIndex;
            player.URL = m_list[m_curMusic];
            player.Ctlcontrols.play();
             
        }

        private void btnBrowse_Click(object sender, EventArgs e)
        {
            // 浏览文件夹路径
            //folderBrowseDlg.RootFolder = Environment.SpecialFolder.
            folderBrowseDlg.SelectedPath = "C:";
            if (folderBrowseDlg.ShowDialog() == DialogResult.OK)
            {
                const int MAX_LENGTH = 30;

                lblPath.Text = folderBrowseDlg.SelectedPath;
                string path = lblPath.Text;

                if (lblPath.Text.Length > MAX_LENGTH)
                {
                    lblPath.Text = lblPath.Text.Substring(0, MAX_LENGTH) + "...";
                }

                // 列表显示
                lbMusic.Items.Clear();
                m_list = MyFile.GetAllMusic(lblPath.Text);
                foreach (string s in m_list)
                {
                    //只显示歌曲的文件名，不显示完整路径
                    lbMusic.Items.Add(Path.GetFileNameWithoutExtension(s));
                }

                // 选择第一项
                lbMusic.SelectedIndex = 0;
                player.Ctlcontrols.stop();
                //MessageBox.Show(a[1]);
                //MyFile.DeleteFold("‪C:\\fmMusic");

            }
        }

        private void folderBrowseDlg_HelpRequest(object sender, EventArgs e)
        {

        }


        /// <summary>
        /// 发送当前进度
        /// </summary>
        private void SendCurProgress()
        {
            int percent = (int)(player.Ctlcontrols.currentPosition * 100.0 / player.currentMedia.duration);
            string sendStr = "" + percent;

            ParameterizedThreadStart parstart = new ParameterizedThreadStart(SendTextThread);
            Thread thread = new Thread(parstart);
            //MessageBox.Show("进度完成");
            sendStr = Protocol.GetSendString(Protocol.PROGRESS, sendStr);
            thread.Start((object)sendStr);
        }


        private void timer_Tick(object sender, EventArgs e)
        {
            SendCurProgress();
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void groupBox1_Enter(object sender, EventArgs e)
        {

        }

    }
}
