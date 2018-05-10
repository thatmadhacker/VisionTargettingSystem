package ca.team2706.vision.trackerboxreloaded;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import SimpleOpenNI.SimpleOpenNI;
import processing.core.PApplet;
import processing.core.PVector;


public class Vision extends PApplet {

	private static final long serialVersionUID = 1L;

	public Vision(){
		
	}
	private static BufferedImage rgb;
	private static List<PVector> vertices = new ArrayList<PVector>();
	private SimpleOpenNI context;
	private static boolean hasData = false;
	public void setup() {
		//size(640, 480, P3D);
		context = new SimpleOpenNI(this);
		context.enableDepth();
		context.enableRGB();
	}

	public void draw() {
		context.update();
		//background(0);
		// image(context.rgbImage(),0,0);
		//drawImg();
		try {
			update();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update() throws Exception {
		// if(currfps <= 4){
		// Thread thread;
		// thread = new Thread(new Runnable(){

		// public void run(){

		// Translate and rotate
		// pushMatrix();
		translate(width / 2, height / 2, -2250);

		try {
			// ImageIO.write((BufferedImage)
			// context.depthImage().getNative(),"png",new
			// File(System.getProperty("user.home")+"/depth.png"));
			// Depth code
			// PImage depth = context.depthImage();
			Vision.rgb = (BufferedImage) context.rgbImage().getNative();
			vertices.clear();
			int[] depths = context.depthMap();
			int skip = 1;
			stroke(255);
			strokeWeight(2);
			beginShape(POINTS);
			for (int x = 0; x < 640; x += skip) {
				for (int y = 0; y < 480; y += skip) {
					int offset = x + y * 640;
					float d = depths[offset];
					PVector point = depthToPointCloudPos(x, y, d);
					//vertex(point.x, point.y, point.z);
					vertices.add(point);
				}
			}
			hasData = true;
			endShape();
			// popMatrix();
			fill(255);
			text(frameRate, 50, 50);
			Thread.sleep(400);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// });

	// thread.start();
	// thread.join(1000);
	// currfps++;
	// }
	// }

	public void drawImg() {
		
	}

	private PVector depthToPointCloudPos(int x, int y, float depthValue) {
		PVector point = new PVector();
		point.z = (depthValue);
		point.x = (x - 254.878f) * point.z / 365.456f;
		point.y = (y - 205.395f) * point.z / 365.465f;
		return point;
	}
	public static Data getData(){
		while(!hasData){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		hasData = false;
		return new Data(vertices,rgb);
	}
	

}
