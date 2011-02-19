package ubernova.universe.geometry

import java.awt.Image;

class ShipGeometry( shipImage:Image ) {
	var image:Image = shipImage;

  def getCentreOfMass( ) = {
    new Vector2D( shipImage.getWidth( null ) / 2, shipImage.getHeight( null ) / 2 );
  }
}