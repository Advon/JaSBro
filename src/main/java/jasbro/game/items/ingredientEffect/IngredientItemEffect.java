package jasbro.game.items.ingredientEffect;

import jasbro.game.character.Charakter;
import jasbro.game.items.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class IngredientItemEffect implements Serializable {
	public abstract String getName();
	public abstract void apply(Charakter character, Item item);
	public abstract IngredientItemEffectType getType();
	
	public List<IngredientItemEffect> getSubEffects() {
		return new ArrayList<IngredientItemEffect>();
	}
	
	
}