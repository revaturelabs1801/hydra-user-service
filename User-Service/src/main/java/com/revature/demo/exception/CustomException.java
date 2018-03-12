package com.revature.demo.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.demo.logging.ServiceAndControllerLogger;

public class CustomException extends Exception {

  private static final long serialVersionUID = 8632434538067813982L;
  private static final Logger logger = LogManager.getLogger(ServiceAndControllerLogger.class);

  public CustomException(Throwable message) {
    super(message);
  }

//  public CustomException(MessagingException e) {
//    logger.error(e);
//  }

  public CustomException(String string) {
    super(string);
  }

}