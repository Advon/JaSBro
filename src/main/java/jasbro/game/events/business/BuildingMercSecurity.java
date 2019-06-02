package jasbro.game.events.business;

import jasbro.Jasbro;
import jasbro.game.GameData;
import jasbro.game.housing.House;
import jasbro.texts.TextUtil;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BuildingMercSecurity implements Serializable{

	private boolean mercenaries = false;
		
		public void perform(House house) {
			GameData gameData = Jasbro.getInstance().getData();
			
			if (mercenaries) {
				int price = (int) (gameData.getMoney()/33);
				if (gameData.canAfford(price)) {
					house.setSecurity(10000);
					gameData.spendMoney(price, TextUtil.t("mercenaries"));
				}
			}
		}
		
	public boolean hasMercs() {
		return mercenaries;
	}
	public void setMercs(boolean m) {
		mercenaries = m;
	}	
}
