package com.fm.activity;

import android.app.Activity;
import android.app.AlertDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.fm.util.App;
import com.fm.util.SDCardFile;
import com.fm.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


//文件浏览器窗口
public class FileActivity extends Activity
{
	private SimpleAdapter		m_adapter;
	private Button				m_btnOk;				//确定按钮
	private ListView			m_lvFile;				//显示文件的列表
	private ArrayList<String>	m_fileNameList;				//用户选择的文件的文件名
	private ArrayList<String>	m_pathList;					//用户选择的文件的文件完整路径
	private String				m_curFolder = "/sdcard";		//当前文件夹
	private Button				m_btnCancel;			//取消粘贴按钮
	private Button				m_btnPaste;				//粘贴按钮
	private Button				m_btnNew;				//新建按钮
	private Button				m_btnFinish;			//完成按钮
	
	private int					m_operatorType = 0;		//操作类型，0为没有，1为复制，2为剪切	
	private String				m_operatorPath = "";	//要复制或剪切的文件路径
	
	//用户需要保持的数据
	private int					m_sortType = 1;			//排序发送
	private int					m_mode = 1;				//模式，0为文件管理器， 1为选择文件，2为选择文件夹
	
	private ArrayList<HashMap<String, Object>>	m_listItem;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file);
		
		//初始化控件
		m_btnOk = (Button) findViewById(R.id.btnSend);
		m_lvFile = (ListView) findViewById(R.id.fileView);
		m_btnCancel = (Button) findViewById(R.id.btnCancel);
		m_btnPaste = (Button) findViewById(R.id.btnPaste);
		m_btnNew = (Button) findViewById(R.id.btnNew);
		m_btnFinish = (Button) findViewById(R.id.btnFinish);
		m_btnCancel.setEnabled(false);
		m_btnPaste.setEnabled(true);
		m_btnFinish.setEnabled(false);
		m_fileNameList = new ArrayList<String>();
		m_pathList = new ArrayList<String>();		
		m_listItem = new ArrayList<HashMap<String, Object>>();	
		
		App.toast("选择要发送的文件");

		//默认打开目录
		this.FilesListView(Environment.getExternalStorageDirectory().getPath());
		m_adapter = new SimpleAdapter(
				FileActivity.this, m_listItem, R.layout.item_list_file,
				new String[] {"image", "name", "path", "type", "parent", "select"},
				new int[] {R.id.image, R.id.file_name, R.id.file_path, R.id.file_type, R.id.file_parent, R.id.select}
		);
		
		
		//m_lvFile.setOnItemClickListener(listener);
		
		
		m_lvFile.setAdapter(m_adapter);
		
		//长按列表
		m_lvFile.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int arg2, long arg3) 
			{
				TextView isDirectory = (TextView) view.findViewById(R.id.file_type); 
				final TextView path = (TextView) view.findViewById(R.id.file_path);
				TextView name = (TextView) view.findViewById(R.id.file_name);
				final String seletePath = path.getText().toString(); 
				
				
				
				//Toast.makeText(FilesViewActivity.this, "shi" + arg2, Toast.LENGTH_SHORT).show();
				
				AlertDialog.Builder builder = new AlertDialog.Builder(FileActivity.this);
				builder.setTitle("请选择操作");
				
				
				DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						switch (which)
						{
						case 0:	//打开					
							openFileItem(seletePath);
							break;
						case 1:	//重命名
							renameItem(seletePath);
							break;
						case 2:	//删除
							deleteItem(seletePath);
							break;
						case 3:	//复制
							copyItem(seletePath);
							break;
						case 4:	//剪切
							cutItem(seletePath);
							break;
						}
					}
				};
				
			
				String[] str = { "打开", "重命名", "删除", "复制", "剪切"};
				builder.setItems(str, listener);
			
				//builder.setNegativeButton("确定", null);
				builder.show();
				
				
				
				
				return true;	//不能return false
			}
		});

		//点击列表
		m_lvFile.setOnItemClickListener(new OnItemClickListener()
		{
			//点击选项
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				TextView isDirectory = (TextView) view.findViewById(R.id.file_type); 
				TextView path = (TextView) view.findViewById(R.id.file_path);
				TextView name = (TextView) view.findViewById(R.id.file_name);
				
				//如果是文件夹	？？
				if (Boolean.parseBoolean(isDirectory.getText().toString()))
				{
					//进入目录
					m_curFolder = path.getText().toString();	//后退的情况？？？
					FilesListView(path.getText().toString());
					m_adapter.notifyDataSetChanged();
					m_fileNameList.clear();		//不能同时选中不同文件夹内的文件
					m_pathList.clear();
					SetSendButtonEnabled();
				}
				else
				{
					//选择文件
					ImageView ImgSelect = (ImageView) view.findViewById(R.id.select);
					if (Integer.parseInt(ImgSelect.getTag().toString()) == 1)
					{
						ImgSelect.setImageResource(R.drawable.unselected);
						ImgSelect.setTag(0);	//tag为0表示为选中，为1表示选中
						m_fileNameList.remove(name.getText().toString());
						m_pathList.remove(path.getText().toString());
					}
					else
					{
						ImgSelect.setImageResource(R.drawable.selected);
						ImgSelect.setTag(1);
						m_fileNameList.add(name.getText().toString());
						m_pathList.add(path.getText().toString());
					}
					SetSendButtonEnabled();
					
					if (m_pathList.size() > 0)
					{
						m_btnFinish.setEnabled(true);
					}
					else
					{
						m_btnFinish.setEnabled(false);
					}
					
				}
			}
			
			
		});
		
		
		m_btnOk.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent();
				intent.putStringArrayListExtra("fileName", m_fileNameList);
				intent.putStringArrayListExtra("safeFileName", m_pathList);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		
		
		m_btnCancel.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				m_operatorType = 0;
				m_btnCancel.setEnabled(false);
				m_btnPaste.setEnabled(false);
			}
		});
		
		
		m_btnPaste.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				//执行粘贴操作
				if (m_operatorType == 1)	//复制
				{
					if (SDCardFile.copyFile(m_operatorPath, m_curFolder))
					{
						App.toast("复制成功");
					}
					else
					{
						App.toast("复制失败");
					}
				}
				else	//剪切
				{
					if (SDCardFile.cutFile(m_operatorPath, m_curFolder))
					{
						App.toast("剪切成功");
					}
					else
					{
						App.toast("剪切失败");
					}
				}
				
				updateFileList();
				
				m_operatorType = 0;
				m_btnCancel.setEnabled(false);
				m_btnPaste.setEnabled(false);
			}
		});
		
	
		m_btnNew.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				newItem();
			}
		});
		
		
		m_btnFinish.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(FileActivity.this);
				builder.setTitle("请选择：");
				
				String[] str = { "删除", "复制", "剪切" };
				builder.setItems(str, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						switch (which)
						{
						case 0:		//删除多个文件
							AlertDialog.Builder builder = new AlertDialog.Builder(FileActivity.this);
					        builder.setTitle("确定删除多个文件?");
					        builder.setCancelable(false);
					        builder.setPositiveButton("取消", null);
					        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() 
					        {
					            public void onClick(DialogInterface dialog, int which)
					            { 
					            	boolean succeed = true;
									for (String path : m_pathList)
									{
										if (!SDCardFile.deleteFile(path))
										{
											succeed = false;
										}
									}
									if (succeed)
									{
										App.toast("删除成功");
										updateFileList();
									}
									else
									{
										App.toast("删除失败");
										//删除部分的情况	？？？
									}
					            }
					        });
					        builder.show();
					     
							break;
						case 1:
							break;
						case 2:
							break;
						}
					}
				});
			
				builder.show();
			}
		});
	}
	
	
	//新建菜单选项
	private void newItem()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(FileActivity.this);
		builder.setTitle("新建：");
		
		String[] str = { "文件夹", "文件" };
		builder.setItems(str, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, final int which)
			{
				final EditText txtNewName = new EditText(FileActivity.this);
		        txtNewName.setFocusable(true);
		        
				AlertDialog.Builder builder = new AlertDialog.Builder(FileActivity.this);
		        if (which == 0)
	        	{
		        	builder.setTitle("新建文件夹");
	        	}
		        else
		        {
		        	builder.setTitle("新建文件");
		        }
		        builder.setMessage("请输入文件名：");
		        builder.setView(txtNewName);
		        builder.setCancelable(false);
		        builder.setNegativeButton("取消", null);
		        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() 
		        {
		            public void onClick(DialogInterface dialog, int which2)	//防止与which混淆
		            { 
		            	final String input = txtNewName.getText().toString().trim();
		            	
		            	if (input.equals(""))
		            	{
		            		App.toast("文件名不能为空");
		            		return;
		            	}
		            	
		            	final String newName = m_curFolder + "/" + input;
		            	//！同名 自动输入原来的文件名
						if(new File(newName).exists())	//如果文件存在同名文件
						{
							App.toast("存在同名文件，创建失败");
						}
						else	//不存在同名文件，直接重命名
						{
							if (which == 0)
							{
								if (SDCardFile.createFolder(newName))
								{
									App.toast("创建文件夹成功");
									updateFileList();
								}
								else
								{
									App.toast("创建文件夹失败");
								}	
							}
							else
							{
								if (SDCardFile.createFile(newName))
								{
									App.toast("创建文件成功");
									updateFileList();
								}
								else
								{
									App.toast("创建文件失败");
								}								
							}
						
							
						}
		            }
		        });
		        builder.show();
				
			
				
			}
		});
		
	
		
	
		//builder.setNegativeButton("确定", null);
		builder.show();
		
	}
	
	
	//打开文件菜单选项
	private void openFileItem(final String path)
	{
		File file = new File(path);
		if (file.isDirectory())
		{
			//打开文件夹	注意出错 统一
			//进入目录
			m_curFolder = path;	//后退的情况？？？
			FilesListView(path);
			m_adapter.notifyDataSetChanged();
			m_fileNameList.clear();		//同时不能选择不同文件夹内的文件
			m_pathList.clear();
			SetSendButtonEnabled();
		}
		else	
		{
			//打开文件
			/* Intent负责对应用中一次操作的动作、动作涉及数据、附加数据进行描述，
			 * Android则根据此Intent的描述，负责找到对应的组件，将 Intent传递给调用的组件，并完成组件的调用
			 * 因此，Intent在这里起着一个媒体中介的作用，专门提供组件互相调用的相关信息，实现调用者与被调用者之间的解耦*/
			Intent intent = new Intent();
			intent.setAction(android.content.Intent.ACTION_VIEW);
			File file2 = new File(file.getAbsolutePath());
			String fileName = file2.getName();
			//根据不同文件类型，打开文件 MIME类型
			if (SDCardFile.checkEndName(fileName, this.getResources().getStringArray(R.array.fileEndingMid))
				|| SDCardFile.checkEndName(fileName, this.getResources().getStringArray(R.array.fileEndingMp3))
				|| SDCardFile.checkEndName(fileName, this.getResources().getStringArray(R.array.fileEndingWav)))
			{
				intent.setDataAndType(Uri.fromFile(file2), "audio/*");
			}
			else if (SDCardFile.checkEndName(fileName, this.getResources().getStringArray(R.array.fileEndingPicture)))
			{
				intent.setDataAndType(Uri.fromFile(file2), "image/*");
			}
			else if (SDCardFile.checkEndName(fileName, this.getResources().getStringArray(R.array.fileEndingTxt)))
			{
				intent.setDataAndType(Uri.fromFile(file2), "text/*");
			}
			else if (SDCardFile.checkEndName(fileName, this.getResources().getStringArray(R.array.fileEndingVideo))
					|| SDCardFile.checkEndName(fileName, this.getResources().getStringArray(R.array.fileEndingWma)))
			{
				intent.setDataAndType(Uri.fromFile(file2), "video/*");
			}
			else if (SDCardFile.checkEndName(fileName, this.getResources().getStringArray(R.array.fileEndingOffice))
					|| SDCardFile.checkEndName(fileName, this.getResources().getStringArray(R.array.fileEndingPdf))
					|| SDCardFile.checkEndName(fileName, this.getResources().getStringArray(R.array.fileEndingRar))
					|| SDCardFile.checkEndName(fileName, this.getResources().getStringArray(R.array.fileEndingZip)))
			{
				intent.setDataAndType(Uri.fromFile(file2), "application/*");
			}
			this.startActivity(intent);
		}
	}
	
	
	//删除菜单选项
	private void deleteItem(final String path)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(FileActivity.this);
        builder.setTitle("确定删除?");
        builder.setCancelable(false);
        builder.setPositiveButton("取消", null);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() 
        {
            public void onClick(DialogInterface dialog, int which)
            { 
            	if (SDCardFile.deleteFile(path))
        		{
            		App.toast("删除成功");
        			
        			updateFileList();
        		}
        		else
        		{
        			App.toast("删除失败");
        		}
            }
        });
        builder.show();
		
	}
	
	
	//重命名菜单选项
	private void renameItem(final String path)
	{
		final EditText txtNewName = new EditText(FileActivity.this);
        txtNewName.setFocusable(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(FileActivity.this);
        builder.setTitle("重命名");
        builder.setMessage("请输入新文件名：");
        builder.setView(txtNewName);
        builder.setCancelable(false);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() 
        {
            public void onClick(DialogInterface dialog, int which)
            { 
            	final String input = txtNewName.getText().toString().trim();
            	
            	if (input.equals(""))
            	{
            		App.toast("文件名不能为空");
            		return;
            	}
            	
            	//String value = path + File.separator + input;
            	final String newName = m_curFolder + "/" + input;
            	//！同名 自动输入原来的文件名
				if(new File(newName).exists())	//如果文件存在同名文件
				{
					AlertDialog.Builder builder2 = new AlertDialog.Builder(FileActivity.this);
			        builder2.setTitle("已存在同名文件，是否覆盖？");
			        builder2.setCancelable(false);
			        builder2.setNegativeButton("取消", null);
			        builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() 
			        {
			        	public void onClick(DialogInterface dialog, int which)
			            { 
			        		//!! 文件夹不能正常覆盖
		        		 	File file = new File(path);
			            	
							file.renameTo(new File(newName));
							updateFileList();
							
			            }
					});
			        builder2.show();
			     
				}
				else	//不存在同名文件，直接重命名
				{
					File file = new File(path);
					file.renameTo(new File(newName));
					updateFileList();
					
				}
            }
        });
     
        //用户体验
        File file = new File(path);
        String name = file.getName();
        txtNewName.setText(name);
        if (file.isDirectory())
        {
        	txtNewName.selectAll();		//文件夹全选
        }
        else
        {
	        //把文件名的非格式部分选中
	        int index = name.lastIndexOf('.');
	        if (index == -1)
	        {
	        	index = name.length();	//找不到格式就全选
	        }
	        
         	txtNewName.setSelection(0, index); 
         }
        
      
        builder.show();
	}
	
	
	//排序菜单选项
	private void sortItem()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(FileActivity.this);
		builder.setTitle("请选择排序方式");
		String[] str = { "按名称递增", "按名称递减", "按大小递增", "按大小递减", "按类型递增", "按类型递减" };
			     
		builder.setItems(str, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				switch (which)
				{
				case 0:				
					
					break;
				case 1:	
					break;
				case 2:	
					
					break;
				case 3:	
					break;
				case 4:	
					break;
				case 5:	
					break;
				}
			}
		});
		//builder.setNegativeButton("确定", null);
		builder.show();
	}
	
	
	//复制菜单选项
	private void copyItem(final String path)
	{
		m_operatorType = 1;
		m_operatorPath = path;
		m_btnCancel.setEnabled(true);
		m_btnPaste.setEnabled(true);
	}
	
	
	//剪切菜单选项
	private void cutItem(final String path)
	{
		m_operatorType = 2;
		m_operatorPath = path;
		m_btnCancel.setEnabled(true);
		m_btnPaste.setEnabled(true);
	}
	
	
	//更新列表
	private void updateFileList()
	{
		FilesListView(m_curFolder);
		m_adapter.notifyDataSetChanged();
	}
	
	
	private void SetSendButtonEnabled()
	{
		m_btnOk.setEnabled(false);
		if (m_fileNameList.size() > 0)
		{
			m_btnOk.setEnabled(true);
		}
	}
	
	
	private static void fileSort(File[] file, int way)
	{
		way = 3;
		
		switch (way)
		{
		//按文件名名排序，递增
		//不区分文件和文件夹，排序方式，选择排序法
		case 0:	
		{
			int length = file.length;
			for (int ii = 0; ii < length - 1; ii++)
			{
				for (int jj = ii + 1; jj < length; jj++)
				{
					if (file[ii].getName().compareTo(file[jj].getName()) > 0)
					{
						File temp = file[ii];
						file[ii] = file[jj];
						file[jj] = temp;
					}
				}
			}
			break;
		}
		
		//按文件名排序，递减
		case 1:
		{
			int length = file.length;
			for (int ii = 0; ii < length - 1; ii++)
			{
				for (int jj = ii + 1; jj < length; jj++)
				{
					if (file[ii].getName().compareTo(file[jj].getName()) < 0)
					{
						File temp = file[ii];
						file[ii] = file[jj];
						file[jj] = temp;
					}
				}
			}
			break;		
		}

		//按大小排序，递增
		case 2:
		{
			int length = file.length;
			for (int ii = 0; ii < length - 1; ii++)
			{
				for (int jj = ii + 1; jj < length; jj++)
				{
					if (SDCardFile.getFilesSize(file[ii]) > SDCardFile.getFilesSize(file[jj]))
					{
						File temp = file[ii];
						file[ii] = file[jj];
						file[jj] = temp;
					}
				}
			}
			break;		
		}
			
		//按大小排序，递减
		case 3:
		{
			int length = file.length;
			for (int ii = 0; ii < length - 1; ii++)
			{
				for (int jj = ii + 1; jj < length; jj++)
				{
					if (SDCardFile.getFilesSize(file[ii]) < SDCardFile.getFilesSize(file[jj]))
					{
						File temp = file[ii];
						file[ii] = file[jj];
						file[jj] = temp;
					}
				}
			}
			break;		
		}
			
		//按类型排序，递增
		//文件夹在前，文件在后，文件夹按名称排序，递增，文件不同格式的按格式夹按格式名排序，递增，同格式的按文件名排序，递增
		case 4:
		{
			int length = file.length;
			for (int ii = 0; ii < length - 1; ii++)
			{
				for (int jj = ii + 1; jj < length; jj++)
				{
					if (file[ii].isFile() && file[jj].isDirectory()
							|| SDCardFile.getType(file[ii]) != SDCardFile.getType(file[jj]) && SDCardFile.getType(file[ii]).compareTo(SDCardFile.getType(file[jj])) > 0
							|| file[ii].getName().compareTo(file[jj].getName()) > 0)
					if (file[ii].getName().compareTo(file[jj].getName()) > 0)
					{
						File temp = file[ii];
						file[ii] = file[jj];
						file[jj] = temp;
					}
				}
			}
			break;
		}
			
		//按类型排序，递减
		case 5:
			break;
			
		//
		}
		
	}
	
	
	//菜单选项常量
	private final int ITEM_SORT = Menu.FIRST;
	private final int ITEM_SEARCH = Menu.FIRST + 1;	
		
	//设置菜单
	@Override  
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		menu.add(0, ITEM_SORT, 0, "排序");
		//menu.add(0, ITEM_HELP, 0, "帮助");
		menu.add(0, ITEM_SEARCH, 0, "搜索");
		
		
		return true;
	}
	 
	
	// 点击菜单按钮响应的事件  
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{ 
		Intent intent = new Intent();;
		
		switch (item.getItemId())
		{
		case ITEM_SORT:	
			sortItem();
			break;
			
		
		}

		return true;
	}
	 
	 
	 
	//进入目录
	private void FilesListView(String selectedPath)
	{
		File selectedFile = new File(selectedPath);
		if (selectedFile.canRead())
		{
			File[] file = selectedFile.listFiles();
			
			fileSort(file, 5);
			
			//Collections.sort(
			
			
			m_listItem.clear();
			for (int i = 0; i < file.length; i++)
			{
				HashMap<String, Object> map = new HashMap<String, Object>();
				//根据文件类型的不同设置不同的图标
				if (file[i].isDirectory())
				{
					map.put("image", R.drawable.folder);
				}
				else
				{
					if (SDCardFile.checkEndName(file[i], "txt"))
					{
						map.put("image", R.drawable.type_txt);
					}/*
					else if (MyFile.checkEndName(file[i], "bmp"))
					{
						BitmapFactory.Options option = new BitmapFactory.Options();
				        option.inSampleSize = 4;
				        Bitmap bitmap = BitmapFactory.decodeFile("sdcard/456.bmp", option);
				        BitmapDrawable bd = new BitmapDrawable(bitmap);
				        //BitmapDrawable bitmapDrawable = (BitmapDrawable) bitmap;     
						Drawable drawable = (Drawable)bd;
						//改为预览
						map.put("image", drawable);
						
						
					}*/
					else
					{
						map.put("image", R.drawable.file);
					}
					
				}
				//map.put("image", file[i].isDirectory()?R.drawable.folder:);
				map.put("name", file[i].getName());
				map.put("path", file[i].getPath());
				map.put("type", file[i].isDirectory());
				map.put("parent", file[i].getParent());
				map.put("select", file[i].isFile() ? R.drawable.unselected : "");
				m_listItem.add(map);
				
			}
			
			//判断有无父目录，增加返回上一级目录菜单
			if (selectedFile.getParent() != null)
			{
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("name", "返回上一级目录");
				map.put("path", selectedFile.getParent());
				map.put("type", true);
				map.put("parent", selectedFile.getParent());
				m_listItem.add(0, map);
			}
		}
		else
		{
			App.toast("该目录不能读取");
		}
	}

}

