package com.huigod.eshop.inventory.vo;

/**
 * @Author: huiGod
 * @Description: response model
 * @Date: 8:54 PM 30/10/2017
 */
public class Response {

  public static final String SUCCESS = "success";
  public static final String FAILURE = "failure";

  private String status;
  private String message;

  public Response() {
  }

  public Response(String status) {
    this.status = status;
  }

  public Response(String status, String message) {
    this.status = status;
    this.message = message;
  }

  public static String getSUCCESS() {
    return SUCCESS;
  }

  public static String getFAILURE() {
    return FAILURE;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
