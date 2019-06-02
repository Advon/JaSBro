package jasbro.gui.town;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import jasbro.Jasbro;
import jasbro.game.housing.House;
import jasbro.game.housing.HouseType;
import jasbro.game.housing.HouseUtil;
import jasbro.game.realestate.Plot;
import jasbro.game.realestate.RealEstateSystem;
import jasbro.gui.GuiUtil;
import jasbro.gui.pages.MessageScreen;
import jasbro.gui.pictures.ImageData;
import jasbro.texts.TextUtil;
import jasbro.util.ConfigHandler;
import jasbro.util.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealEstateMenu extends JPanel {
	private List<JButton> buyButtons = new ArrayList<JButton>();
	private List<House> houses;

	private JPanel plotDetailsPanel;
	
	public RealEstateMenu() {
		removeAll();
		
		init();
	}
	
	public void init() {
		removeAll();
		houses = Jasbro.getInstance().getData().getHouses();
		setOpaque(false);		
		
		double width = ConfigHandler.getResolution(Settings.RESOLUTIONWIDTH);
		double height = ConfigHandler.getResolution(Settings.RESOLUTIONHEIGHT);
		int widthRat = (int) (width/1280);
		int heightRat = (int) (height/720);
		int iconSize = (int) (65*width/1280);
		int backHomeBtnWidth = (int) (150*width/1280);
		int backHomeBtnHeight = (int) (50*width/1280);
		
		ImageIcon buyPlotIcon1 = new ImageIcon("images/buttons/buyplot.png");
		Image buyPlotImage1 = buyPlotIcon1.getImage().getScaledInstance( backHomeBtnWidth, backHomeBtnHeight,  java.awt.Image.SCALE_SMOOTH ) ;  
		buyPlotIcon1 = new ImageIcon(buyPlotImage1);
		
		ImageIcon buyPlotIcon2 = new ImageIcon("images/buttons/buyplot hover.png");
		Image buyPlotImage2 = buyPlotIcon2.getImage().getScaledInstance( backHomeBtnWidth, backHomeBtnHeight,  java.awt.Image.SCALE_SMOOTH ) ;  
		buyPlotIcon2 = new ImageIcon(buyPlotImage2);
		
		JButton buyPlotButton = new JButton(buyPlotIcon1);
		buyPlotButton.setRolloverIcon(buyPlotIcon2);
		buyPlotButton.setPressedIcon(buyPlotIcon1);
		buyPlotButton.setBounds((int) (15*width/1280),(int) (480*height/720), backHomeBtnWidth, backHomeBtnHeight);
		buyPlotButton.setBorderPainted(false); 
		buyPlotButton.setContentAreaFilled(false); 
		buyPlotButton.setFocusPainted(false); 
		buyPlotButton.setOpaque(false);
		/* TODO Add the feature to replace bought plots with an owned sign. Also to actually implement plots.
		 */
	    try {
			add(buyPlotButton);
	    } catch (java.lang.NullPointerException e) {
	    	// We need a way to remove this error
	    }
		buyPlotButton.addActionListener(new  ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Jasbro.getInstance().getGui().showBuyPlotMapScreen(1);
			}
		});
		
		ImageIcon homeIcon1 = new ImageIcon("images/buttons/home.png");
		Image homeImage1 = homeIcon1.getImage().getScaledInstance( backHomeBtnWidth, backHomeBtnHeight,  java.awt.Image.SCALE_SMOOTH ) ;  
		homeIcon1 = new ImageIcon(homeImage1);
		
		ImageIcon homeIcon2 = new ImageIcon("images/buttons/home hover.png");
		Image homeImage2 = homeIcon2.getImage().getScaledInstance( backHomeBtnWidth, backHomeBtnHeight,  java.awt.Image.SCALE_SMOOTH ) ;  
		homeIcon2 = new ImageIcon(homeImage2);
		
		JButton homeButton = new JButton(homeIcon1);
		homeButton.setRolloverIcon(homeIcon2);
		homeButton.setPressedIcon(homeIcon1);
		homeButton.setBounds((int) (15*width/1280),(int) (550*height/720), backHomeBtnWidth, backHomeBtnHeight);
		homeButton.setBorderPainted(false); 
		homeButton.setContentAreaFilled(false); 
		homeButton.setFocusPainted(false); 
		homeButton.setOpaque(false);
	    try {
	    	add(homeButton);
	    } catch (java.lang.NullPointerException e) {
	    	// We need a way to remove this error
	    }
		homeButton.addActionListener(new  ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Jasbro.getInstance().getGui().showHouseManagementScreen();
			}
		});
		
		ImageIcon backIcon1 = new ImageIcon("images/buttons/back.png");
		Image backImage1 = backIcon1.getImage().getScaledInstance( backHomeBtnWidth, backHomeBtnHeight,  java.awt.Image.SCALE_SMOOTH ) ;  
		backIcon1 = new ImageIcon(backImage1);
		
		ImageIcon backIcon2 = new ImageIcon("images/buttons/back hover.png");
		Image backImage2 = backIcon2.getImage().getScaledInstance( backHomeBtnWidth, backHomeBtnHeight,  java.awt.Image.SCALE_SMOOTH ) ;  
		backIcon2 = new ImageIcon(backImage2);
		
		JButton backButton = new JButton(backIcon1);
		backButton.setRolloverIcon(backIcon2);
		backButton.setPressedIcon(backIcon1);
		backButton.setBounds((int) (15*width/1280),(int) (620*height/720), backHomeBtnWidth, backHomeBtnHeight);
		backButton.setBorderPainted(false); 
		backButton.setContentAreaFilled(false); 
		backButton.setFocusPainted(false); 
		backButton.setOpaque(false);
	    try {
			add(backButton);
	    } catch (java.lang.NullPointerException e) {
	    	// We need a way to remove this error
	    }
		backButton.addActionListener(new  ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Jasbro.getInstance().getGui().showBuildersGuildScreen();
			}
		});
		
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("135dlu"),
				ColumnSpec.decode("pref:grow"),
				ColumnSpec.decode("pref:grow"),},
				new RowSpec[] {
				RowSpec.decode("20dlu"),
				RowSpec.decode("1dlu:grow"),
				RowSpec.decode("20dlu"),}));
		
		addHierarchyBoundsListener(new HierarchyBoundsAdapter() {
			@Override
			public void ancestorResized(HierarchyEvent e) {
				revalidate();
			}
		});

		//Panel for changing plot shit
		{
			final JPanel plotPanel = new JPanel();
			plotPanel.setBackground(GuiUtil.DEFAULTTRANSPARENTCOLOR);
			plotPanel.setBorder(new LineBorder(new Color(139, 69, 19), 1, false));
			plotPanel.setOpaque(true);
			add(plotPanel, "3, 2, fill, fill");

			plotPanel.setLayout(new FormLayout(new ColumnSpec[]{
						ColumnSpec.decode("1dlu:grow"),
					},
					new RowSpec[]{
						FormFactory.DEFAULT_ROWSPEC,
						RowSpec.decode("20dlu"),
						FormFactory.PREF_ROWSPEC,
						RowSpec.decode("20dlu"),
						RowSpec.decode("default:grow"),
						RowSpec.decode("default:grow(40)"),
					}));

			JLabel lblPlot = new JLabel(TextUtil.t("ui.realestate.plotdetails"));
			lblPlot.setFont(new Font("Tahoma", Font.BOLD, 15));
			plotPanel.add(lblPlot, "1, 1, center, default");

			final JComboBox<Plot> plotComboBox = new JComboBox<>();

			plotComboBox.addItem(null);
			plotComboBox.setSelectedIndex(0);
			for (Plot p: Jasbro.getInstance().getData().getRealEstateSystem().getOwnedPlots()) {
				plotComboBox.addItem(p);
			}

			plotComboBox.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					displayPlotDetails((Plot) plotComboBox.getSelectedItem());
					validate();
					repaint();
				}
			});

			plotComboBox.setBorder(new EmptyBorder(2, 2, 2, 2));
			plotPanel.add(plotComboBox, "1, 3, fill, top");

			plotDetailsPanel = new JPanel();
			plotDetailsPanel.setOpaque(false);
			plotPanel.add(plotDetailsPanel, "1, 5, fill, default");
		}

		//Panel for selling houses
		{
			final JPanel sellHousePanel = new JPanel();
			sellHousePanel.setBackground(GuiUtil.DEFAULTTRANSPARENTCOLOR);
			sellHousePanel.setBorder(new LineBorder(new Color(139, 69, 19), 1, false));
			sellHousePanel.setOpaque(true);
			//add(sellHousePanel, "5, 2, fill, fill");
			sellHousePanel.setLayout(new FormLayout(new ColumnSpec[] {
					ColumnSpec.decode("1dlu:grow"),},
					new RowSpec[] {
					RowSpec.decode("pref:none"),
					RowSpec.decode("fill:default:grow"),}));
			
			JLabel lblSellHouse = new JLabel("Sell Houses");
			lblSellHouse.setFont(new Font("Tahoma", Font.BOLD, 15));
			sellHousePanel.add(lblSellHouse, "1, 1, center, default");
			
			FormLayout formLayout = (FormLayout)sellHousePanel.getLayout();
			for (int i = 0; i < houses.size(); i++) {
				House house = houses.get(i);
				JPanel curHousePanel = new JPanel();
				curHousePanel.setOpaque(false);
				curHousePanel.setLayout(new GridLayout(1, 1));
				curHousePanel.setBorder(new EmptyBorder(10,20,10,20));
				formLayout.insertRow(i+2, RowSpec.decode("pref:none"));
				sellHousePanel.add(curHousePanel, "1,"+(i+2)+", fill, top");
				
				Object arguments[] = {house.getName(), house.getSellPrice()};
				JButton sellButton = new JButton(TextUtil.t("ui.realestate.sellhouse", arguments));
				sellButton.addActionListener(new MySellListener(house));
				sellButton.setEnabled(houses.size() > 1);
				curHousePanel.add(sellButton);
			}
		}
		
		
		//Panel for building Houses
		{
			final JPanel buildHousePanel = new JPanel();
			buildHousePanel.setBackground(GuiUtil.DEFAULTTRANSPARENTCOLOR);
			buildHousePanel.setBorder(new LineBorder(new Color(139, 69, 19), 1, false));
			buildHousePanel.setOpaque(true);
			//add(buildHousePanel, "3, 2, fill, fill");
			buildHousePanel.setLayout(new FormLayout(new ColumnSpec[] {
					ColumnSpec.decode("1dlu:grow"),},
					new RowSpec[] {
					RowSpec.decode("pref:none"),
					RowSpec.decode("fill:default:grow"),}));
			
			JLabel lblBuyHouse = new JLabel("Build Houses");
			lblBuyHouse.setFont(new Font("Tahoma", Font.BOLD, 15));
			buildHousePanel.add(lblBuyHouse, "1, 1, center, default");
			
			ActionListener myBuildListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton button = (JButton) e.getSource();
					HouseType type = HouseType.valueOf(button.getActionCommand());
					if (type != null) {
						House house = HouseUtil.newHouse(type);
						if (Jasbro.getInstance().getData().canAfford(house.getValue() * 2)) {
							houses.add(house);
							Jasbro.getInstance().getData().spendMoney(house.getValue() * 2, house.getName());
							new MessageScreen(house.getName() + " built!", house.getImage(), null);
							init();
						}
					}
				}
			};
			
			FormLayout formLayout = (FormLayout)buildHousePanel.getLayout();
			List<HouseType> houseTypes = Jasbro.getInstance().getData().getUnlocks().getAvailableHouseTypes();
			for (int i = 0; i < houseTypes.size(); i++) {
				HouseType type = houseTypes.get(i);
				JPanel curHousePanel = new JPanel();
				curHousePanel.setOpaque(false);
				curHousePanel.setLayout(new GridLayout(1, 1));
				curHousePanel.setBorder(new EmptyBorder(10,20,10,20));
				formLayout.insertRow(i+2, RowSpec.decode("pref:none"));
				buildHousePanel.add(curHousePanel, "1,"+(i+2)+", fill, top");
				
				House house = HouseUtil.newHouse(type);
				Object arguments[] = {house.getHouseType().getText(), house.getValue() * 2};
				JButton builtButton = new JButton(TextUtil.t("ui.realestate.buildhouse", arguments));
				builtButton.addActionListener(myBuildListener);
				builtButton.setActionCommand(type.toString());
				if (!Jasbro.getInstance().getData().canAfford(house.getValue() * 2)) {
					builtButton.setEnabled(false);
				}
				curHousePanel.add(builtButton);
			}
		}
		validate();
		
		addMouseListener (new MouseAdapter(){
	        public void mouseClicked(MouseEvent e) {
	            if (SwingUtilities.isRightMouseButton(e)) {
	            	Jasbro.getInstance().getGui().showBuildersGuildScreen();
	            }
	        }		
		});
	}

	private void displayPlotDetails(final Plot selectedPlot) {
		plotDetailsPanel.removeAll();
		plotDetailsPanel.setLayout(new FormLayout(new ColumnSpec[]{
			ColumnSpec.decode("1dlu:grow")
		},
		new RowSpec[]{
			FormFactory.DEFAULT_ROWSPEC,
			RowSpec.decode("default:grow"),
			RowSpec.decode("default:none"),
			RowSpec.decode("5dlu"),
			RowSpec.decode("default:none"),
			RowSpec.decode("5dlu"),
			RowSpec.decode("default:none"),
		}));

		if (selectedPlot != null) {
			final JButton buyHouseButton = new JButton();
			final JButton demoHouseButton = new JButton();

			final JComboBox<HouseType> houseTypeJComboBox = new JComboBox<>();
			houseTypeJComboBox.addItem(null);
			houseTypeJComboBox.setSelectedIndex(0);

			/*
			 * Listeners START
			 */
			buyHouseButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					 House h = HouseUtil.newHouse((HouseType) houseTypeJComboBox.getSelectedItem());
					 int demoCost = (selectedPlot.getHouse() == null) ? 0 : selectedPlot.getHouse().getValue()/2;

					if (Jasbro.getInstance().getData().canAfford(h.getValue() + demoCost)) {
						String message;

						if (demoCost > 0) {
							message = TextUtil.t("ui.realestate.replaceplot", h.getName(),
									selectedPlot.getHouse().getName(), TextUtil.t(selectedPlot.getId()));
							Jasbro.getInstance().getData().getRealEstateSystem().demolishHouse(selectedPlot.getId(),
																							   Jasbro.getInstance().getData());
						}
						else {
							message = TextUtil.t("ui.realestate.buildplot", h.getName(), TextUtil.t(selectedPlot.getId()));
						}

						Jasbro.getInstance().getData().getRealEstateSystem().buildHouse(selectedPlot.getId(), h,
																						Jasbro.getInstance().getData());
						new MessageScreen(message, h.getImage(), null);
						init();
					}
				}
			});

			demoHouseButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					House h = selectedPlot.getHouse();
					if (Jasbro.getInstance().getData().canAfford(h.getValue()/2)) {
						Jasbro.getInstance().getData().getRealEstateSystem().demolishHouse(selectedPlot.getId(),
																						   Jasbro.getInstance().getData());
						new MessageScreen(TextUtil.t("ui.realestate.demoplot", h.getName(), TextUtil.t(selectedPlot.getId())),
										  h.getImage(), null);
						init();
					}
				}
			});

			houseTypeJComboBox.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if (houseTypeJComboBox.getSelectedItem() != null) {
						HouseType ht = (HouseType) houseTypeJComboBox.getSelectedItem();

						int demoCost = (selectedPlot.getHouse() == null) ? 0 : selectedPlot.getHouse().getValue() / 2;
						buyHouseButton.setText(TextUtil.t("ui.realestate.buyhouse",
														 ht.getText(), HouseUtil.newHouse(ht).getValue() + demoCost));
						if (selectedPlot.getHouse() != null) {
							buyHouseButton.setEnabled(ht != selectedPlot.getHouse().getHouseType());
						} else {
							buyHouseButton.setEnabled(true);
						}
						buyHouseButton.setVisible(true);
					} else {
						buyHouseButton.setVisible(false);
					}
				}
			});

			houseTypeJComboBox.setRenderer(new DefaultListCellRenderer() {
				@Override
				public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
					JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
					if (value != null)
						lbl.setText(((HouseType)value).getText());
					else
						lbl.setText(" ");
					return lbl;
				}
			});
			/*
			 * Listeners END
			 */

			int added = -1;

			if (selectedPlot.getHouse() == null) {
				added = -2;
				buyHouseButton.setVisible(false);
				demoHouseButton.setVisible(false);
				demoHouseButton.setEnabled(false);
			} else {
				demoHouseButton.setText(TextUtil.t("ui.realestate.demohouse",
													selectedPlot.getHouse().getName(),
													selectedPlot.getHouse().getValue()/2));

			}

			for (int i = 0; i < Jasbro.getInstance().getData().getUnlocks().getAvailableHouseTypes().size(); i++) {
				HouseType houseType = Jasbro.getInstance().getData().getUnlocks().getAvailableHouseTypes().get(i);
				if (HouseUtil.newHouse(houseType).getRooms().size() <= selectedPlot.getMaxSize()) {
					if (added == -1 && houseType == selectedPlot.getHouse().getHouseType()) {
						added = i+1;
					}
					houseTypeJComboBox.addItem(houseType);
				}
			}

			if (added == -1) {
				HouseType curType = selectedPlot.getHouse().getHouseType();
				added = houseTypeJComboBox.getItemCount();
				houseTypeJComboBox.addItem(curType);
			}

			if (added > -1) {
				houseTypeJComboBox.setSelectedIndex(added);
			}

			plotDetailsPanel.add(houseTypeJComboBox, "1, 3, fill, top");
			plotDetailsPanel.add(buyHouseButton, "1,5,fill,top");
			plotDetailsPanel.add(demoHouseButton, "1,7,fill,top");

			int houseCount = 0;
			for (Plot p: Jasbro.getInstance().getData().getRealEstateSystem().getOwnedPlots()) {
				if (p.getHouse() != null) {
					houseCount++;
				}
			}

			if (houseCount < 2) {
				demoHouseButton.setEnabled(false);
			}

		}

		plotDetailsPanel.validate();
		plotDetailsPanel.repaint();
	}

	public boolean playerOwnsHouseType(HouseType houseType) {
		for (House house : Jasbro.getInstance().getData().getHouses()) {
			if (houseType == house.getHouseType()) {
				return true;
			}
		}
		return false;
	}
	
	public class MySellListener implements ActionListener {
		private House house;
		
		public MySellListener(House house) {
			this.house = house;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			synchronized (RealEstateMenu.this) {
				if (houses.size() > 0) {
					house.empty();
					houses.remove(house);
					Jasbro.getInstance().getData().earnMoney(house.getSellPrice(), house.getName());
					new MessageScreen(house.getName() + " sold!", house.getImage(), null);
					init();
				}
			}
		}
		
	}
}