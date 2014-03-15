package aeon.examplegame;

import aeon.console.Logger;
import aeon.engine.AeonActivity;

public class ExampleGame extends AeonActivity
{
	@Override
	public void on_game_start()
	{
		Logger.Info("on_game_start()");
	}

	@Override
	public void on_update(float dt)
	{
		Logger.Info("on_update()");
	}

	@Override
	public void on_game_stop()
	{
		Logger.Info("on_game_stop()");
	}
}
