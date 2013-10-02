package en.odecram.story_liner.views;

import en.odecram.story_liner.controlers.Actions;
import en.odecram.story_liner.controlers.Save;
import en.odecram.story_liner.controlers.mainContent.KeyboardListener;
import en.odecram.story_liner.controlers.mainContent.MenuActionListener;
import en.odecram.story_liner.controlers.mainContent.mouseListener;
import en.odecram.story_liner.models.Loading;
import en.odecram.story_liner.models.Step;
import en.odecram.story_liner.models.interfaces.OnMouse;
import en.odecram.story_liner.views.dragables.ImageElement;
import en.odecram.story_liner.views.dragables.TextElement;
import en.odecram.story_liner.views.dragables.VerticalSectionSeparator;
import en.odecram.story_liner.views.dragables.models.Abs_StepElement;
import en.odecram.story_liner.views.sections.LeftSection;
import en.odecram.story_liner.views.sections.StepEditionSection;
import en.odecram.story_liner.views.sections.StepEditionSection2;
import en.odecram.story_liner.views.sections.StepEditionSection3;
import en.odecram.story_liner.views.sections.StepEditionSection4;
import en.odecram.story_liner.views.sections.StepMenageSection;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import sun.util.BuddhistCalendar;

/**
 *
 * @author De Cramer Oliver
 */
public class MainContent extends JPanel {

	static final public String APP_NAME = "Story Liner";
	/*
	 * The minimal width of the left section. 
	 */
	static final public int LEFTSECTION_MINWIDTH = 175;
	/**
	 * Step menaging sections height
	 */
	static final public int STEPMENAGESECTION_HEIGHT = 125;
	/**
	 * The Height of the menu
	 */
	static final public int MENU_HEIGHT = 20;
	/**
	 * The left section panel
	 */
	private LeftSection left_section;
	/**
	 * The width of this panel
	 */
	private int left_section_width;
	/**
	 * The step editor section on the top right
	 */
	private StepEditionSection stepEditor_section;
	private StepEditionSection3 stepEditor_section3;
	/**
	 * The step menage section on the bottom right
	 */
	public StepMenageSection stepMenage_section;
	/**
	 * THe menuBar on the top
	 */
	private JMenuBar menu;
	/**
	 * The panel that will show it self only when need to add text
	 */
	public TextAdd tadd;
	/**
	 * the tool popup menu
	 */
	private ToolsPanel tools;
	/**
	 * The transperent drag panel on top of all other panels
	 */
	private DragPanel dragPanel;
	/**
	 * The jframe showing the window.
	 */
	public JFrame jframe;
	public Actions action;
	JMenuItem menu_lineColor;
	JMenuItem menu_bgColor;
	public ButtonGroup menu_group_mode;
	/**
	 * The mouse listener that allows all drag and drops
	 */
	private mouseListener ml;

	public MainContent(JFrame jframe) {
		this(jframe, null);
	}

	public MainContent(JFrame jframe, Loading l) {
		//Default width is minimum width
		left_section_width = LEFTSECTION_MINWIDTH;
		this.jframe = jframe;

		setLayout(null);

		if (l != null) {
			l.nextStep("Creatin Edition Panel & Mouse Listener");
		}
		//Creating the StepEditionSection
		createEditionPandML();

		if (l != null) {
			l.nextStep("Creating Drag Panel");
		}
		//Creating the drag panel
		createDragPanel();

		if (l != null) {
			l.nextStep("...");
		}
		//Creating the view that allows text to be addeds
		tadd = new TextAdd(this, stepEditor_section);
		add(tadd);

		if (l != null) {
			l.nextStep("Creating Left Section & File Explorer");
		}
		//Creating the left section
		left_section = new LeftSection(this);
		add(left_section);

		if (l != null) {
			l.nextStep("...");
		}
		//We only add Step editor now. (TO put it below textAdd
		add(stepEditor_section3);

		if (l != null) {
			l.nextStep("Creating Step Management Panel");
		}
		//Creating the Step Manage Section
		stepMenage_section = new StepMenageSection(this, stepEditor_section);
		add(stepMenage_section);

		if (l != null) {
			l.nextStep("Creating Actions");
		}
		//Creating the Actons class
		action = new Actions(this, tadd, stepMenage_section, ml, stepEditor_section);

		if (l != null) {
			l.nextStep("Creating Menu");
		}
		//Creating the menu.
		menu = new JMenuBar();
		//Creating the menu items
		createMenu();
		//We added to the panel
		add(menu);

		//create the tool menu
		createToolsPanel();

		if (l != null) {
			l.nextStep("Creating Empty Step");
		}
		//Creating and empty Step
		Step s = new Step(this);
		stepEditor_section.setStep(s);
		stepMenage_section.setSelectedStep(s);
		stepMenage_section.addSteptoEnd(s);

		if (l != null) {
			l.nextStep("Starting to generate the Window");
		}
		reArrenge();
	}

