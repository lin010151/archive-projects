namespace mySchool
{
    partial class MainForm
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.btn = new System.Windows.Forms.Button();
            this.btnBrowse = new System.Windows.Forms.Button();
            this.lblPath = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.lblClientState = new System.Windows.Forms.Label();
            this.lblMsg = new System.Windows.Forms.Label();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.comboBox1 = new System.Windows.Forms.ComboBox();
            this.txtPort = new System.Windows.Forms.TextBox();
            this.lblServerState = new System.Windows.Forms.Label();
            this.btnCreateServer = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.player = new AxWMPLib.AxWindowsMediaPlayer();
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.创建服务器ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.帮助ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.TSMIAbout = new System.Windows.Forms.ToolStripMenuItem();
            this.folderBrowseDlg = new System.Windows.Forms.FolderBrowserDialog();
            this.lbMusic = new System.Windows.Forms.ListBox();
            this.timer = new System.Windows.Forms.Timer(this.components);
            this.groupBox3.SuspendLayout();
            this.groupBox1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.player)).BeginInit();
            this.menuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // groupBox3
            // 
            this.groupBox3.Controls.Add(this.btn);
            this.groupBox3.Controls.Add(this.btnBrowse);
            this.groupBox3.Controls.Add(this.lblPath);
            this.groupBox3.Controls.Add(this.label3);
            this.groupBox3.Location = new System.Drawing.Point(701, 421);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(411, 162);
            this.groupBox3.TabIndex = 13;
            this.groupBox3.TabStop = false;
            this.groupBox3.Text = "共享文件夹";
            // 
            // btn
            // 
            this.btn.Location = new System.Drawing.Point(220, 97);
            this.btn.Name = "btn";
            this.btn.Size = new System.Drawing.Size(75, 35);
            this.btn.TabIndex = 14;
            this.btn.Text = "button1";
            this.btn.UseVisualStyleBackColor = true;
            this.btn.Click += new System.EventHandler(this.button1_Click);
            // 
            // btnBrowse
            // 
            this.btnBrowse.Location = new System.Drawing.Point(45, 97);
            this.btnBrowse.Name = "btnBrowse";
            this.btnBrowse.Size = new System.Drawing.Size(89, 35);
            this.btnBrowse.TabIndex = 13;
            this.btnBrowse.Text = "浏览";
            this.btnBrowse.UseVisualStyleBackColor = true;
            this.btnBrowse.Click += new System.EventHandler(this.btnBrowse_Click);
            // 
            // lblPath
            // 
            this.lblPath.AutoSize = true;
            this.lblPath.Location = new System.Drawing.Point(167, 59);
            this.lblPath.Name = "lblPath";
            this.lblPath.Size = new System.Drawing.Size(120, 22);
            this.lblPath.TabIndex = 12;
            this.lblPath.Text = "C:\\fmMusic";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(41, 59);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(76, 22);
            this.label3.TabIndex = 11;
            this.label3.Text = "路径：";
            // 
            // lblClientState
            // 
            this.lblClientState.Location = new System.Drawing.Point(25, 373);
            this.lblClientState.Name = "lblClientState";
            this.lblClientState.Size = new System.Drawing.Size(380, 22);
            this.lblClientState.TabIndex = 3;
            this.lblClientState.Text = "没有客户端连接";
            // 
            // lblMsg
            // 
            this.lblMsg.AutoSize = true;
            this.lblMsg.Location = new System.Drawing.Point(450, 373);
            this.lblMsg.Name = "lblMsg";
            this.lblMsg.Size = new System.Drawing.Size(54, 22);
            this.lblMsg.TabIndex = 4;
            this.lblMsg.Text = "消息";
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.comboBox1);
            this.groupBox1.Controls.Add(this.txtPort);
            this.groupBox1.Controls.Add(this.lblServerState);
            this.groupBox1.Controls.Add(this.btnCreateServer);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Controls.Add(this.label2);
            this.groupBox1.Location = new System.Drawing.Point(0, 407);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(633, 162);
            this.groupBox1.TabIndex = 9;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "服务器";
            this.groupBox1.Enter += new System.EventHandler(this.groupBox1_Enter);
            // 
            // comboBox1
            // 
            this.comboBox1.FormattingEnabled = true;
            this.comboBox1.Location = new System.Drawing.Point(272, 51);
            this.comboBox1.Name = "comboBox1";
            this.comboBox1.Size = new System.Drawing.Size(333, 30);
            this.comboBox1.TabIndex = 11;
            this.comboBox1.SelectedIndexChanged += new System.EventHandler(this.comboBox1_SelectedIndexChanged);
            // 
            // txtPort
            // 
            this.txtPort.Location = new System.Drawing.Point(281, 92);
            this.txtPort.Name = "txtPort";
            this.txtPort.Size = new System.Drawing.Size(100, 32);
            this.txtPort.TabIndex = 10;
            this.txtPort.Text = "1234";
            // 
            // lblServerState
            // 
            this.lblServerState.AutoSize = true;
            this.lblServerState.Location = new System.Drawing.Point(25, 39);
            this.lblServerState.Name = "lblServerState";
            this.lblServerState.Size = new System.Drawing.Size(142, 22);
            this.lblServerState.TabIndex = 2;
            this.lblServerState.Text = "服务器未创建";
            // 
            // btnCreateServer
            // 
            this.btnCreateServer.Location = new System.Drawing.Point(25, 102);
            this.btnCreateServer.Margin = new System.Windows.Forms.Padding(4);
            this.btnCreateServer.Name = "btnCreateServer";
            this.btnCreateServer.Size = new System.Drawing.Size(139, 34);
            this.btnCreateServer.TabIndex = 0;
            this.btnCreateServer.Text = "创建服务器";
            this.btnCreateServer.UseVisualStyleBackColor = true;
            this.btnCreateServer.Click += new System.EventHandler(this.btnCreateServer_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(210, 54);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(43, 22);
            this.label1.TabIndex = 6;
            this.label1.Text = "IP:";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(210, 95);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(65, 22);
            this.label2.TabIndex = 7;
            this.label2.Text = "端口:";
            // 
            // player
            // 
            this.player.Enabled = true;
            this.player.Location = new System.Drawing.Point(0, 31);
            this.player.Name = "player";
            this.player.OcxState = ((System.Windows.Forms.AxHost.State)(resources.GetObject("player.OcxState")));
            this.player.Size = new System.Drawing.Size(619, 323);
            this.player.TabIndex = 14;
            this.player.PlayStateChange += new AxWMPLib._WMPOCXEvents_PlayStateChangeEventHandler(this.axWindowsMediaPlayer1_PlayStateChange);
            this.player.Enter += new System.EventHandler(this.axWindowsMediaPlayer1_Enter);
            // 
            // menuStrip1
            // 
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.创建服务器ToolStripMenuItem,
            this.帮助ToolStripMenuItem});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(1027, 28);
            this.menuStrip1.TabIndex = 15;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // 创建服务器ToolStripMenuItem
            // 
            this.创建服务器ToolStripMenuItem.Name = "创建服务器ToolStripMenuItem";
            this.创建服务器ToolStripMenuItem.Size = new System.Drawing.Size(116, 24);
            this.创建服务器ToolStripMenuItem.Text = "创建服务器(&C)";
            // 
            // 帮助ToolStripMenuItem
            // 
            this.帮助ToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.TSMIAbout});
            this.帮助ToolStripMenuItem.Name = "帮助ToolStripMenuItem";
            this.帮助ToolStripMenuItem.Size = new System.Drawing.Size(73, 24);
            this.帮助ToolStripMenuItem.Text = "帮助(&H)";
            // 
            // TSMIAbout
            // 
            this.TSMIAbout.Name = "TSMIAbout";
            this.TSMIAbout.Size = new System.Drawing.Size(129, 24);
            this.TSMIAbout.Text = "关于(&A)";
            this.TSMIAbout.Click += new System.EventHandler(this.TSMIAbout_Click);
            // 
            // folderBrowseDlg
            // 
            this.folderBrowseDlg.HelpRequest += new System.EventHandler(this.folderBrowseDlg_HelpRequest);
            // 
            // lbMusic
            // 
            this.lbMusic.FormattingEnabled = true;
            this.lbMusic.ItemHeight = 22;
            this.lbMusic.Location = new System.Drawing.Point(644, 31);
            this.lbMusic.Name = "lbMusic";
            this.lbMusic.Size = new System.Drawing.Size(371, 312);
            this.lbMusic.TabIndex = 16;
            this.lbMusic.SelectedIndexChanged += new System.EventHandler(this.lbMusic_SelectedIndexChanged);
            // 
            // timer
            // 
            this.timer.Interval = 1000;
            this.timer.Tick += new System.EventHandler(this.timer_Tick);
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(11F, 22F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(128)))), ((int)(((byte)(255)))), ((int)(((byte)(128)))));
            this.ClientSize = new System.Drawing.Size(1027, 595);
            this.Controls.Add(this.lbMusic);
            this.Controls.Add(this.lblMsg);
            this.Controls.Add(this.lblClientState);
            this.Controls.Add(this.player);
            this.Controls.Add(this.groupBox3);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.menuStrip1);
            this.Font = new System.Drawing.Font("宋体", 13F);
            this.MainMenuStrip = this.menuStrip1;
            this.Margin = new System.Windows.Forms.Padding(4);
            this.Name = "MainForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "家庭影音";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.Form1_FormClosing);
            this.Load += new System.EventHandler(this.Form1_Load);
            this.groupBox3.ResumeLayout(false);
            this.groupBox3.PerformLayout();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.player)).EndInit();
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.GroupBox groupBox3;
        private System.Windows.Forms.Button btn;
        private System.Windows.Forms.Button btnBrowse;
        private System.Windows.Forms.Label lblPath;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label lblClientState;
        private System.Windows.Forms.Label lblMsg;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Label lblServerState;
        private System.Windows.Forms.Button btnCreateServer;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private AxWMPLib.AxWindowsMediaPlayer player;
        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem 创建服务器ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem 帮助ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem TSMIAbout;
        private System.Windows.Forms.FolderBrowserDialog folderBrowseDlg;
        private System.Windows.Forms.TextBox txtPort;
        private System.Windows.Forms.ListBox lbMusic;
        private System.Windows.Forms.Timer timer;
        private System.Windows.Forms.ComboBox comboBox1;


    }
}

