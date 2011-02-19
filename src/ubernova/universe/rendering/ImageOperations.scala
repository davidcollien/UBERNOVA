package ubernova.universe.rendering

import ubernova.universe.geometry.Vector2D
import java.awt.geom.{AffineTransform, Path2D}
import java.awt.{Graphics, Image, Graphics2D, Color}

/**
 * Author: David Collien
 * Date: 19/02/11
 * Time: 4:47 PM
 *
 * All Rights Reserved. 
 * Provided "as is", without warranty of any kind.
 */

object ImageOperations {
  def drawThruster( g:Graphics2D, vector:Vector2D, x:Int, y:Int, multiplier:Int ) = {
    val width:Float = 5;
    val arrowLength = (vector.magnitude() * multiplier);

    val drawVect:Vector2D = vector.normalised( arrowLength );
    val endVect:Vector2D  = (vector rotatedBy (math.Pi/2.0)).normalise( width/2 );

    val endX:Int = (drawVect.x + x).asInstanceOf[Int];
    val endY:Int = (drawVect.y + y).asInstanceOf[Int];

    val path:Path2D.Float = new Path2D.Float();
    path.moveTo( x, y );
    path.lineTo( endX + endVect.x, endY + endVect.y );
    path.lineTo( endX - endVect.x, endY - endVect.y );
    path.lineTo( x, y );

    g.setColor( Color.white );
    g.fill( path );
    g.draw( path );
  }

  def paintRotated( g:Graphics, image:Image, rotation:Double, x:Int, y:Int, imageOffsetX:Int, imageOffsetY:Int ) {
		val g2d:Graphics2D = g.asInstanceOf[Graphics2D];

		// save state
		val beforeXForm:AffineTransform  = g2d.getTransform( );

		// rotate
		val rotatedXForm:AffineTransform = beforeXForm.clone( ).asInstanceOf[AffineTransform];

		// centre of rotation
		val centreX:Int = x;
		val centreY:Int = y;
		rotatedXForm.rotate( rotation, centreX, centreY );
		g2d.setTransform( rotatedXForm );

		val imageX:Int = x - imageOffsetX;
		val imageY:Int = y - imageOffsetY;

		// draw
		g2d.drawImage( image, imageX, imageY, null );

		// restore state
		g2d.setTransform( beforeXForm );
	}
}