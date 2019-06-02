package jasbro.game.items.ingredientEffect;

import jasbro.util.itemEditor.usableItemEffectPanel.UsableItemAddConditionPanel;
import jasbro.util.itemEditor.usableItemEffectPanel.UsableItemAddGoldPanel;
import jasbro.util.itemEditor.usableItemEffectPanel.UsableItemAddSpecializationPanel;
import jasbro.util.itemEditor.usableItemEffectPanel.UsableItemAddTraitPanel;
import jasbro.util.itemEditor.usableItemEffectPanel.UsableItemChangeAttributeMaxPanel;
import jasbro.util.itemEditor.usableItemEffectPanel.UsableItemChangeAttributePanel;
import jasbro.util.itemEditor.usableItemEffectPanel.UsableItemCooldownPanel;
import jasbro.util.itemEditor.usableItemEffectPanel.UsableItemEffectChancePanel;
import jasbro.util.itemEditor.usableItemEffectPanel.UsableItemRemoveConditionPanel;
import jasbro.util.itemEditor.usableItemEffectPanel.UsableItemRemoveTraitPanel;
import jasbro.util.itemEditor.usableItemEffectPanel.UsableItemShowMessagePanel;
import jasbro.util.itemEditor.usableItemEffectPanel.UsableItemSpeedUpPregnancyPanel;

import javax.swing.JPanel;

public enum IngredientItemEffectType {
	CHANGEATTRIBUTE(IngredientItemChangeAttribute.class, UsableItemAddGoldPanel.class),
	;
	
	private Class<? extends IngredientItemEffect> itemEffectClass;
	private Class<? extends JPanel> itemEffectPanelClass;
	
	private IngredientItemEffectType(Class<? extends IngredientItemEffect> itemEffectClass,
			Class<? extends JPanel> itemEffectPanelClass) {
		this.itemEffectClass = itemEffectClass;
		this.itemEffectPanelClass = itemEffectPanelClass;
	}
	
	public Class<? extends IngredientItemEffect> getItemEffectClass() {
		return itemEffectClass;
	}
	
	public Class<? extends JPanel> getItemEffectPanelClass() {
		return itemEffectPanelClass;
	}
}