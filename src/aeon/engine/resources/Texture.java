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

package aeon.engine.resources;

import aeon.utility.Vector2f;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class Texture extends Resource
{
	public Texture(Bitmap bitmap)
	{
		super(ResourceType.Texture);
		
		m_size.set(bitmap.getWidth(), bitmap.getHeight());
		
		__finalize_texture(bitmap);
	}

	@Override
	public boolean unload(ResourceManager resourcemanager)
	{
	    if (m_texture == 0)
	    	return false;

	    //glDeleteTextures wants an array,... so we make one...
	    int[] handle = new int[1];
	    handle[0] = m_texture;
	    GLES20.glDeleteTextures(1, handle, 0);

		return true;
	}
	
	public Vector2f get_size()
	{
		return m_size;
	}
	
	private void __finalize_texture(Bitmap bitmap)
	{
		int texture = __generate_gltexture_handle();

		//Bind it
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
		
		//Set texture parameters
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
		
		//Upload the bitmap to texture memory
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
		
		m_texture = texture;
	}
	
	private int __generate_gltexture_handle()
	{
		int[] handle = new int[1];
		GLES20.glGenTextures(1, handle, 0);
		
		return handle[0];
	}

	int 			m_texture = 0;
	Vector2f 		m_size;
}
