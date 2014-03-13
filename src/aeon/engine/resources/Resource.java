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

public abstract class Resource
{
	public Resource(ResourceType type, String name)
	{
		m_type = type;
		m_name = name;
	}
	
	public abstract boolean 		load(ResourceManager resourcemanager);
	public abstract boolean 		unload(ResourceManager resourcemanager);
	
	protected ResourceType			m_type;
	protected String				m_name;
}
