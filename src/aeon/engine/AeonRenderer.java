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

import java.util.Vector;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import aeon.console.Logger;
import aeon.engine.resources.ResourceManager;
import aeon.engine.resources.Shader;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

public class AeonRenderer implements GLSurfaceView.Renderer
{
	private static final int FULLSCREEN_VIRTUAL_RESOLUTION_WIDTH = 1280;
	private static final int FULLSCREEN_VIRTUAL_RESOLUTION_HEIGHT = 720;
	
	AeonRenderer(AeonActivity activity)
	{
		m_activity = activity;
		m_activity.register_renderer(this);
	}
	
    public void onSurfaceCreated(GL10 unused, EGLConfig config)
    {
    	Logger.Info("Setting up GLES...");
    	
    	__setup_gles();
    	__load_internal_resources();
    	__setup_matrices();
    	
        //After all this, the game is initialized. So we can call the on_game_loaded.
        Logger.Info("Engine ready.");
        
        m_activity.on_game_start();
    }

    public void onDrawFrame(GL10 unused)
    {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        
        //TODO: Calculate delta time.
        m_activity.on_update(0.1f);
        
        m_active_shader.bind();
        
        //Draw everything
        for (Sprite sprite : m_sprites)
        {
        	sprite.get_texture().bind();

        	//Set up our matrix in the shader
        	m_active_shader.set_model_matrix(sprite.get_matrix());

        	//Set the color
        	m_active_shader.set_color(sprite.get_color());

        	//Set up our buffers to render.
        	GLES20.glVertexAttribPointer(
    			Shader.SHADER_ATTRIB_VERTEX_ID,
    			2, 
    			GLES20.GL_FLOAT, 
    			false, 
    			4 * 4, 
    			sprite.get_vertex_data()
        	);
        	
        	GLES20.glEnableVertexAttribArray(Shader.SHADER_ATTRIB_VERTEX_ID);

        	//The texture offsets start at UV in the struct. However we need to cast vertex data to a char first so we add the offset in bytes
        	//properly. This might seem weird.. but it's the only proper way that doesn't generate a gcc warning.
        	/*GLES20.glVertexAttribPointer(
        			Shader.SHADER_ATTRIB_TEXCOORD_ID,
        			2,
        			GLES20.GL_FLOAT, 
        			false, 
        			4 * 4,
        			sprite.get_vertex_data().
        	);
        	
        	glEnableVertexAttribArray(SHADER_ATTRIB_TEXCOORD_ID);*/

        	GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        }
    }

    public void onSurfaceChanged(GL10 unused, int width, int height)
    {
        GLES20.glViewport(0, 0, width, height);
    }
  
    public void add_sprite(Sprite sprite)
    {
    	m_sprites.add(sprite);
    }
    
    private void __setup_gles()
    {
    	GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
    	GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
    }
    
    private void __load_internal_resources()
    {
        m_default_shader = ResourceManager.load_shader("shaders/default");
        
        if(m_default_shader == null)
        {
        	Logger.Error("Default shader could not be loaded. Rendering may not work.");
        }
        
        m_active_shader = m_default_shader;
    }
    
    private void __setup_matrices()
    {
    	//Set up our projection matrix to ortho, so that left top is 0,0
    	Matrix.orthoM(
			m_projection_matrix, 0, //Matrix and offset
			0, //Left
			FULLSCREEN_VIRTUAL_RESOLUTION_WIDTH, //Right
			FULLSCREEN_VIRTUAL_RESOLUTION_HEIGHT, //Bottom
			0, //Top
			-1, //Near
			1 //Far
    	);
    	
    	//Set the rest up to be identity
    	Matrix.setIdentityM(m_view_matrix, 0);
    	
    	m_default_shader.set_projection_matrix(m_projection_matrix);
    	m_default_shader.set_view_matrix(m_view_matrix);
    }
    
    Vector<Sprite> m_sprites = new Vector<Sprite>();
    AeonActivity m_activity;
    
    Shader m_default_shader;
    Shader m_active_shader;
    
    float [] m_projection_matrix = new float[16];
    float [] m_view_matrix = new float[16];
}
