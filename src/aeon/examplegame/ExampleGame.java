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

package aeon.examplegame;

import aeon.console.Logger;
import aeon.engine.AeonActivity;
import aeon.engine.Sprite;
import aeon.engine.resources.ResourceManager;
import aeon.engine.resources.Texture;
import android.opengl.Matrix;

public class ExampleGame extends AeonActivity
{
	Sprite sprite;
	Texture texture;
	
	@Override
	public void on_game_start()
	{
		Logger.Info("on_game_start()");
		
		texture = ResourceManager.load_texture("textures/mario.png");
		sprite = new Sprite(texture);
		
		Matrix.translateM(sprite.get_matrix(), 0, 10, 10, 0);
	}

	@Override
	public void on_update(float dt)
	{
		//Logger.Info("on_update()");
	}

	@Override
	public void on_game_stop()
	{
		Logger.Info("on_game_stop()");
	}
}
