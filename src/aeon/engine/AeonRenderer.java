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

package aeon.engine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import aeon.console.Logger;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

public class AeonRenderer implements GLSurfaceView.Renderer
{
	AeonRenderer(AeonActivity activity)
	{
		m_activity = activity;
	}
	
    public void onSurfaceCreated(GL10 unused, EGLConfig config)
    {
    	Logger.Info("Setting up GLES...");
    	
        GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
        
        //After all this, the game is initialized. So we can call the on_game_loaded.
        Logger.Info("Engine ready.");
        m_activity.on_game_start();
    }

    public void onDrawFrame(GL10 unused)
    {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        
        //TODO: Calculate delta time.
        m_activity.on_update(0.1f);
    }

    public void onSurfaceChanged(GL10 unused, int width, int height)
    {
        GLES20.glViewport(0, 0, width, height);
    }
    
    AeonActivity m_activity;
}
