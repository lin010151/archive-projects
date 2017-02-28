using System;
using System.Data;
using System.Data.OleDb;
using System.Windows.Forms;


namespace mySchool
{
    //数据库类，负责直接操作数据库
    class DataBase
    {
        private static string m_path = "C:/mySchool.mdb";    //存放数据库文件的文件夹
        private static OleDbConnection m_connect = null;


        //打开数据库
        public static bool Open()
        {
            try
            {
                //string st = string.Format("INSERT INTO [account] VALUES('{0}', '{1}')", "11111", "22222"); 
                //@"Provider=Microsoft.ACE.OLEDB.12.0;Data Source=数据库名.accdb"
                m_connect = new OleDbConnection("Provider=Microsoft.Jet.OLEDB.4.0;Data Source=" + m_path);
                m_connect.Open();
               
                return true;

            }
            catch (Exception error)
            {
                MessageBox.Show("错误：" + error.ToString());  //改
                return false;
            }
           
        }


        //关闭数据库
        public static bool Close()
        {
            //m_connect.Close();    //注意异常
            return true;
        }


        //执行一条sql语句
        //【返回】执行成功返回true，出错返回false
        public static bool ExecuteSql(string sql)
        {
            try
            {
                OleDbCommand cmd = new OleDbCommand(sql, m_connect);
                cmd.ExecuteReader();
            }
            catch (Exception error)
            {
                MessageBox.Show("错误：" + error.ToString());
            }
            return true;
        }


        public static bool CreateDataBase(string DBName)
        {
            //ADOX.CatalogClass cat = new ADOX.CatalogClass();
            return true;
        }


        //注意已经存在的情况
        public static bool CreateTable(string tableName)
        {
            if (tableName == "")
            {
                return false;
            }

            try
            {           
                string st = string.Format("CREATE table table2(name int identity (1,1) not null, userName char(10) not null, pid char(18) not null, tel char(15) not null, address varchar(50))");
               
                OleDbCommand cmd = new OleDbCommand(st, m_connect);
                cmd.ExecuteReader();
            }
            catch (Exception error)
            {
                MessageBox.Show("错误2：" + error.ToString());  //改
            }
           
            return true;
        }
    

        //注意不存在的情况
        //完全删除表
        public static bool DeleteTable(string tableName)
        {
            string sql = string.Format("DROP TABLE " + tableName);
            return ExecuteSql(sql);  
        }


        //清空表
        public static bool ClearTable(string tableName)
        {
            string sql = string.Format("TURNCATE TABLE " + tableName);
            return ExecuteSql(sql);  
        }



        //获得所有学校名的数组
        public static string[] GetAllSchool()
        {
            string[] strs = {"123", "456"};
            try
            {
                string st = string.Format("SELECT school FROM [school]");

                OleDbCommand cmd = new OleDbCommand(st, m_connect);
                OleDbDataReader reader = cmd.ExecuteReader();
                
                while (reader.Read())
                {
                    reader.ToString();
                }

                reader.Close();

            }
            catch (Exception error)
            {
                MessageBox.Show("错误：" + error.ToString());  //改
            }
           
            return strs;
        }
    
    
        //检查账号的密码是否匹配
        public static bool Check(string account, string password)
        {
            try
            {
             
                string st = string.Format("SELECT * FROM [account] WHERE account = '{0}' AND password = '{1}'", account, password);

                OleDbCommand cmd = new OleDbCommand(st, m_connect);
                OleDbDataReader reader = cmd.ExecuteReader();

                if (reader.HasRows)
                {
                    reader.Close();
                    return true;
                }
                else
                {
                    reader.Close();
                    return false;
                }

            }
            catch (Exception error)
            {
                MessageBox.Show("错误：" + error.ToString());  //改
                return false;
            }
           
        }


        public static bool AddData(string name)
        {
            return true;
        }


        public static bool GetTables(OleDbConnection conn)        
        {              
            int result = 0;              
            DataTable schemaTable =  conn.GetOleDbSchemaTable(OleDbSchemaGuid.Tables,                                                               new object[] {null, null, null, "TABLE"});                
            if (schemaTable != null)           
            {                  
                for (int row = 0; row < schemaTable.Rows.Count; row++)
                {                  
                    string col_name =  schemaTable.Rows[row]["TABLE_NAME"].ToString();                     
                    if (col_name == "MyChooseStock")  
                    {                     
                        result++;            
                    }                
                }           
            }             
            if (result == 0)             
                return false; 
            return true;         
        }          




    }


}
