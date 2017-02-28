using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Net.NetworkInformation;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Windows.Forms;
using mySchool.other;


namespace mySchool
{
    /// <summary>
    /// 服务器类，负责与客户端的所有交互
    /// </summary>
    class MyServer
    {
        private static Socket       m_socket = null;    //服务器的socket
        private static int         m_port = 1234;      //端口号
        private static IPAddress    m_serverIp;         //服务器的IP地址


        /// <summary>
        /// 获取本机IP
        /// </summary>
        /// <returns></returns>
        public static string GetServerIP()
        {
            return m_serverIp.ToString();
            //IPAddress ServerIp = Dns.GetHostEntry(Dns.GetHostName()).AddressList[2];
            //return ServerIp.ToString();
        }


        /// <summary>
        /// 获取端口号
        /// </summary>
        /// <returns></returns>
        public static string GetPort()
        {
            return "" + m_port;
        }


        public static Socket GetServerSocket()
        {
            return m_socket;
        }


        /// <summary>
        /// 创建服务器
        /// </summary>
        /// <param name="ip">IP地址</param>
        /// <param name="port">端口号</param>
        /// <returns></returns>
        public static bool CreateServer(string ip, int port)
        {
            // TODO 异常为处理 当为联网时
            IPAddress IPadr = IPAddress.Parse(ip.Split(':')[0]); //先把string类型转换成IPAddress类型
            IPEndPoint EndPoint;
            if (IPadr != null)
            {
                EndPoint = new IPEndPoint(IPadr, port);//传递IPAddress和Port

            }
            else
            {
                MessageBox.Show("没有得到正确IP地址！！");
                return false;
            }

            m_port = port;
            m_serverIp = IPadr;

            try
            {
                m_serverIp = IPadr;
               

                m_socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
                m_socket.Bind(EndPoint);


                return true;
            }
            catch (Exception el)
            {
                MessageBox.Show("创建服务器失败：" + el.Message);
                return false;
            }
        

        }


        /// <summary>
        /// 获取本地IP地址
        /// </summary>
        /// <returns></returns>
        public static IPAddress GetLocalIP()
        {
            // string strAddr = "";
            IPAddress IP = null;
            try
            {
                string strHostName = System.Net.Dns.GetHostName();
                System.Net.IPHostEntry ipEntry = System.Net.Dns.GetHostEntry(strHostName);


            
                IPGlobalProperties ipInfo = IPGlobalProperties.GetIPGlobalProperties();
                //返回有关本地计算机上的 Internet 协议版本 4 (IPV4) 传输控制协议 (TCP) 连接的信息。 
                TcpConnectionInformation[] ipStaticIp4 = ipInfo.GetActiveTcpConnections();

                //获取本地IP终结点
                IPEndPoint localEndPoint = ipStaticIp4[ipStaticIp4.Length - 1].LocalEndPoint;
                //string localIp = localEndPoint.Address.ToString();
                IP = localEndPoint.Address;
              

                /*
                foreach (IPAddress _ipaddress in ipEntry.AddressList)
                {
                          //MessageBox.Show(_ipaddress.AddressFamily.ToString());
                    if (_ipaddress.AddressFamily.ToString().ToLower() == "internetwork")
                    {
                        //strAddr = _ipaddress.ToString();
                        IP = _ipaddress;
                        break;
                    }
                }
                */
                //  Log.Debug("  IP:" + strAddr);
            }
            catch (Exception e)
            {
                //Log.Error("  Get IP Address Error:" + e.Message);
                MessageBox.Show("没有获得主机的IP！！！");
            }

            return IP;
        }


