package ubernova.hangar.spacecraft

import java.awt.event.{KeyEvent, KeyListener}

/**
 * Author: David Collien
 * Date: 19/02/11
 * Time: 6:42 PM
 *
 * All Rights Reserved. 
 * Provided "as is", without warranty of any kind.
 */

class ShipController( controlledShip:Ship ) extends KeyListener {
  var ship:Ship = controlledShip;

  def keyReleased( e:KeyEvent ) = {
    ship.setKeyUp( e.getKeyCode );
  }

  def keyPressed( e:KeyEvent ) = {
    ship.setKeyDown( e.getKeyCode );
  }

  def keyTyped( p1:KeyEvent ) = {
    // nothing
  }
}