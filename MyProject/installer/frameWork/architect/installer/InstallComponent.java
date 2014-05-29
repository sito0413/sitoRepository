package frameWork.architect.installer;

public class InstallComponent {
	public String label;
	public String name;
	public int size;
	
	public InstallComponent(final String label, final String name, final int size) {
		super();
		this.label = label;
		this.name = name;
		this.size = size;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getName() {
		return name;
	}
	
	public int getSize() {
		return size;
	}
}