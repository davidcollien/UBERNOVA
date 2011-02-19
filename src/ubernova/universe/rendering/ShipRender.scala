package ubernova.universe.rendering

import ubernova.hangar.spacecraft.Ship
import ubernova.universe.geometry.{Vector2D, ShipGeometry}
import java.awt.{Graphics2D, Color}


/**
 * Author: David Collien
 * Date: 19/02/11
 * Time: 4:20 PM
 *
 * All Rights Reserved. 
 * Provided "as is", without warranty of any kind.
 */

class ShipRender( shipToRender:Ship ) {
  var ship:Ship = shipToRender;

  def update( ) = {
    this.ship.update( );
  }

  def render( g:Graphics2D, offsetX:Int, offsetY:Int ) = {
    
    val shipGeometry:ShipGeometry = shipToRender.geometry;
    val centreOfMass = shipGeometry.getCentreOfMass( );

    ImageOperations.paintRotated( g,
      shipGeometry.image,
      ship.facingDirection,
      ship.position.x.asInstanceOf[Int] + offsetX,
      ship.position.y.asInstanceOf[Int] + offsetY,
      centreOfMass.x.asInstanceOf[Int],
      centreOfMass.y.asInstanceOf[Int] );

		for ( thruster <- this.ship.thrusters ) {
			if ( thruster.getActivated( ) ) {

				g.setColor( Color.WHITE );
				val thrusterPos:Vector2D = (thruster.getOffsetVector rotatedBy ship.facingDirection) + ship.position;

				ImageOperations.drawThruster(
          g,
          thruster.getThrustVector( ) rotatedBy ship.facingDirection,
          thrusterPos.x.asInstanceOf[Int] + offsetX ,
          thrusterPos.y.asInstanceOf[Int] + offsetY,
          5000 );

				thruster.setActivated( false );
			}
		}
  }
}