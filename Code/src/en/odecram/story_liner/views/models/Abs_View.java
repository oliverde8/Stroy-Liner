
package en.odecram.story_liner.views.models;

import javax.swing.JPanel;

/**
 *
 * @author De Cramer Oliver
 */
public abstract class Abs_View extends JPanel{

	protected float scale = 1;

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
}
