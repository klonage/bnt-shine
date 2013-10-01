package pl.bntsystems.bntshine;

public abstract class SingleItem {
	protected String name;
	protected String userName;
	protected int address;
	protected int group;
	
	public SingleItem(String name, String userName, int address, int group) {
		this.name = name;
		this.userName = userName;
		this.address = address;
		this.group = group;
	}
	
	public boolean isGroup() {
		return address == -1;
	}
	
	public abstract DeviceType getType();
}

class LightItem extends SingleItem {
	private boolean state;
	
	public LightItem(String name, String userName, int address, int group) {
		super(name, userName, address, group);
		
		state = false;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
	public boolean getState() {
		return state;
	}
	
	@Override
	public DeviceType getType() {
		return DeviceType.LIGHT;
	}
	
}

class LightDimmerItem extends SingleItem {
	private int value; 
	
	public LightDimmerItem(String name, String userName, int address, int group) {
		super(name, userName, address, group);
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public DeviceType getType() {
		return DeviceType.LIGHT_DIMMER;
	}
	
}
