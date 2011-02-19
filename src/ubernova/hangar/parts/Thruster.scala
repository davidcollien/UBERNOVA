package ubernova.hangar.parts

import ubernova.universe.geometry.Vector2D;

trait Thruster {
	def getThrustVector( ):Vector2D;
	def getOffsetVector( ):Vector2D;
	def getMass( ):Double;
	def setActivated( activated:Boolean );
	def getActivated( ):Boolean;
}