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

import aeon.console.Logger;
import aeon.utility.Color;
import android.opengl.GLES20;

public class Shader extends Resource
{
	private static final String 	SHADER_PROJECTION_MATRIX_NAME 	= "age_projection_matrix";
	private static final String 	SHADER_MODEL_MATRIX_NAME 		= "age_model_matrix";
	private static final String 	SHADER_VIEW_MATRIX_NAME 		= "age_view_matrix";
	
	private static final String 	SHADER_TEXTURE0_NAME 			= "age_texture";
	private static final String 	SHADER_COLOR0_NAME 				= "age_color";
	
	public static final int 		SHADER_ATTRIB_VERTEX_ID 		= 0;
	private static final String 	SHADER_ATTRIB_VERTEX_NAME 		= "age_position";
	
	public static final int 		SHADER_ATTRIB_TEXCOORD_ID 		= 1;
	private static final String 	SHADER_ATTRIB_TEXCOORD_NAME 	= "age_texcoord";
	
	public Shader()
	{
		super(ResourceType.Shader);
	}
	
	public boolean load(String name)
	{
		if(name == null || name == "")
			return false;
		
		//Load the vertex shader from file
		String vertex_src = ResourceManager.read_asset_as_string(name + ".vert");
		
		if(vertex_src == null)
			return false;
		
		//Compile the shader
		int vertex_shader = __compile_shader(ShaderType.Vertex, vertex_src, name);

		if(vertex_shader == 0)
			return false;
		
		//Load the fragment shader from file
		String fragment_src = ResourceManager.read_asset_as_string(name + ".frag");
		
		if(fragment_src == null)
			return false;
		
		//Compile the shader
		int fragment_shader = __compile_shader(ShaderType.Fragment, fragment_src, name);
		
		if(fragment_shader == 0)
			return false;
		
		//Link them together
		int program = __link_program(fragment_shader, vertex_shader, name);
		
		//Decrease ref counter on the shader objects because we don't need them
		//seperate from a program.
		GLES20.glDeleteShader(vertex_shader);
		GLES20.glDeleteShader(fragment_shader);
		
		if(program == 0)
			return false;
		
		//All went well compiling and linking. Save the program! woohoo
		m_program = program;
		
		//Set up the uniform locations for the matrices in this shader
		m_projection_matrix_handle = GLES20.glGetUniformLocation(m_program, SHADER_PROJECTION_MATRIX_NAME);
		m_model_matrix_handle = GLES20.glGetUniformLocation(m_program, SHADER_MODEL_MATRIX_NAME);
		m_view_matrix_handle = GLES20.glGetUniformLocation(m_program, SHADER_VIEW_MATRIX_NAME);

		m_texture0_handle = GLES20.glGetUniformLocation(m_program, SHADER_TEXTURE0_NAME);
		m_color0_handle = GLES20.glGetUniformLocation(m_program, SHADER_COLOR0_NAME);
		
		if(
			m_projection_matrix_handle == -1 ||
			m_model_matrix_handle == -1 ||
			m_view_matrix_handle == -1 ||
			m_texture0_handle == -1 ||
			m_color0_handle == -1)
		{
			Logger.Warning("Not all required uniform variables were found in the shader. " +
					"This may lead to unexpected behavior."
			);
		}
		
		m_loaded = true;
		
		return true;
	}

	@Override
	public boolean unload()
	{
		if(m_program != 0)
		{
			GLES20.glDeleteProgram(m_program);
			
			m_program = 0;
			m_projection_matrix_handle = 0;
			m_model_matrix_handle = 0;
			m_view_matrix_handle = 0;
			m_texture0_handle = 0;
			m_color0_handle = 0;
			
			m_loaded = true;
		}
	
		return false;
	}
	
	public void bind()
	{
		if(m_program != 0)
			GLES20.glUseProgram(m_program);
	}
	
	public void set_projection_matrix(float[] matrix)
	{
		__set_matrix(matrix, m_projection_matrix_handle);
	}
	
	public void set_model_matrix(float[] matrix)
	{
		__set_matrix(matrix, m_model_matrix_handle);
	}
	
	public void set_view_matrix(float[] matrix)
	{
		__set_matrix(matrix, m_view_matrix_handle);
	}
	
	private void __set_matrix(float[] matrix, int handle)
	{
		if(matrix == null || handle == -1)
			return;
		
		GLES20.glUniformMatrix4fv(handle, 1, false, matrix, 0);
	}
	
	public void set_color(Color color)
	{
		if(color == null || m_color0_handle == -1)
			return;

		GLES20.glUniform4f(m_color0_handle, color.r, color.g, color.b, color.a);
	}
	
	private int __compile_shader(ShaderType type, String src, String name)
	{
		int gl_shader_type = 0;
		
		if(type == ShaderType.Vertex)
			gl_shader_type = GLES20.GL_VERTEX_SHADER;
		else if(type == ShaderType.Fragment)
			gl_shader_type = GLES20.GL_FRAGMENT_SHADER;
		else
		{
			Logger.Warning("Unknown shader type.");
			return 0;
		}
		
	    int shader = GLES20.glCreateShader(gl_shader_type);
	    
	    GLES20.glShaderSource(shader, src);
	    GLES20.glCompileShader(shader);
	    
	    //Get the compilation result
	    int[] compiled = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
        
        //Did compiling go wrong?
        if (compiled[0] == 0) 
        {       			
        	Logger.Error(String.format("Shader %s could not be compiled!", name));
        	Logger.Error(GLES20.glGetShaderInfoLog(shader));
        	
        	//Free up this handle
        	GLES20.glDeleteShader(shader);
        	
        	return 0;
        }

	    return shader;
	}
	
	private int __link_program(int fragment, int vertex, String name)
	{
		int program = GLES20.glCreateProgram();
		
		//Attach the shaders to the new program
	    GLES20.glAttachShader(program, vertex);
	    GLES20.glAttachShader(program, fragment);
	    
	    //Bind attrib locations
	    GLES20.glBindAttribLocation(program, SHADER_ATTRIB_VERTEX_ID, SHADER_ATTRIB_VERTEX_NAME);
	    GLES20.glBindAttribLocation(program, SHADER_ATTRIB_TEXCOORD_ID, SHADER_ATTRIB_TEXCOORD_NAME);
	    
	    //Link
	    GLES20.glLinkProgram(program);
	    
	    //Get the linker result
	    int[] linked = new int[1];
	    GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linked, 0);
	    
	    //Did linking go wrong?
	    if(linked[0] == 0)
	    {
        	Logger.Error(String.format("Shader %s could not be linked!", name));
        	Logger.Error(GLES20.glGetProgramInfoLog(program));
        	
        	//Free up this handle
        	GLES20.glDeleteProgram(program);
        	
        	return 0;
	    }
	    
	    return program;
	}

	private int m_program = 0;
	private int m_projection_matrix_handle = 0;
	private int m_model_matrix_handle = 0;
	private int m_view_matrix_handle = 0;
	private int m_texture0_handle = 0; //TODO: Currently unused.
	private int m_color0_handle = 0;
}