	public void createEditionPandML() {
		stepEditor_section = new StepEditionSection(this);
		stepEditor_section3 = new StepEditionSection3(this, stepEditor_section);

		//Creating the mouse listener
		ml = new mouseListener(this, stepEditor_section);

		//Adding the mouse listener to the Panel
		addMouseListener(ml);
		addMouseMotionListener(ml);

		//We won't use any layout
		setLayout(null);

		//Now that the mouseListener is ready initializing the StepEditor
		stepEditor_section.init();
	}

	/**
	 * insert element in the popup menu
	 */
	public void createToolsPanel() {
		tools = new ToolsPanel(this, action);
		tools.addTool("img/add/text.png", "Add Text", "add text");

		tools.addTool("img/edit/delete.png", "Delete selected", "edit.delete");

		tools.addTool("img/edit/bringFront.jpg", "BringFront", "edit.forward");

		tools.addTool("img/edit/bringFront.jpg", "BringFirst", "edit.forwardFull");

		tools.addTool(generateIconFromColor(action.getLineColor(), 20, 20),
				"Edit draw color", "edit.drawColor");

		tools.addTool(generateIconFromColor(action.getBgColor(), 20, 20),
				"Edit back color", "edit.backColor");

		tools.addTool(generateIconFromColor(action.getBgColor(), 20, 20),
				"Make BackGround Transperent", "edit.backColorTransperent");

		tools.setComponentPopupMenu(tools);
	}

	/**
	 *
	 * @return the popupmenu
	 */
	public ToolsPanel getTools(Object om) {
		
		tools = new ToolsPanel(this, action);

		if(om != null && om instanceof Abs_StepElement){
			
			tools.addTool("img/edit/delete.png", "Delete Element", "edit.delete");
			
			Abs_StepElement e = (Abs_StepElement)om;
			
			if( e instanceof TextElement){
				tools.addTool("img/add/text.png", "Edit Text", "edit.text");
			}
			
			tools.addTool("img/edit/bringFront.jpg", "BringFront", "edit.forward");

			tools.addTool("img/edit/bringFront.jpg", "BringFirst", "edit.forwardFull");
			
			if( !(om instanceof ImageElement)){
				tools.addTool(generateIconFromColor(action.getLineColor(), 20, 20),
						"Edit Line color", "edit.drawColor");

				tools.addTool(generateIconFromColor(action.getBgColor(), 20, 20),
						"Edit Fill color", "edit.backColor");

				tools.addTool(generateIconFromColor(action.getBgColor(), 20, 20),
						"Make Fill Transperent", "edit.backColorTransperent");
			}
			
			if(e.isConnected()){
				tools.addTool("img/edit/link_broken.png", "Disconnect Element", "edit.disconnect");
			}else{
				tools.addTool("img/edit/link_connected.png", "Reconnect Element", "edit.connect");
			}	
		}else{
			tools.addTool("img/edit/delete.png", "Delete Step", "edit.delete");
		}

		tools.setComponentPopupMenu(tools);
		
		return tools;
	}

