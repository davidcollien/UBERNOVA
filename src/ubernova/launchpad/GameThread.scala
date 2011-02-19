package ubernova.launchpad

/**
 * Author: David Collien
 * Date: 19/02/11
 * Time: 7:05 PM
 *
 * All Rights Reserved. 
 * Provided "as is", without warranty of any kind.
 */

class GameThread( window:GameWindow ) extends Thread {
  var gameWindow:GameWindow = window;
  var isRunning:Boolean = false;


  override def run( ) = {
    isRunning = true;

    try {
      while ( isRunning ) {
        this.window.update( );
        Thread.sleep( 30 );
      }
    } catch {
      case e:InterruptedException => isRunning = false;
    }

    isRunning = false;
  }
}