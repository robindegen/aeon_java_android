package aeon.engine;

import aeon.engine.resources.Texture;
import aeon.utility.Color;
import aeon.utility.Vector2f;

public class Sprite
{
	public Sprite(Texture texture)
	{
		__init_vertex_data();
		set_size(texture.get_size());
	}
	
	public Sprite(Texture texture, boolean autosize)
	{
		__init_vertex_data();
		
		if(autosize)
			set_size(texture.get_size());
	}
	
	public void set_color(Color color)
	{
		m_color = color;
	}
	
	public void set_size(float width, float height)
	{
		m_vertex_data[5] = height;
		m_vertex_data[8] = width;
		m_vertex_data[12] = width;
		m_vertex_data[13] = height;
	}
	
	public void set_size(Vector2f size)
	{
		set_size(size.x, size.y);
	}
	
	private void __init_vertex_data()
	{
		/*
		 * Vertex data is interleaved with UV texture mapping.
		 * A vertex looks like this:
		 * struct Vertex
		 * {
		 *    float position[2];
		 *    float uv[2];
		 * }
		 */
		
		m_vertex_data[0] = 0;
		m_vertex_data[1] = 0;
		m_vertex_data[2] = 0;
		m_vertex_data[3] = 0;
		
		m_vertex_data[4] = 0;
		m_vertex_data[5] = 0;
		m_vertex_data[6] = 0;
		m_vertex_data[7] = 1;
		
		m_vertex_data[8] = 0;
		m_vertex_data[9] = 0;
		m_vertex_data[10] = 1;
		m_vertex_data[11] = 0;
		
		m_vertex_data[12] = 0;
		m_vertex_data[13] = 0;
		m_vertex_data[14] = 1;
		m_vertex_data[15] = 1;
	}
	
	//4 points of 2 vertex and 2 uv data floats
	private float [] m_vertex_data = new float[4 * 4];
	private Color m_color = new Color(1, 1, 1);
}
