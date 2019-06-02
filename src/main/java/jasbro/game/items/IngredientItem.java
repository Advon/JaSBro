package jasbro.game.items;

import jasbro.game.character.Charakter;
import jasbro.game.character.Condition;
import jasbro.game.character.conditions.ItemCooldown;
import jasbro.game.events.EventType;
import jasbro.game.events.MyEvent;
import jasbro.game.items.ingredientEffect.IngredientItemEffect;
import jasbro.game.items.usableItemEffects.UsableItemEffect;
import jasbro.texts.TextUtil;

public class IngredientItem extends Item {
	
	public IngredientItem(String id) {
		super(id, ItemType.INGREDIENT);
	}	
	
	public IngredientItem(Item item) {
		super(item);
		setType(ItemType.INGREDIENT);
	}
	
	private IngredientItemEffect itemEffect;
	
	@Override
	public String getText() {
		return "<b>" + getName() + "</b>\n" +
				TextUtil.t("typeConsumable") + "\n" +
				TextUtil.t("valueItem", new Object[]{getValue()}) + "\n" +
				getDescription();
	}
	
	public IngredientItemEffect getItemEffect() {
		return itemEffect;
	}
	
	public void setItemEffect(IngredientItemEffect itemEffect) {
		this.itemEffect = itemEffect;	}
	
	
}