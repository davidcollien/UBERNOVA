package ubernova.universe.backgrounds

/**
 * Author: David Collien
 * Date: 19/02/11
 * Time: 3:59 PM
 *
 * All Rights Reserved. 
 * Provided "as is", without warranty of any kind.
 */

import java.util.Random
import ubernova.universe.geometry.Vector2D
import java.awt.{Image, Color, Graphics}
import javax.imageio.ImageIO
import ubernova.universe.rendering.ImageOperations
import java.io.File

class Starfield( ) {
  private val maxStars = 200;
  private val minBrightness = 200;

  private val starImages:Array[Image] = new Array( 3 );

  starImages(0) = ImageIO.read( new File( "assets/s1.png" ) );
  starImages(1) = ImageIO.read( new File( "assets/s2.png" ) );
  starImages(2) = ImageIO.read( new File( "assets/s3.png" ) );

  private def hashForTile( x:Int, y:Int ) = {
    x*x*499 + y*487;
  }

  private def drawTile( g:Graphics, hash:Long, x:Double, y:Double, width:Int, height:Int ) = {
    val speedResolution = 5;
    val randomiser:Random = new Random( hash );

    for ( i:Int <- 0 to maxStars ) {

      val starX = randomiser.nextInt( width )  + x.toInt;
      val starY = randomiser.nextInt( height ) + y.toInt;

      val brightness = randomiser.nextInt( minBrightness );
      val colour     = randomiser.nextInt( 255-minBrightness );

      var red:Int  = 0;
      var blue:Int = 0;

      if ( randomiser.nextInt( 2 ) == 0 ) {
        red = colour;
      } else {
        blue = colour;
      }


      val size:Int = randomiser.nextInt( 3 ) + 1;
      drawStar( g, starX, starY, brightness+red, brightness, brightness+blue, size );
		}
  }

  private def drawStar( graphics:Graphics, x:Int, y:Int, r:Int, g:Int, b:Int, size:Int ) = {
    graphics.setColor( new Color( r, g, b ) );
    graphics.fillOval( x, y, size, size );
  }

  def render( g:Graphics, scroll:Vector2D, width:Int, height:Int ) = {
    
    val left   = scroll.x;
    val right  = scroll.x + width;
    val top    = scroll.y;
    val bottom = scroll.y + height;

    val tiles = List( (left, top), (right, top), (left, bottom), (right, bottom) );

    for ( tile <- tiles ) {
      val ( tx, ty ) = tile;

      val tileX = math.floor(tx/width);
      val tileY = math.floor(ty/height);
      
      val hash    = hashForTile( tileX.toInt, tileY.toInt );
      val screenX = tileX * width  - scroll.x;
      val screenY = tileY * height - scroll.y;

      drawTile( g, hash, screenX, screenY, width, height );
    }

  }

}