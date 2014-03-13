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

import aeon.console.Logger;
import aeon.engine.AeonActivity;

public class ResourceManager
{
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
			Logger.Warning(String.format("Could not read asset %s", filename));
			e.printStackTrace();
		}
		
		return null;
	}
}
