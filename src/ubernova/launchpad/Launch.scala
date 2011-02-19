package ubernova.launchpad

/**
 * Author: David Collien
 * Date: 19/02/11
 * Time: 4:57 PM
 *
 * All Rights Reserved. 
 * Provided "as is", without warranty of any kind.
 */

object Launch {
  def main( args:Array[String] ) {
    println( "Hello World\n" );

    val gameWindow:GameWindow = new GameWindow( );
    gameWindow.setVisible( true );
  }
}