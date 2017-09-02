package org.olia.demo.ws;

import org.json.JSONObject;

public class OliaTicket {
	private String id;
	private int num;
	private String name;
	private String description;
	private String priority;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ID", id);
		jsonObject.put("Number", num);
		jsonObject.put("Name", name);
		jsonObject.put("Description", description);
		jsonObject.put("Priority", priority);
		
		return jsonObject;
	}
	
	public String toString() {
		return toJSON().toString();
	}
}
