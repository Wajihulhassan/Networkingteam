package me.istrate.restaurantapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RestaurantActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant);
		Button tableReady = (Button) findViewById(R.id.tableReady);
		tableReady.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(RestaurantActivity.this, TableReadyActivity.class);
				startActivity(intent);
			}
		});		
		
		Button Order = (Button) findViewById(R.id.ordersbtn);
		Order.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(RestaurantActivity.this, OrderListActivity.class);
				startActivity(intent);
			}
		});	
		
		Button Alert = (Button) findViewById(R.id.alertbtn);
		Alert.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RestaurantActivity.this, AlertListActivity.class);
				startActivity(intent);
				
			}
		});
		
	}
}
