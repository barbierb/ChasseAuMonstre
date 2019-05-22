package plateau;

public interface ContientItem {
	
	public boolean hasEtoile();
	public void placerEtoile();
	public void enleverEtoile();
	
	
	public int getLongueVue();
	public void placerLVTemp();
	public void placerLV();
	public void decrLV();
}
