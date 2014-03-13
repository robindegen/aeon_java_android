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
import android.opengl.GLES20;

public class Shader extends Resource
{
	public Shader(String name)
	{
		super(ResourceType.Shader, name);
	}

	@Override
	public boolean load(ResourceManager resourcemanager)
	{
		if(m_name == null || m_name == "")
			return false;
		
		//Load the fragment shader from file
		String fragment_src = resourcemanager.read_asset_as_string(m_name + ".frag");
		
		if(fragment_src == null)
			return false;
		
		//Compile the shader
		int fragment_shader = __compile_shader(ShaderType.Fragment, fragment_src);
		
		if(fragment_shader == 0)
			return false;
		
		String vertex_src = resourcemanager.read_asset_as_string(m_name + ".vert");
		
		if(vertex_src == null)
			return false;
		
		//Compile the shader
		int vertex_shader = __compile_shader(ShaderType.Vertex, vertex_src);

		if(vertex_shader == 0)
			return false;
		
		int program = __link_program(fragment_shader, vertex_shader);
		
		if(program == 0)
			return false;
		
		m_program = program;
		
		return false;
	}

	@Override
	public boolean unload(ResourceManager resourcemanager)
	{
		return false;
	}
	
	private int __compile_shader(ShaderType type, String src)
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
        
        if (compiled[0] == 0) 
        {       			
        	Logger.Error(String.format("Shader %s could not be compiled!", m_name));
        	Logger.Error(GLES20.glGetShaderInfoLog(shader));
        	
        	//Free up this handle
        	GLES20.glDeleteShader(shader);
        	
        	return 0;
        }

	    return shader;
	}
	
	private int __link_program(int fragment, int vertex)
	{
		int program = GLES20.glCreateProgram();
		
	    GLES20.glAttachShader(program, vertex);
	    GLES20.glAttachShader(program, fragment);
	    
	    GLES20.glLinkProgram(program);
	    
	    //Get the linker result
	    int[] linked = new int[1];
	    GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linked, 0);
	    
	    if(linked[0] == 0)
	    {
        	Logger.Error(String.format("Shader %s could not be linked!", m_name));
        	Logger.Error(GLES20.glGetProgramInfoLog(program));
        	
        	//Free up this handle
        	GLES20.glDeleteProgram(program);
        	
        	return 0;
	    }
	    
	    return program;
	}

	int m_program;
}
