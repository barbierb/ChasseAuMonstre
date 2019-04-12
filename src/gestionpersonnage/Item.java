package gestionpersonnage;

public abstract class Item{
		public boolean recuperable;
		

		public abstract String toString();
		
		public boolean estRecuperable() {
			return this.recuperable;
		}
}