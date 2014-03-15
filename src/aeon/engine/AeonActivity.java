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
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public abstract class AeonActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        Logger.Info("Aeon Android Game Engine initializing...");
        
        //Set the singleton
        m_singleton = this;

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        m_glsurfaceview = new AeonGLSurface(this);
        
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(m_glsurfaceview);
    }
    
    public AeonRenderer get_renderer()
    {
    	return m_renderer;
    }
    
    public void register_renderer(AeonRenderer renderer)
    {
    	m_renderer = renderer;
    }
    
    public static AeonActivity get_singleton()
    {
    	return m_singleton;
    }
    
    public abstract void on_game_start();
    public abstract void on_update(float dt);
    
    //TODO: implement on_game_stop
    public abstract void on_game_stop();
    
    private static AeonActivity 		m_singleton;
    
    private GLSurfaceView 				m_glsurfaceview;
    private AeonRenderer				m_renderer;
}
