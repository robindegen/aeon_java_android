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

/* Medium floating point precision */
precision mediump float;

/* Normalized texture coords from the vertex shader */
varying mediump vec2 age_texcoordout;

/* Texture bound to TEXTURE0 */
uniform sampler2D age_texture;

/* Color to blend the texture with */
uniform vec4 age_color;

void main()
{
	gl_FragColor = texture2D(age_texture, age_texcoordout) * age_color;
}
