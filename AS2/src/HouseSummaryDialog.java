
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class HouseSummaryDialog extends JFrame {
	
	/* Code goes here */
	private ArrayList<House> houseList;
	public JLabel addressLabel1=new JLabel("Address Line 1: ");
	public JLabel addressLabel2=new JLabel("Address Line 2: ");
	public JLabel houseID=new JLabel("ID: ");
	public JLabel bedroomsLabel=new JLabel("Number of bedrooms: ");
	public JLabel bathroomsLabel=new JLabel("Number of bathrooms: ");
	public JLabel priceLabel=new JLabel("Price: ");
	public JLabel contactNumberLabel=new JLabel("Contact Number: ");
	public JLabel averagepriceLabel=new JLabel("Average Price: ");
	public JLabel averageprice;
	public JLabel emptyLabel=new JLabel();
	public JLabel emptyLabel1=new JLabel();
	public JLabel emptyLabel2=new JLabel();
	public JLabel emptyLabel3=new JLabel();
	public JPanel panel;
	
	
	public HouseSummaryDialog(ArrayList<House> houseList)
	{
		this.houseList=houseList;
		panel=new JPanel(new GridLayout(houseList.size()+2,7));
		panel.add(houseID);
		panel.add(addressLabel1);
		panel.add(addressLabel2);
		panel.add(bedroomsLabel);
		panel.add(bathroomsLabel);
		panel.add(priceLabel);
		panel.add(contactNumberLabel);
		
		JLabel tempaddressLabel1;
		JLabel tempaddressLabel2;
		JLabel temphouseID;
		JLabel tempbedroomsLabel;
		JLabel tempbathroomsLabel;
		JLabel temppriceLabel;
		JLabel tempcontactNumberLabel;
		
		for(int i=0;i<houseList.size();i++)
		{
			temphouseID=new JLabel(Integer.toString(houseList.get(i).getId()));
			tempaddressLabel1=new JLabel(houseList.get(i).getStreet());
			tempaddressLabel2=new JLabel(houseList.get(i).getCity());
			tempbedroomsLabel=new JLabel(Integer.toString(houseList.get(i).getBedrooms()));
			tempbathroomsLabel=new JLabel(Integer.toString(houseList.get(i).getBathrooms()));
			temppriceLabel=new JLabel(Double.toString(houseList.get(i).getPrice()));
			tempcontactNumberLabel=new JLabel(houseList.get(i).getContactNo());
			
			
			panel.add(temphouseID);
			panel.add(tempaddressLabel1);
			panel.add(tempaddressLabel2);
			panel.add(tempbedroomsLabel);
			panel.add(tempbathroomsLabel);
			panel.add(temppriceLabel);
			panel.add(tempcontactNumberLabel);
		}
		
		double sum=0;
		double average;
		
		for(int i=0;i<houseList.size();i++)
		{
			sum=sum+houseList.get(i).getPrice();
		}
		
		average=sum/houseList.size();
		
		averageprice=new JLabel(Double.toString(average));
		
		panel.add(averagepriceLabel);
		panel.add(emptyLabel);
		panel.add(emptyLabel1);
		panel.add(emptyLabel2);
		panel.add(emptyLabel3);
		panel.add(averageprice);
		
		
		
		add(panel);
		
		setTitle("Summary");
		setSize(1000, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}

}
