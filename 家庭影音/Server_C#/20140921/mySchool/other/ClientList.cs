using System;
using System.Collections;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Text;

namespace mySchool.other
{
    /// <summary>
    /// 客户端列表
    /// </summary>
    class ClientList
    {
        private static int newId = 1;
        private static Hashtable map = new Hashtable();

        //为每一个用户分配一个不同的id号
        private static string getNewID()
        {
            string id = newId.ToString();
            newId++;
            return id;
        }


        public static string Add(Socket socket)
        {
            string id = getNewID();

            if (!map.Contains(id))
            {
                Tool.Log("添加一个socket，ID为：" + id);
                map.Add(id, socket);
                return id;
            }
            else
            {
                Tool.Log("id错误");
                return null;
            }
          
        }


        public static void Remove(string id)
        {
            Tool.Log("移去一个socket，值为：" + id);
            Socket socket = getSocket(id);
            socket.Close();
            map.Remove(id);
        }


        public static bool isEmpty()
        {
            if (map.Count == 0)//TODO fasel
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    
    
        public static bool haveId(string id)
        {
            return map.Contains(id);
        }
    

        public static Socket[] getAllSocket()
        {
            List<Socket> list = new List<Socket>();
            list.Clear();

            foreach (DictionaryEntry de in map)
            {
                list.Add((Socket)de.Value);
            }
          
            Socket[] s = list.ToArray();
            return s;
        }
    

        public static Socket getSocket(string id)
        {
            return (Socket)map[id];

        }
    
    
        public static int GetClientCount()
        {
            return map.Count;
        }
    


    }
}
