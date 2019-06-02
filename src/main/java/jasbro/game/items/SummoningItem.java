package jasbro.game.items;

public class SummoningItem extends Item {
	
	private String monsterID;
	
	public SummoningItem(String id) {
		super(id, ItemType.SUMMONING);
	}
	
	public SummoningItem(Item item) {
		super(item);
		setType(ItemType.SUMMONING);
	}
	
	public String getSummonedMonster(){
		return monsterID;
	}
	
	public void setSummonedMonster(String monsterID){
		this.monsterID = monsterID;
	}
	
	@Override
	public String getText(){
		return getDescription();
	}
}