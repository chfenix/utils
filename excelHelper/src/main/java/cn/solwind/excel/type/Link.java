package cn.solwind.excel.type;

/**
* @author chfenix
* @version 创建时间：2019-05-24
*
* 超链接类
*/

public class Link {
	
	/*
	 * 链接类型枚举
	 */
	public enum LinkType {
		FILE, URL, EMAIL, DOCUMENT
	}
	
	public Link(Object value, String address, LinkType type) {
		super();
		this.value = value;
		this.address = address;
		this.type = type;
	}

	/*
	 * 显示内容
	 */
	private Object value;
	
	/*
	 * 链接
	 */
	private String address;
	
	/*
	 * 链接类型
	 */
	private LinkType type;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LinkType getType() {
		return type;
	}

	public void setType(LinkType type) {
		this.type = type;
	}
	
	
}
