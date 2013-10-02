/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package en.odecram.story_liner.views;

import en.odecram.story_liner.controlers.Actions;
import en.odecram.story_liner.controlers.mainContent.MenuActionListener;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Nassim
 */
public class ToolsPanel extends JPopupMenu {

	/**
	 * The main content panel
	 */
	private MainContent mContent;
	/**
	 * actions to do when for each option in the menu
	 */
	private Actions actions;
	/**
	 * the actin listener menu
	 */
	private MenuActionListener mal;
	/**
	 * Tool list
	 */
	private ArrayList<JButton> Buttons;
	/**
	 *
	 * Tool name list
	 */
	private ArrayList<String> Button_names;

	public ToolsPanel(MainContent mContent, Actions actions) {
		this.mContent = mContent;
		this.actions = actions;
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		mal = new MenuActionListener(actions);
		//this.setSize(new Dimension(100,100));


		Buttons = new ArrayList<JButton>();
		Button_names = new ArrayList<String>();

	}

	public void addTool(Icon icone, String name, String cmd) {
		System.out.println("dans add image tool");

		JMenuItem menuItem = new JMenuItem(name);
		menuItem.setActionCommand(cmd);
		menuItem.setIcon(icone);
		menuItem.addActionListener(mal);

		add(menuItem);
	}

	/**
	 * Changes the previewed image
	 *
	 * @param path The path to the image to preview
	 */
	public void addTool(String path, String name, String cmd) {

		Icon icone = new ImageIcon(path);
		addTool(icone, name, cmd);
	}
}
