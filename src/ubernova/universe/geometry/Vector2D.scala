package ubernova.universe.geometry

/**
 * Author: David Collien
 * Date: 19/02/11
 * Time: 3:59 PM
 *
 * All Rights Reserved.
 * Provided "as is", without warranty of any kind.
 */

class Vector2D ( ix:Double, iy:Double ) {
	var x:Double = ix;
	var y:Double = iy;
	
	def :=( newVectValue:Vector2D ):Vector2D = {
		this.x = newVectValue.x;
		this.y = newVectValue.y;
		
		this;
	}
	
	def :=( x:Double, y:Double ):Vector2D = {
		this.x = x;
		this.y = y;
		
		this;
	}
	
	override def clone( ):Vector2D = {
		new Vector2D( this.x, this.y );
	}
	
	// Rotation
	def rotateBy( theta:Double ):Vector2D = {
		val cosT = Math.cos( theta );
		val sinT = Math.sin( theta );
		
		val newX = this.x * cosT - this.y * sinT;
		val newY = this.x * sinT + this.y * cosT;
		
		this.x = newX;
		this.y = newY;
		
		this;
	}
	
	def rotatedBy( theta:Double ):Vector2D = {
		this.clone rotateBy theta;
	}
	
	// Rotation about an axis point
	def axisRotatedBy( axis:Vector2D, theta:Double ):Vector2D = {
		axis + ((this - axis) rotatedBy theta);
	}
	
	def axisRotateBy( axis:Vector2D, theta:Double ) = {
		this := this.axisRotatedBy( axis, theta );
		this;
	}
	
	// Angle between
	def angleBetween( other:Vector2D ):Double = {
		Math.acos( this.normalised( ) dot other.normalised( ) );
	}
	
	def angleOf( ):Double = {
		Math.atan2( this.y, this.x );
	}
	
	// Angle of other relative to this
	def angleRelative( other:Vector2D ):Double = {
		other.angleOf( ) - this.angleOf( );
	}
	
	def +=( other:Vector2D ):Vector2D = {
		this.x += other.x;
		this.y += other.y;
		
		this;
	}
	
	def +=( x:Double, y:Double ):Vector2D = {
		this.x += x;
		this.y += y;
		
		this;
	}
	
	def +( other:Vector2D ):Vector2D = {
		this.clone += other;
	}
	
	def +( x:Double, y:Double ):Vector2D = {
		this.clone += ( x, y );
	}
	
	def -=( other:Vector2D ):Vector2D = {
		this.x -= other.x;
		this.y -= other.y;
		
		this;
	}
	
	def -=( x:Double, y:Double ):Vector2D = {
		this.x -= x;
		this.y -= y;
		
		this;
	}
	
	def -( other:Vector2D ):Vector2D = {
		this.clone -= other;
	}
	
	def -( x:Double, y:Double ):Vector2D = {
		this.clone -= ( x, y );
	}
	
	def unary_-( ):Vector2D = {
		new Vector2D( -this.x, -this.y );
	}
	
	def *=( scalar:Double ):Vector2D = {
		this.x *= scalar;
		this.y *= scalar;
		
		this;
	}
	
	def *( scalar:Double ):Vector2D = {
		this.clone *= scalar;
	}
	
	
	def /=( scalar:Double ):Vector2D = {
		this.x /= scalar;
		this.y /= scalar;
		
		this;
	}
	
	def /( scalar:Double ):Vector2D = {
		this.clone /= scalar;
	}
	
	def dot( other:Vector2D ):Double = {
		(this.x * other.x) + (this.y * other.y);
	}
	
	def magnitude( ):Double = {
		Math.sqrt( this.x * this.x + this.y * this.y );
	}
	
	// Normalisation
	
	def normalise( size:Double ):Vector2D = {
		val mag = this.magnitude;
		this.x = (this.x / mag) * size;
		this.y = (this.y / mag) * size;
		
		this;
	}
	
	def normalize( size:Double ):Vector2D = {
		normalise( size );
	}
	
	def normalise( ):Vector2D = {
		normalise( 1 );
	}
	
	def normalize( ):Vector2D = {
		normalize( 1 );
	}
	
	def normalised( size:Double ):Vector2D = {
		this.clone.normalise( size );
	}
	
	def normalized( size:Double ):Vector2D = {
		this.clone.normalize( size );
	}
	
	def normalised( ):Vector2D = {
		this.clone.normalise( );
	}
	
	def normalized( ):Vector2D = {
		this.clone.normalize( );
	}
	
	
}