package me.istrate.restaurantapp;

public class Order {
	
	private int m_iTableNumber;
	private String m_sOrder;
	
	public Order() {}
	
	//builder pattern
	public Order TableNumber(int i) {
		m_iTableNumber = i;
		return this;
	}
	public Order OrderContent(String s) {
		m_sOrder = s;
		return this;
	}
	
	public void setTableNumber(int i) {
		m_iTableNumber = i;
	}
	
	public void setOrder(String s) {
		m_sOrder = s;
	}
	
	public String getOrder() {
		return m_sOrder;
	}
	public int getTableNumber() {
		return m_iTableNumber;
	}
	
	@Override
	public String toString() {
		return "Table nr: " + getTableNumber() + " Order is: " + getOrder();
	}
}
