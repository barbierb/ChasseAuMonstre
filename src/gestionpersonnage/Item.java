package gestionpersonnage;

public abstract class Item{
		protected boolean recuperable;
		

		public abstract String toString();
		
		public boolean estRecuperable() {
			return this.recuperable;
		}
}