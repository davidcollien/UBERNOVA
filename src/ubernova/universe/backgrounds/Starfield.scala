package ubernova.universe.backgrounds

/**
 * Author: David Collien
 * Date: 19/02/11
 * Time: 3:59 PM
 *
 * All Rights Reserved. 
 * Provided "as is", without warranty of any kind.
 */

import java.awt.Color
import java.awt.Graphics
import java.util.Random

import scala.math

class Starfield( ) {
  private val maxStars = 200;
  private val minBrightness = 200;

  private def hashForTile( x:Int, y:Int ) = {
    x*x*499 + y*487;
  }

  private def drawTile( g:Graphics, hash:Long, x:Int, y:Int, width:Int, height:Int ) = {
    val randomiser:Random = new Random( hash );

    for ( i:Int <- 0 to maxStars ) {
      val starX = randomiser.nextInt( width );
      val starY = randomiser.nextInt( height );

      drawStar( g, starX, starY, randomiser );
    }
  }

  private def drawStar( g:Graphics, x:Int, y:Int, randomiser:Random ) = {
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

    g.setColor( new Color( brightness + red, brightness, brightness + blue ) );
    g.fillOval( x, y, size, size );
  }

  def render( g:Graphics, x:Int, y:Int, width:Int, height:Int ) = {
    val left   = x;
    val right  = x+width;
    val top    = y;
    val bottom = y+height;

    val tileX1:Int = math.floor( left/height ).asInstanceOf[Int];
    val tileY1:Int = math.floor( top/height ).asInstanceOf[Int];


    val tileX2:Int = math.floor( right/width ).asInstanceOf[Int];
		val tileY2:Int = math.floor( top/height ).asInstanceOf[Int];

		val tileX3:Int = math.floor( left/width ).asInstanceOf[Int];
		val tileY3:Int = math.floor( bottom/height ).asInstanceOf[Int];

		val tileX4:Int = math.floor( right/width ).asInstanceOf[Int];
		val tileY4:Int = math.floor( bottom/height ).asInstanceOf[Int];

		val screenX1:Int = tileX1 * width  - x;
		val screenY1:Int = tileY1 * height - y;

		val screenX2:Int = tileX2 * width  - x;
		val screenY2:Int = tileY2 * height - y;

		val screenX3:Int = tileX3 * width  - x;
		val screenY3:Int = tileY3 * height - y;

		val screenX4:Int = tileX4 * width  - x;
		val screenY4:Int = tileY4 * height - y;

    drawTile( g, hashForTile( tileX1, tileY1 ), screenX1, screenY1, width, height );
    drawTile( g, hashForTile( tileX2, tileY2 ), screenX2, screenY2, width, height );
    drawTile( g, hashForTile( tileX3, tileY3 ), screenX3, screenY3, width, height );
    drawTile( g, hashForTile( tileX4, tileY4 ), screenX4, screenY4, width, height );

  }

}