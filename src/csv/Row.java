package csv;

public class Row {
	private String[] data;
	private CSVTable table;
	
	public Row(String[] data, CSVTable parent) {
		this.table = parent;
		this.data = data;
	}
	
	public String get(String key) {
		int index = table.getKeyIndex(key);
		if (index < 0) {
			return null;
		} else {
			return data[index];
		}
	}
}
