package jasbro.game.realestate;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import jasbro.Jasbro;
import jasbro.game.GameData;
import jasbro.game.world.Time;
import jasbro.gui.objects.div.MyImage;
import jasbro.gui.pictures.ImageData;
import jasbro.texts.TextUtil;
import jasbro.util.ConfigHandler;
import jasbro.util.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BuyPlotMapMenu extends MyImage {
	private static final Logger LOG = LogManager.getLogger(BuyPlotMapMenu.class);

	int width = ConfigHandler.getResolution(Settings.RESOLUTIONWIDTH);
	int height = ConfigHandler.getResolution(Settings.RESOLUTIONHEIGHT);
	int widthRat = (int) (width/1280);
	int heightRat = (int) (height/720);
	int iconSize = 65*width/1280;
	int backHomeBtnWidth = (int) (150*width/1280);
	int backHomeBtnHeight = (int) (50*width/1280);
	int arrowBtnWidth = (int) (150*width/1280);
	int arrowBtnHeight = (int) (50*width/1280);
	private ImageIcon plotIcon1 = new ImageIcon("images/buttons/landmarker.png");
	private ImageIcon plotIcon2 = new ImageIcon("images/buttons/landmarker hover.png");
	private ImageIcon plotIcon3 = new ImageIcon("images/buttons/landmarker_sold.png");
	private ImageIcon plotIcon4 = new ImageIcon("images/buttons/landmarker_sold hover.png");
	private ImageIcon leftIcon1 = new ImageIcon("images/buttons/arrowleft.png");
	private ImageIcon leftIcon2 = new ImageIcon("images/buttons/arrowleft hover.png");
	private ImageIcon rightIcon1 = new ImageIcon("images/buttons/arrowright.png");
	private ImageIcon rightIcon2 = new ImageIcon("images/buttons/arrowright hover.png");

	private final int mapSize = 4;
	private int mapID;
	
	/**
	 * Create the panel.
	 */
	public BuyPlotMapMenu(int mapID) {
		removeAll();
		
		addMouseListener (new MouseAdapter(){
		    public void mouseClicked(MouseEvent e) {
		        if (SwingUtilities.isRightMouseButton(e)) {
		        	Jasbro.getInstance().getGui().showRealEstate();
		        }
		    }		
		});

		Image plotImage1 = plotIcon1.getImage().getScaledInstance( iconSize, iconSize,  java.awt.Image.SCALE_SMOOTH );
		plotIcon1 = new ImageIcon(plotImage1);

		Image plotImage2 = plotIcon2.getImage().getScaledInstance( iconSize, iconSize,  java.awt.Image.SCALE_SMOOTH );
		plotIcon2 = new ImageIcon(plotImage2);

		Image plotImage3 = plotIcon3.getImage().getScaledInstance( iconSize, iconSize,  java.awt.Image.SCALE_SMOOTH );
		plotIcon3 = new ImageIcon(plotImage3);

		Image plotImage4 = plotIcon4.getImage().getScaledInstance( iconSize, iconSize,  java.awt.Image.SCALE_SMOOTH );
		plotIcon4 = new ImageIcon(plotImage4);
		
		Image leftImage1 = leftIcon1.getImage().getScaledInstance( backHomeBtnHeight, backHomeBtnWidth,  java.awt.Image.SCALE_SMOOTH ) ;
		leftIcon1 = new ImageIcon(leftImage1);
		
		Image leftImage2 = leftIcon2.getImage().getScaledInstance( backHomeBtnHeight, backHomeBtnWidth,  java.awt.Image.SCALE_SMOOTH ) ;
		leftIcon2 = new ImageIcon(leftImage2);
		
		Image rightImage1 = rightIcon1.getImage().getScaledInstance( backHomeBtnHeight, backHomeBtnWidth,  java.awt.Image.SCALE_SMOOTH ) ;
		rightIcon1 = new ImageIcon(rightImage1);
				
		Image rightImage2 = rightIcon2.getImage().getScaledInstance( backHomeBtnHeight, backHomeBtnWidth,  java.awt.Image.SCALE_SMOOTH ) ;
		rightIcon2 = new ImageIcon(rightImage2);

		this.mapID = mapID;
		displayMap();
	}

	public void displayMap() {
		removeAll();
		setBackgroundImage(getTownImage(getMap()));
		setBackground(Color.WHITE);
		setLayout(null);
		setVisible(true);

		int[] xPos = new int[0];
		int[] yPos = new int[0];

		switch (mapID) {
			case 1:
				xPos = new int[]{580, 50, 425, 190};
				yPos = new int[]{320, 80, 280, 320};
				break;
			case 2:
				xPos = new int[]{860, 475, 595, 800, 1150};
				yPos = new int[]{505, 15, 220, 320, 460};
				break;
			case 3:
				xPos = new int[]{600, 630, 970, 100};
				yPos = new int[]{520, 35, 125, 320};
				break;
			case 4:
				xPos = new int[]{550, 150, 670, 685, 190};
				yPos = new int[]{585, 385, 75, 323, 170};
				break;
			default:
				LOG.warn("Trying to display a non-existent map '{}'", mapID);
				break;
		}

		if (Jasbro.getInstance().getData().getTime() != Time.NIGHT) {
			JButton plotButton;

			for (int i = 0; i < xPos.length; i++) {
				plotButton = buildPlotButton(getPlotName(i+1), xPos[i], yPos[i]);
				if (plotButton != null) add(plotButton);
			}

			buildMapChangeButtons();
		}

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
		add(backButton);

		backButton.addActionListener(new  ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Jasbro.getInstance().getGui().showRealEstate();
			}
		});

		validate();
		repaint();
	}

	public ImageData getTownImage(String map) {
		switch(map) {
			case "map2":
				return new ImageData("images/backgrounds/map2.jpg");
			case "map3":
				return new ImageData("images/backgrounds/map3.jpg");
			case "map4":
				return new ImageData("images/backgrounds/map4.jpg");
			default:
				return new ImageData("images/backgrounds/town_morning.png");
		}
	}

	private String getMap() {
		return "map" + mapID;
	}

	private String getPlotName(int num) {
		return getMap() + ".plot" + num;
	}

	/**
	 * Builds the button that allows the user to purchase a plot.
	 * @param plotID The plot's id.
	 * @param x The x position of the button. (This will scaled according to the ratio.)
	 * @param y The y position of the button. (This will scaled according to the ratio.)
	 * @return The button to attach to the UI.
	 */
	private JButton buildPlotButton(final String plotID, int x, int y) {
		final GameData gameData = Jasbro.getInstance().getData();
		final Plot curPlot = gameData.getRealEstateSystem().getPlot(plotID);


		if (curPlot == null) {
			LOG.warn("Couldn't make button for plot {} on map {}", plotID, mapID);
			return null; // TODO Throw error
		}

		final boolean owned = Jasbro.getInstance().getData().getRealEstateSystem().getOwnedPlot(plotID) != null;

		JButton plotButton = new JButton((owned) ? plotIcon3 : plotIcon1);
		plotButton.setRolloverIcon((owned) ? plotIcon4 : plotIcon2);
		plotButton.setPressedIcon((owned) ? plotIcon3 : plotIcon1);
		plotButton.setBounds(x *width/1280, y*height/720, iconSize, iconSize);
		plotButton.setBorderPainted(false);
		plotButton.setContentAreaFilled(false);
		plotButton.setFocusPainted(false);
		plotButton.setOpaque(false);
		plotButton.setToolTipText(TextUtil.t(plotID)); // TODO This may need to be changed if maps are to become defined by users.

		plotButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!owned && JOptionPane.showConfirmDialog (Jasbro.getInstance().getGui(), TextUtil.t("ui.realestate.buyplot", curPlot.getCost()), TextUtil.t("ui.confirmResetPerks.title"), JOptionPane.OK_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
					if (gameData.canAfford(curPlot.getCost())) {
						gameData.getRealEstateSystem().buyPlot(plotID, gameData);
						displayMap();
					}
					else
						LOG.info("Player too poor to afford plot. {} < {}", Jasbro.getInstance().getData().getMoney(), curPlot.getCost());
				} else if (owned && JOptionPane.showConfirmDialog(Jasbro.getInstance().getGui(), TextUtil.t("ui.realestate.sellplot", curPlot.getCost()/2)) == JOptionPane.YES_OPTION) {
					gameData.getRealEstateSystem().sellPlot(plotID, gameData);
					displayMap();
				}
			}
		});

		return plotButton;
	}

	/**
	 * Builds the arrows on the sides of the buy plot screen that navigate between the different maps.
	 */
	private void buildMapChangeButtons() {
		final int left = (mapID - 1 <= 0) ? mapSize: mapID - 1;
		final int right = (mapID % mapSize) + 1;

		JButton leftButton = new JButton(leftIcon1);
		leftButton.setRolloverIcon(leftIcon2);
		leftButton.setPressedIcon(leftIcon1);
		leftButton.setBounds((int) (15*width/1280),(int) (250*height/720), backHomeBtnHeight, backHomeBtnWidth);
		leftButton.setBorderPainted(false);
		leftButton.setContentAreaFilled(false);
		leftButton.setFocusPainted(false);
		leftButton.setOpaque(false);
		add(leftButton);

		leftButton.addActionListener(new  ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOG.info("Left button goes to: {}", left);
				Jasbro.getInstance().getGui().showBuyPlotMapScreen(left);
			}
		});

		JButton rightButton = new JButton(rightIcon1);
		rightButton.setRolloverIcon(rightIcon2);
		rightButton.setPressedIcon(rightIcon1);
		rightButton.setBounds((int) (1210*width/1280),(int) (250*height/720), backHomeBtnHeight, backHomeBtnWidth);
		rightButton.setBorderPainted(false);
		rightButton.setContentAreaFilled(false);
		rightButton.setFocusPainted(false);
		rightButton.setOpaque(false);
		add(rightButton);

		rightButton.addActionListener(new  ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOG.info("Right button goes to: {}", right);
				Jasbro.getInstance().getGui().showBuyPlotMapScreen(right);
			}
		});
	}
}