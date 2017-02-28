using mySchool.form;
using mySchool.other;
using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using System.Windows.Forms;
using System.Collections;
using System.Diagnostics;
using ThoughtWorks;
using ThoughtWorks.QRCode;
using ThoughtWorks.QRCode.Codec;
using ThoughtWorks.QRCode.Codec.Data;
using System.Drawing;
using System.Text;
using QuickMark;
namespace mySchool
{
    public partial class MainForm : Form
    {
        //副线程发送给主线程的消息（副线程不能UI操作）
        const int MSG_ACCEPT = 1;           //创建服务器失败
        const int MSG_RECEIVE = 2;          //收到文本消息
        const int MSG_SEND_SUCCEED = 4;     //发送成功
        const int MSG_SEND_FAIL = 5;        //发送失败
        const int MSG_RESPONSE_SUCCEED = 6; //服务器响应成功
        const int MSG_RESPONSE_FAIL = 7;    //服务器响应失败
        const int MSG_EXCEPTION = 8;        //连接异常
        const int MSG_RECEIVE_SUCCEED = 9;
        const int MSG_RECEIVE_FAIL = 10;
        const int MSG_EXIT = 11;            //
        const int MSG_UPADTE_COUNT = 12;


        volatile int whatRecv = 0;  //0为接收文字，1为接收文件

        //歌曲
        private string[] m_list = null;     //歌曲名
        private int     m_curMusic;         //当前播放的音乐序号

        //播放器状态
        private bool    playing = false;    //当前是否正在播放


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



            if (MyServer.haveCreate())
            {
                MessageBox.Show("已创建服务器，不能再创建");
                return;
            }

            if (!MyServer.CreateServer(ip, 1234))
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
          
                //显示二维码
                string path = UserData.getPath() + "\\code.bmp";

                CreateTwoCode ctc = new CreateTwoCode();

                //调用的时候换成这个方法   传递2个参数就行了   一个是要生成的字符，一个是生成之后二维码保存路径
                MyFile.DeleteFile(path);
                ctc.CreateNewCode("ip<" + comboBox1.Text + ">port<" + txtPort.Text + ">", path);

