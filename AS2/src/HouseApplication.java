
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.text.NumberFormat;
import java.util.*;

public class HouseApplication extends JFrame {
	
	ArrayList<House> houseList = new ArrayList<House>();
	
	String password="3175";

	String[][] records = { {"113 The Maltings", "Dublin 8", "2", "1", "155500.00", "House1.jpg", "(087) 9011135"},
			   {"78 Newington Lodge", "Dublin 14", "3", "2", "310000.00", "House2.jpg", "(087) 9010580"},
			   {"62 Bohernabreena Road", "Dublin 24", "3", "1", "220000.00", "House3.jpg", "(087) 6023159"},
			   {"18 Castledevitt Park", "Dublin 15", "3", "3", "325000.00", "House4.jpg", "(087) 9010580"},
			   {"40 Dunsawny Road", "Swords", "3", "19", "245000.00", "House5.jpg", "(087) 9011135"}};
	int currentItem=0;
	
	public JPanel toppanel=new JPanel();
	public JPanel buttonpanel=new JPanel();
	public JPanel bottompanel=new JPanel();
	ImageIcon icon = new ImageIcon("House1.jpg");
	public JLabel houseImage=new JLabel(icon,SwingConstants.CENTER);
	public JLabel houseIDLabel=new JLabel("House ID: ");
	private JTextField houseIDField=new JTextField(20);
	public JLabel addressLabel1=new JLabel("Address Line 1: ");
	private JTextField addressField1=new JTextField(20);
	public JLabel addressLabel2=new JLabel("Address Line 2: ");
	private JTextField addressField2=new JTextField(20);
	public JLabel bedroomsLabel=new JLabel("Number of bedrooms: ");
	private JTextField bedroomsField=new JTextField(20);
	public JLabel bathroomsLabel=new JLabel("Number of bathrooms: ");
	private JTextField bathroomsField=new JTextField(20);
	public JLabel priceLabel=new JLabel("Price: ");
	private JTextField priceField=new JTextField(20);
	public JLabel priceChangeLabel=new JLabel("Price Change: ");
	private JTextField priceChangeField=new JTextField(20);
	public JLabel contactNumberLabel=new JLabel("Contact Number: ");
	private JTextField contactNumberField=new JTextField(20);
	ImageIcon first = new ImageIcon("first.png");
	JButton firstButton = new JButton(first);
	ImageIcon previous = new ImageIcon("prev.png");
	JButton previousButton = new JButton(previous);
	ImageIcon next = new ImageIcon("next.png");
	JButton nextButton = new JButton(next);
	ImageIcon last = new ImageIcon("last.png");
	JButton lastButton = new JButton("last");
	JButton editButton = new JButton("Edit");
	