	public void createDragPanel() {
		dragPanel = new DragPanel(this);
		//Making it transperent
		dragPanel.setOpaque(false);
		//Background color fully transperent
		dragPanel.setBackground(new Color(0, 0, 0, 0));
		//Adding the drag Panel
		add(dragPanel);
	}

	/*
	 * Will re arrange the content in the panel to fit to the new size of the Panel.
	 */
	public void reArrenge() {
		//The menu is on the top
		menu.setBounds(0, 0, getWidth(), MENU_HEIGHT);

		//The left section on the left just below the menu
		left_section.setBounds(0, MENU_HEIGHT, left_section_width, getHeight() - JMenuBar.HEIGHT);
		left_section.reArrenge();


		//The step Editor section on the right below the menu
		stepEditor_section3.setBounds(left_section_width, MENU_HEIGHT, getWidth() - left_section_width, getHeight() - STEPMENAGESECTION_HEIGHT - MENU_HEIGHT - 5);
		stepEditor_section3.reArrenge();

		//BElow the stepEditor
		stepMenage_section.setBounds(left_section_width, getHeight() - STEPMENAGESECTION_HEIGHT, getWidth() - left_section_width, STEPMENAGESECTION_HEIGHT - JMenuBar.HEIGHT);
		stepMenage_section.reArrenge();

		//If the text Add panel is active (we are writing a new text)
		if (tadd.isActive()) //On top of the StepEditor below the menu.
		{
			tadd.setBounds(left_section_width + VerticalSectionSeparator.section_separatorWidth + 5,
					MENU_HEIGHT,
					(int) ((getWidth() - left_section_width - VerticalSectionSeparator.section_separatorWidth - 10) * .8), 20);
		} else//We won't show it 
		{
			tadd.setBounds(0, 0, 0, 0);
		}
		tadd.reArrenge();

		//The drag panel is huge.
		dragPanel.setBounds(0, JMenuBar.HEIGHT, getWidth(), getHeight() - JMenuBar.HEIGHT);

		//We force repaint
		validate();
		repaint();
	}

