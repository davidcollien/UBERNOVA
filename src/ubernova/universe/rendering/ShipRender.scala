package ubernova.universe.rendering

import ubernova.hangar.spacecraft.Ship
import ubernova.universe.geometry.{Vector2D, ShipGeometry}
import java.awt.{AlphaComposite, Graphics2D, Color}
import rendering.MotionBlurOp
import java.awt.image.{Raster, BufferedImage}

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
  var rotatedImg:BufferedImage = null;
  var blurImg:BufferedImage = null;
  var thrusterImg:BufferedImage = null;

  var cWidth:Int  = 0;
  var cHeight:Int = 0;

  refresh( );

  
  def refresh( ) = {
    rotatedImg = new BufferedImage( ship.geometry.image.getWidth( null )*2, ship.geometry.image.getHeight( null )*2, BufferedImage.TYPE_INT_ARGB );
    cWidth = rotatedImg.getWidth;
    cHeight = rotatedImg.getHeight;

    blurImg = new BufferedImage( cWidth, cHeight, BufferedImage.TYPE_INT_ARGB );
    thrusterImg = new BufferedImage( cWidth, cHeight, BufferedImage.TYPE_INT_ARGB );

  }
  
  def update( ) = {
    this.ship.update( );
  }

  def render( g:Graphics2D, offsetX:Double, offsetY:Double ) = {
    
    val shipGeometry:ShipGeometry = shipToRender.geometry;
    val centreOfMass = shipGeometry.getCentreOfMass( );




    val speedRatio = ship.velocity.magnitude / ship.maxSpeed;

    val thrusterG = thrusterImg.getGraphics;
    thrusterG.clearRect( 0, 0, cWidth, cHeight );
    
		for ( thruster <- this.ship.thrusters ) {
			if ( thruster.getActivated( ) ) {

				thrusterG.setColor( Color.WHITE );
				val thrusterPos:Vector2D = (thruster.getOffsetVector rotatedBy ship.facingDirection);

				ImageOperations.drawThruster(
          thrusterG.asInstanceOf[Graphics2D],
          thruster.getThrustVector( ) rotatedBy ship.facingDirection,
          (cWidth/2 + thrusterPos.x).toInt,
          (cHeight/2 + thrusterPos.y).toInt,
          500 );

				thruster.setActivated( false );
			}
		}

    val thrusterBlur:MotionBlurOp = new MotionBlurOp( 0, 0, 0, 0.2.toFloat );
    thrusterBlur.filter( thrusterImg, thrusterImg );


    rotatedImg.getGraphics.drawImage( thrusterImg,
      0,
      0,
      null );
      

    
    ImageOperations.paintRotated( rotatedImg.getGraphics,
      shipGeometry.image,
      ship.facingDirection,
      cWidth/2,
      cHeight/2,
      centreOfMass.x.toInt,
      centreOfMass.y.toInt );

    val motionBlurOp:MotionBlurOp = new MotionBlurOp( 10, -ship.velocity.angleOf.toFloat, 0, 0 );
    motionBlurOp.filter( rotatedImg, blurImg );

    val composite:AlphaComposite = AlphaComposite.getInstance( AlphaComposite.SRC_OVER, speedRatio.toFloat );

    val prevComposite = g.getComposite( );

    val imgX = ship.position.x + offsetX - cWidth/2;
    val imgY = ship.position.y + offsetY - cHeight/2;

    g.drawImage( rotatedImg, imgX.toInt, imgY.toInt, null );
    g.setComposite( composite );
    g.drawImage( blurImg, imgX.toInt, imgY.toInt, null );
    
    g.setComposite( prevComposite );

  }
}