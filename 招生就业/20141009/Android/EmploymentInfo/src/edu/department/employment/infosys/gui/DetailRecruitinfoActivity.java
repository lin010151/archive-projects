/**
 * 
 */
package edu.department.employment.infosys.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import edu.department.employment.infosys.R;
import edu.department.employment.infosys.model.Company;
import edu.department.employment.infosys.model.GlobalDataInstance;
import edu.department.employment.infosys.model.Post;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author dragonzhu
 * 
 */
public class DetailRecruitinfoActivity extends Activity {

	private String id; // ��˾id
	private Handler handler; // ��Ϣ������
	private Post post; // ��Ƹ��λ��Ϣ
	private Company company; // ��Ƹ��λ��Ϣ
	// ��չ�����б����
	private ExpandableListView expandableListView;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailrecruit_activity);

		// �ҵ���չ�����б�����
		expandableListView = (ExpandableListView) findViewById(R.id.postepLV);
		company = new Company();
		post = new Post();

		// ���ǰһ�����ڴ�������url
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		id = bundle.getString("id");

		if (null == id)
			return;
		// ��ȡ����
		getDataByUrlThread(); // �����̻߳�ȡ�ڲ���Ƹ����

		// ������Ϣ�����߳�
		handler = new Handler() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 0x123) {
					final ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
						// ��������ͼ��ͼƬ
						int[] logos = new int[] { R.drawable.company,
								R.drawable.recruit };

						// ��������ͼ����ʾ����
						private String[] generalsTypes = new String[] {
								company.getCompanyName(), post.getPostinfo() };

						// ����ͼ��ʾ����
						private String[][] titles = new String[][] {
								{ "��λ���ܣ�", "    ��ϵ�ˣ�", "��ϵ�绰��", "��        �棺",
										"��        �䣺", "��λ��ҳ��", "��λ���ʣ�",
										"ͨѶ��ַ��", "�������룺", "��       ע��" },
								{ "ְλ���", "����������", "��Ƹ������", "��λҪ��",
										"н        ˮ��", "ѧ        ����",
										"ר        ҵ��", "��        ע��", "����ʱ�䣺" } };

						// ����ͼ��ʾ����
						private String[][] generals = new String[][] {
								{ company.getCompanyIntro(),
										company.getContact(),
										company.getTelephone(),
										company.getFax(),
										company.getCompanyEmail(),
										company.getWeburl(), company.getType(),
										company.getCompanyAddress(),
										company.getPostalcode(),
										company.getNote() },

								{ post.getJobsCategory(),
										post.getJobsAddress(),
										post.getRecruitNum().toString(),
										post.getPostRequirements(),
										post.getSalary().toString(),
										post.getEducation(), post.getMajor(),
										post.getNote(), post.getReleaseTime() } };

						// �Լ�����һ�����������Ϣ�ķ���
						TextView getTextView() {
							AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
									ViewGroup.LayoutParams.WRAP_CONTENT, 64);
							TextView textView = new TextView(
									DetailRecruitinfoActivity.this);
							textView.setLayoutParams(lp);
							textView.setGravity(Gravity.CENTER_VERTICAL);
							textView.setPadding(36, 0, 0, 0);
							textView.setTextSize(20);
							textView.setTextColor(Color.BLACK);
							return textView;
						}

						@Override
						public boolean isChildSelectable(int groupPosition,
								int childPosition) {
							// TODO Auto-generated method stub
							return true;
						}

						@Override
						public boolean hasStableIds() {
							// TODO Auto-generated method stub
							return true;
						}

						@Override
						public View getGroupView(int groupPosition,
								boolean isExpanded, View convertView,
								ViewGroup parent) {
							// TODO Auto-generated method stub
							LinearLayout ll = new LinearLayout(
									DetailRecruitinfoActivity.this);
							ll.setOrientation(0);
							ImageView logo = new ImageView(
									DetailRecruitinfoActivity.this);
							logo.setImageResource(logos[groupPosition]);
							logo.setPadding(50, 0, 0, 0);
							ll.addView(logo);
							TextView textView = getTextView();
							textView.setTextColor(Color.BLACK);
							textView.setText(getGroup(groupPosition).toString());
							ll.addView(textView);

							return ll;
						}

						@Override
						public long getGroupId(int groupPosition) {
							// TODO Auto-generated method stub
							return groupPosition;
						}

						@Override
						public int getGroupCount() {
							// TODO Auto-generated method stub
							return generalsTypes.length;
						}

						@Override
						public Object getGroup(int groupPosition) {
							// TODO Auto-generated method stub
							return generalsTypes[groupPosition];
						}

						@Override
						public int getChildrenCount(int groupPosition) {
							// TODO Auto-generated method stub
							return generals[groupPosition].length;
						}

						@Override
						public View getChildView(int groupPosition,
								int childPosition, boolean isLastChild,
								View convertView, ViewGroup parents) {
							// TODO Auto-generated method stub
							LinearLayout ll = new LinearLayout(
									DetailRecruitinfoActivity.this);
							ll.setOrientation(0);
							TextView title = getTextView();
							title.setText(titles[groupPosition][childPosition]);
							ll.addView(title);
							TextView textView = getTextView();
							textView.setText(getChild(groupPosition,
									childPosition).toString());
							ll.addView(textView);
							return ll;
						}

						@Override
						public long getChildId(int groupPosition,
								int childPosition) {
							// TODO Auto-generated method stub
							return childPosition;
						}

						@Override
						public Object getChild(int groupPosition,
								int childPosition) {
							// TODO Auto-generated method stub
							return generals[groupPosition][childPosition];
						}
					};
					expandableListView.setAdapter(adapter);
				}
			}
		};
	}

	// �����̻߳�ȡ�ڲ���Ƹ����
	private void getDataByUrlThread() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					// ���ӵ�¼��̨
					HttpClient httpclient = new DefaultHttpClient(); // �����µĿͻ���
					// ��ַuri
					String uri = getResources().getString(R.string.web_url)
							+ "/postjson";
					HttpPost httppost = new HttpPost(uri);
					// ����HTTP POST�������������NameValuePair����
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("postID", id));
					params.add(new BasicNameValuePair("userid", GlobalDataInstance
							.GetInstance().getLogin().getUserID()));
					params.add(new BasicNameValuePair("what", "single"));

					// �󶨵�����Entry
					httppost.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse httpResponse = httpclient.execute(httppost);
					// ����״̬�룬����ɹ���������
					int code = httpResponse.getStatusLine().getStatusCode();
					if (code == 200) {
						// �õ�Ӧ����ַ�������Ҳ��һ��JSON��ʽ���������
						String retSrc = EntityUtils.toString(httpResponse
								.getEntity());
						JSONObject result = new JSONObject(retSrc);
						
						// ������Ƹ��λ��Ϣ
						post.setPostID(result.getString("postID"));
						post.setPostName(result.getString("postName"));
						post.setPostRequirements(result
								.getString("postRequirements"));
						post.setPostinfo(result.getString("postinfo"));
						post.setReleaseTime(result.getString("recruitDate"));
						post.setRecruitNum(result.getInt("recruitNum"));
						post.setEducation(result.getString("education"));
						post.setJobsCategory(result.getString("jobsAddress"));
						post.setJobsCategory(result.getString("jobsCategory"));
						post.setMajor(result.getString("major"));
						post.setNote(result.getString("note"));
						
						// ������Ƹ��λ��Ϣ
						JSONObject tmp = result.getJSONObject("company");
						company.setCompanyID(tmp.getString("companyID"));
						company.setCompanyName(tmp.getString("companyName"));
						company.setCompanyIntro(tmp.getString("companyIntro"));
						company.setCompanyAddress(tmp
								.getString("companyAddress"));
						company.setCompanyEmail(tmp.getString("companyEmail"));
						company.setContact(tmp.getString("contact"));
						company.setFax(tmp.getString("fax"));
						company.setNote(tmp.getString("note"));
						company.setPostalcode(tmp.getString("postalcode"));
						company.setTelephone(tmp.getString("telephone"));
						company.setType(tmp.getString("type"));
						company.setWeburl(tmp.getString("weburl"));

						Message msg = new Message();
						// ������Ϣ������ͼ
						msg.what = 0x123;
						msg.obj = null;
						handler.sendMessage(msg);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

}