	/**
	 * Creates the elements of the menu
	 */
	private void createMenu() {
		menu.getAccessibleContext().setAccessibleDescription("The main Menu");

		JMenu subMenu = new JMenu("File");
		MenuActionListener mal = new MenuActionListener(action);
		subMenu.addActionListener(mal);

		JMenuItem menuItem;
		menu.add(subMenu);

		menuItem = new JMenuItem("Save as");
		menuItem.getAccessibleContext().setAccessibleDescription("");
		menuItem.setActionCommand("File.saveAs");
		menuItem.setIcon(new ImageIcon("img/saveas.png"));
		menuItem.addActionListener(mal);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("Save");
		menuItem.getAccessibleContext().setAccessibleDescription("");
		menuItem.setActionCommand("File.save");
		menuItem.setAccelerator(KeyStroke.getKeyStroke("control S"));
		menuItem.setIcon(new ImageIcon("img/save.png"));
		menuItem.addActionListener(mal);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("Open");
		menuItem.getAccessibleContext().setAccessibleDescription("");
		menuItem.setActionCommand("File.open");
		menuItem.setAccelerator(KeyStroke.getKeyStroke("control 0"));
		menuItem.setIcon(new ImageIcon("img/open.png"));
		menuItem.addActionListener(mal);
		subMenu.add(menuItem);

		subMenu.insertSeparator(subMenu.getItemCount());
		
		menuItem = new JMenuItem("Export as PNG");
		menuItem.getAccessibleContext().setAccessibleDescription("");
		menuItem.setActionCommand("File.exportPNG");
		menuItem.setIcon(new ImageIcon("img/exportPicture.png"));
		menuItem.addActionListener(mal);
		subMenu.add(menuItem);
		
		subMenu.insertSeparator(subMenu.getItemCount());
		
		menuItem = new JMenuItem("Next Step");
		menuItem.getAccessibleContext().setAccessibleDescription("");
		menuItem.setActionCommand("File.nextStep");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));
		menuItem.setIcon(new ImageIcon("img/next.png"));
		menuItem.addActionListener(mal);
		subMenu.add(menuItem);
		
		menuItem = new JMenuItem("Previous Step");
		menuItem.getAccessibleContext().setAccessibleDescription("");
		menuItem.setActionCommand("File.prevStep");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0));
		menuItem.setIcon(new ImageIcon("img/prev.png"));
		menuItem.addActionListener(mal);
		subMenu.add(menuItem);
		
		/**
		 * EDIT Sub Menu *
		 */
		subMenu = new JMenu("Edit");
		subMenu.addActionListener(mal);
		menu.add(subMenu);

		menuItem = new JMenuItem("delete selected");
		menuItem.getAccessibleContext().setAccessibleDescription("");
		menuItem.setActionCommand("edit.delete");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		menuItem.setIcon(new ImageIcon("img/edit/delete.png"));
		menuItem.addActionListener(mal);
		subMenu.add(menuItem);

		subMenu.insertSeparator(subMenu.getItemCount());

		menuItem = new JMenuItem("Bring Front");
		menuItem.getAccessibleContext().setAccessibleDescription("");
		menuItem.setActionCommand("edit.forward");
		menuItem.setAccelerator(KeyStroke.getKeyStroke("control U"));
		menuItem.setIcon(new ImageIcon("img/edit/bringFront.jpg"));
		menuItem.addActionListener(mal);
		subMenu.add(menuItem);


		menuItem = new JMenuItem("Bring to First");
		menuItem.getAccessibleContext().setAccessibleDescription("");
		menuItem.setActionCommand("edit.forwardFull");
		menuItem.setIcon(new ImageIcon("img/edit/bringFront.jpg"));
		menuItem.addActionListener(mal);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("Bring Back");
		menuItem.getAccessibleContext().setAccessibleDescription("");
		menuItem.setActionCommand("edit.back");
		menuItem.setAccelerator(KeyStroke.getKeyStroke("control D"));
		menuItem.setIcon(new ImageIcon("img/edit/sendBack.jpg"));
		menuItem.addActionListener(mal);
		subMenu.add(menuItem);

		subMenu.insertSeparator(subMenu.getItemCount());


		menu_lineColor = new JMenuItem("Select Draw Color");
		menu_lineColor.getAccessibleContext().setAccessibleDescription("");
		menu_lineColor.setActionCommand("edit.drawColor");
		menu_lineColor.setIcon(generateIconFromColor(action.getLineColor(), 20, 20));
		menu_lineColor.addActionListener(mal);
		subMenu.add(menu_lineColor);

		menu_bgColor = new JMenuItem("Select BackGround Color");
		menu_bgColor.getAccessibleContext().setAccessibleDescription("");
		menu_bgColor.setActionCommand("edit.backColor");
		menu_bgColor.setIcon(generateIconFromColor(action.getBgColor(), 20, 20));
		menu_bgColor.addActionListener(mal);
		subMenu.add(menu_bgColor);

		menuItem = new JMenuItem("Make BackGround Transperent");
		menuItem.getAccessibleContext().setAccessibleDescription("");
		menuItem.setActionCommand("edit.backColorTransperent");
		menuItem.setIcon(generateIconFromColor(action.getBgColor(), 20, 20));
		menuItem.addActionListener(mal);
		subMenu.add(menuItem);

		/**
		 * ADD Sub Menu *
		 */
		subMenu = new JMenu("Mode");
		subMenu.addActionListener(mal);
		menu.add(subMenu);

		menu_group_mode = new ButtonGroup();
		JRadioButtonMenuItem ritem = new JRadioButtonMenuItem("Selection");
		ritem.setActionCommand("mode.selection");
		ritem.setAccelerator(KeyStroke.getKeyStroke("control "+(subMenu.getItemCount()+1)));
		ritem.addActionListener(mal);
		ritem.setSelected(true);
		menu_group_mode.add(ritem);
		subMenu.add(ritem);

		ritem = new JRadioButtonMenuItem("Draw Line");
		ritem.setActionCommand("mode.line");
		ritem.setAccelerator(KeyStroke.getKeyStroke("control "+(subMenu.getItemCount()+1)));
		ritem.addActionListener(mal);
		menu_group_mode.add(ritem);
		subMenu.add(ritem);
		
		ritem = new JRadioButtonMenuItem("Draw Rectangle");
		ritem.setActionCommand("mode.rectangle");
		ritem.setAccelerator(KeyStroke.getKeyStroke("control "+(subMenu.getItemCount()+1)));
		ritem.addActionListener(mal);
		menu_group_mode.add(ritem);
		subMenu.add(ritem);

		ritem = new JRadioButtonMenuItem("Draw Cercle");
		ritem.setActionCommand("mode.cercle");
		ritem.setAccelerator(KeyStroke.getKeyStroke("control "+(subMenu.getItemCount()+1)));
		ritem.addActionListener(mal);
		menu_group_mode.add(ritem);
		subMenu.add(ritem);

		/**
		 * ADD Sub Menu *
		 */
		subMenu = new JMenu("Add");
		subMenu.addActionListener(mal);
		menu.add(subMenu);

		menuItem = new JMenuItem("Text");
		menuItem.setActionCommand("add text");
		menuItem.setAccelerator(KeyStroke.getKeyStroke("control T"));
		menuItem.setIcon(new ImageIcon("img/add/text.png"));
		menuItem.addActionListener(mal);
		subMenu.add(menuItem);
	}

	public void mouse_addViewToMouse(OnMouse m) {
		ml.add_OnePaneMouse(m);
	}

	public void mouse_removeViewToMouse(OnMouse m) {
		ml.remove_PaneMouse(m);
	}

	public void mouse_addMultiDragToMouse(OnMouse m) {
		ml.add_multiPaneMouse(m);
	}

	public mouseListener getMouse_Listener() {
		return ml;
	}

	public int getLeft_section_width() {
		return left_section_width;
	}

	public void setLeft_section_width(int left_section_width) {
		this.left_section_width = left_section_width;
	}

	public void startMultiDrag(Abs_StepElement i) {
		dragPanel.setV(i);
	}

	public void endMuliDrag() {
		dragPanel.setV(null);
		reArrenge();
	}

	public void rename(String name) {
		jframe.setTitle(APP_NAME + " - " + name);
	}

	public void key_cancel() {
		if (tadd.isActive()) {
			tadd.cancel();
		} else if (ml.getViewSelected() != null) {
			ml.forceLoseSelect();
		}
	}

	public void key_enter() {
		if (tadd.isActive()) {
			tadd.confirm();
		} else if (ml.getViewSelected() instanceof TextElement) {
		}
	}

	public void key_any(KeyEvent e) {
		if (tadd.isActive()) {
			tadd.keyAction(e);
		}
	}

	public void setEnabled(boolean b) {
		jframe.setEnabled(b);
	}

	public void updateMenuColors() {
		menu_lineColor.setIcon(generateIconFromColor(action.getLineColor(), 20, 20));
		menu_bgColor.setIcon(generateIconFromColor(action.getBgColor(), 20, 20));
	}

	private ImageIcon generateIconFromColor(Color c, int w, int h) {

		final Image bgimage = new ImageIcon("img/edit/transperent.png").getImage();

		// Create BufferedImage
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		// Get a Graphics object
		Graphics g = img.getGraphics();

		// Create white background
		g.drawImage(bgimage, 0, 0, w, h, menu);
		g.fillRect(0, 0, w, h);

		// Draw a slightly larger black circle
		// first to give the impression of a
		// dark border
		g.setColor(c);
		g.fillRect(0, 0, w, h);

		// Create imageIcon from BufferedImage
		return new ImageIcon(img);
	}
}
