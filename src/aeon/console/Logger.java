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

package aeon.console;

import android.util.Log;

//Wrapper class around the android logger. In the future we may want to do
//more advanced logging.
public class Logger
{
	private static final String TAG = "AGE";
	
	public static void Info(String msg)
	{
		Log.i(TAG, "[INF] " + msg);
	}
	
	public static void Debug(String msg)
	{
		Log.d(TAG, "[DBG] " + msg);
	}
	
	public static void Warning(String msg)
	{
		Log.w(TAG, "[WRN] " + msg);
	}
	
	public static void Error(String msg)
	{
		Log.e(TAG, "[ERR] " + msg);
	}
}
