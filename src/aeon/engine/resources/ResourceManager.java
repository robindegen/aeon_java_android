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

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import aeon.console.Logger;
import aeon.engine.AeonActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ResourceManager
{
	//TODO: We can move texture loading to another thread and do finalizing (upload to GPU) on the render thread
	public Texture load_texture(String name)
	{
		Resource resource = __check_if_resource_loaded(name, ResourceType.Texture);
		
		//If the resource was already loaded, return that one.
		if(resource != null)
			return (Texture) resource;
			
		Bitmap bitmap = read_asset_as_bitmap(name);
		
		if(bitmap == null)
		{
			Logger.Error(String.format("Could not load texture %s.", name));
			return null;
		}
		
		Texture texture = new Texture(bitmap);
		
		__register_resource(name, texture);
		
		return texture;
	}
	
	public Shader load_shader(String name)
	{
		Resource resource = __check_if_resource_loaded(name, ResourceType.Texture);
		
		//If the resource was already loaded, return that one.
		if(resource != null)
			return (Shader) resource;
			
		Shader shader = new Shader();
		
		//Load the shader and check the result
		if(!shader.load(this, name))
			return null;
			
		__register_resource(name, shader);
		
		return shader;
	}

	public String read_asset_as_string(String filename)
	{	
		try
		{
			InputStream stream = AeonActivity.get_singleton().getAssets().open(filename);
	        int size = stream.available();
	        byte[] buffer = new byte[size];
	        stream.read(buffer);
	        stream.close();
	        
	        String file_contents = new String(buffer);
	        
	        return file_contents;
		}
		catch (Exception e)
		{
			Logger.Error(String.format("Could not read asset %s as string.", filename));
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Bitmap read_asset_as_bitmap(String filename)
	{
		try
		{
			InputStream stream = AeonActivity.get_singleton().getAssets().open(filename);
			Bitmap bitmap = BitmapFactory.decodeStream(stream);
			
			return bitmap;
	    }
	    catch (Exception e)
	    {
			Logger.Error(String.format("Could not read asset %s as bitmap.", filename));
			e.printStackTrace();
	    }

	    return null;
	}
	
	private Resource __check_if_resource_loaded(String name, ResourceType type)
	{
		//See if the resource is loaded already
		Resource resource = m_loaded_resources.get(name);
		
		//The resource was not loaded before.
		if(resource == null)
			return null;
		
		//Is the resource still loaded?
		if(!resource.loaded())
		{
			//Remove it from the list.. no need to keep it around
			m_loaded_resources.remove(name);
			return null;
		}
		
		//Is the loaded resource of the same type?
		if(resource.type() != type)
		{
			Logger.Warning(
				String.format(
					"Resource type was not equal to previous load. Can't load resource %s.",
					name
				)
			);
			
			return null;
		}
		
		//We found it! This means we can reuse it.
		return resource;
	}
	
	private void __register_resource(String name, Resource resource)
	{
		Resource previous_resource = m_loaded_resources.put(name, resource);
		
		//If put returns != null, then the key was already present.. that should not happen in our case though...
		if(previous_resource != null)
		{
			Logger.Error(
				String.format(
					"Resource %s was loaded twice somehow. Old resource was overwritten. This should never happen.",
					name
				)
			);
		}
	}
	
	private Map<String, Resource> m_loaded_resources = new HashMap<String, Resource>();
}
