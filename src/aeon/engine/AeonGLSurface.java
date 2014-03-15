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

import aeon.console.Logger;
import android.content.Context;
import android.opengl.GLSurfaceView;

public class AeonGLSurface extends GLSurfaceView
{
	//I'd rather this being a AeonActivity, but the SDK warns about expecting a Context type.
    public AeonGLSurface(Context activity)
    {
        super(activity);
        
        m_activity = (AeonActivity) activity;
        
        // Create an OpenGL ES 2.0 context
        Logger.Info("Creating EGL 2 context...");
        setEGLContextClientVersion(2);
        
        // Set the Renderer for drawing on the GLSurfaceView
        Logger.Info("Starting renderer...");
        m_renderer = new AeonRenderer(m_activity);
        setRenderer(m_renderer);
    }
    
    private AeonActivity m_activity;
    private AeonRenderer m_renderer;
}
