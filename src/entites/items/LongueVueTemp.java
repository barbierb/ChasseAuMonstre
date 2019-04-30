package entites.items;

import launcher.Engine;
import plateau.Case;
import plateau.Position;

/**
 * Classe qui �tend une longue vue et repr�sente les longues vues bonus pour le chasseur � l'utilisation d'une �toile</br> Ces longues vues durent TEMPS_VIE tours.
 * @author Sylvain
 */
public class LongueVueTemp extends LongueVue {
	public final int TEMPS_VIE;

	private int vie;

	public LongueVueTemp(Position pos) {
		super(pos);
		this.TEMPS_VIE = 3;
		LongueVueTemp.NB_MAX = 3;
		this.vie = 0;
	}
	
	/**
	 * @return le temps depuis lequel la longue vue est pos�e
	 */
	public int getVie() {
		return vie;
	}

	/**
	 * incr�mente le nombre de tour depuis lequel la longue vue est pos�e
	 */
	public void incrVie() {
		this.vie++;
	}
	
	/**
	 * V�rfifie toutes les longues vues temporaires du tableau et les supprime si n�cessaire
	 */
	public static void checkLVTemp() {
		for(Case c : Engine.getInstance().getPlateau()) {
			for(Item i : c.getDedans()) {
				if(i instanceof LongueVueTemp) {
					LongueVueTemp lvt = (LongueVueTemp) i;
					if(lvt.getVie() == lvt.TEMPS_VIE) {
						lvt.supprLongueVue();
					} else lvt.incrVie();
				}
			}
		}
	}
}
