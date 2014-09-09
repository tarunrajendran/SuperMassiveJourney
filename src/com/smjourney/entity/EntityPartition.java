package com.smjourney.entity;

import com.smjourney.map.Cell;
import com.smjourney.smjourney.Main;

/**
 * A part of a whole Entity.
 * This entity partition contains the data for which whole Entity it refers to and the individual cell that the entity partition is situated on.
 */
public class EntityPartition 
{
	
	private Entity entityReference;
	private Cell cell;
	
	/**
	 * Constructs an EntityPartition with a reference to which Cell it is situated on and a reference to the whole Entity.
	 * @param entityReference the whole Entity
	 * @param cell the Cell in which the EntityPartition is situated
	 */
	public EntityPartition(Entity entityReference, Cell cell)
	{
		this.entityReference = entityReference;
		this.cell = cell;
		Main.debugStream.println(this.toString() + " created.");
	}
	
	/**
	 * Retrieves the Cell in which the EntityPartition is situated.
	 * @return cell the Cell in which the EntityPartition is situated.
	 */
	public Cell getCell()
	{
		return cell;
	}
	
	/**
	 * Retrieves the Entity which the EntityPartition refers to
	 * @return entityReference the Entity to which the EntityPartition refers to
	 */
	public Entity getEntityReference()
	{
		return entityReference;
	}
	
	/**
	 * Returns string-form information on the object.
	 */
	public String toString()
	{
		String s = "";
		
		s += "EntityPartition[";
		s += getCell().toString() + ", ";
		s += getEntityReference().toString() + "]";
		
		return s;
	}

}
