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

public class Color
{
	public Color(float _r, float _g, float _b)
	{
		_r = r;
		_g = g;
		_b = b;
	}
	
	public Color(float _r, float _g, float _b, float _a)
	{
		_r = r;
		_g = g;
		_b = b;
		_a = a;
	}
	
	public float r = 0;
	public float g = 0;
	public float b = 0;
	public float a = 1;
}
