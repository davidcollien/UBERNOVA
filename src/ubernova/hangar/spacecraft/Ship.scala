package ubernova.hangar.spacecraft

import ubernova.universe.geometry._
import ubernova.hangar.parts._

import scala.collection.mutable.HashMap
import scala.collection.mutable.HashSet
import scala.collection.mutable.ListBuffer

abstract class Ship( pos:Vector2D, facing:Double, shipGeometry:ShipGeometry ) {
	
	var position:Vector2D = pos;
	var facingDirection:Double = facing;
	
	var geometry:ShipGeometry = shipGeometry;
	var thrusters:HashSet[Thruster] = new HashSet;
	
	var maxSpeed:Double = 20;
	var maxAngularVelocity:Double = 0.1;
	
	var velocity:Vector2D = new Vector2D( 0, 0 );
	var netForce:Vector2D = new Vector2D( 0, 0 );
	
	var angularVelocity:Double = 0;
	var netTorque:Double = 0;
	
	var momentOfInertia:Double = calculateMomentOfInertia( );
	var mass:Double = calculateMass( );
	
	var controlState:HashSet[Int] = new HashSet( );
	
	var thrusterBindings:HashMap[Int, ListBuffer[Thruster]]  = new HashMap;
	
	def calculateMass( ):Double = {
		1.0;
	}
	
	def calculateMomentOfInertia( ):Double = {
		500;
	}
	
	
	def performAction( action:Int ) = {
		if ( this.thrusterBindings.contains( action ) ) {
			val thrusterList = this.thrusterBindings.get( action ).get;
			thrusterList.foreach( thruster => activateThruster( thruster ) );
		}
	}


  def activateThruster( thruster:Thruster ) = {
    thruster.setActivated( true );
    
    val thrustVect = thruster.getThrustVector() rotatedBy this.facingDirection;
		val offsetVect = thruster.getOffsetVector() rotatedBy this.facingDirection;

    val torque = thrustVect.magnitude * offsetVect.magnitude * math.sin( thrustVect.angleRelative( offsetVect ) );

    this.netForce  += thrustVect;
    this.netTorque += torque;
  }

  /*
	def activateThruster( thruster:Thruster ) = {
		thruster.setActivated( true );
		
		// force vector of a thruster
		val thrustVect = thruster.getThrustVector() rotatedBy this.facingDirection;	
		
		// a positional offset from the point of reference of the ship's centre of mass
		val offsetVect = thruster.getOffsetVector() rotatedBy this.facingDirection;
		
		val offsetDirection = -(offsetVect.normalised)
		
		// radial force
		val propulsionForce = offsetDirection * (thrustVect dot offsetVect);
		
		this.netForce += propulsionForce;
		
		// force tangential to the thruster's radius around the centre of mass
		val tangentialForce = thrustVect - propulsionForce;
			
		// torque is cross product of tangentialForce and -offsetVect,
		// but we're in 2 dimensions so:
		val torque = tangentialForce.magnitude * -(offsetVect.magnitude) * Math.sin( tangentialForce.angleRelative( -offsetVect ) );
		
		this.netTorque += torque;

	}
	*/

	/**
	 * Updates a ship's position from its velocity
	 */
	def update( ) = {

		this.netForce = new Vector2D( 0, 0 );
		this.netTorque = 0;
		
		for ( action:Int <- this.controlState ) {
			this.performAction( action );
		}
		
		val acceleration = this.netForce / this.mass;
		val angularAcceleration = this.netTorque / this.momentOfInertia;
		
		this.velocity += acceleration;
		this.angularVelocity += angularAcceleration;

		// limit speed
		if ( this.velocity.magnitude() > this.maxSpeed ) {
			this.velocity.normalise( this.maxSpeed );
		}
		
		// limit angular velocity
		if ( this.angularVelocity > this.maxAngularVelocity ) {
			this.angularVelocity = this.maxAngularVelocity;
		}

		this.position += this.velocity;
		this.facingDirection += this.angularVelocity;
	}
	
	def bindKey( key:Int, thruster:Thruster ) = {
		var bindings = this.thrusterBindings.get( key ).getOrElse( new ListBuffer[Thruster] );
		bindings.append( thruster );

		this.thrusterBindings.put( key, bindings );
		this.thrusters.add( thruster );
	}

	def setKeyDown( keyCode:Int ) = {
		controlState.add( keyCode );
	}
	
	def setKeyUp( keyCode:Int ) = {
		controlState.remove( keyCode );
	}
	
	
}