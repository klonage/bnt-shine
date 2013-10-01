package pl.bntsystems.bntshine;

public class ItemFactory {
	public static SingleItem createItem(String name, String customName, int address, int group, DeviceType type) {
		switch (type) {
		case LIGHT:
			return new LightItem(name, customName, address, group);
		default:
			return null;
		}
	}
}

enum DeviceType {
	LIGHT, LIGHT_DIMMER, SHUTTER, SCENE
}
