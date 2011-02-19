package ubernova.launchpad

import java.awt.Frame
import ubernova.hangar.spacecraft.demo.TestFighter
import ubernova.universe.geometry.Vector2D
import java.awt.event.{WindowEvent, WindowAdapter}
import ubernova.hangar.spacecraft.{ShipController, Ship}
import ubernova.universe.views.SpaceView


/**
 * Author: David Collien
 * Date: 19/02/11
 * Time: 6:31 PM
 *
 * All Rights Reserved. 
 * Provided "as is", without warranty of any kind.
 */

class GameWindow extends Frame( "UBERNOVA" ) {

  var playerShip:Ship     = new TestFighter( new Vector2D( 0, 0 ), 0 );

  var spaceView:SpaceView = new SpaceView( playerShip );
  spaceView.addShip( playerShip );

  var shipController:ShipController = new ShipController( playerShip );
  spaceView.addKeyListener( shipController );

  val gameThread:GameThread = new GameThread( this );
  gameThread.start( );

  this.add( spaceView );
  
  this.setSize( 800, 600 );
  this.addWindowListener( new WindowAdapter( ) {
    override def windowClosing( e:WindowEvent ) = {
      System.exit( 0 );
    }
  } );

  def update( ) = {
    spaceView.update( );
  }
  
}