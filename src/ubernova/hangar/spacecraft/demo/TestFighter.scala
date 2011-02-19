package ubernova.hangar.spacecraft.demo

import java.io.File
import javax.imageio.ImageIO
import java.awt.event.KeyEvent;

import ubernova.hangar.parts._
import ubernova.hangar.spacecraft.Ship
import ubernova.universe.geometry._

class TestFighter( pos:Vector2D, facing:Double ) extends Ship( pos, facing, null ) {
	this.geometry = createGeometry( );
	
	def createGeometry( ):ShipGeometry = {
		return new ShipGeometry( ImageIO.read( new File( "assets/xfighter.png" ) ) );
	}
	

	val leftWingThruster = new Thruster( ) {
		private var isActivated:Boolean = false;
		
		
		def getActivated( ):Boolean = {
			isActivated;
		}
		
		def setActivated( activated:Boolean ) = {
			this.isActivated = activated;
		}
		
		def getThrustVector( ):Vector2D = {
			new Vector2D( 0, 0.002 );
		}

		def getOffsetVector( ):Vector2D = {
			new Vector2D( -50, 43 );
		}

		def getMass( ):Double = {
			1.0;
		}
	};
	
	val rightWingThruster = new Thruster( ) {
		private var isActivated:Boolean = false;
		
		
		def getActivated( ):Boolean = {
			isActivated;
		}
		
		def setActivated( activated:Boolean ) = {
			this.isActivated = activated;
		}
		
		def getThrustVector( ):Vector2D = {
			new Vector2D( 0, 0.002 );
		}

		def getOffsetVector( ):Vector2D = {
			new Vector2D( 50, 43 );
		}

		def getMass( ):Double = {
			1.0;
		}
	};
	
	val revThruster = new Thruster( ) {
		private var isActivated:Boolean = false;
		
		
		def getActivated( ):Boolean = {
			isActivated;
		}
		
		def setActivated( activated:Boolean ) = {
			this.isActivated = activated;
		}
		
		def getThrustVector( ):Vector2D = {
			new Vector2D( 0, -0.005 );
		}

		def getOffsetVector( ):Vector2D = {
			new Vector2D( 0, -56 );
		}

		def getMass( ):Double = {
			1.0;
		}
	};
	
	val leftThruster = new Thruster( ) {
		private var isActivated:Boolean = false;
		
		
		def getActivated( ):Boolean = {
			isActivated;
		}
		
		def setActivated( activated:Boolean ) = {
			this.isActivated = activated;
		}
		
		def getThrustVector( ):Vector2D = {
			new Vector2D( 0, 0.005 );
		}

		def getOffsetVector( ):Vector2D = {
			new Vector2D( -9, 52 );
		}

		def getMass( ):Double = {
			1.0;
		}
	};
	
	val rightThruster = new Thruster( ) {
		private var isActivated:Boolean = false;
		
		
		def getActivated( ):Boolean = {
			isActivated;
		}
		
		def setActivated( activated:Boolean ) = {
			this.isActivated = activated;
		}
		
		def getThrustVector( ):Vector2D = {
			new Vector2D( 0, 0.005 );
		}

		def getOffsetVector( ):Vector2D = {
			new Vector2D( 9, 52 );
		}

		def getMass( ):Double = {
			1.0;
		}
	};
	
	
	this.bindKey( KeyEvent.VK_D, leftWingThruster );
	this.bindKey( KeyEvent.VK_A, rightWingThruster );
	this.bindKey( KeyEvent.VK_S, revThruster );
	this.bindKey( KeyEvent.VK_W, leftThruster );
	this.bindKey( KeyEvent.VK_W, rightThruster );
		
}