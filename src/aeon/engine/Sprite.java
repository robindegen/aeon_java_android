package aeon.engine;

import aeon.engine.resources.Texture;
import aeon.utility.Color;

public class Sprite
{
	public Sprite(Texture texture, boolean autosize)
	{
	}
	
	public void set_color(Color color)
	{
		m_color = color;
	}
	
	private void __init_vertex_data()
	{
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
		
		//Position data
		/*mVertexData[0].position[0] = 0.0f;
		mVertexData[0].position[1] = 0.0f;
		mVertexData[1].position[0] = 0.0f;
		mVertexData[1].position[1] = 0.0f;
		mVertexData[2].position[0] = 0.0f;
		mVertexData[2].position[1] = 0.0f;
		mVertexData[3].position[0] = 0.0f;
		mVertexData[3].position[1] = 0.0f;

		//UV data
		mVertexData[0].uv[0] = 0.0f;
		mVertexData[0].uv[1] = 0.0f;
		mVertexData[1].uv[0] = 0.0f;
		mVertexData[1].uv[1] = 1.0f;
		mVertexData[2].uv[0] = 1.0f;
		mVertexData[2].uv[1] = 0.0f;
		mVertexData[3].uv[0] = 1.0f;
		mVertexData[3].uv[1] = 1.0f;*/
	}
	
	//4 points of 2 vertex and 2 uv data floats
	private float [] m_vertex_data = new float[4 * 4];
	private Color m_color = new Color(1, 1, 1);
}
