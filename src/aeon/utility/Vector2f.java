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

package aeon.utility;

public class Vector2f
{
	public Vector2f(float _x, float _y)
	{
		x = _x;
		y = _y;
	}
	
	public void set(int _x, int _y)
	{
		x = (float) _x;
		y = (float) _y;
	}
	
	public void set(Vector2f vector)
	{
		x = vector.x;
		y = vector.y;
	}
	
	public void set(float _x, float _y)
	{
		x = _x;
		y = _y;
	}
	
	public float x = 0;
	public float y = 0;
}