        /// <summary>
        /// 获得所有的IP地址
        /// </summary>
        /// <returns></returns>
        public static string[] GetLocalIPs()
        {
 
            try
            {
                string strHostName = Dns.GetHostName();
                IPHostEntry ipEntry = Dns.GetHostEntry(strHostName);
               

                string[] result = new string[ipEntry.AddressList.Length];
                int i = 0;
                foreach (IPAddress _ipaddress in ipEntry.AddressList)
                {
                    result[i] = _ipaddress.ToString();
                    i++;
                }
                return result;
                /*
                IPGlobalProperties ipInfo = IPGlobalProperties.GetIPGlobalProperties();
                //返回有关本地计算机上的 Internet 协议版本 4 (IPV4) 传输控制协议 (TCP) 连接的信息。 
                TcpConnectionInformation[] ipStaticIp4 = ipInfo.GetActiveTcpConnections();

                //获取本地IP终结点
                IPEndPoint localEndPoint = ipStaticIp4[ipStaticIp4.Length - 1].LocalEndPoint;
                //string localIp = localEndPoint.Address.ToString();
                IP = localEndPoint.Address;
                */

                //  Log.Debug("  IP:" + strAddr);
            }
            catch (System.Exception e)
            {
                //Log.Error("  Get IP Address Error:" + e.Message);
                MessageBox.Show("没有获得主机的IP！！！");
                return null;
            }

          
            //return IP.ToString();
           
        }


        public static string GetLocalIpv4()
        {
            try
            {
                // 获得网络接口，网卡，拨号器，适配器都会有一个网络接口 
                NetworkInterface[] networkInterfaces = NetworkInterface.GetAllNetworkInterfaces();

                foreach (NetworkInterface network in networkInterfaces)
                {
                    // 获得当前网络接口属性
                    IPInterfaceProperties properties = network.GetIPProperties();

                    // 每个网络接口可能会有多个IP地址 
                    foreach (IPAddressInformation address in properties.UnicastAddresses)
                    {
                        // 如果此IP不是ipv4，则进行下一次循环
                        if (address.Address.AddressFamily != AddressFamily.InterNetwork)
                            continue;

                        // 忽略127.0.0.1
                        if (IPAddress.IsLoopback(address.Address))
                            continue;

                        return address.Address.ToString();
                    }
                }
            }

            catch (Exception ex)
            {
                MessageBox.Show("Error: " + ex.Message);
            }

            return null;
        }


        /// <summary>
        /// 从客户端接受文字
        /// </summary>
        /// <param name="socket">socket</param>
        /// <returns>返回接收到的字符串，出现异常、出错返回null，客户端退出返回"退出"</returns>
        public static string ReceiveText(Socket socket)
        {
            if (m_socket == null)
            {
                return null;
            }

            if (socket == null)
            {
                return null;
            }

            const int LEN = 5000;
            string recvStr = "";
            byte[] recvBytes = new byte[LEN];
            int bytes;

            try
            {
                bytes = socket.Receive(recvBytes, recvBytes.Length, 0); //从客户端接受消息

                recvStr += Encoding.UTF8.GetString(recvBytes, 0, bytes);

                if (recvStr.Equals(""))
                {
                    Tool.Log("空 退出");
                    return "退出";
                }
                else
                {
                     return recvStr;

                }
            }
            catch (Exception e)
            {
                Tool.Log("可能客户端已退出");
                return "退出";
            }
        }


        /// <summary>
        /// 发送文字给客户端
        /// </summary>
        /// <param name="socket"></param>
        /// <param name="sendStr"></param>
        /// <returns>成功返回true，失败返回false</returns>
        public static bool SendText(Socket socket, string sendStr)
        {
            if (socket == null)
            {
                return false;
            }


            try
            {
                byte[] bs = Encoding.UTF8.GetBytes(sendStr);
                socket.Send(bs, bs.Length, 0);  //返回信息给客户端
                return true;
            }
            catch (Exception)
            {
                return false;
            }

        }

   
        /// <summary>
        /// 是否已经创建了服务器
        /// </summary>
        /// <returns>返回是否已经创建了服务器</returns>
        public static bool haveCreate()
        {
            if (m_socket == null)
            {
                return false;
            }
            else
            {
                return true;
            }
        }


