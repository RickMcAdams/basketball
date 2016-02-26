/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rick.fbb.model;

/**
 *
 * @author User
 */
public class Team {

  private String id;
  private String name;
  private String region;
  private String seed;

  /**
   * @return the id
   */
  public String getId() {
	return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(String id) {
	this.id = id;
  }

  /**
   * @return the name
   */
  public String getName() {
	return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
	this.name = name;
  }

  /**
   * @return the region
   */
  public String getRegion() {
	return region;
  }

  /**
   * @param region the region to set
   */
  public void setRegion(String region) {
	this.region = region;
  }

  /**
   * @return the seed
   */
  public String getSeed() {
	return seed;
  }

  /**
   * @param seed the seed to set
   */
  public void setSeed(String seed) {
	this.seed = seed;
  }
}
