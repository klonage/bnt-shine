package com.bnt.bntshine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.bnt.bntshine.activities.MainActivity;

public class ProfileManager {
	MainGridAdapter adapter;
	MainActivity activity;

	public void setAdapter(MainGridAdapter adapter) {
		this.adapter = adapter;
	}

	public void setActivity(MainActivity activity) {
		this.activity = activity;
	}

	public void saveToConfigFile() {
		for (int i = 0; i < adapter.pageCount(); i++) {
			savePageToConfigFile(i);
		}
	}

	private void savePageToConfigFile(int page) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
		SharedPreferences.Editor editor = preferences.edit();

		editor.putInt("profile.page_items_count_" + page, adapter.getPage(page).itemsCount());

		for (int i = 0; i < adapter.getPage(page).itemsCount(); i++) {
			GlobalItem item = adapter.getPage(page).getItem(i);
			editor.putInt(getPrefixPage(page, i) + ".address", item.getAddress());
			editor.putInt(getPrefixPage(page, i) + ".group", item.getGroup());
			editor.putString(getPrefixPage(page, i) + ".user_name", item.getUserName());
			editor.putInt(getPrefixPage(page, i) + ".type", item.getType());
		}

		editor.commit();
	}

	private String getPrefixPage(int page, int i) {
		return "profile.page_" + page + "_" + "_" + i;
	}

	public boolean saveToExternalFile(String fileName) {
		if (!isExternalStorageWritable())
			return false;

		try {
			Document doc = generateXmlTree();
			
			if (doc == null) 
				return false;
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			transformer.transform(new DOMSource(doc), result);
			File file = new File(fileName);

			FileOutputStream f = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(f);
			pw.print(writer.toString());
			pw.flush();
			pw.close();
			f.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	private Document generateXmlTree() {
		Document doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e) {
			return null;
		}
		Element root = doc.createElement("settings");
		doc.appendChild(root); 
		
		for (int page = 0; page < AppConfiguration.getPagesCount(); page++) {

			Element elementPage = doc.createElement("page");
			elementPage.setAttribute("items_count", Integer.toString(adapter.getPage(page).itemsCount()));
			elementPage.setAttribute("id", Integer.toString(page));
			root.appendChild(elementPage);

			for (int i = 0; i < adapter.getPage(page).itemsCount(); i++) {
				GlobalItem item = adapter.getPage(page).getItem(i);
				Element elementItem = doc.createElement("item");
				elementItem.setAttribute("address", Integer.toString(item.getAddress()));
				elementItem.setAttribute("group", Integer.toString(item.getGroup()));
				elementItem.setAttribute("user_name", item.getUserName());
				elementItem.setAttribute("type", Integer.toString(item.getType()));
				elementPage.appendChild(elementItem);
			}
		}
		return doc;
	}

	private boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	public void loadFromConfigFile() {
		for (int i = 0; i < AppConfiguration.getPagesCount(); i++) {
			loadPageFromConfigFile(i);
		}
	}

	private void loadPageFromConfigFile(int page) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
		int cnt = preferences.getInt("profile.page_items_count_" + page, 0);

		for (int i = 0; i < cnt; i++) {
			int address = preferences.getInt(getPrefixPage(page, i) + ".address", -1);
			int group = preferences.getInt(getPrefixPage(page, i) + ".group", -1);
			int type = preferences.getInt(getPrefixPage(page, i) + ".type", 1);
			String userName = preferences.getString(getPrefixPage(page, i) +  ".user_name", "");

			GlobalItem item = getGlobalItem(address, group, type);

			if (item != null) {
				item.setUserName(userName);
				adapter.getPage(page).addItem(item);
			}
		}
	}

	private GlobalItem getGlobalItem(int address, int group, int type) {
		List<GlobalItem> items = ((MyApplication) activity.getApplication()).getAllItems();

		if (address == -1)
			return getGroupItem(group, type);

		for (GlobalItem item : items) {
			if (item.getGroup() == group && item.getAddress() == address && item.getType() == type) {
				return item;
			}
		}

		return null;
	}

	private GlobalItem getGroupItem(int group, int type) {
		Map<Integer, String> names = ((MyApplication) activity.getApplication()).getGroupNames();

		return new GlobalItem(names.get(group), group, -1, type, adapter);
	}

	public void ImportProfile(String fileName) {

	}

	public void ExportProfile(String fileName) {

	}

	public void removeLocalProfile() {
		for (int i = 0; i < adapter.pageCount(); i++) {
			adapter.getPage(i).getItems().clear();
		}

		saveToConfigFile();
		activity.refreshMainView();
	}
}