	public HouseApplication() {
		super("Estate Agent Application");
	//Transform the array of data into Houses on the ArrayList 
		for (int i = 0; i < records.length; i++) {
			houseList.add(new House(records[i][0], records[i][1], Integer.parseInt(records[i][2]), 
					Integer.parseInt(records[i][3]), Double.parseDouble(records[i][4]), records[i][5], records[i][6]));
		}
			
		JMenuBar modifyMenuBar=initComponents();
		this.setJMenuBar(modifyMenuBar);
		//toppanel.add(modifyMenuBar,"North,wrap");
		
		toppanel.setLayout(new MigLayout());
		toppanel.add(houseImage,"North,push,grow,span 2,wrap");
		toppanel.add(houseIDLabel,"pushx,growx,span 10");
		houseIDField.setEditable(false);
		toppanel.add(houseIDField,"pushx,growx,wrap");
		toppanel.add(addressLabel1,"pushx,growx,span 10");
		addressField1.setEditable(false);
		toppanel.add(addressField1,"pushx,growx,wrap");
		toppanel.add(addressLabel2,"pushx,growx,span 10");
		addressField2.setEditable(false);
		toppanel.add(addressField2,"pushx,growx,wrap");
		toppanel.add(bedroomsLabel,"pushx,growx,span 10");
		bedroomsField.setEditable(false);
		toppanel.add(bedroomsField,"pushx,growx,wrap");
		toppanel.add(bathroomsLabel,"pushx,growx,span 10");
		bathroomsField.setEditable(false);
		toppanel.add(bathroomsField,"pushx,growx,wrap");
		toppanel.add(priceLabel,"pushx,growx,span 10");
		priceField.setEditable(false);
		toppanel.add(priceField,"pushx,growx,wrap");
		toppanel.add(priceChangeLabel,"pushx,growx,span 10");
		priceChangeField.setEditable(false);
		toppanel.add(priceChangeField,"pushx,growx,wrap");
		toppanel.add(contactNumberLabel,"pushx,growx,span 10");
		contactNumberField.setEditable(false);
		toppanel.add(contactNumberField,"pushx,growx,wrap");
		
		updateFields();
		
		//houseImage.add(icon);
		
		
		
		buttonpanel.setLayout(new GridLayout(1,5));
		buttonpanel.add(firstButton);
		firstButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			currentItem=0;
			updateFields();
			}
			});
		
		buttonpanel.add(previousButton);
		previousButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentItem!=0)
			currentItem--;
			updateFields();
			}
			});
		
		buttonpanel.add(editButton);
		editButton.addActionListener(new ActionListener() {
			boolean clicked=false;
			public void actionPerformed(ActionEvent e) {
			if(clicked)
			{
				double newPrice=Double.parseDouble(priceField.getText());
				houseList.get(currentItem).setChange(newPrice-houseList.get(currentItem).getPrice());
				houseList.get(currentItem).setPrice(newPrice);
				priceChangeField.setText(Double.toString(houseList.get(currentItem).getChange()));
				priceField.setEditable(false);
				editButton.setText("Edit");	
				JOptionPane.showMessageDialog(toppanel,"The house record has been updated");
				clicked=false;
				
			}
			else {
			editButton.setText("Save");
			priceField.setEditable(true);
			clicked=true;
			}
			}
			});
			
		buttonpanel.add(nextButton);
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentItem!=houseList.size()-1) {
				currentItem++;
				updateFields();
				}
			}
			});
		
		buttonpanel.add(lastButton);
		lastButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentItem=houseList.size()-1;
				updateFields();
			}
			});
		
		setLayout(new BorderLayout());
		
		add(toppanel,BorderLayout.NORTH);
		bottompanel.add(buttonpanel);
		add(bottompanel,BorderLayout.SOUTH);
		
		//validate();
		add(toppanel);
		setSize(400, 550);
		setVisible(true);
	}

	public JMenuBar initComponents() {
		
		/* Set up your menus and menu items here */
		JMenu modifyMenu = new JMenu("Modify");
		JMenuItem createMI = new JMenuItem("Create");
		modifyMenu.add(createMI);		
		createMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean correct=getPassword();
				if(correct)
				{
					new CreateHouseDialog(houseList);
				}
			
			}
			});		
		
		JMenuItem deleteMI = new JMenuItem("Delete");
		modifyMenu.add(deleteMI);		
		deleteMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int input=JOptionPane.showConfirmDialog(toppanel,"This will delete the record, are you sure you would like to continue?"
						,"Warning",2,0);
				if(input==0)
				{
					boolean correct=getPassword();
					if (correct)
					{
						//remove
						houseList.remove(houseList.get(currentItem));
						if(currentItem!=houseList.size()-1)
						{
							updateFields();
						}
						else
						{
							currentItem=houseList.size()-1;
							updateFields();
						}
					}
				}
				
			}
			});		
		
			
		
		JMenu reportMenu=new JMenu("Report");
		JMenuItem searchRecords=new JMenuItem("Search Records");
		reportMenu.add(searchRecords);
		searchRecords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//get all ids of houses and add them to ids array
				Object[] ids = new Object[houseList.size()] ;
				int id;
				for(int i=0;i<houseList.size();i++)
				{
					id=houseList.get(i).getId();
					ids[i]=id;
				}
				int s = (int)JOptionPane.showInputDialog(
				                    toppanel,
				                    "Choose House ID", 
				                    "Input",
				                    JOptionPane.PLAIN_MESSAGE,
				                    null,
				                    ids,
				                    ids[0]);
				for(int i=0;i<houseList.size();i++)
				{
					if(s==houseList.get(i).getId())
					{
						currentItem=houseList.indexOf(houseList.get(i));
						updateFields();
					}
				}
			}
			});		
		
		JMenuItem summaryReport=new JMenuItem("Summary Report");
		reportMenu.add(summaryReport);
		summaryReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new HouseSummaryDialog(houseList);
			}
			});		
		
		JMenu close=new JMenu("Close");
		JMenuItem exit=new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			});
		close.add(exit);
		
		
		JMenuBar modifyMenuBar=new JMenuBar();
		modifyMenuBar.add(modifyMenu);
		modifyMenuBar.add(reportMenu);
		modifyMenuBar.add(close);
		
		return modifyMenuBar;
		}
	
	public boolean getPassword()
	{
		boolean correct=false;
		String attempt =JOptionPane.showInputDialog(toppanel, "Enter Password:");
		if (attempt.equalsIgnoreCase(password))
		{
			correct=true;
		}
		else
		{
			JOptionPane.showMessageDialog(toppanel,"The password you entered was incorrect");
		}
		return correct;
	}

	public void updateFields()
	{
		ImageIcon icon1 = new ImageIcon(houseList.get(currentItem).getImageLocation());
		houseImage.setIcon(icon1);
		houseIDField.setText(Integer.toString(houseList.get(currentItem).getId()));
		addressField1.setText(houseList.get(currentItem).getStreet());
		addressField2.setText(houseList.get(currentItem).getCity());
		bedroomsField.setText(Integer.toString(houseList.get(currentItem).getBedrooms()));
		bathroomsField.setText(Integer.toString(houseList.get(currentItem).getBathrooms()));
		priceField.setText(Double.toString(houseList.get(currentItem).getPrice()));
		priceChangeField.setText(Double.toString(houseList.get(currentItem).getChange()));
		contactNumberField.setText(houseList.get(currentItem).getContactNo());
	}
	
	public static void main(String[] args) {
		HouseApplication ha = new HouseApplication();
		
	}

}
