package gestionpersonnage;

public abstract class Item{
		private boolean recuperable;
		
		/**
		 * Constructeur de la classe Item
		 */
		public Item(boolean recuperable) {
			this.recuperable = recuperable;
		}
		
		public abstract String toString();
}