        /// <summary>
        /// 接收文件
        /// </summary>
        /// <param name="acceptSock"></param>
        /// <returns>成功返回0，失败返回非0，异常返回1</returns>
        public static int ReceiveFile(object acceptSock)
        {
            
            bool flag = false;
            Socket recvSocket = (Socket)acceptSock;
           

            byte[] byteArry = new byte[256];

            string folderPath = "C:/";

            //如果目录不存在就创建
            string path = "recvVoice";
            if (!Directory.Exists(path))
            {
                Directory.CreateDirectory(path);
            }
            
            string strpath = "recvVoice/123.wav";    //OK
            //string strpath = "‪C:/123.wav";     //NO
            //string strpath = "F:/Users/cjh1/Desktop/mySchool-服务端/mySchool";     //NO

            byte[] buffLen = new byte[50];
            int count = recvSocket.Receive(buffLen);
            string strRec = System.Text.Encoding.Default.GetString(buffLen);
            string fLen = strRec.Substring(0, strRec.IndexOf("\n"));    //客户端退出时异常,以及长度不能小于0
            int fileLen = 0;
            try
            {
                fileLen = Convert.ToInt32(fLen);
            }
            catch (Exception el)
            {
                MessageBox.Show(el.Message);
            }
          


            int recCount = 0;
            int readLen = 0;
            MemoryStream memStream = new MemoryStream();
            try
            {
                while ((recCount = recvSocket.Receive(byteArry)) > 0)
                {
                    readLen = readLen + recCount;
                    if (readLen >= fileLen)
                    {
                        memStream.Write(byteArry, 0, recCount);
                        flag = true;
                        break;
                    }
                    memStream.Write(byteArry, 0, recCount);
                    flag = true;
                }
            }
            catch (Exception el)
            {
                flag = false;
                memStream.Close();
                //   fs.Close();
                memStream = null;
                //  fs = null;
                MessageBox.Show(el.Message);
                ReceiveFile(acceptSock);
                return Return.EXCEPTION;
            }

            if (flag)
            {
                FileStream fs = new FileStream(strpath, FileMode.OpenOrCreate);
                memStream.WriteTo(fs);
                fs.Close();
                fs = null;
                
                IPAddress clientIp = ((System.Net.IPEndPoint)recvSocket.RemoteEndPoint).Address;
                string strip = clientIp.ToString();

                //int index = socketIpList.IndexOf(strip);
                //AddVoiceToListBox(socketNameList[index] + "****" + socketIpList[index], strpath, flag);
            }
            flag = false;
            memStream.Close();
            memStream = null;
            ReceiveFile(acceptSock);
            MessageBox.Show("12345");
            return Return.SUCCEED;
        }


        /// <summary>
        /// 发送文件
        /// </summary>
        /// <param name="acceptSock">客户端的socket</param>
        /// <param name="vpath">要发送的文件的完整路径</param>
        /// <returns></returns>
        public static bool SendFile(Socket acceptSock, string vpath)
        {
            try
            {
                Socket sendSocket = (Socket)acceptSock;
                byte[] byteArray = new byte[100];
                //sendSocket.Receive(byteArray);//接收数据
                //将字节数组转成字符串
                string strRec = System.Text.Encoding.Default.GetString(byteArray);
                // MessageBox.Show(strRec);
                Stream st = File.OpenRead(vpath);   //异常
                Byte[] b = new Byte[1024];

                string strlen = st.Length.ToString();
                byte[] bytes = System.Text.Encoding.Default.GetBytes(strlen + "\r\n");
                sendSocket.Send(bytes);



                int count = 0;
                int sent = 0;
                int offset = 0;
                int left = 0;
                Thread.Sleep(1000);           //重点哦
                while (offset < st.Length)
                {
                    //buffer.CopyTo(b, 1024);
                    count = st.Read(b, 0, 1024);
                    offset = offset + count;
                    left = Convert.ToInt32(st.Length) - offset;
                    if (left <= 1024)
                    {
                        byte[] temp = new byte[left];
                        sent = sendSocket.Send(b);
                        // Thread.Sleep(10);
                        count = st.Read(b, 0, left);
                        sent = sendSocket.Send(temp);
                        break;
                    }
                    sent = sendSocket.Send(b);

                }

                IPAddress clientIp = ((System.Net.IPEndPoint)sendSocket.RemoteEndPoint).Address;
                string strip = clientIp.ToString();

                //int index = socketIpList.IndexOf(strip);
                //AddVoiceToListBox("Server Send To ****" + socketNameList[index], vpath, true);

            }
            catch (Exception err)
            {
                MessageBox.Show(err.GetType() + ":" + err.Message);
                return false;
            }

            return true;
        }






        
        
    }
}