                this.pictureBox1.BackgroundImage = Image.FromFile(path);
            }


        }


        /// <summary>
        /// 监听客户端的连接
        /// </summary>
        private void ServerListen()
        {
            //同步方案
            Socket serverSocket = MyServer.GetServerSocket();
            
            //TODO  程序退出时不能退出死循环
            serverSocket.Listen(10);    //开始监听，限制同时在线的最大客户端数量为10 

            serverSocket.BeginAccept(AcceptCallBack, serverSocket); 

            

            //Tool.Log("监听死循环结束,");

        }


        void AcceptCallBack(IAsyncResult ar)
        {
            Tool.Log("监听到一个连接");

            Socket socket = ar.AsyncState as Socket;
            //结束异步Accept并获已连接的Socket
            Socket acceptSock = socket.EndAccept(ar); 
         

            //测试是否连接成功
            if (!acceptSock.Connected)
            {
                MessageBox.Show("未连接");
            }

            //获得客户端的IP地址
            IPAddress clientIp = ((System.Net.IPEndPoint)acceptSock.RemoteEndPoint).Address;
            string strip = clientIp.ToString();

            string id = ClientList.Add(acceptSock);


            //为每一个客户端分配一个接收线程
            //创建线程开始接受消息
            Thread th = new Thread(delegate()
            {
                ReceiveThread(acceptSock, id);
            });
            th.Start();


            //ClientList.Add()后再发送
            SendMessage(id, MSG_ACCEPT, id);
   //TODO 数据包多合一       




            //继续异步Accept，保持Accept一直开启！
            socket.BeginAccept(AcceptCallBack, socket); 
        }
        
        
        /// <summary>
        /// 接收文字线程
        /// </summary>
        /// <param name="socket"></param>
        public void ReceiveThread(Socket socket, string id)
        {
            if (whatRecv == 0)  //接收文字
            {
                while (ClientList.haveId(id))
                {
                    if (whatRecv == 0)
                    {
                        Tool.Log("接收死循环开始");
                        string recvStr = MyServer.ReceiveText((Socket)socket);
         
                        if (recvStr != null)
                        {
                            if (!recvStr.Equals("退出"))
                            {
                                Tool.Log("接收到文字:" + recvStr);

                                SendMessage(id, MSG_RECEIVE, recvStr);
                            }
                            else
                            {
                                Tool.Log("ID为" + id + "的客户端退出");
                                ClientList.Remove(id);
  
                            }
                        
                        }

                    }
                    else if (whatRecv == 1)     //接收文件
                    {
                        int ret = MyServer.ReceiveFile((Socket)socket);    //接收文件
                        MessageBox.Show("接收返回");    //不能返回         
                        if (ret == Return.SUCCEED)
                        {
                            //SendMessage(MSG_RECEIVE_SUCCEED, "接收成功");
                        }
                        else
                        {
                            //SendMessage(MSG_RECEIVE_FAIL, "接收失败");
                        }
                    }

               

                }

                Tool.Log("ID为" + id + "的接收死循环结束");
                SendMessage(id, MSG_UPADTE_COUNT, null);
    
               
            }
        }


        private delegate void Delegate(string id, int msg, object obj1);


        /// <summary>
        /// 给主线程发送消息
        /// </summary>
        private void SendMessage(string id, int what, string msg)
        {
            Delegate d = new Delegate(MsgProc);
            object[] arg = new object[] { id, what, msg };
            this.Invoke(d, arg);
        }


        /// <summary>
        /// 负责处理线程消息
        /// </summary>
        /// <param name="msg"></param>
        /// <param name="obj1"></param>
        void MsgProc(string id, int msg, object obj1)
        {
            switch (msg)
            {
                case MSG_UPADTE_COUNT:
                    lblClientState.Text = "客户端数：" + ClientList.GetClientCount();
                    break;

                case MSG_ACCEPT:
                    lblClientState.Text = "客户端数：" + ClientList.GetClientCount();
                    break;

                case MSG_SEND_SUCCEED:
                    Tool.Log("发送成功（" + (string)obj1 + "）");
                    break;

                case MSG_SEND_FAIL:
                    Tool.Log("发送失败");
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
                    Tool.Log("接收文件成功（" + (string)obj1 + "）");
                    break;

                case MSG_RECEIVE_FAIL:
                    Tool.Log("接收文件失败（" + (string)obj1 + "）");
                    break;
                    
                // 收到文字
                // TODO 协议处理
                case MSG_RECEIVE:
                    //Tool.Log("接收文字（" + (string)obj1 + "）");
  
                    switch (Protocol.GetCommand((string)obj1))
                    {
                        case Protocol.PAUSE:
                            player.Ctlcontrols.pause();
                            playing = false;
                            SendButtonState();
                            break;

                        case Protocol.PLAY:
                            player.Ctlcontrols.play();
                            playing = true;
                            //SendName();//必要？
                            SendButtonState();
                            break;

                        case Protocol.INIT:
                            SendInitData(ClientList.getSocket(id));
                            break;

                        case Protocol.TURN_DOWN:
                            player.settings.volume -= 10;
                            if (player.settings.volume < 0)
                            {
                                player.settings.volume = 0;
                            }
                            SendSound(ClientList.getSocket(id));
                            break;

                        case Protocol.TURN_UP:
                            player.settings.volume += 10;
                            if (player.settings.volume > 100)
                            {
                                player.settings.volume = 100;
                            }
                            SendSound(ClientList.getSocket(id));
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
                            SendName();
                            playing = true;
                            break;

                        case Protocol.NEXT:
                            NextMusic();
                            SendName();
                            playing = true;
                            break;

                        case Protocol.SEND_LIST:
                            
                            SendList(ClientList.getSocket(id));
                            break;

                        case Protocol.PLAY_NAME:
                            string name = Protocol.GetContent();
                            player.URL = name;
                            player.Ctlcontrols.play();

                            //列表选中对应的歌曲
                            for (int i = 0; i < lbMusic.Items.Count; i++)
                            {
                                //MessageBox.Show(lbMusic.Items[i].ToString()+"和"+name);
                                if (lbMusic.Items[i].ToString().Equals(Path.GetFileNameWithoutExtension(name)))
                                {
                                    m_curMusic = i;
                                    lbMusic.SelectedIndex = i;
                                    
                                }
                            }
                            
                            SendName();
                            break;

                        case Protocol.CLOSE_CLIENT:
                            lblClientState.Text = "客户端数：" + ClientList.GetClientCount();
                            ClientList.Remove(id);
                            break;
                    }
                    break;
            }

        }

 
        /*
        /// <summary>
        /// 发送文件线程
        /// </summary>
        private void SendThread(Socket socket)
        {
            bool ret = Server.SendFile(socket, "C:/123.bmp");   //clientSocketList[0]改以及路径没错
           
            if (ret)
            {
                SendMessage(MSG_SEND_SUCCEED, "发送文件成功");
            }
            else
            {
                SendMessage(MSG_SEND_FAIL, "发送文件失败");
            }
          

        }
        */

        private void Form1_Load(object sender, EventArgs e)
        {
            //填充IP
            
            string[] s = MyServer.GetLocalIPs();
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

            //player.settings.autoStart = true;

            player.settings.volume = 20;
           
        }

        
        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {

            App.isRuning = false;

            //关闭数据库
            DataBase.Close();

            //先判断 clientSocketList[0] = null;     //TODO
            // 退出，可以强制关闭所有线程
            Environment.Exit(0);//有时异常？？TODO
        }


    
        /*发送文件
         Thread thread = new Thread(new ThreadStart(SendThread));
            thread.Start(); 
         */


        private void SendTextThread(object socket, object sendStr)
        {
            Tool.Log("发送： " + (string)sendStr);
            MyServer.SendText((Socket)socket, (string)sendStr);
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
        

        private void TSMIAbout_Click(object sender, EventArgs e)
        {
            AboutForm form = new AboutForm();
            form.ShowDialog();
        }


        private void axWindowsMediaPlayer1_PlayStateChange(object sender, AxWMPLib._WMPOCXEvents_PlayStateChangeEvent e)
        {
            //播放状态，1=停止，2=暂停，3=播放，6=正在缓冲，9=正在连接，10=准备就绪
            switch (e.newState)
            {
                case 8:
                    break;

                case 3:
                    playing = true;
                    if (!ClientList.isEmpty())
                    {
                        timer.Start();
                    }
                    SendButtonState();
                    break;

                case 1:
                    playing = false;

                    if (!ClientList.isEmpty())
                    {
                        timer.Stop();
                    }
                    SendButtonState();

                    break;

                case 2:
                    playing = false;
                    if (!ClientList.isEmpty())
                    {
                        timer.Stop();
                    }
                    SendButtonState();
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

            SendName();
             
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

                if (m_list == null)
                {
                    MessageBox.Show("文件夹里没有音乐或视频");
                    return;
                }
                foreach (string s in m_list)
                {
                    //只显示歌曲的文件名，不显示完整路径
                    lbMusic.Items.Add(Path.GetFileNameWithoutExtension(s));
                }

                // 选择第一项
                lbMusic.SelectedIndex = 0;
                player.Ctlcontrols.stop();

            }
        }

     

        /// <summary>
        /// 定时器任务
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void timer_Tick(object sender, EventArgs e)
        {
            SendCurProgress();
        }


        class ThreadInfo
        {
            public Socket socket { get; set; }

            public int SelectedIndex { get; set; }

        }  

        /// <summary>
        /// 发送文本
        /// </summary>
        /// <param name="socket"></param>
        /// <param name="sendStr"></param>
        private void SendText(Socket socket, string sendStr)
        {
            //ThreadPool.QueueUserWorkItem(new WaitCallback(SendTextThread), a);

            Thread t = new Thread(delegate()
            {
                SendTextThread(socket, sendStr);
            });
            t.Start();
        }


//=====================发送数据给服务端===========================================
        /// <summary>
        /// 给客户端发送初始化数据
        /// </summary>
        private void SendInitData(Socket socket)
        {
            //发送当前进度
            SendCurProgress(socket);
            Thread.Sleep(500);//使消息不连在一起
            //发送播放按钮状态
            SendButtonState(socket);

            Thread.Sleep(500);//使消息不连在一起

            //发送当前歌曲名
            SendName(socket);
            Thread.Sleep(500);//使消息不连在一起 TODO 改
            
            SendList(socket);


        }
        
        
        /// <summary>
        /// 发送歌曲名
        /// </summary>
        private void SendName(Socket socket)
        {
            string sendStr = Protocol.GetSendString(Protocol.NAME, Path.GetFileNameWithoutExtension(player.URL));
            SendText(socket, sendStr);
        }


        /// <summary>
        /// 给所有的客户端发送歌曲名
        /// </summary>
        private void SendName()
        {
            Socket[] socket = ClientList.getAllSocket();
            string sendStr = Protocol.GetSendString(Protocol.NAME, Path.GetFileNameWithoutExtension(player.URL));
            foreach (Socket s in socket)
            {
                SendText(s, sendStr);

            }
        }


        /// <summary>
        /// 发送音量大小
        /// </summary>
        /// <param name="socket"></param>
        private void SendSound(Socket socket)
        {
            string sendStr = player.settings.volume.ToString();
            sendStr = Protocol.GetSendString(Protocol.SOUND, sendStr);
            SendText(socket, sendStr);
        }


        /// <summary>
        /// 发送播放/暂停按钮的状态
        /// </summary>
        /// <param name="socket"></param>
        private void SendButtonState(Socket socket)
        {
            string sendStr;

            if (playing)
            {
                sendStr = Protocol.GetSendString(Protocol.PLAY);
            }
            else if (!playing)
            {
                sendStr = Protocol.GetSendString(Protocol.PAUSE);
            }
            else
                if (player.playState == WMPLib.WMPPlayState.wmppsPlaying)
                {
                    sendStr = Protocol.GetSendString(Protocol.PLAY);
                }
                else if (player.playState == WMPLib.WMPPlayState.wmppsPaused
                    || player.playState == WMPLib.WMPPlayState.wmppsStopped)
                {
                    sendStr = Protocol.GetSendString(Protocol.PAUSE);
                }
                else
                {
                    Tool.Log("按钮状态未知");
                    return;
                }

            SendText(socket, sendStr);
        }


        private void SendButtonState()
        {
            string sendStr;

            if (playing)
            {
                sendStr = Protocol.GetSendString(Protocol.PLAY);
            }
            else if (!playing)
            {
                sendStr = Protocol.GetSendString(Protocol.PAUSE);
            }
            else
                if (player.playState == WMPLib.WMPPlayState.wmppsPlaying)
                {
                    sendStr = Protocol.GetSendString(Protocol.PLAY);
                }
                else if (player.playState == WMPLib.WMPPlayState.wmppsPaused
                    || player.playState == WMPLib.WMPPlayState.wmppsStopped)
                {
                    sendStr = Protocol.GetSendString(Protocol.PAUSE);
                }
                else
                {
                    Tool.Log("按钮状态未知");
                    return;
                }

            Socket[] socket = ClientList.getAllSocket();
            foreach (Socket s in socket)
            {
                SendText(s, sendStr);

            }
        }
        
        /// <summary>
        /// 给客户端发送歌曲列表
        /// </summary>
        private void SendList(Socket socket)
        {
            string sendStr = null;
            foreach (string s in m_list)
            {
                sendStr += s + Protocol.getSeparator();
            }

            sendStr = Protocol.GetSendString(Protocol.SEND_LIST, sendStr);

            SendText(socket, sendStr);
        }


        /// <summary>
        /// 发送当前进度
        /// </summary>
        private void SendCurProgress(Socket socket)
        {
            int percent;
            try
            {
                if (player.currentMedia.duration == 0) //TODO 可以？
                {
                    percent = 0;
                }
                else
                {
                    percent = (int)(player.Ctlcontrols.currentPosition * 100.0 / player.currentMedia.duration);

                }

            }
            catch
            {
                percent = 0;
            }

            string sendStr = "" + percent;
            sendStr = Protocol.GetSendString(Protocol.PROGRESS, sendStr);

            SendText(socket, sendStr);
        }


        private void SendCurProgress()
        {
            int percent;
            try
            {
                if (player.currentMedia.duration == 0) //TODO 可以？
                {
                    percent = 0;
                }
                else
                {
                    percent = (int)(player.Ctlcontrols.currentPosition * 100.0 / player.currentMedia.duration);

                }

            }
            catch
            {
                percent = 0;
            }

            string sendStr = "" + percent;
            sendStr = Protocol.GetSendString(Protocol.PROGRESS, sendStr);

            Socket[] socket = ClientList.getAllSocket();
            foreach (Socket s in socket)
            {
                SendText(s, sendStr);
            }
            
        }

        private void btn_Click(object sender, EventArgs e)
        {
            Other.CloseComputer();

        }


        /*   二维码第三方的控件 我进行了二次封装 方便大家使用
         *   CreateTwoCode中有三个方法 
         *   一个是对图片二值化处理的方法   ctc.BitmapTo2Bpp(); 
         *   一个是原始的生成二维码方法,建议对二位相关概念比较熟悉的朋友使用   （调用这个方法时  建议用二值化处理一下，便于识别）  ctc.CreateCode()
         *   一个是新的生成二维码方法,建议只是为了生成二维码的朋友使用   （这个方法不需要再调用二值化处理方法）  ctc.CreateNewCode();
         * 
         *    如果有什么不懂的可以加我Q 1093991777
         *    或者等我的博客  里面有对二维码和一维码的详细介绍 
         *   
         * 
       */
        private void button1_Click(object sender, EventArgs e)
        {
            

        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            

        }

      
        


//================================================================

        
    }

}

//TODO 每一个客户端退出时对应的线程应关闭

/*
 public class MyTcpServer
{
    public MyTcpServer()
    {
        IPEndPoint localEndPoint = new IPEndPoint(_ipAddress, _port);
        Socket socket = new Socket(AddressFamily.InterNetwork, 
                                            SocketType.Stream, 
                                            ProtocolType.Tcp); 
        socket.Bind(localEndPoint);
        socket.Listen(20); 
        //启动异步Accept
        socket.BeginAccept(AcceptCallBack, socket); 
    }
     
    void AcceptCallBack(IAsyncResult ar)
    {
        Socket socket = ar.AsyncState as Socket
        //结束异步Accept并获已连接的Socket
        Socket connection = socket.EndAccept(ar); 
         
        // 通过connection收发操作的代码略……
         
        //继续异步Accept，保持Accept一直开启！
        socket.BeginAccept(AcceptCallBack, socket); 
    }
}
 */