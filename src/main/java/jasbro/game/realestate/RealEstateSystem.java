package jasbro.game.realestate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jasbro.game.GameData;
import jasbro.game.housing.House;
import jasbro.game.housing.HouseType;
import jasbro.game.housing.HouseUtil;

/**
 * Logic code for plots of land and construction. Public functions should be
 * kept to a minimum, and preferably game mechanic related.
 * 
 * @author somextra
 */
public class RealEstateSystem {
	private static final transient Logger LOG = LogManager.getLogger(RealEstateSystem.class);

	private final Map<String, Plot> plots;
	private final List<Plot> ownedPlots;
	private final List<Plot> freePlots;

	public RealEstateSystem() {
		this.plots = new HashMap<>();
		this.ownedPlots = new ArrayList<>();
		this.freePlots = new ArrayList<>();
	}

	public void init() {
		Plot start = createPlot("start.plot", 4, 0, 0, HouseUtil.newHouse(HouseType.HUT));

		// Map 1
		this.freePlots.add(createPlot("map1.plot1", 4, 10000, 0));
		this.freePlots.add(createPlot("map1.plot2", 10, 25000, 0));
		this.freePlots.add(createPlot("map1.plot3", 8, 20000, 0));
		this.freePlots.add(createPlot("map1.plot4", 6, 15000, 0));

		// Map 2
		this.freePlots.add(createPlot("map2.plot1", 8, 20000, 0));
		this.freePlots.add(createPlot("map2.plot2", 12, 30000, 0));
		this.freePlots.add(createPlot("map2.plot3", 6, 15000, 0));
		this.freePlots.add(createPlot("map2.plot4", 8, 20000, 0));
		this.freePlots.add(createPlot("map2.plot5", 10, 25000, 0));

		// Map 3
		this.freePlots.add(createPlot("map3.plot1", 14, 35000, 0));
		this.freePlots.add(createPlot("map3.plot2", 10, 25000, 0));
		this.freePlots.add(createPlot("map3.plot3", 8, 20000, 0));
		this.freePlots.add(createPlot("map3.plot4", 10, 25000, 0));

		// Map 4
		this.freePlots.add(createPlot("map4.plot1", 10, 25000, 0));
		this.freePlots.add(createPlot("map4.plot2", 12, 30000, 0));
		this.freePlots.add(createPlot("map4.plot3", 16, 40000, 0));
		this.freePlots.add(createPlot("map4.plot4", 12, 30000, 0));
		this.freePlots.add(createPlot("map4.plot5", 8, 20000, 0));

		this.ownedPlots.add(start);
	}

	public Plot createPlot(final String id, final int maxSize, final int cost, final int quality, final House house) {
		Validate.notBlank(id, "Attempted to create a plot with a blank ID");
		if (this.plots.containsKey(id)) {
			LOG.error("Attempted to create plot with duplicate id '{}'", id);
			return null;
		}
		Plot p = new Plot(id, maxSize, cost, quality, house);
		this.plots.put(id, p);
		return p;
	}

	public Plot createPlot(final String id, final int maxSize, final int cost, final int quality) {
		return createPlot(id, maxSize, cost, quality, null);
	}

	public Collection<Plot> getOwnedPlots() {
		return Collections.unmodifiableList(ownedPlots);
	}

	public Collection<Plot> getFreePlots() {
		return Collections.unmodifiableList(freePlots);
	}

	public Collection<Plot> getAllPlots() {
		return Collections.unmodifiableCollection(this.plots.values());
	}

	public Plot getPlot(final String id) {
		return this.plots.get(id);
	}

	public Plot getOwnedPlot(final String id) {
		Plot p = this.plots.get(id);
		if (this.ownedPlots.contains(p)) {
			return p;
		}
		return null;
	}

	public Plot getFreePlot(final String id) {
		Plot p = this.plots.get(id);
		if (this.freePlots.contains(p)) {
			return p;
		}
		return null;
	}

	/**
	 * Buys a plot of land, making it owned
	 * 
	 * @param id
	 *            The ID of the plot to buy
	 * @param data
	 *            The GameData to charge
	 */
	public void buyPlot(final String id, final GameData data) {
		Plot plot = getRequiredFreePlot(id);

		if (data.canAfford(plot.getCost())) {
			data.spendMoney(plot.getCost(), plot);
			ownedPlots.add(plot);
			freePlots.remove(plot);
		} else {
			LOG.warn("Cannot afford plot {}", id);
		}
	}

	/**
	 * Sells a plot of land and makes it free
	 * 
	 * @param id
	 *            The owned plot of land
	 * @param data
	 *            The GameData to credit
	 */
	public void sellPlot(final String id, final GameData data) {
		Plot plot = getRequiredOwnedPlot(id);

		data.earnMoney(plot.getCost()/2, plot);
		ownedPlots.remove(plot);
		freePlots.add(plot);
	}

	/**
	 * Places a house on a plot of land
	 * 
	 * @param plotId
	 *            The plot to build on
	 * @param house
	 *            The house to build
	 * @param data
	 *            The GameData for charging
	 */
	public void buildHouse(final String plotId, final House house, final GameData data) {
		// TODO how to handle the house? move house selection/creation here?

		Plot plot = getOwnedPlot(plotId);

		if (data.canAfford(house.getValue()) && canPlaceHouse(plotId, house)) {
			data.spendMoney(house.getValue(), plot);
			plot.setHouse(house);
		} else {
			LOG.warn("Can't place house '{}' on plot '{}'. This probably shouldn't have been reached.",
					house.getHouseType(), plotId);
		}
	}

	/**
	 * Removes the house on the plot of land
	 * 
	 * @param plotId
	 *            The plot to clear
	 * @param data
	 *            The GameData for charging
	 */
	public void demolishHouse(final String plotId, final GameData data) {
		Plot plot = getRequiredOwnedPlot(plotId);

		if(data.canAfford(plot.getHouse().getValue() / 2)) {
			data.spendMoney(plot.getHouse().getValue() / 2, plot);
			plot.getHouse().empty();
			plot.setHouse(null);
		}
	}

	private Plot getRequiredPlot(final String id) {
		Plot p = this.plots.get(id);
		Validate.notNull(p, "Given plot ID '%s' does not exist", id);
		return p;
	}

	private Plot getRequiredOwnedPlot(final String id) {
		Plot p = getRequiredPlot(id);
		Validate.isTrue(this.ownedPlots.contains(p), "Given ID '%s' does not match an owned plot of land", id);
		return p;
	}

	private Plot getRequiredFreePlot(final String id) {
		Plot p = getRequiredPlot(id);
		Validate.isTrue(this.freePlots.contains(p), "Given ID '%s' does not match a free plot of land", id);
		return p;
	}

	private boolean canPlaceHouse(final String plotId, final House house) {
		return getOwnedPlot(plotId).getMaxSize() >= house.getRoomAmount();
	}
}
