package jasbro.game.events.rooms;

import java.util.List;

import jasbro.Util.TypeAmounts;
import jasbro.game.character.activities.ActivityDetails;
import jasbro.game.character.activities.ActivityType;
import jasbro.game.events.EventType;
import jasbro.game.events.MyEvent;
import jasbro.game.housing.ConfigurableRoom;
import jasbro.game.housing.House;
import jasbro.game.housing.RoomInfo;
import jasbro.game.world.Time;

public class Crypt extends ConfigurableRoom implements RoomEventHandler {	
	private String demonName="nobody";
	private int ritualAdvancment = 0;
	
	public Crypt(final RoomInfo roomInfo) {
		super(roomInfo);
	}
	
	public Crypt(final RoomInfo roomInfo, final House house) {
		super(roomInfo, house);
	}
	
	@Override
	public String getName() {
		return super.getName() + " Worshipping " + demonName + " (Advancment:" + ritualAdvancment +"/13)";
	}
	@Override
	public void handleEvent(MyEvent e) {
		super.handleEvent(e);
	}


	public String getDemonName() {
		return demonName;
	}

	public void setDemonName(String demonName) {
		this.demonName = demonName;
	}

	public int getRitualAdvancment() {
		return ritualAdvancment;
	}

	public void setRitualAdvancment(int ritualAdvancment) {
		this.ritualAdvancment = ritualAdvancment;
	}
	

	

}