package com.kituri.app.model;

import java.util.HashSet;
import java.util.Set;

import android.util.Log;

public class  Logger{

	  private static boolean logging = false;

	  private static Logger INSTANCE = new Logger();
	  
	  private Set<SPLoggerListener> listeners = new HashSet<SPLoggerListener>();
	  
	  static boolean toggleLogging()
	  {
	    logging = !logging;
	    return logging;
	  }

	  public static boolean isLogging() {
	    return logging;
	  }

	  public static boolean enableLogging(boolean shouldLog) {
	    logging = shouldLog;
	    return logging;
	  }

	  public static void e(String message) {
		    if (logging) {
		      Log.e("", message);
		      INSTANCE.log(Level.ERROR, "", message, null);
		    }
		  }
	  
	  public static void e(String tag, String message) {
	    if (logging) {
	      Log.e(tag, message);
	      INSTANCE.log(Level.ERROR, tag, message, null);
	    }
	  }

	  public static void e(String tag, String message, Exception exception) {
	    if (logging) {
	      Log.w(tag, message, exception);
	      INSTANCE.log(Level.ERROR, tag, message, exception);
	    }
	  }

	  public static void d(String message) {
		    if (logging) {
		      Log.d("", message);
		      INSTANCE.log(Level.DEBUG, "", message, null);
		    }
		  }
	  
	  public static void d(String tag, String message) {
	    if (logging) {
	      Log.d(tag, message);
	      INSTANCE.log(Level.DEBUG, tag, message, null);
	    }
	  }

	  public static void i(String tag, String message) {
	    if (logging) {
	      Log.i(tag, message);
	      INSTANCE.log(Level.INFO, tag, message, null);
	    }
	  }
	  
	  public static void i(String message) {
		    if (logging) {
		      Log.i("", message);
		      INSTANCE.log(Level.INFO, "", message, null);
		    }
		  }

	  public static void v(String tag, String message) {
	    if (logging) {
	      Log.v(tag, message);
	      INSTANCE.log(Level.VERBOSE, tag, message, null);
	    }
	  }

	  public static void w(String tag, String message) {
	    if (logging) {
	      Log.w(tag, message);
	      INSTANCE.log(Level.WARNING, tag, message, null);
	    }
	  }

	  public static void w(String tag, String message, Exception exception) {
	    if (logging) {
	      Log.w(tag, message, exception);
	      INSTANCE.log(Level.WARNING, tag, message, exception);
	    }
	  }

	  public void log(final Level level, final String tag, final String message, final Exception exception)
	  {
	    if (!this.listeners.isEmpty())
	    	//level, tag, message, exception
	      new Thread(new Runnable()
	      {
	        public void run() {
	          for (SPLoggerListener listener : Logger.INSTANCE.listeners)
	            listener.log(level, tag, message, exception);
	        }
	      }).start();
	  }

	  public static boolean addLoggerListener(SPLoggerListener newListener)
	  {
	    return INSTANCE.listeners.add(newListener);
	  }

	  public static boolean removeLoggerListener(SPLoggerListener listener) {
	    return INSTANCE.listeners.remove(listener);
	  }

	  public static enum Level
	  {
	    VERBOSE, 
	    DEBUG, 
	    INFO, 
	    WARNING, 
	    ERROR;
	  }
	  
	  public abstract interface SPLoggerListener
	  {
	    public abstract void log(Logger.Level paramLevel, String paramString1, String paramString2, Exception paramException);
	  }
	  
	}
