package en.odecram.story_liner.views.dragables;

import en.odecram.story_liner.models.Save.ImageInfo;
import en.odecram.story_liner.models.interfaces.OnMouse;
import en.odecram.story_liner.models.stepElement.Properties;
import en.odecram.story_liner.views.MainContent;
import en.odecram.story_liner.views.dragables.models.Abs_StepElement;
import en.odecram.story_liner.views.models.Abs_View;
import en.odecram.story_liner.widgets.LoadingScreen;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingWorker;

/**
 * This is a Step Element that shows an Image
 *
 * @author De Cramer Oliver
 */
public class ImageElement extends Abs_StepElement {

	public static final int IMG_MAX_SIZE = 800;
	/**
	 * The image that will be shown
	 */
	private Image image;
	private int i_width;
	private int i_height;

	private static SwingWorker<Integer, Integer> sw;
	
	private static LoadingScreen ls;
	
	public ImageElement(Abs_StepElement origin) {
		super(origin);
		image = ((ImageElement) origin).image;
		i_height = ((ImageElement) origin).i_height;
		i_width = ((ImageElement) origin).i_width;
	}

	/**
	 *
	 * @param mContent The main Content view
	 * @param prop THe properties of the Element
	 * @param parent The parent view of the element
	 * @param imgPath The path to the image to be shown
	 */
	public ImageElement(final MainContent mContent, Properties prop, Abs_View parent, final String imgPath) {
		//Calling parent constructor
		super(mContent, prop, parent);
		/*
		if(sw != null){
			System.out.println("Oups");
			sw.cancel(true);
			ls.finish();
			mContent.setEnabled(true);
			mContent.reArrenge();
			sw = null;
			ls = null;
			return;
		}
		System.out.println("Oups2");
		
		sw = new SwingWorker<Integer, Integer>() {
			@Override
			protected Integer doInBackground() throws Exception {
				//Creating a Loading Screen

				ls = new LoadingScreen(3,20);
				ls.setAlwaysOnTop(true);*/
				try {
					/*ls.nextStep("Opening The Image");
					//Loading the image*/
					BufferedImage bimg = ImageIO.read(new File(imgPath));

					int w = bimg.getWidth();
					int h = bimg.getHeight();

					if (w > IMG_MAX_SIZE) {
						h = (int) ((float) h * ((float) IMG_MAX_SIZE / w));
						w = IMG_MAX_SIZE;
					}
					if (h > 800) {
						w = (int) ((float) w * ((float) IMG_MAX_SIZE / h));
						h = IMG_MAX_SIZE;
					}
					
					//ls.nextStep("Resizing the Image ...");
					image = bimg.getScaledInstance(w, h, h);
					
					//ls.nextStep("Resizing the Image");
					i_height = h;
					i_width = w;
					
					
					//ls.finish();
					mContent.setEnabled(true);
					mContent.reArrenge();
					
				} catch (IOException ex) {
					//ls.finish();
					mContent.setEnabled(true);
					image = null;
					mContent.reArrenge();
				}
				/*return null;
			}
		};
		System.out.println("Oups3");
		mContent.setEnabled(false);
		System.out.println("Oups4");
		sw.execute();
		System.out.println("Oups5");*/
	}

	public ImageElement(MainContent mContent, Properties prop, Abs_View parent) {
		//Calling parent constructor
		super(mContent, prop, parent);
	}

	@Override
	public void paint(Graphics g, Point pos, Dimension dim, float scale) {
		//To draw an image we need a Grapics 2D
		Graphics2D g2d = (Graphics2D) g;
		//If the image was loaded
		if (image != null) {
			//We draw it
			g2d.drawImage(image, pos.x, pos.y, dim.width, dim.height, getParent());
		} else {
			/**
			 * If the demanded image couldn't be loaded we should load a default
			 * image.
			 */
		}
	}

	@Override
	public OnMouse clone() {
		return new ImageElement(this);
	}

	@Override
	public java.io.Serializable getData() {
		System.out.println(i_width + " " + i_height);
		int[] pixels = new int[i_width * i_height];
		PixelGrabber pg = new PixelGrabber(image, 0, 0, i_width, i_height, pixels, 0, i_width);
		try {
			pg.grabPixels();
		} catch (InterruptedException ex) {
			Logger.getLogger(ImageElement.class.getName()).log(Level.SEVERE, null, ex);
		}
		ImageInfo ii = new ImageInfo();
		ii.pixels = pixels;
		ii.w = i_width;
		ii.h = i_height;
		return ii;
	}  //  private int[] getArrayFromImage()

	@Override
	public void setData(java.io.Serializable s) {
		ImageInfo ii = (ImageInfo) s;
		MemoryImageSource mis = new MemoryImageSource(ii.w, ii.h, ii.pixels, 0, ii.w);
		Toolkit tk = Toolkit.getDefaultToolkit();
		image = tk.createImage(mis);
		i_width = ii.w;
		i_height = ii.h;
		System.out.println(i_width + " " + i_height);
	}
}
