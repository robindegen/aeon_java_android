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

/* Matrices */
uniform mat4 age_projection_matrix;
uniform mat4 age_model_matrix;
uniform mat4 age_view_matrix;

/* Positions from the vertex buffers */
attribute vec4 age_position;

/* Texture UV coords */
attribute vec2 age_texcoord;

/* Used to forward the normalized texture coords to the fragment shader */
varying vec2 age_texcoordout;

void main()
{
	gl_Position = age_projection_matrix * age_model_matrix * age_view_matrix * age_position;
	
	/* Forward the tex coords to the fragment shader */
	age_texcoordout = age_texcoord;
}
