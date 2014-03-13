package aeon.engine;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class AeonGLSurface extends GLSurfaceView
{
    public AeonGLSurface(Context context)
    {
        super(context);
        
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(new AeonRenderer());
    }
}
