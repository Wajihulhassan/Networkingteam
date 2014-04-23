package me.istrate.restaurantapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class OrderListActivity extends Activity {

	private ArrayList < Order > m_Orders = new ArrayList< Order >();
	int convert = 0;
	
	SharedPreferences prefs;
	Editor editor;
	JSONArray jsonArray = null;
	ArrayAdapter<Order> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_list);

		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		editor = prefs.edit();

		String json = prefs.getString("orders", "[]");
		//get json array
		try {
			jsonArray = new JSONArray(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			int tableNr = 0;
			String sTableNr = "";
			String orderContent = "";
			try {
				sTableNr = jsonArray.getJSONObject(i).get("Table").toString();
				orderContent = jsonArray.getJSONObject(i).get("Order").toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			//mothafucka
			tableNr = Integer.parseInt(sTableNr+0) / 10;
			m_Orders.add(new Order().TableNumber(tableNr).OrderContent(orderContent));
		}		
		
		ListView orderList = (ListView)findViewById(R.id.order_list);
		
		//register list for context menu
		registerForContextMenu(orderList);
		
		adapter = new ArrayAdapter<Order>(this, android.R.layout.simple_list_item_1, m_Orders);
		orderList.setAdapter(adapter);
	
		Button addOrder = (Button) findViewById(R.id.order_button);
		addOrder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(OrderListActivity.this, OrderActivity.class);
				startActivity(intent);
			}
		});			
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.context_menu, menu);
	}
	
	//removes one element from json array
	public JSONArray removeElement(JSONArray oldArray, int pos) {
		JSONArray newArray = new JSONArray();
		for (int i = 0; i < oldArray.length(); i++) {
			if (i != pos) {
				try {
					newArray.put(oldArray.getJSONObject(i));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return newArray;
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo();
		switch(item.getItemId()) {
			case (R.id.delete_order):
		        // Show message
			jsonArray = removeElement(jsonArray, menuInfo.position);
			editor.putString("orders", jsonArray.toString());
			editor.commit();		
			adapter.remove(adapter.getItem(menuInfo.position));
	        break;
		}
		return true;
	}
}
