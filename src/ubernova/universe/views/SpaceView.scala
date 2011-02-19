package ubernova.universe.views

import collection.mutable.HashSet
import ubernova.universe.rendering.ShipRender
import ubernova.universe.geometry.Vector2D
import ubernova.hangar.spacecraft.Ship
import java.awt._
import ubernova.universe.backgrounds.Starfield

/**
 * Author: David Collien
 * Date: 19/02/11
 * Time: 6:45 PM
 *
 * All Rights Reserved. 
 * Provided "as is", without warranty of any kind.
 */

class SpaceView( playersShip:Ship ) extends Canvas {
  private var offscreen:Image = null;
  private var playerShip:Ship = playersShip;
  private var scroll:Vector2D = new Vector2D( 0, 0 );

  private var ships:HashSet[ShipRender] = new HashSet;
  private var dimension:Dimension = new Dimension( 0, 0 );
  private var stars:Starfield = new Starfield;

  def setPlayerShip( ship:Ship ) = {
    this.playerShip = ship;
  }

  def addShip( ship:Ship ) = {
    ships.add( new ShipRender( ship ) );
  }

  override def paint( g:Graphics ) = {
    if ( dimension.width != this.getWidth && dimension.height != this.getHeight ) {
      offscreen = createImage( this.getWidth, this.getHeight );
      dimension.setSize( this.getWidth, this.getHeight );
    }

    val offG:Graphics = offscreen.getGraphics;

    offG.clearRect( 0, 0, dimension.width, dimension.height );
    offG.setColor( new Color( 0, 0, 0 ) );
    offG.fillRect( 0, 0, dimension.width, dimension.height );

    stars.render( offG, scroll.x.asInstanceOf[Int], scroll.y.asInstanceOf[Int], dimension.width, dimension.height );

    val midX = dimension.width/2;
    val midY = dimension.height/2;

    for ( ship <- ships ) {
      ship.render(
        offG.asInstanceOf[Graphics2D],
        (scroll.x + midX).asInstanceOf[Int],
        (scroll.y + midY).asInstanceOf[Int] );
    }

    g.drawImage( offscreen, 0, 0, this );
  }

  def update( ) = {
    ships.foreach( ship => ship.update );

    scroll = -playerShip.position;

    repaint( );
  }

  
  
}