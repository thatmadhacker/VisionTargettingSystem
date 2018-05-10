package ca.team2706.vision.trackerboxreloaded;

import java.awt.image.BufferedImage;
import java.util.List;

import processing.core.PVector;

public class Data{
	private List<PVector> vertices;
	private BufferedImage image;
	
	public Data(List<PVector> vertices, BufferedImage image) {
		this.vertices = vertices;
		this.image = image;
	}
	
	public List<PVector> getVertices() {
		return vertices;
	}
	
	public BufferedImage getImage() {
		return image;
	}
}