package me.istrate.restaurantapp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class OrderActivity extends Activity {
	
	private EditText mEdit;
	private EditText mEdit2;

	JSONArray m_Orders = null;
	JSONObject m_Order = new JSONObject();
	SharedPreferences prefs;
	Editor editor;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//load prefs
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		editor = prefs.edit();
		//load json string
		String orders = prefs.getString("orders", "[]");
		try {
			m_Orders =  new JSONArray(orders.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_screen);	
		
		mEdit = (EditText)findViewById(R.id.order2);
		mEdit2 = (EditText)findViewById(R.id.order);

		Button sendOrder = (Button) findViewById(R.id.sendOrder);
		sendOrder.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				String number = mEdit2.getText().toString();
				String tableOrder = mEdit.getText().toString();
				try {
					m_Order.put("Order", tableOrder);
					m_Order.put("Table", number);
					m_Orders.put(m_Order);					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				editor.putString("orders", m_Orders.toString());
				
				editor.commit();
				String str = m_Order.toString();
				 Thread cThread = new Thread(new SendOrder(str));
				 cThread.start();
				Intent intent = new Intent(OrderActivity.this, OrderListActivity.class);
				startActivity(intent);
			}
		});	
	}
	public class SendOrder implements Runnable {
		public String order ;
		public SendOrder(String str) {
			order = str;
		}
        public void run() {
        	String serverIpAddress = "192.241.253.156";
        	InetAddress serverAddr = null;
        	try {
				serverAddr = InetAddress.getByName(serverIpAddress);
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
                Socket socket = null;
				try {
					socket = new Socket(serverAddr,6789);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
                PrintWriter out = null;
				try {
					out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					out.println(order);
                 
        }
	}
}

