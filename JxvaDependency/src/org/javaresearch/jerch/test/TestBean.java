/*
 * Created on 2005-3-16
 *
 */
package org.javaresearch.jerch.test;



import java.sql.Timestamp;
import java.util.Date;

import org.javaresearch.jerch.Mappable;
import org.javaresearch.jerch.NameMatchMappable;

/**
 * @author liumin
 */
public class TestBean implements Mappable {
  Long id;

  String name;

  Timestamp createtime;

  Date testdate;

  Integer size;

  Integer count;
  static Mappable map=new NameMatchMappable();

  public String toString() {
    return "TestBean:" + id + "," + name + "," + createtime + "," + testdate
        + "," + size + "," + count;
  }

  /**
   * @return Returns the createtime.
   */
  public Timestamp getCreatetime() {
    return createtime;
  }

  /**
   * @param createtime The createtime to set.
   */
  public void setCreatetime(Timestamp createtime) {
    this.createtime = createtime;
  }

  /**
   * @return Returns the id.
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id The id to set.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return Returns the name.
   */
  public String getName() {
    return name;
  }

  /**
   * @param name The name to set.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return Returns the size.
   */
  public Integer getSize() {
    return size;
  }

  /**
   * @param size The size to set.
   */
  public void setSize(Integer size) {
    this.size = size;
  }

  /**
   * @return Returns the testdate.
   */
  public Date getTestdate() {
    return testdate;
  }

  /**
   * @param testdate The testdate to set.
   */
  public void setTestdate(Date testdate) {
    this.testdate = testdate;
  }

  /**
   * @return Returns the count.
   */
  public Integer getCount() {
    return count;
  }

  /**
   * @param count The count to set.
   */
  public void setCount(Integer count) {
    this.count = count;
  }

  public String getMapMethod(String fieldName) {
    return map.getMapMethod(fieldName);
  }

  public Class getMethodParameterType(String fieldName,int dbType) {
    return map.getMethodParameterType(fieldName,dbType);
  }

}