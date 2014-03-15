/**
* Aeon Android Game Engine
* 
* This file is part of Aeon Android Game Engine (AGE).
*
* AGE is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* AGE is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with AGE.  If not, see <http://www.gnu.org/licenses/>.
*
* @author Robin Degen
* @version 0.1
*/

package aeon.engine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import aeon.engine.resources.Texture;
import aeon.utility.Color;
import aeon.utility.Vector2f;
import android.opengl.Matrix;

public class Sprite
{
	public Sprite(Texture texture)
	{
		__init_vertex_data();
		__setup_bytebuffer();
		
		m_texture = texture;
		Matrix.setIdentityM(m_matrix, 0);
		
		set_size(texture.get_size());		
		
		//TODO: Do this better....
		AeonActivity.get_singleton().get_renderer().add_sprite(this);
	}
	
	public Sprite(Texture texture, boolean autosize)
	{
		__init_vertex_data();
		__setup_bytebuffer();
		
		m_texture = texture;
		Matrix.setIdentityM(m_matrix, 0);
		
		if(autosize)
			set_size(texture.get_size());
		
		//TODO: Do this better....
		AeonActivity.get_singleton().get_renderer().add_sprite(this);
	}
	
	public Color get_color()
	{
		return m_color;
	}
	
	public void set_size(float width, float height)
	{
		m_vertex_data[5] = height;
		m_vertex_data[8] = width;
		m_vertex_data[12] = width;
		m_vertex_data[13] = height;
		
		//Write the new data into the vertexbuffer
        m_vertexbuffer.put(m_vertex_data);
        m_vertexbuffer.position(0);
	}
	
	public void set_size(Vector2f size)
	{
		set_size(size.x, size.y);
	}
	
	public float[] get_matrix()
	{
		return m_matrix;
	}
	
	public FloatBuffer get_vertex_data()
	{
		return m_vertexbuffer;
	}
	
	public Texture get_texture()
	{
		return m_texture;
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
	
	private void __setup_bytebuffer()
	{
		m_bytebuffer = ByteBuffer.allocateDirect
		(
			//Amount of vertices * sizeof(float)
			m_vertex_data.length * 4
		);
		
        // use the device hardware's native byte order
		m_bytebuffer.order(ByteOrder.nativeOrder());
        
        m_vertexbuffer = m_bytebuffer.asFloatBuffer();
	}
	
	//4 points of 2 vertex and 2 uv data floats
	private float [] 	m_vertex_data = new float[4 * 4];
	private Texture 	m_texture;
	private Color 		m_color = new Color(1, 1, 1);
	private float [] 	m_matrix = new float[16];
	
	private ByteBuffer m_bytebuffer;
	private FloatBuffer m_vertexbuffer;
}
